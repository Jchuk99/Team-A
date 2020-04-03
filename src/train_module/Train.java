package src.train_module;

import src.track_module.Block;
import src.track_module.Edge;
import src.track_module.TrackModule;
import src.train_controller.TrainController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleBooleanProperty;


import javafx.beans.property.BooleanProperty;

public class Train {

    int UUID;
    TrainController controller;
    Block prevBlock = null;
    Block currentBlock = null;
    Boolean goForward = false;
    Boolean insideOneBlock = true;
    float currentSpeed = 0;
    float currentPower = 0;
    float currentPosition = 0;
    float currentAcceleration = 0;
    float currentGrade = 0;
    float temperatureInside = 70;

    float suggestedSpeed = 0;
    float authority = 0;
    float targetPower = 0;

    int passengerCount = 10;
    float currentWeight = (float) 52.2;

    BooleanProperty leftDoorWorking = new SimpleBooleanProperty(true);
    BooleanProperty rightDoorWorking = new SimpleBooleanProperty(true);
    BooleanProperty lightWorking = new SimpleBooleanProperty(true);
    BooleanProperty serviceBrakeWorking = new SimpleBooleanProperty(true);
    BooleanProperty emergencyBrakeWorking = new SimpleBooleanProperty(true);
    BooleanProperty engineWorking = new SimpleBooleanProperty(true);

    // true means on
    BooleanProperty leftDoorState = new SimpleBooleanProperty(false);
    BooleanProperty rightDoorState = new SimpleBooleanProperty(false);
    BooleanProperty lightState = new SimpleBooleanProperty(false);
    BooleanProperty serviceBrakeState = new SimpleBooleanProperty(false);
    BooleanProperty emergencyBrakeState = new SimpleBooleanProperty(false);

    // in tons
    final static float emptyWeight = (float) 40.9;
    // in meter
    final static float length = (float) 32.2;

    private StringProperty suggestedSpeedString = new SimpleStringProperty("");
    private StringProperty currentSpeedString = new SimpleStringProperty("");
    private StringProperty authorityString = new SimpleStringProperty("");
    private StringProperty currentPowerString = new SimpleStringProperty("");
    private StringProperty passengerCountString = new SimpleStringProperty("");
    private StringProperty currentWeightString = new SimpleStringProperty("");
    private StringProperty currentAccelerationString = new SimpleStringProperty("");
    private StringProperty currentGradeString = new SimpleStringProperty("");
    private StringProperty temperatureInsideString = new SimpleStringProperty("");

    public Train(int id, TrainController trainController, Block currentBlock) {
        UUID = id;
        controller = trainController;
        this.currentBlock = currentBlock;
    }

    public void setBlock(Block block) {
        prevBlock = currentBlock;
        currentBlock = block;
        currentBlock.setTrain(this);
        goForward = true;
        insideOneBlock = true;
        // TODO: check direction
    }

    public void update() {
        // testing
        //if (currentBlock == null) return;

        //currentGrade = currentBlock.getGrade();
        
        // TODO: use the formula here
        currentPower = targetPower;
        if (!engineWorking.getValue()) currentPower = 0;

        // TODO: use the formula here
        // testing
        //currentAcceleration = UUID;
        //currentSpeed += currentAcceleration;
        if (currentSpeed > 40) currentSpeed = 40;

        
        // spec: max speed 70 km/h
        // service brake 1.2 m/s^2
        // emergency brake 2.73 m/s^2
        // max passenger 74 + 148 = 222
        // max power 120 kW

        currentPosition += currentSpeed;

        updateBlock();
        updateString();
    }

    private void updateBlock() {
        // check still inside current block
        if (currentPosition > currentBlock.getLength()) {
            currentPosition -= currentBlock.getLength();
            nextBlock();
        } else if (currentPosition < 0) {
            nextBlock();
            currentPosition += currentBlock.getLength();
        }
        // check if the end of the train leaves previous block
        if (insideOneBlock) return;
        if (goForward) {
            if (currentPosition - length > 0) prevBlock.setTrain(null);
        } else {
            if (currentPosition + length > currentBlock.getLength()) prevBlock.setTrain(null);
        }
    }

    private void nextBlock() {
        //currentBlock.setTrain(null);
        insideOneBlock = false;
        Block nextBlock = null;

        // if its a connected edge and it's not the previous block
        for (Edge edge: currentBlock.getEdges()){
               Block edgeBlock = edge.getBlock();
            if(edge.getConnected() && !edgeBlock.equals(prevBlock)){
                nextBlock = edgeBlock;
            }
        };

        assert nextBlock != null;
        System.out.println("Next Block: " + nextBlock.getBlockNumber());

        prevBlock = currentBlock;
        currentBlock = nextBlock;
        currentBlock.setTrain(this);
        currentBlock = nextBlock;
    }


    public void setSpeed(float speed){
        currentSpeed = speed;
    }
    
    public void setAuthority(float authority){
        this.authority = authority;
    }

    public int getUUID() {
        return UUID;
    }

    public void destroyTrain() {
        // TODO: destroy train controller
        //controller.destroy();
    }


    // called by track model only
    public void setTrain(float suggestedSpeed, float authority) {
        controller.setTrain(suggestedSpeed, authority);
    }


    /****** called by train controller and GUI ******/
    public void setPower(float power) {
        // TODO: check max power
        targetPower = power;
    }

    public void setLeftDoor(boolean state) {
        if (leftDoorWorking.getValue()) leftDoorState.setValue(state);
    }

    public void setRightDoor(boolean state) {
        if (rightDoorWorking.getValue()) rightDoorState.setValue(state);
    }

    public void setLight(boolean state) {
        if (lightWorking.getValue()) lightState.setValue(state);
    }

    public void setServiceBrake(boolean state) {
        if (serviceBrakeWorking.getValue()) serviceBrakeState.setValue(state);
    }

    public void setEmergencyBrake(boolean state) {
        if (emergencyBrakeWorking.getValue()) emergencyBrakeState.setValue(state);
    }

    public BooleanProperty getLeftDoorState() {
        return leftDoorState;
    }

    public BooleanProperty getRightDoorState() {
        return rightDoorState;
    }

    public BooleanProperty getLightState() {
        return lightState;
    }

    public BooleanProperty getServiceBrakeState() {
        return serviceBrakeState;
    }

    public BooleanProperty getEmergencyBrakeState() {
        return emergencyBrakeState;
    }

    public BooleanProperty getLeftDoorWorking() {
        return leftDoorWorking;
    }

    public BooleanProperty getRightDoorWorking() {
        return rightDoorWorking;
    }

    public BooleanProperty getLightWorking() {
        return lightWorking;
    }

    public BooleanProperty getServiceBrakeWorking() {
        return serviceBrakeWorking;
    }

    public BooleanProperty getEmergencyBrakeWorking() {
        return emergencyBrakeWorking;
    }
    
    public BooleanProperty getEngineWorking() {
        return engineWorking;
    }
    /****** called by train controller and GUI ******/



    /****** for GUI ******/
    private void updateString() {
        suggestedSpeedString.setValue(suggestedSpeed + " mph");
        currentSpeedString.setValue(currentSpeed + " mph");
        authorityString.setValue(authority + " ft");
        currentPowerString.setValue(currentPower + " kW");
        passengerCountString.setValue(passengerCount + "");
        currentWeightString.setValue(currentWeight + " tons");
        currentAccelerationString.setValue(currentAcceleration + " ft/s^2");
        currentGradeString.setValue(currentGrade + " %");
        temperatureInsideString.setValue(temperatureInside + " F");
    }

    public StringProperty getName() {
        return new SimpleStringProperty("Train " + UUID);
    }

    public StringProperty getSuggestedSpeed() {
        return suggestedSpeedString;
    }

    public StringProperty getCurrentSpeed() {
        return currentSpeedString;
    }

    public StringProperty getAuthority() {
        return authorityString;
    }

    public StringProperty getCurrentPower() {
        return currentPowerString;
    }

    public StringProperty getPassengetCount() {
        return passengerCountString;
    }

    public StringProperty getCurrentWeight() {
        return currentWeightString;
    }

    public StringProperty getCurrentAcceleration() {
        return currentAccelerationString;
    }

    public StringProperty getCurrentGrade() {
        return currentGradeString;
    }

    public StringProperty getTemperatureInside() {
        return temperatureInsideString;
    }
    /****** for GUI ******/

}