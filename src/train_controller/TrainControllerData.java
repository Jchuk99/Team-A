package src.train_controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.BooleanProperty;

public class TrainControllerData {

    private TrainController currentTC;
    private StringProperty driverSpeed = new SimpleStringProperty("0");
    private StringProperty beacon = new SimpleStringProperty("");
    private StringProperty suggestedSpeed = new SimpleStringProperty("");
    private StringProperty currentSpeed = new SimpleStringProperty("");
    private StringProperty authority = new SimpleStringProperty("");
    private StringProperty currentPower = new SimpleStringProperty("");
    private StringProperty currentAcceleration = new SimpleStringProperty("");
    private StringProperty hvacSetpoint = new SimpleStringProperty("68");

    private BooleanProperty leftDoorsControlClosed=new SimpleBooleanProperty(false);
    private BooleanProperty rightDoorsControlClosed=new SimpleBooleanProperty(false);
    private BooleanProperty manualModeOn=new SimpleBooleanProperty(false);
    private BooleanProperty cabinLightsControlOn=new SimpleBooleanProperty(false);
    private BooleanProperty headLightsControlOn=new SimpleBooleanProperty(false);
    private BooleanProperty emergencyBrakeControlOn=new SimpleBooleanProperty(false);
    private BooleanProperty serviceBrakeControlOn=new SimpleBooleanProperty(false);


    private BooleanProperty leftDoorWorking = new SimpleBooleanProperty(false);
    private BooleanProperty rightDoorWorking = new SimpleBooleanProperty(false);
    private BooleanProperty lightWorking = new SimpleBooleanProperty(false);
    private BooleanProperty serviceBrakeWorking = new SimpleBooleanProperty(false);
    private BooleanProperty emergencyBrakeWorking = new SimpleBooleanProperty(false);
    private BooleanProperty engineWorking = new SimpleBooleanProperty(false);

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
            beacon.unbind();
            beacon.setValue("");
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
            beacon.bind(currentTC.getBeacon());
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
    public StringProperty getBeacon(){
        return beacon;
    }

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
        if(currentTC!=null){
            currentTC.setManualModeOn(x);
        }
    }
    
    /**
    
    */
    public void setLeftDoorsControlClosed(boolean x){
        if(currentTC!=null){
        currentTC.setLeftDoorsControlClosed(x);
        }
    }
    
    /**
    
    */
    public void setRightDoorsControlClosed(boolean x){
        if(currentTC!=null){
            currentTC.setRightDoorsControlClosed(x);
        }
    }
    
    
    /**
    
    */
    public void setCabinLightsControlOn(boolean x){
        if(currentTC!=null){
            currentTC.setCabinLightsControlOn(x);
        }
    }
    
    /**
    
    */
    public void setHeadLightsControlOn(boolean x){
        if(currentTC!=null){    
         currentTC.setHeadLightsControlOn(x);
        }
    }
    
    /**
    
    */
    public void setHVACSetpoint(int x){
        if(currentTC!=null){
        currentTC.setHVACSetpoint(x);
        }
    }
    
    /**
    
    */
    public void setDriverSpeed(int x){
        if(currentTC!=null){
        currentTC.setDriverSpeed(x);
       }
    }
    
    /**
    
    */
    public void setEmergencyBrakeControlOn(boolean x){
        if(currentTC!=null){
        currentTC.setEmergencyBrakeControlOn(x);
        }
    }
    
    /**
    
    */
    public void setServiceBrakeControlOn(boolean x){
        if(currentTC!=null){
        currentTC.setServiceBrakeControlOn(x);
        }
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