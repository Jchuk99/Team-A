package src.train_module;

import src.track_module.Block;
import src.train_controller.TrainControllerModule.TrainController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Train {

    int UUID;
    TrainController controller;
    Block currentBlock = null;
    Boolean goForward = true;
    float currentSpeed = 0;
    float currentPower = 0;
    float currentPosition = 0;
    float currentAcceleration = 0;
    float temperatureInside = 70;

    float suggestedSpeed = 0;
    float authority = 0;
    float targetPower = 0;

    int passengerCount = 10;
    float currentWeight = (float) 52.2;

    Boolean leftDoorWorking = true;
    Boolean rightDoorWorking = true;
    Boolean lightWorking = true;
    Boolean serviceBrakeWorking = true;
    Boolean emergencyBrakeWorking = true;
    Boolean engineWorking = true;

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

        
        // TODO: use the formula here
        currentPower = targetPower;

        // TODO: use the formula here
        // testing
        currentAcceleration = UUID;
        currentSpeed += currentAcceleration;
        if (currentSpeed > 40) currentSpeed = 40;

        currentPosition += currentSpeed;

        // TODO: put train id in correct block


        updateString();
    }

    private void updateString() {
        suggestedSpeedString.setValue(suggestedSpeed + " mph");
        currentSpeedString.setValue(currentSpeed + " mph");
        authorityString.setValue(authority + " ft");
        currentPowerString.setValue(currentPower + " kW");
        passengerCountString.setValue(passengerCount + "");
        currentWeightString.setValue(currentWeight + " tons");
        currentAccelerationString.setValue(currentAcceleration + " ft/s^2");
        // TODO: get grade from block
        currentGradeString.setValue("0.5" + " %");
        temperatureInsideString.setValue(temperatureInside + " F");
    }

    public int getUUID() {
        return UUID;
    }

    public void setTrain(float suggestedSpeed, float authority) {
        // setTrain called by track model only
        controller.setTrain(suggestedSpeed, authority);
    }

    public void setPower(float power) {
        // TODO: check max power
        targetPower = power;
    }

    public void destroyTrain() {
        // TODO: destroy train controller
        //controller.destroy();
    }

    // for GUI

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

    // private methods

    private void setBlock(Boolean occupied) {
        if (occupied) {
            // set block occupied
        } else {
            // set block empty
        }
    }

}