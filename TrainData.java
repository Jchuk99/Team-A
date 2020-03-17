package src.train_module;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TrainData {

    Train currentTrain;
    StringProperty suggestedSpeed;
    StringProperty currentSpeed;
    StringProperty authority;
    StringProperty currentPower;
    StringProperty passengerCount;
    StringProperty currentWeight;
    StringProperty currentAcceleration;
    StringProperty currentGrade;
    StringProperty temperatureInside;

    public TrainData() {
        suggestedSpeed = new SimpleStringProperty("");
        currentSpeed = new SimpleStringProperty("");
        authority = new SimpleStringProperty("");
        currentPower = new SimpleStringProperty("");
        passengerCount = new SimpleStringProperty("");
        currentWeight = new SimpleStringProperty("");
        currentAcceleration = new SimpleStringProperty("");
        currentGrade = new SimpleStringProperty("");
        temperatureInside = new SimpleStringProperty("");
    }

    public void setTrain(Train train) {
        // set Train to train or null for GUI to pull data from
        currentTrain = train;
        if (train == null) {
            suggestedSpeed.setValue("");
            currentSpeed.setValue("");
            authority.setValue("");
            currentPower.setValue("");
            passengerCount.setValue("");
            currentWeight.setValue("");
            currentAcceleration.setValue("");
            //currentGrade.setValue("");
            temperatureInside.setValue("");
        } else {
            suggestedSpeed.setValue(currentTrain.suggestedSpeed + " mph");
            currentSpeed.setValue(currentTrain.currentSpeed + " mph");
            authority.setValue(currentTrain.authority + " ft");
            currentPower.setValue(currentTrain.currentPower + " kW");
            passengerCount.setValue(currentTrain.passengerCount + "");
            currentWeight.setValue(currentTrain.currentWeight + " tons");
            currentAcceleration.setValue(currentTrain.currentAcceleration + " ft/s^2");
            //currentGrade.setValue(currentTrain.currentGrade + " %");
            temperatureInside.setValue(currentTrain.temperatureInside + " F");
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
        // TODO: get grade from block
        return new SimpleStringProperty("0.5 %");
    }
 
    public StringProperty getTemperature() {
        return temperatureInside;
    }
 
}