package src.ctc;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.UUID;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import src.track_module.Block;

public class CTCTrain {

    private float authority;
    private float suggestedSpeed; // IN METERS PER SECOND INTENRALLY
    private int trainID;
    private UUID prevPathBlock = null;
    private UUID destination;
    private UUID startPos;
    private UUID currPos;
    private UUID prevPos = null;
    private int errorStatus; //TODO: make this an enum.
    private LocalTime dispatchTime;
    private Route route = new Route();

    private StringProperty suggestedSpeedString = new SimpleStringProperty("");
    private StringProperty currPosString = new SimpleStringProperty("");
    private StringProperty authorityString = new SimpleStringProperty("");
    private StringProperty destinationString = new SimpleStringProperty("");
    private StringProperty trainIDString = new SimpleStringProperty("");


    public CTCTrain(int trainID){
        // All trains start in the yard.
        this.trainID = trainID;
        currPos = CTCModule.map.getYard().getUUID();
    }

    // used for when there's no start and endtime, AKA when train is dispatched by dispatcher
    public void addPath(UUID dest){
        Block destination = CTCModule.map.getBlock(dest);


        //If train does not have any queued paths then will be in yard.
        if (route.size() == 0){
            // block connected to yard depending on line
            startPos = CTCModule.map.getStartingBlockID(destination.getLine());
            //TODO: add a isDipatched method;
            //TODO: whenever train is dispatched set currPos to start;
            //setCurrPos(start);
            //TODO: look @ add path logic
            route.addPath(startPos, dest, prevPathBlock);
            prevPathBlock = route.getLastPath().getBeforeEndBlock();
        }
        else{
            route.addPath(route.getLastPath().getEndBlock(), dest, prevPathBlock);
            prevPathBlock = route.getLastPath().getBeforeEndBlock();
        }

        //TODO: calculate authorities the real wau.
        authority = (float) route.getCurrPath().getCourse().size();
    }

    public void addTimePath(UUID dest, LocalTime startTime, LocalTime endTime){
        Block destination = CTCModule.map.getBlock(dest);
        UUID start;

        //If train does not have any queued paths then will be in yard.
        if (route.size() == 0){
            // block connected to yard depending on line
            start = CTCModule.map.getStartingBlockID(destination.getLine());
            //TODO: add a isDipatched method;
            //TODO: whenever train is dispatched set currPos to start;
            //setCurrPos(start);
            //TODO: look @ add path logic
            route.addTimePath(start, dest, prevPathBlock, startTime, endTime);
            prevPathBlock = route.getLastPath().getBeforeEndBlock();
        }
        else{
            start = route.getLastPath().getEndBlock();
            route.addTimePath(start, dest, prevPathBlock, startTime, endTime);
            prevPathBlock = route.getLastPath().getBeforeEndBlock();
        }

        //TODO: calculate authorities the real wau.
        authority = (float) route.getCurrPath().getCourse().size();
    }
    public void update(){
        updateCurrPath();
        updateString();
    }
    public void updateCurrPath(){
        Path currPath = route.getCurrPath();
        if (currPath != null){
            currPath.updateCourse(currPos, prevPos);
        }

    }

    public UUID getNextBlockID(UUID currBlock){
        if (route.size() > 0){
            return route.getCurrPath().getNextBlockID(currBlock);
        }
        else{
            return null;
        }
    }

    public void getNextPath(){
        route.getNextPath();
        if (route.getCurrPath() != null){
            setDestination(route.getCurrPath().getEndBlock());
        }
    }

    public void goToYard(){
        setDestination(CTCModule.map.getYard().getUUID());
        route.addPath(currPos, CTCModule.map.getYard().getUUID(), prevPathBlock);
        prevPathBlock = null;
    }

    public boolean atDestination(){
        return currPos.equals(destination);
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
        prevPos = this.currPos;
        this.currPos = currPos;
    }
    public void setDispatchTime(LocalTime dispatchTime){
        this.dispatchTime = dispatchTime;
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
    public LocalTime getDispatchTime(){
        return dispatchTime;
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