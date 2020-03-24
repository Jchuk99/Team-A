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

    public BooleanProperty leftDoorsControlClosed=new SimpleBooleanProperty(false);
    public BooleanProperty rightDoorsControlClosed=new SimpleBooleanProperty(false);
    public BooleanProperty manualModeOn=new SimpleBooleanProperty(false);
    public BooleanProperty cabinLightsControlOn=new SimpleBooleanProperty(false);
    public BooleanProperty headLightsControlOn=new SimpleBooleanProperty(false);
    public BooleanProperty emergencyBrakeControlOn=new SimpleBooleanProperty(false);
    public BooleanProperty serviceBrakeControlOn=new SimpleBooleanProperty(false);


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

            leftDoorsControlClosed.unbind();
            leftDoorsControlClosed.setValue(false);
            rightDoorsControlClosed.unbind();
            rightDoorsControlClosed.setValue(false);
            manualModeOn.unbind();
            manualModeOn.setValue(false);
            cabinLightsControlOn.unbind();
            cabinLightsControlOn.setValue(false);
            headLightsControlOn.unbind();
            headLightsControlOn.setValue(false);
            emergencyBrakeControlOn.unbind();
            emergencyBrakeControlOn.setValue(false);
            serviceBrakeControlOn.unbind();
            serviceBrakeControlOn.setValue(false);

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
            driverSpeed.bind(currentTC.getDriverSpeed());
            authority.bind(currentTC.getAuthority());
            currentPower.bind(currentTC.getCurrentPower());
            currentAcceleration.bind(currentTC.getCurrentAcceleration());
            hvacSetpoint.bind(currentTC.getHVACSetpoint());

            leftDoorsControlClosed.bind(currentTC.getLeftDoorsControlClosed());
            rightDoorsControlClosed.bind(currentTC.getRightDoorsControlClosed());
            manualModeOn.bind(currentTC.getManualModeOn());
            cabinLightsControlOn.bind(currentTC.getCabinLightsControlOn());
            headLightsControlOn.bind(currentTC.getHeadLightsControlOn());
            emergencyBrakeControlOn.bind(currentTC.getEmergencyBrakeControlOn());
            serviceBrakeControlOn.bind(currentTC.getServiceBrakeControlOn());

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

    public void setManualModeOn(boolean x){
        currentTC.setManualModeOn(x);
    }
    
    /**
    
    */
    public void setLeftDoorsControlClosed(boolean x){
        currentTC.setLeftDoorsControlClosed(x);
    }
    
    /**
    
    */
    public void setRightDoorsControlClosed(boolean x){
        currentTC.setRightDoorsControlClosed(x);
    }
    
    
    /**
    
    */
    public void setCabinLightsControlOn(boolean x){
        currentTC.setCabinLightsControlOn(x);
    }
    
    /**
    
    */
    public void setHeadLightsControlOn(boolean x){
        currentTC.setHeadLightsControlOn(x);
    }
    
    /**
    
    */
    public void setHVACSetpoint(int x){
        currentTC.setHVACSetpoint(x);
    }
    
    /**
    
    */
    public void setDriverSpeed(int x){
        currentTC.setDriverSpeed(x);
    }
    
    /**
    
    */
    public void setEmergencyBrakeControlOn(boolean x){
        currentTC.setEmergencyBrakeControlOn(x);
    }
    
    /**
    
    */
    public void setServiceBrakeControlOn(boolean x){
        currentTC.setServiceBrakeControlOn(x);
    }

    /**
    
    */
    public BooleanProperty getManualModeOn(){
        return manualModeOn;
    }
    
    /**
    
    */	
    public BooleanProperty getLeftDoorsControlClosed(){
        return leftDoorsControlClosed;
    }
    
    /**
    
    */
    public BooleanProperty getRightDoorsControlClosed(){
        return rightDoorsControlClosed;
    }

    /**
    
    */
    public BooleanProperty getCabinLightsControlOn(){
        return cabinLightsControlOn;
    }
    
    /**
    
    */
    public BooleanProperty getHeadLightsControlOn(){
        return headLightsControlOn;
    }
    
    /**
    
    */
    public BooleanProperty getEmergencyBrakeControlOn(){
        return emergencyBrakeControlOn;
    }
    
    /**
    
    */
    public BooleanProperty getServiceBrakeControlOn(){
        return serviceBrakeControlOn;
    }
}