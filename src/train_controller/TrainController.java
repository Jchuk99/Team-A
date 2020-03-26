package src.train_controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import java.lang.Float;
import java.lang.Integer;
import src.train_module.Train;

public class TrainController {
    private Train attachedTrain = null;
    //public TrainControllerUI attachedUI;
    private BooleanProperty leftDoorsControlClosed;
    private BooleanProperty rightDoorsControlClosed;
    private BooleanProperty manualModeOn;
    private BooleanProperty cabinLightsControlOn;
    private BooleanProperty headLightsControlOn;
    private StringProperty hvacSetpoint;
    private StringProperty driverSpeed;
    //private StringProperty beacon;
    private BooleanProperty emergencyBrakeControlOn;
    private BooleanProperty serviceBrakeControlOn;
    private int UUID;
    //public BooleanProperty leftDoorStateTest=new SimpleBooleanProperty(false);
    private float v_cmd;
    //float v_cmd_prev;
    private float v_curr;
    private float v_err;
    //float v_err_prev;
    private float power;
    //float v_prev;
    //float TIMESTEP=(float)50.0; //ms
    private float kp=(float)50.0;
    private float ki=(float)50.0;
    /**
    
    */
    public TrainController(){ //
        //attachedUI = new TrainControllerUI(this);
        UUID=0;
        leftDoorsControlClosed=new SimpleBooleanProperty(false);
        rightDoorsControlClosed=new SimpleBooleanProperty(false);
        manualModeOn=new SimpleBooleanProperty(false);
        cabinLightsControlOn=new SimpleBooleanProperty(true);
        headLightsControlOn=new SimpleBooleanProperty(true);
        hvacSetpoint=new SimpleStringProperty("68 deg F");
        driverSpeed=new SimpleStringProperty("0 mph");
        emergencyBrakeControlOn=new SimpleBooleanProperty(true);
        serviceBrakeControlOn=new SimpleBooleanProperty(true);
        //v_prev=(float)0.0;
        //v_cmd_prev=(float)0.0;
        
    }

    public void attachTrain(Train train) {
        attachedTrain=train;
        UUID=train.getUUID();
    }

    public void update() {
        
        if (attachedTrain == null) {
            return;
        }
        
        if(manualModeOn.getValue()){
            if(driverSpeed.getValueSafe().isEmpty()){
                v_cmd=(float)0.0;
            }
            else{
                v_cmd=Float.parseFloat(driverSpeed.getValueSafe().substring(0,2));
            }
        }
        else {
            if(attachedTrain.getSuggestedSpeed().getValueSafe().isEmpty()){
                v_cmd=(float)0.0;
            }
            else{
                v_cmd=Float.parseFloat(attachedTrain.getSuggestedSpeed().getValueSafe().split(" ")[0]);
            }
        }
        if(attachedTrain.getCurrentSpeed().getValueSafe().isEmpty()){
            v_curr=(float)0.0;
        }
        else{
            v_curr=Float.parseFloat(attachedTrain.getCurrentSpeed().getValueSafe().split(" ")[0]);
        }
        v_err=v_cmd-v_curr;
        //v_err_prev=v_cmd_prev-v_prev;


        //v_cmd_prev=v_cmd_curr;
        //v_prev=v_curr;
        if(getAuthority().getValueSafe()=="0"){
            power=(float)0.0;    
        }
        else{
            power=(float)(v_err*kp+v_curr*ki);
        }
        attachedTrain.setPower(power);
    }

    public void setTrain(float suggestedSpeed, float authority) {
        // get set train information from train model
        
    }
    
    /**
    
    */
    //public class Controller{
        /**
        
        */
        public StringProperty getName(){
            return new SimpleStringProperty("Train "+UUID);
            
        }
        public BooleanProperty getManualModeOn(){
            return manualModeOn;
        }
        
        /**
        
        */
        public void setManualModeOn(boolean x){
            manualModeOn.setValue(x);
        }
        
        /**
        
        */	
        public BooleanProperty getLeftDoorsControlClosed(){
            return leftDoorsControlClosed;
        }
        
        /**
        
        */
        public void setLeftDoorsControlClosed(boolean x){
            leftDoorsControlClosed.setValue(x);
        }
        
        /**
        
        */
        public BooleanProperty getRightDoorsControlClosed(){
            return rightDoorsControlClosed;
        }
        
        /**
        
        */
        public void setRightDoorsControlClosed(boolean x){
            rightDoorsControlClosed.setValue(x);
        }
        
        /**
        
        */
        public BooleanProperty getCabinLightsControlOn(){
            return cabinLightsControlOn;
        }
        
        /**
        
        */
        public void setCabinLightsControlOn(boolean x){
            cabinLightsControlOn.setValue(x);
        }
        
        /**
        
        */
        public BooleanProperty getHeadLightsControlOn(){
            return headLightsControlOn;
        }
        
        /**
        
        */
        public void setHeadLightsControlOn(boolean x){
            headLightsControlOn.setValue(x);
        }
        
        /**
        
        */
        public StringProperty getHVACSetpoint(){
            return hvacSetpoint;
        }
        
        /**
        
        */
        public void setHVACSetpoint(int x){
            hvacSetpoint.setValue(x+" deg F");
        }
        
        /**
        
        */
        public StringProperty getDriverSpeed(){
            return driverSpeed;
        }
        
        /**
        
        */
        public void setDriverSpeed(int x){
            driverSpeed.setValue(x+" mph");
        }
        
        /**
        
        */
        public BooleanProperty getEmergencyBrakeControlOn(){
            return emergencyBrakeControlOn;
        }
        
        /**
        
        */
        public void setEmergencyBrakeControlOn(boolean x){
            emergencyBrakeControlOn.setValue(x);
        }
        
        /**
        
        */
        public BooleanProperty getServiceBrakeControlOn(){
            return serviceBrakeControlOn;
        }
        
        /**
        
        */
        public void setServiceBrakeControlOn(boolean x){
            serviceBrakeControlOn.setValue(x);
        }
    
        public StringProperty getBeacon(){
            return new SimpleStringProperty("XKCD47");
        }
    
    // /**
        
    // */
    //// public class Train{
        
        // /**
        
        // */
        
        /**
        
        */
        public StringProperty getAuthority(){
            return attachedTrain.getAuthority();
        }
        
        /**
        
        */
        public StringProperty getSuggestedSpeed(){
            return attachedTrain.getSuggestedSpeed();
        }
        
        /**
        
        */
        public StringProperty getCurrentSpeed(){
            return attachedTrain.getCurrentSpeed();
        }
        
        /**
        
        */
        public StringProperty getCurrentAcceleration(){
            return attachedTrain.getCurrentAcceleration();
        }
        
        /**
        
        */
        public StringProperty getCurrentPower(){
            return attachedTrain.getCurrentPower();
        }

		public BooleanProperty getLeftDoorWorking() {
			return attachedTrain.getLeftDoorWorking();
        }
        
        public BooleanProperty getRightDoorWorking() {
			return attachedTrain.getRightDoorWorking();
        }

        public BooleanProperty getLightWorking() {
			return attachedTrain.getLightWorking();
        }

        public BooleanProperty getServiceBrakeWorking() {
			return attachedTrain.getServiceBrakeWorking();
        }

        public BooleanProperty getEmergencyBrakeWorking() {
			return attachedTrain.getEmergencyBrakeWorking();
        }

        public BooleanProperty getEngineWorking() {
			return attachedTrain.getEngineWorking();
        }
        /**
        
        */
        //public String getBeacon(){
        //    return attachedTrain.getBeacon();
        //}
        
        /**
        
        */
        //public String getMap(){
        //    return attachedTrain.getMap();
        //}
    }
    
    // /**
        
    // */
    //// public class Power{
        
        // /**
        
        // */
        // public float calculateNewPower(){//fake sample output
            // if(manualModeOn==true){
                // return 3*(driverSpeed-getActualSpeed());
            // }
            // else{
                // return 3*(getSuggestedSpeed()-getActualSpeed());
            // }
        // }
    // }
    
    /*
    public static void main(String[] args){
        /*new Thread(){
            @Override
            public void run(){
                javafx.application.Application.launch(TrainControllerUI.class);
            }
        }.start();
        TrainModule t=new TrainModule();
        //Train tr = new Train();
        //TrainControllerModule TCM=new TrainControllerModule(tr);
        //TrainControllerUI tcUI=TrainControllerUI.waitForStartUpTest();
        
    }
    */