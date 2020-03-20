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
            suggestedSpeed.unbind();
            suggestedSpeed.setValue("");
            currentSpeed.unbind();
            currentSpeed.setValue("");
            authority.setValue("");
            authority.unbind();
            currentPower.setValue("");
            currentPower.unbind();
            passengerCount.setValue("");
            passengerCount.unbind();
            currentWeight.setValue("");
            currentWeight.unbind();
            currentAcceleration.setValue("");
            currentAcceleration.unbind();
            currentGrade.setValue("");
            currentGrade.unbind();
            temperatureInside.setValue("");
            temperatureInside.unbind();
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