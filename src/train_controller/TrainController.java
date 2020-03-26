package src.train_controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import src.train_module.Train;

public class TrainController {
    public Train attachedTrain = null;
    public TrainControllerUI attachedUI;
    public boolean leftDoorsControlClosed;
    public boolean rightDoorsControlClosed;
    public boolean manualModeOn;
    public boolean cabinLightsControlOn;
    public boolean headLightsControlOn;
    public int hvacSetpoint;
    public float driverSpeed;
    public boolean emergencyBrakeControlOn;
    public boolean serviceBrakeControlOn;
    public int UUID;
    //public BooleanProperty leftDoorStateTest=new SimpleBooleanProperty(false);
    
    

    /**
    
    */
    public TrainController(){ //
        //attachedUI = new TrainControllerUI(this);
        UUID=0;
        leftDoorsControlClosed=false;
        rightDoorsControlClosed=false;
        manualModeOn=false;
        cabinLightsControlOn=true;
        headLightsControlOn=true;
        int hvacSetpoint=68;
        float driverSpeed=(float)0.0;
        emergencyBrakeControlOn=true;
        serviceBrakeControlOn=true;
    }

    public void attachTrain(Train train) {
        attachedTrain = train;
        UUID = train.getUUID();
    }

    public void update() {
        
        if (attachedTrain == null) {
            return;
        }
        attachedTrain.setPower(10);
    }

    public void setTrain(float suggestedSpeed, float authority) {
        // get set train information from train mode
        attachedTrain.setSpeed(suggestedSpeed);
        attachedTrain.setAuthority(authority);
        
    }
    
    /**
    
    */
    //public class Controller{
        /**
        
        */
        public StringProperty getName(){
            return new SimpleStringProperty("Train "+UUID);
            
        }
        public boolean getManualModeOn(){
            return manualModeOn;
        }
        
        /**
        
        */
        public void setManualModeOn(boolean x){
            manualModeOn=x;
        }
        
        /**
        
        */	
        public boolean getLeftDoorsControlClosed(){
            return leftDoorsControlClosed;
        }
        
        /**
        
        */
        public void setLeftDoorsControlClosed(boolean x){
            leftDoorsControlClosed=x;
        }
        
        /**
        
        */
        public boolean getRightDoorsControlClosed(){
            return rightDoorsControlClosed;
        }
        
        /**
        
        */
        public void setRightDoorsControlClosed(boolean x){
            rightDoorsControlClosed=x;
        }
        
        /**
        
        */
        public boolean getCabinLightsControlOn(){
            return cabinLightsControlOn;
        }
        
        /**
        
        */
        public void setCabinLightsControlOn(boolean x){
            cabinLightsControlOn=x;
        }
        
        /**
        
        */
        public boolean getHeadLightsControlOn(){
            return headLightsControlOn;
        }
        
        /**
        
        */
        public void setHeadLightsControlOn(boolean x){
            headLightsControlOn=x;
        }
        
        /**
        
        */
        public int getHVACSetpoint(){
            return hvacSetpoint;
        }
        
        /**
        
        */
        public void setHVACSetpoint(int x){
            hvacSetpoint=x;
        }
        
        /**
        
        */
        public float getDriverSpeed(){
            return driverSpeed;
        }
        
        /**
        
        */
        public void setDriverSpeed(float x){
            driverSpeed=x;
        }
        
        /**
        
        */
        public boolean getEmergencyBrakeControlOn(){
            return emergencyBrakeControlOn;
        }
        
        /**
        
        */
        public void setEmergencyBrakeControlOn(boolean x){
            emergencyBrakeControlOn=x;
        }
        
        /**
        
        */
        public boolean getServiceBrakeControlOn(){
            return serviceBrakeControlOn;
        }
        
        /**
        
        */
        public void setServiceBrakeControlOn(boolean x){
            serviceBrakeControlOn=x;
        }
    
    
    
    // /**
        
    // */
    //// public class Train{
        
        // /**
        
        // */
        // public String getError(){
            // return attachedTrain.getError();
        // }
        
        // /**
        
        // */
        // public float getAuthority(){
            // return attachedTrain.getAuthority();
        // }
        
        // /**
        
        // */
        // public float getSuggestedSpeed(){
            // return attachedTrain.getSuggestedSpeed();
        // }
        
        // /**
        
        // */
        // public float getActualSpeed(){
            // return attachedTrain.getActualSpeed();
        // }
        
        // /**
        
        // */
        // public float getAcceleration(){
            // return attachedTrain.getAcceleration();
        // }
        
        // /**
        
        // */
        // public float getPower(){
            // return attachedTrain.getPower();
        // }
        
        // /**
        
        // */
        // public String getBeacon(){
            // return attachedTrain.getBeacon();
        // }
        
        // /**
        
        // */
        // public String getMap(){
            // return attachedTrain.getMap();
        // }
    // }
    
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
    }
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