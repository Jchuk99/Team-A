package src.train_module;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.BooleanProperty;

public class TrainData {

    Train currentTrain;
    StringProperty suggestedSpeed = new SimpleStringProperty("");
    StringProperty currentSpeed = new SimpleStringProperty("");
    StringProperty authority = new SimpleStringProperty("");
    StringProperty currentPower = new SimpleStringProperty("");
    StringProperty passengerCount = new SimpleStringProperty("");
    StringProperty currentWeight = new SimpleStringProperty("");
    StringProperty currentAcceleration = new SimpleStringProperty("");
    StringProperty currentGrade = new SimpleStringProperty("");
    StringProperty temperatureInside = new SimpleStringProperty("");

    BooleanProperty leftDoorWorking = new SimpleBooleanProperty(false);
    BooleanProperty rightDoorWorking = new SimpleBooleanProperty(false);
    BooleanProperty lightWorking = new SimpleBooleanProperty(false);
    BooleanProperty serviceBrakeWorking = new SimpleBooleanProperty(false);
    BooleanProperty emergencyBrakeWorking = new SimpleBooleanProperty(false);
    BooleanProperty engineWorking = new SimpleBooleanProperty(false);

    public TrainData() {
    }

    public void setTrain(Train train) {
        // set Train to train or null for GUI to pull data from
        currentTrain = train;
        if (train == null) {
            suggestedSpeed.unbind();
            suggestedSpeed.setValue("");
            currentSpeed.unbind();
            currentSpeed.setValue("");
            authority.unbind();
            authority.setValue("");
            currentPower.unbind();
            currentPower.setValue("");
            passengerCount.unbind();
            passengerCount.setValue("");
            currentWeight.unbind();
            currentWeight.setValue("");
            currentAcceleration.unbind();
            currentAcceleration.setValue("");
            currentGrade.unbind();
            currentGrade.setValue("");
            temperatureInside.unbind();
            temperatureInside.setValue("");

            leftDoorWorking.unbind();
            leftDoorWorking.setValue(false);
            rightDoorWorking.unbind();
            rightDoorWorking.setValue(false);
            lightWorking.unbind();
            lightWorking.setValue(false);
            serviceBrakeWorking.unbind();
            serviceBrakeWorking.setValue(false);
            emergencyBrakeWorking.unbind();
            emergencyBrakeWorking.setValue(false);
            engineWorking.unbind();
            engineWorking.setValue(false);
        } else {
            suggestedSpeed.bind(currentTrain.getSuggestedSpeed());
            currentSpeed.bind(currentTrain.getCurrentSpeed());
            authority.bind(currentTrain.getAuthority());
            currentPower.bind(currentTrain.getCurrentPower());
            passengerCount.bind(currentTrain.getPassengetCount());
            currentWeight.bind(currentTrain.getCurrentWeight());
            currentAcceleration.bind(currentTrain.getCurrentAcceleration());
            currentGrade.bind(currentTrain.getCurrentGrade());
            temperatureInside.bind(currentTrain.getTemperature());

            leftDoorWorking.bind(currentTrain.getLeftDoorWorking());
            rightDoorWorking.bind(currentTrain.getRightDoorWorking());
            lightWorking.bind(currentTrain.getLightWorking());
            serviceBrakeWorking.bind(currentTrain.getServiceBrakeWorking());
            emergencyBrakeWorking.bind(currentTrain.getEmergencyBrakeWorking());
            engineWorking.bind(currentTrain.getEngineWorking());
        }
    }


    // for GUI

    public StringProperty getSuggestedSpeed() {
        return suggestedSpeed;
    }
 
    public StringProperty getCurrentSpeed() {
        return currentSpeed;
    }
 
    public StringProperty getAuthority() {
       return authority;
    }
 
    public StringProperty getCurrentPower() {
        return currentPower;
    }
 
    public StringProperty getPassengetCount() {
        return passengerCount;
    }
 
    public StringProperty getCurrentWeight() {
        return currentWeight;
    }
 
    public StringProperty getCurrentAcceleration() {
        return currentAcceleration;
    }
 
    public StringProperty getCurrentGrade() {
        return currentGrade;
    }
 
    public StringProperty getTemperature() {
        return temperatureInside;
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
}