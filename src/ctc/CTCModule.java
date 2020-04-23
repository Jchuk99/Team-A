package src.ctc;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.UUID;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import src.Module;
import src.ctc.CTCBlockConstructor.CTCShift;
import src.ctc.CTCBlockConstructor.CTCStation;
import src.track_module.Block;
import src.track_module.Edge;

public class CTCModule extends Module{
    public static final int MAX_AUTHORITY = 4;
    public static CTCMap map = null;
    public StringProperty greenTickets = new SimpleStringProperty("");
    public StringProperty redTickets = new SimpleStringProperty("");
    private TrainTable trainTable = new TrainTable();
    private Schedule schedule = new Schedule(trainTable);
    

    public void update(){

        if (validMap()){

            if (!schedule.created){
                schedule.createSchedule();
            }

            map.updateMap();
            dispatchTrains();
            updateTrainPositions();
            updateTrains();
            updateTrainAuthorities();
            updateStationSales();
        }

        // use this module to get/set data from wayside every cycle which includes
        // track state (includes occupied blocks)
        // ticket sales for each line from track module
        // train error infomartion, from waysides.

        // gives off list of CTC trains(Suggested Speed(M/s), Authority, currPosition of each train)
        // gives off switches(Integer Boolean Hashmap)
        // gives off closed blocks
    }
    

    public void main() {
        schedule.readInSchedule();
    }

    public CTCModule(){
        super();
        CTCUI.setCTCModule(this);
    }

    // need an initMap method seperate.
    public void updateMap(){
            map.updateMap();
    }
    //
    public void initMap(){
        if (map == null){
            map = new CTCMap(trackControllerModule, trackModule);
            map.initMap();
        }
    }

    public void updateTrains(){
        List<CTCTrain> trains = getTrains();
        for (CTCTrain train: trains){
            train.update();
        }
    }

    public void updateTrainPositions(){
        List<UUID> occupiedBlocks = map.getOccupiedBlocks();
        List<UUID> closedBlocks = map.getClosedBlocks();
        List<CTCTrain> trains = getTrains();

        if(trains.size() > 0){
            for (CTCTrain train : trains){
                if (train.atDestination()){
                    if (train.inYard()){
                        trainTable.destroyTrain(train);
                        break;
                    }
                    if (train.isDwelling()){
                        if (train.isDoneDwelling(date.toLocalTime())){
                            train.getNextPath();
                            if (train.getRoute().size() == 0 ){
                                System.out.println("Train route is done.");
                                if (!train.inYard()){
                                    train.goToYard();
                                }
                            }
                            train.setDwelling(false);
                        }
                    }
                    else{
                        train.setDwellStart(date.toLocalTime());
                        train.setDwelling(true);
                    }
                }
               // not at it's destination
                else{
                    if (train.inYard() && train.isDispatched()){
                        trainTable.destroyTrain(train);
                        break;
                    }

                    if (train.onMap()){
                        Block currBlock = map.getBlock(train.getCurrPos());
                        for (Edge e: currBlock.getEdges()){
                            UUID edgeBlockID = e.getBlock().getUUID();
                            boolean isOccupied = occupiedBlocks.contains(edgeBlockID);
                            boolean isClosed = closedBlocks.contains(edgeBlockID);  
                            boolean prevBlock = edgeBlockID.equals(train.getPrevPos());

                            if(edgeBlockID.equals(map.getYard().getUUID()) && e.getConnected() && !occupiedBlocks.contains(currBlock.getUUID())){
                                train.setCurrPos(map.getYard().getUUID());
                            }
                            else{
                                if(!prevBlock && isOccupied && !isClosed){
                                    train.setCurrPos(edgeBlockID);
                                    if (!train.onPath()){
                                        train.updateCurrPath();
                                    }
                                }
                            }
                        }
                    }
                }
            } 
        }  
    }

    //
    public void updateTrainAuthorities(){
        //TODO: Error Check, discuss train coming out of yard.
        List<CTCTrain> trains = getTrainsOnMap();
        List<UUID> occupiedBlocks = map.getOccupiedBlocks();
        List<UUID> authorityBlocks = new ArrayList<UUID>();
        List<UUID> closedBlocks = map.getClosedBlocks();

        for (CTCTrain train: trains){
            UUID nextBlockID = train.getNextBlockID(train.getCurrPos());
            int authority = 0;
            while (nextBlockID != null  && authority < MAX_AUTHORITY){
                boolean isOccupied = occupiedBlocks.contains(nextBlockID);
                boolean isClosed = closedBlocks.contains(nextBlockID);
                boolean hasAuthority = authorityBlocks.contains(nextBlockID);
                if (!isOccupied && !isClosed && !hasAuthority){
                    ++authority;
                    authorityBlocks.add(nextBlockID);
                }
                nextBlockID = train.getNextBlockID(nextBlockID);
            }
            //System.out.println("Train ID: " + train.getTrainID());
            //System.out.println("Train authority: "+ authority);
            train.setAuthority(authority + 1);
        }
    }

    public void updateStationSales(){
        redTickets.setValue("" + map.getRedLineSales());
        greenTickets.setValue("" + map.getGreenLineSales());
    }

    
    public void dispatchTrains(){
        PriorityQueue<CTCTrain> dispatchQueue = trainTable.getDispatchQueue();
        CTCTrain trainToDispatch = dispatchQueue.peek();

        // if the queue isn't empty 
        if (trainToDispatch !=null){
            LocalTime dispatchTime = trainToDispatch.getDispatchTime();
            LocalTime currTime = date.toLocalTime();

            if (currTime.equals(dispatchTime) || currTime.isAfter(dispatchTime)){
                trainToDispatch.setCurrPos(trainToDispatch.startPos);
                Path currPath = trainToDispatch.getRoute().getCurrPath();

                if (currPath != null){
                    trainToDispatch.setDestination(currPath.getEndBlock());
                    if (currPath instanceof TimePath){
                        TimePath currTimePath = (TimePath) currPath;
                        trainToDispatch.setSuggestedSpeed(currTimePath.calcSuggestedSpeed());
                    }
                }

                trainToDispatch.setDispatched(true);
                this.trackModule.dispatchTrain(trainToDispatch);
                // take train out of dispatch Queue if dispatched.
                dispatchQueue.poll(); 
            }
        }

    }

    //TODO: rename, you're creating a train that's going into the dispatchQueue , which will be sent to dispatched on one of the updateCycles.
    // train will have a dispatchTime equal to current time, the hope is for it to dispatch instantly.
    public void dispatch(String trainIDString, float suggestedSpeed, UUID destination){

        // need to give speed in meters per second, authority, train ID, and route 
        suggestedSpeed = suggestedSpeed/(float)2.237; // CONVERSION TO METERS PER SECOND
        int trainID = Integer.parseInt(trainIDString.split(" ")[1]);

        trainTable.createTrain(trainID, date.toLocalTime());
        CTCTrain train = trainTable.getTrain(trainID);

        train.setSuggestedSpeed(suggestedSpeed);
        train.addPath(destination);

    }

    public List<CTCTrain> getTrains(){
        List<CTCTrain> trainList = trainTable.getTrains();
        Collections.sort(trainList, new trainComparator());
        return trainList;
    }

    public List<CTCTrain> getTrainsOnMap(){
        List<CTCTrain> trainsOnMap = trainTable.getTrainsOnMap();
        Collections.sort(trainsOnMap, new trainComparator());
        return trainsOnMap;
    }

    public List<Integer> getTrainIDs(){
        return trainTable.getTrainIDs();
    }
    public HashMap<Integer, CTCTrain> getTrainMap(){
        return trainTable.getTrainMap();
    }

    public List<CTCShift> getSwitchPositions(){
        if (validMap()){
            return map.getSwitchList();
        }
        else{
            return null;
        }
    }
    public List<UUID> getClosedBlocks() {
        if (validMap()){
            return map.getClosedBlocks();
        }
        else{
            return null;
        }
    }
    public List<UUID> getOccupiedBlocks() {
        if (validMap()){
            return map.getOccupiedBlocks();
        }
        else{
            return null;
        }
    }


    /****** for GUI ******/

    public ObservableList<CTCTrain> getObservableTrains(){
        ObservableList<CTCTrain> trainList = trainTable.getObservableTrains();
        FXCollections.sort(trainList, new trainComparator());
        return trainList;
    }

    public ObservableList<CTCStation> getObservableStationList(){
        ObservableList<CTCStation> stationList = FXCollections.observableList(map.getStationList());
        return stationList;
    }

    public ObservableList<Block> getObservableBlockList(){
         ObservableList<Block> blockList = FXCollections.observableList(map.getBlockList());
         FXCollections.sort(blockList, new blockNumberComparator());
         return blockList;
    }
    public ObservableList<Block> getObservableGreenBlocks(){
        ObservableList<Block> greenBlocks = FXCollections.observableList(map.getGreenBlocks());
        FXCollections.sort(greenBlocks, new blockNumberComparator());
        return greenBlocks;
    }
   public ObservableList<Block> getObservableRedBlocks(){
        ObservableList<Block> redBlocks = FXCollections.observableList(map.getRedBlocks());
        FXCollections.sort(redBlocks, new blockNumberComparator());
        return redBlocks;
    }

    public boolean validMap(){
        return map != null;
    }
    
}