package src.train_module;

import src.track_module.Block;
import src.train_controller.TrainControllerModule.TrainController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.BooleanProperty;

public class Train {

    int UUID;
    TrainController controller;
    Block currentBlock = null;
    Boolean goForward = true;
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

    final static float emptyWeight = 47;
    final static float length = 100;

    private StringProperty suggestedSpeedString = new SimpleStringProperty("");
    private StringProperty currentSpeedString = new SimpleStringProperty("");
    private StringProperty authorityString = new SimpleStringProperty("");
    private StringProperty currentPowerString = new SimpleStringProperty("");
    private StringProperty passengerCountString = new SimpleStringProperty("");
    private StringProperty currentWeightString = new SimpleStringProperty("");
    private StringProperty currentAccelerationString = new SimpleStringProperty("");
    private StringProperty currentGradeString = new SimpleStringProperty("");
    private StringProperty temperatureInsideString = new SimpleStringProperty("");

    public Train(int id, TrainController trainController) {
        UUID = id;
        controller = trainController;
    }

    public void setBlock(Block block) {
        currentBlock = block;
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
        currentAcceleration = UUID;
        currentSpeed += currentAcceleration;
        if (currentSpeed > 40) currentSpeed = 40;

        currentPosition += currentSpeed;

        // TODO: put train id in correct block

        //updateBlock();
        updateString();
    }

    private void updateBlock() {
        // check still inside current block
        Block nextBlock;
        if (currentPosition > currentBlock.getLength()) {
            currentPosition -= currentBlock.getLength();
            currentBlock.setTrain(null);
            // TODO:get next block here
            //nextBlock = currentBlock.getEdges();
            //currentBlock = nextBlock;
            //currentBlock.setTrain(this);
        } else if (currentPosition < 0) {
            // opposite direction

        }

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

    public StringProperty getTemperature() {
        return temperatureInsideString;
    }
    /****** for GUI ******/

}