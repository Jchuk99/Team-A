package src.ctc;

import java.util.UUID;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CTCTrain {

    private float authority;
    private float suggestedSpeed; // IN METERS PER SECOND INTENRALLY
    private int trainID;
    private UUID destination;
    private UUID currPos;
    //private int errorStatus; //TODO: make this an enum.
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
            currPos = start;
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

        authority = (float) route.getCurrPath().getCourse().size();
    }

    public UUID getNextBlockID(){
        return route.getCurrPath().getNextBlockID(currPos);
    }
    
    public void setAuthority(float authority){
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
    public float getAuthority(){
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

    /*** FOR GUI ***/

    public StringProperty getTrainIDProperty() { return new SimpleStringProperty("Train " + trainID); }
    public StringProperty getCurrPosProperty() { return new SimpleStringProperty("Train " + trainID); }

        //return new SimpleStringProperty("" + CTCModule.map.getBlock(currPos).getBlockNumber()); }
    public StringProperty getDestProperty() { return new SimpleStringProperty("" + CTCModule.map.getBlock(destination).getBlockNumber()); }
    public StringProperty getSuggestedSpeedProperty() { return new SimpleStringProperty("" + (suggestedSpeed * 2.237)); }
}