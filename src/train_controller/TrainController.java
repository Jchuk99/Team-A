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
    public static TrainControllerModule trainControllerModule;
    
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
    private float suggestedSpeed;
    private float authority;
    private float v_cmd;
    //float v_cmd_prev;
    private float v_curr;
    private float v_err;
    //float v_err_prev;
    private float power;
    //float v_prev;
    //float TIMESTEP=(float)50.0; //ms
    private float kp=(float)20.0;
    private float ki=(float)10.0;
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
        authority=(float)0.0;
        suggestedSpeed=(float)0.0;
    }

    public void attachTrain(Train train) {
        attachedTrain = train;
        UUID = train.getUUID();
    }

    public void update() {
        
        if (attachedTrain == null) {
            return;
        }
        
        //getSA(); //use until regular updates from track Module are implemented

        if(vitalCheck()){
            setPower();
        }
        else{
            attachedTrain.setPower(0);
            setEmergencyBrakeControlOn(true);
            setServiceBrakeControlOn(true);
        }

        
    }
    public boolean vitalCheck(){
        if(!getEngineWorking().get()){
             return false;
        }
        else if(!getEmergencyBrakeWorking().get()){
            return false;
        }
        else if(!getServiceBrakeWorking().get()){
            return false;
        }
        else if(!getLeftDoorWorking().get()){
            return false;
        }
        else if(!getRightDoorWorking().get()){
            return false;
        }
        return true;
    }


    public void setPower(){
        if(manualModeOn.getValue()){
            if(driverSpeed.getValueSafe().isEmpty()){
                v_cmd=(float)0.0;
            }
            else{
                v_cmd=Float.parseFloat(driverSpeed.getValueSafe().split(" ")[0]);
            }
        }
        else {
            v_cmd=suggestedSpeed;
        }
        
        if(attachedTrain.getCurrentSpeed().getValueSafe().isEmpty()){
            v_curr=(float)0.0;
        }
        else{
            v_curr=Float.parseFloat(attachedTrain.getCurrentSpeed().getValueSafe().split(" ")[0]);
        }
        
        //braking distance calc (does this violate non-constant-acceleration)
        //aS+0.5*(V_curr-0)^2+g(h1-h2)=0
        //S=(1/a)(-g(h1-h2)-0.5*V_curr^2)
        //biggest grade=5% and -5%
        //acceleration IS NOT A CONSTANT

        //stupid soln (dry track, 5% grade downhill, 19.125 meter change in elevation)
        //S=1.7*(1/(-3.63-2.73))*(-9.81*(19.125)-0.5*V_curr^2)
        //for slightly over top speed (20 m/s), slightly under 3 blocks to eBrake

        int eBrakeDistance=(int)(1.7*(1/(-3.63-2.73))*(-9.81*(19.125)-0.5*v_curr*v_curr));
        int sBrakeDistance=(int)(1.7*(1/(-3.63-1.2))*(-9.81*(19.125)-0.5*v_curr*v_curr));
        int noPowerDistance= (int) ( 1.7 * (1 / (-3.63)) * (-9.81 * (19.125) - 0.5 * v_curr * v_curr));


        v_err=v_cmd-v_curr;
        //v_err_prev=v_cmd_prev-v_prev;
        

        
        

        //v_cmd_prev=v_cmd_curr;
        //v_prev=v_curr;
        if(authority<=0){
            power=(float)0.0;    
        }
        else{
            power=(float)(v_err*kp+v_curr*ki);
        }
        attachedTrain.setPower(power);

    }

    public void setTrain(float sSpeed, float auth) {
        suggestedSpeed=sSpeed;
        authority=auth;
    }

    public void setTestTrain(float sSpeed, float auth) {
        // get set train information from train mode
        attachedTrain.setSpeed(sSpeed);
        attachedTrain.setAuthority(auth);
        
    }


    public void getSA(){
        if(attachedTrain.getAuthority().getValueSafe().isEmpty()){
            authority=(float)0.0;
        }
        else{
            authority=Float.parseFloat(attachedTrain.getAuthority().getValueSafe().split(" ")[0]);
        }

        if(attachedTrain.getSuggestedSpeed().getValueSafe().isEmpty()){
            suggestedSpeed=(float)0.0;
        }
        else{
            suggestedSpeed=Float.parseFloat(attachedTrain.getSuggestedSpeed().getValueSafe().split(" ")[0]);
        }
    }
    
    public void destroy(){
        attachedTrain=null;
        trainControllerModule.destroy(UUID);
    }
    public void nextBlock(){
        authority--;
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
            attachedTrain.setLeftDoor(x);
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
            attachedTrain.setRightDoor(x);
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
            attachedTrain.setLight(x);
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
            attachedTrain.setLight(x);
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
            attachedTrain.setEmergencyBrake(x);
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
            attachedTrain.setServiceBrake(x);
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
            return new SimpleStringProperty(String.valueOf(authority)+" ft");
        }
        
        /**
        
        */
        public StringProperty getSuggestedSpeed(){
            return new SimpleStringProperty(String.valueOf(suggestedSpeed)+" mph");
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