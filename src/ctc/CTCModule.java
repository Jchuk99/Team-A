package src.ctc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import src.Module;
import src.track_module.Block;
import src.track_module.BlockConstructor.Shift;
import src.track_module.BlockConstructor.Station;


public class CTCModule extends Module{
    public static final int MAX_AUTHORITY = 3;
    public static CTCMap map = null;
    private Schedule schedule = new Schedule();
    

    public void update(){

        if (validMap()){
            map.updateMap();
            updateTrainPositions();
            updateTrains();
            updateTrainAuthorities();
        }

        // LOGIC: check if the next block on the train's path is occupied. If it is there's two options:
        // 1. The train went to that next block. If so update it's position
        // 2. It's closed. If so, don't update it's position.

        // use this module to get/set data from wayside every cycle which includes
        // track state (includes occupied blocks)
        // ticket sales for each line
        // train error infomartion, how the fuck am i supposed to assign this to each train?

        // gives off list of CTC trains(Suggested Speed(M/s), Authority, currPosition of each train)
        // gives off switches(Integer Boolean Hashmap)
        // gives off occupied blocks
    }
    

    public void main() {
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
        //TODO: should I use occupied blocks or should I feed the block into map?
        List<UUID> occupiedBlocks = map.getOccupiedBlocks();
        List<UUID> closedBlocks = map.getClosedBlocks();

        // Need method to get all trains.  
        List<CTCTrain> trains = getTrains();

        if(trains.size() > 0){
            for (CTCTrain train: trains){
                //TODO: change all the blockID's to blocks, just get the ID from the block.
                UUID nextBlock = train.getNextBlockID(train.getCurrPos());
                if (nextBlock != null){
                    boolean isOccupied = occupiedBlocks.contains(nextBlock);
                    boolean isClosed = closedBlocks.contains(nextBlock);

                    if (isOccupied && !isClosed) {
                        train.setCurrPos(nextBlock);
                       // System.out.println("Train ID: " + train.getTrainID());
                       // System.out.println("currPos : " + map.getBlock(train.getCurrPos()).getBlockNumber());
                    }
                }
                else{
                    // if the nextBlock is null then we should be @ our destination
                    //TODO: think of edge cases.
                    //TODO: only update the current Path after waiting 3 minutes at station.
                    train.getNextPath();
                    if (train.getRoute().size() == 0 ){
                        System.out.println("Train route is done.");
                        if (!train.inYard()){
                            train.goToYard();
                        }else{
                           // schedule.destroyTrain(train);
                        }
                    }
                }

            }
            
        }
    }

    public void updateTrainAuthorities(){
        //TODO: Error Check, discuss train coming out of yard.
        List<CTCTrain> trains = getTrains();
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
           // System.out.println("Train ID: " + train.getTrainID());
           // System.out.println("Train authority: "+ authority);
            train.setAuthority(authority);
        }
    }

    public void dispatch(String trainID, float suggestedSpeed, UUID destination){

        // need to give speed in meters per second, authority, train ID, and route 
        suggestedSpeed = suggestedSpeed/(float)2.237; // METERS PER SECOND
        CTCTrain train = schedule.dispatchTrain(trainID, suggestedSpeed, destination);
        this.trackModule.dispatchTrain(train);

    }

    public List<CTCTrain> getTrains(){
        List<CTCTrain> trainList = schedule.getTrains();
        Collections.sort(trainList, new trainComparator());
        return trainList;
    }
    public HashMap<Integer, CTCTrain> getTrainMap(){
        return schedule.getTrainMap();
    }

    public List<Shift> getSwitchPositions(){return map.getSwitchList();}
    public List<UUID> getClosedBlocks() {return map.getClosedBlocks();}

    /****** for GUI ******/

    public ObservableList<CTCTrain> getObservableTrains(){
        ObservableList<CTCTrain> trainList = schedule.getObservableTrains();
        FXCollections.sort(trainList, new trainComparator());
        return trainList;
    }

    public ObservableList<Station> getObservableStationList(){
        ObservableList<Station> stationList = FXCollections.observableList(map.getStationList());
        return stationList;
   }

    public ObservableList<Block> getObservableBlockList(){
         ObservableList<Block> blockList = FXCollections.observableList(map.getBlockList());
         FXCollections.sort(blockList, new blockNumberComparator());
         return blockList;
    }

    private boolean validMap(){
        return map != null;
    }
    
}