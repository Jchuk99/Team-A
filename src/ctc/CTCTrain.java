package src.ctc;

import java.util.UUID;

public class CTCTrain {

    private int authority;
    private float suggestedSpeed; // IN METERS PER SECOND INTENRALLY
    private int trainID;
    private UUID destination;
    private UUID currPos;
    //private int errorStatus; //TODO: make this an enum.
    //private long timeOnBlock; // IN SECONDS INTERNALLY
    private Route route;

    public CTCTrain(){
        // All trains start in the yard.
        currPos = CTCModule.map.getYardID();
    }

    public void updateRoute(UUID dest){
        UUID start;
        if (route == null){
            route = new Route();
        }

        //If train does not have any queued paths then will be in yard.
        if (route.size() == 0){
            // block connected to yard depending on line
            start = CTCModule.map.getStartingBlockID();
            route.addPath(start, dest);
        }
        else{
            start = route.getLastPath().getEndBlock();
            route.addPath(start, dest);
            //Tif train is @ the end of it's path then update the current path.
            if (currPos == route.getCurrPath().getEndBlock()){
               route.updateCurrPath();
            } 
        }

        authority = route.getCurrPath().getCourse().size();
    }

    public UUID getNextBlockID(){
        return route.getCurrPath().getNextBlockID(currPos);
    }
    
    public void setAuthority(int authority){
        this.authority = authority;
    }
    public void setSuggestedSpeed(float suggestedSpeed){
        this.suggestedSpeed = suggestedSpeed;
    }
    public void setTrainID(int trainID){
        this.trainID = trainID;
    }
    public void setDestination(UUID destination){
        this.destination = destination;
    }
    public void setCurrPos(UUID currPos){
        this.currPos = currPos;
    }
    public Route getRoute(){
        return route;
    }
    public int getAuthority(){
        return authority;
    }
    public float getSuggestedSpeed(){
        return suggestedSpeed;
    }
    public int getTrainID(){
        return trainID;
    }
    public UUID getDestination(){
        return destination;
    }
    public UUID getCurrPos(){
        return currPos;
    }
}