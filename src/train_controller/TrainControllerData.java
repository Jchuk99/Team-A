package src.train_controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.BooleanProperty;

public class TrainControllerData {

    TrainController currentTC;
    StringProperty driverSpeed = new SimpleStringProperty("");
    StringProperty suggestedSpeed = new SimpleStringProperty("");
    StringProperty currentSpeed = new SimpleStringProperty("");
    StringProperty authority = new SimpleStringProperty("");
    StringProperty currentPower = new SimpleStringProperty("");
    StringProperty currentAcceleration = new SimpleStringProperty("");
    StringProperty hvacSetpoint = new SimpleStringProperty("");

    BooleanProperty leftDoorWorking = new SimpleBooleanProperty(false);
    BooleanProperty rightDoorWorking = new SimpleBooleanProperty(false);
    BooleanProperty lightWorking = new SimpleBooleanProperty(false);
    BooleanProperty serviceBrakeWorking = new SimpleBooleanProperty(false);
    BooleanProperty emergencyBrakeWorking = new SimpleBooleanProperty(false);
    BooleanProperty engineWorking = new SimpleBooleanProperty(false);

    public TrainControllerData() {
    }

    public void setTrainController(TrainController tc) {
        // set Train to train or null for GUI to pull data from
        currentTC = tc;
        if (tc == null) {
            suggestedSpeed.unbind();
            suggestedSpeed.setValue("");
            driverSpeed.unbind();
            driverSpeed.setValue("");
            currentSpeed.unbind();
            currentSpeed.setValue("");
            authority.unbind();
            authority.setValue("");
            currentPower.unbind();
            currentPower.setValue("");
            currentAcceleration.unbind();
            currentAcceleration.setValue("");
            hvacSetpoint.unbind();
            hvacSetpoint.setValue("");

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
            suggestedSpeed.bind(currentTC.getSuggestedSpeed());
            currentSpeed.bind(currentTC.getCurrentSpeed());
            authority.bind(currentTC.getAuthority());
            currentPower.bind(currentTC.getCurrentPower());
            currentAcceleration.bind(currentTC.getCurrentAcceleration());
            hvacSetpoint.bind(currentTC.getHVACSetpoint());

            leftDoorWorking.bind(currentTC.getLeftDoorWorking());
            rightDoorWorking.bind(currentTC.getRightDoorWorking());
            lightWorking.bind(currentTC.getLightWorking());
            serviceBrakeWorking.bind(currentTC.getServiceBrakeWorking());
            emergencyBrakeWorking.bind(currentTC.getEmergencyBrakeWorking());
            engineWorking.bind(currentTC.getEngineWorking());
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
 
    public StringProperty getDriverSpeed() {
        return driverSpeed;
    }
 
    public StringProperty getCurrentAcceleration() {
        return currentAcceleration;
    }
 
    public StringProperty getHVACSetpoint() {
        return hvacSetpoint;
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