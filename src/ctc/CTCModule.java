package src.ctc;

import java.util.List;
import java.util.Set;

import src.Module;

public class CTCModule extends Module{
    public static CTCMap map = null;
    private Schedule schedule = new Schedule();

    public void update(){

        updateMap();
        // Need method to get occupied blocks from map.
        List<Integer> occupiedBlocks = map.getOccupiedBlocks();
        
        System.out.println("Occupied Blocks: ");
        for(Integer blockID : occupiedBlocks) {
            System.out.println(blockID);
        }

        // Need method to get all trains.
        // List<CTCTrains> trains = schedule.getTrains(); // If the list size is 0 don't do anything.

        // LOGIC: check if the next block on the train's path is occupied. If it is there's two options:
        // 1. The train went to that next block. If so update it's position
        // 2. It's closed. If so, don't update it's position.
        // I need to talk with the group to see if this makes sense.



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

    public void updateMap(){
        if (map == null){
            map = new CTCMap(trackControllerModule);
            map.initMap();
        }
        else{
            map.updateMap();
        }
    }

    public void dispatch(String trainID, float suggestedSpeed, String destination){

        updateMap();

        // need to give speed in meters per second, authority, train ID, and route 
        int destinationInt = Integer.parseInt(destination);
        suggestedSpeed = suggestedSpeed/(float)2.237; // METERS PER SECOND
        CTCTrain train = schedule.dispatchTrain(trainID, suggestedSpeed, destinationInt);
        //TODO: should probably name this method dispatchTrain
        this.trackModule.createTrain(train.getSuggestedSpeed(), (float) train.getAuthority(), train.getRoute());

    }

    public Set<CTCTrain> getTrains(){
        return schedule.getTrains();
    }
}