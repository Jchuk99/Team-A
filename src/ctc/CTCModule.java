package src.ctc;

import src.Module;

public class CTCModule extends Module{
    public static CTCMap map = null;
    private Schedule schedule = null;

    public void main() {
        // use this module to get/set data from wayside every cycle which includes
        // track state (includes occupied blocks)
        // ticket sales for each line
        // train error infomartion
        
        // gives off list of CTC trains(Speed(M/s), Authority, currPosition of each train)
        // gives off switches
        // gives off occupied blocks
    }

    public CTCModule(){
        super();
        CTCUI.setCTCModule(this);
    }

    public void updateMap(){
        if (map == null){
            map = new CTCMap(trackControllerModule);
        }
        map.updateMap();
    }

    public void dispatch(String trainID, float suggestedSpeed, String destination){
        if (schedule == null){
           schedule = new Schedule();
        }

        updateMap();

        // need to give speed in meters per second, authority, train ID, and route 
        int destinationInt = Integer.parseInt(destination);
        suggestedSpeed = suggestedSpeed/(float)2.237; // METERS PER SECOND
        CTCTrain train = schedule.createTrain(trainID, suggestedSpeed, destinationInt);
        this.trackModule.createTrain(train.getSuggestedSpeed(), (float) train.getAuthority(), train.getRoute());

    }
}