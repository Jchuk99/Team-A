package src.ctc;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import src.track_module.Block;

public class CTCTrain {
    private static long dwellTime = 60; // IN SECONDS
    private float authority = 0;
    private float suggestedSpeed; // IN METERS PER SECOND INTENRALLY
    private int trainID;
    private UUID prevPathBlock = null;
    private UUID destination;
    public UUID startPos;
    private UUID currPos;
    private UUID prevPos = null;
    private int errorStatus; //TODO: make this an enum.
    private LocalTime dispatchTime;
    private LocalTime dwellStart;
    private Route route = new Route();
    private boolean dwelling = false;
    private boolean dispatched = false;

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
            route.addPath(startPos, dest, prevPathBlock);
            prevPathBlock = route.getLastPath().getBeforeEndBlock();
        }
        else{
            route.addPath(route.getLastPath().getEndBlock(), dest, prevPathBlock);
            prevPathBlock = route.getLastPath().getBeforeEndBlock();
        }

    }

    public void addTimePath(UUID dest, LocalTime startTime, LocalTime endTime){
        Block destination = CTCModule.map.getBlock(dest);

        //If train does not have any queued paths then will be in yard.
        if (route.size() == 0){
            // block connected to yard depending on line
            startPos = CTCModule.map.getStartingBlockID(destination.getLine());
            //TODO: add a isDipatched method, maybe just dispatched when not in yard.
            route.addTimePath(startPos, dest, prevPathBlock, startTime, endTime);
            prevPathBlock = route.getLastPath().getBeforeEndBlock();
        }
        else{
            route.addTimePath(route.getLastPath().getEndBlock(), dest, prevPathBlock, startTime, endTime);
            prevPathBlock = route.getLastPath().getBeforeEndBlock();
        }
    }
    public void update(){
        //updateCurrPath();
        updateString();
    }
    
    public void updateCurrPath(){
        Path currPath = route.getCurrPath();
        if (currPath != null){
            // this constantly updates
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
        Path currPath = route.getCurrPath();
        if (currPath != null){
            setDestination(route.getCurrPath().getEndBlock());
            if (currPath instanceof TimePath){
                TimePath currTimePath = (TimePath) currPath;
                setSuggestedSpeed(currTimePath.calcSuggestedSpeed());
            }
        }
    }

    public void goToYard(){
        setDestination(CTCModule.map.getYard().getUUID());
        route.addPath(currPos, CTCModule.map.getYard().getUUID(), prevPos);
        prevPathBlock = null;
    }

    public boolean atDestination(){
        return currPos.equals(destination);
    }
    public boolean inYard(){
        boolean inYard = currPos.equals(CTCModule.map.getYard().getUUID());
        return inYard;
    }
    public boolean onMap(){
        return !inYard();
    }
    public boolean isDwelling(){
        return dwelling;
    }
    public boolean isDoneDwelling(LocalTime currTime){
        boolean doneDwelling = false;
        long elapsedSeconds = ChronoUnit.SECONDS.between(dwellStart, currTime);
        if (elapsedSeconds >= dwellTime){
            doneDwelling = true;
        }
        return doneDwelling;
    }
    public boolean onPath(){
        boolean onPath = false;
        Path currPath = route.getCurrPath();
        if (currPath != null){
            onPath = currPath.getCourse().contains(currPos);
        }
        return onPath;
    }
    public boolean isDispatched(){
        return dispatched;
    }
    
    // getters and setters.
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
    public void setDwelling( boolean dwelling){
        this.dwelling = dwelling;
    }
    public void setDwellStart(LocalTime dwellStart){
        this.dwellStart = dwellStart;
    }
    public void setDispatched(Boolean dispatched){
        this.dispatched = dispatched;
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
    public UUID getStartPos(){
        return startPos;
    }
    public UUID getCurrPos(){
        return currPos;
    }
    public UUID getPrevPos(){
        return prevPos;
    }
    public LocalTime getDispatchTime(){
        return dispatchTime;
    }

    /*** FOR GUI ***/

    public void updateString() {
        DecimalFormat df = new DecimalFormat("##.##");
        suggestedSpeedString.setValue(df.format((suggestedSpeed * 2.237)) + " mph"); // IN M/s
        if (inYard()){
            currPosString.setValue("YARD");
        }
        else{
            currPosString.setValue("Block " + CTCModule.map.getBlock(currPos).getBlockNumber() );
        }
        if (destination != null){
            if (destination.equals(CTCModule.map.getYard().getUUID())){
                destinationString.setValue("YARD");
            }
            else{
                destinationString.setValue("Block " + CTCModule.map.getBlock(destination).getBlockNumber());
            }
        }
        trainIDString.setValue("Train " + trainID);
        authorityString.setValue(authority + " Blocks");

    }
    
    public StringProperty getTrainIDProperty() { return trainIDString; }
    public StringProperty getCurrPosProperty() { return currPosString; }
    public StringProperty getDestProperty() { return destinationString; }
    public StringProperty getSuggestedSpeedProperty() { return suggestedSpeedString; }
}