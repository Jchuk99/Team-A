package src.ctc;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import src.Module;
import src.track_module.Block;
import src.track_module.BlockConstructor.Station;


public class CTCModule extends Module{
    public static CTCMap map = null;
    private Schedule schedule = new Schedule();
    

    public void update(){

        updateMap();
        // Need method to get occupied blocks from map.
        List<UUID> occupiedBlocks = map.getOccupiedBlocks();
        
        System.out.println("Occupied Blocks: ");
        for(UUID blockID : occupiedBlocks) {
            System.out.println(map.getBlock(blockID).getBlockNumber());
        }

        // Need method to get all trains.
        /*
        Set<CTCTrain> trains = getTrains();
        System.out.println("Trains: " + trains.size());
        if(trains.size() > 0){
            for (CTCTrain train: trains){
                // if the trains next block is null then do work
                if (map.getBlock(train.getNextBlockID()).getOccupied()) {
                    train.setCurrPos(train.getNextBlockID());
                    System.out.println("Train ID: " + train.getTrainID());
                    System.out.println("currPos : " + train.getCurrPos());
                }

            }
            
        }*/// If the list size is 0 don't do anything.

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
        if (map == null){
            map = new CTCMap(trackControllerModule, trackModule);
            map.initMap();
        }
        else{
            map.updateMap();
        }
    }

    public void dispatch(String trainID, float suggestedSpeed, UUID destination){
        updateMap();


        // need to give speed in meters per second, authority, train ID, and route 
        suggestedSpeed = suggestedSpeed/(float)2.237; // METERS PER SECOND
        CTCTrain train = schedule.dispatchTrain(trainID, suggestedSpeed, destination);
        //TODO: should probably name this method dispatchTrain
        this.trackModule.dispatchTrain(train);

    }

    public Set<CTCTrain> getTrains(){
        return schedule.getTrains();
    }
    public HashMap<Integer, CTCTrain> getTrainMap(){
        return schedule.getTrainMap();
    }

    // need a getTrain Set method.

    //public HashMap<UUID, Boolean> getSwitchStates{
    //}
    //public ArrayList<UUID> getClosedBlocks

    /****** for GUI ******/

    public ObservableList<CTCTrain> getObservableTrains(){
        ObservableList<CTCTrain> trainList = schedule.getObservableTrains();
        FXCollections.sort(trainList, new trainComparator());
        return trainList;
    }

    public ObservableList<Station> getObservableStationList(){
        updateMap();
        ObservableList<Station> stationList = FXCollections.observableList(map.getStationList());
        return stationList;
   }

    public ObservableList<Block> getObservableBlockList(){
         updateMap();
         ObservableList<Block> blockList = FXCollections.observableList(map.getBlockList());
         FXCollections.sort(blockList, new blockNumberComparator());
         return blockList;
    }
    
}