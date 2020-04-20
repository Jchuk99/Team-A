package src.train_module;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class TrainData {

    Train currentTrain;
    BooleanProperty trainDestroyedListener = new SimpleBooleanProperty(false);

    StringProperty suggestedSpeed = new SimpleStringProperty("");
    StringProperty currentSpeed = new SimpleStringProperty("");
    StringProperty authority = new SimpleStringProperty("");
    StringProperty currentPower = new SimpleStringProperty("");
    StringProperty passengerCount = new SimpleStringProperty("");
    StringProperty currentWeight = new SimpleStringProperty("");
    StringProperty currentAcceleration = new SimpleStringProperty("");
    StringProperty currentGrade = new SimpleStringProperty("");
    StringProperty temperatureInside = new SimpleStringProperty("");

    StringProperty beacon = new SimpleStringProperty("");

    BooleanProperty leftDoorWorking = new SimpleBooleanProperty(false);
    BooleanProperty rightDoorWorking = new SimpleBooleanProperty(false);
    BooleanProperty lightWorking = new SimpleBooleanProperty(false);
    BooleanProperty serviceBrakeWorking = new SimpleBooleanProperty(false);
    BooleanProperty emergencyBrakeWorking = new SimpleBooleanProperty(false);
    BooleanProperty emergencyBrakeState = new SimpleBooleanProperty(false);
    BooleanProperty engineWorking = new SimpleBooleanProperty(false);

    public TrainData() {
        trainDestroyedListener.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue o,Boolean oldVal, Boolean newVal){
                if (newVal) setTrain(null);
            }
        });
    }

    public void setTrain(Train train) {
        // set Train to train or null for GUI to pull data from
        currentTrain = train;
        if (train == null) {
            trainDestroyedListener.unbind();
            trainDestroyedListener.setValue(false);

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

            beacon.unbind();
            beacon.setValue("");

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
            emergencyBrakeState.unbind();
            emergencyBrakeState.setValue(false);
            engineWorking.unbind();
            engineWorking.setValue(false);
        } else {
            trainDestroyedListener.bind(currentTrain.removeFlag);

            suggestedSpeed.bind(currentTrain.getSuggestedSpeed());
            currentSpeed.bind(currentTrain.getCurrentSpeed());
            authority.bind(currentTrain.getAuthority());
            currentPower.bind(currentTrain.getCurrentPower());
            passengerCount.bind(currentTrain.getPassengetCount());
            currentWeight.bind(currentTrain.getCurrentWeight());
            currentAcceleration.bind(currentTrain.getCurrentAcceleration());
            currentGrade.bind(currentTrain.getCurrentGrade());
            temperatureInside.bind(currentTrain.getTemperatureInside());

            beacon.bind(currentTrain.getBeacon());

            leftDoorWorking.bind(currentTrain.getLeftDoorWorking());
            rightDoorWorking.bind(currentTrain.getRightDoorWorking());
            lightWorking.bind(currentTrain.getLightWorking());
            serviceBrakeWorking.bind(currentTrain.getServiceBrakeWorking());
            emergencyBrakeWorking.bind(currentTrain.getEmergencyBrakeWorking());
            emergencyBrakeState.bind(currentTrain.getEmergencyBrakeState());
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
 
    public StringProperty getTemperatureInside() {
        return temperatureInside;
    }

    public StringProperty getBeacon() {
        return beacon;
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
    
    public BooleanProperty getEmergencyBrakeState() {
        return emergencyBrakeState;
    }
    
    public BooleanProperty getEngineWorking() {
        return engineWorking;
    }


    public void setLeftDoorWorking(boolean state) {
        if (currentTrain != null) currentTrain.getLeftDoorWorking().setValue(state);
    }

    public void setRightDoorWorking(boolean state) {
        if (currentTrain != null) currentTrain.getRightDoorWorking().setValue(state);
    }

    public void setLightWorking(boolean state) {
        if (currentTrain != null) currentTrain.getLightWorking().setValue(state);
    }
    
    public void setServiceBrakeWorking(boolean state) {
        if (currentTrain != null) currentTrain.getServiceBrakeWorking().setValue(state);
    }
    
    public void setEmergencyBrakeWorking(boolean state) {
        if (currentTrain != null) currentTrain.getEmergencyBrakeWorking().setValue(state);
    }
    
    public void setEngineWorking(boolean state) {
        if (currentTrain != null) currentTrain.getEngineWorking().setValue(state);
    }
    
    public void setEmergencyBrake(boolean state) {
        if (currentTrain != null) currentTrain.setEmergencyBrake(state);
    }
    
}