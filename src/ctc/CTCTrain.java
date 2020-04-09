package src.ctc;

import java.text.DecimalFormat;
import java.util.UUID;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import src.track_module.Block;

public class CTCTrain {

    private float authority;
    private float suggestedSpeed; // IN METERS PER SECOND INTENRALLY
    private int trainID;
    private UUID prevBlock = null;
    private UUID destination;
    private UUID currPos;
    //private int errorStatus; //TODO: make this an enum.
    private Route route = new Route();

    private StringProperty suggestedSpeedString = new SimpleStringProperty("");
    private StringProperty currPosString = new SimpleStringProperty("");
    private StringProperty authorityString = new SimpleStringProperty("");
    private StringProperty destinationString = new SimpleStringProperty("");
    private StringProperty trainIDString = new SimpleStringProperty("");


    public CTCTrain(){
        // All trains start in the yard.
        currPos = CTCModule.map.getYard().getUUID();
    }

    public void addPath(UUID dest){
        Block destination = CTCModule.map.getBlock(dest);
        UUID start;

        //If train does not have any queued paths then will be in yard.
        if (route.size() == 0){
            // block connected to yard depending on line
            start = CTCModule.map.getStartingBlockID(destination.getLine());
            currPos = start;
            route.addPath(start, dest, prevBlock);
            prevBlock = route.getLastPath().getBeforeEndBlock();
        }
        else{
            start = route.getLastPath().getEndBlock();
            route.addPath(start, dest, prevBlock);
            prevBlock = route.getLastPath().getBeforeEndBlock();
        }

        //TODO: calculate authorities the real wau.
        authority = (float) route.getCurrPath().getCourse().size();
    }
    public void update(){
        updateString();
    }

    public UUID getNextBlockID(){
        if (route.size() > 0){
            return route.getCurrPath().getNextBlockID(currPos);
        }
        else{
            return null;
        }
    }

    public UUID getNextBlockID(UUID nextBlockID){
        if (route.size() > 0){
            return route.getCurrPath().getNextBlockID(nextBlockID);
        }
        else{
            return null;
        }
    }


    public void updateCurrPath(){
        route.updateCurrPath();
        if (route.getCurrPath() != null){
            setDestination(route.getCurrPath().getEndBlock());
        }
    }

    public void goToYard(){
        setDestination(CTCModule.map.getYard().getUUID());
        route.addPath(currPos, CTCModule.map.getYard().getUUID(), prevBlock);
        prevBlock = null;
    }

    public boolean inYard(){
        return currPos == CTCModule.map.getYard().getUUID();
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

    public void updateString() {
        DecimalFormat df = new DecimalFormat("##.##");
        suggestedSpeedString.setValue(df.format((suggestedSpeed * 2.237)) + " mph"); // IN M/s
        currPosString.setValue("Block " + CTCModule.map.getBlock(currPos).getBlockNumber() );
        destinationString.setValue("Block " + CTCModule.map.getBlock(destination).getBlockNumber());
        trainIDString.setValue("Train " + trainID);
        authorityString.setValue(authority + " Blocks");

    }
    
    public StringProperty getTrainIDProperty() { return trainIDString; }
    public StringProperty getCurrPosProperty() { return currPosString; }
    public StringProperty getDestProperty() { return destinationString; }
    public StringProperty getSuggestedSpeedProperty() { return suggestedSpeedString; }
}