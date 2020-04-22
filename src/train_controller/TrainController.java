package src.train_controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import java.lang.Float;
import java.lang.Integer;

import java.math.*;

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
    private float v_cmd2;
    //float v_cmd_prev;
    private float v_curr;
    private float v_curr2;
    private float v_err;
    private float v_err2;
    //float v_err_prev;
    private float power;
    private float v_prev;
    private float v_prev2;
    //float TIMESTEP=(float)50.0; //ms
    private float kp=(float)20.0;
    private float ki=(float)10.0;
    private StringProperty timerMan=new SimpleStringProperty("11:00:23");
    private StringProperty beacon=new SimpleStringProperty("STATION; CASTLE SHANNON; BLOCK 96");
    private StringProperty oldBeacon=new SimpleStringProperty("STATION; CASTLE SHANNON; BLOCK 96");
    private StringProperty controlLaw=new SimpleStringProperty("20,10");
    private boolean beaconUpdate=false;
    private boolean pickup;
    private float slowdownSpeed=(float)12.5;
    private float crawlSpeed=(float)0.2;
    private float slowdownAuth=(float)4.0;
    private float crawlAuth=(float)1.0;
    private float midStop=(float)33.0;
    private float crawlDistance=(float)0.0;
    private float timeStep=(float)(50.0/1000.0);
    private boolean radicalSlowDown1;
    private boolean radicalSlowDown2;
    public BooleanProperty removeFlag = new SimpleBooleanProperty(false);
    /**
    
    */
    public TrainController(TrainControllerModule tcm){ //
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
        trainControllerModule=tcm;
        authority=(float)0.0;
        suggestedSpeed=(float)0.0;
        v_curr=(float)0.0;
        timerMan.bind(trainControllerModule.timeString);
        
    }

    public void attachTrain(Train train) {
        attachedTrain = train;
        UUID = train.getUUID();
    }

    public void update() {
        
        if (attachedTrain == null) {
            return;
        }
        
        //testing ONLY
        /*suggestedSpeed=(float)40.0;
        authority=(float)40.0;
        */
        //getSA(); //use until regular updates from track Module are implemented

        //check if beacon updates
        if(!oldBeacon.getValueSafe().equalsIgnoreCase(beacon.getValueSafe())){
            oldBeacon.setValue(beacon.getValueSafe());
            beaconUpdate=true;
            
            // update midStop if half-block length greater than 33 m 
            /*if(((float)0.5*Float.parseFloat(beacon.getValueSafe().split(";")[3]))-(float)33.0>(float)0.0001){
                //midStop=Float.parseFloat(beacon.getValueSafe().split(";")[3]);
            }*/
        }
        timeStep = ((float)trainControllerModule.TIMESTEP) / 1000;
        if(authority<=(float)0.1){
            attachedTrain.setPower(0);
            setEmergencyBrakeControlOn(true);
            setServiceBrakeControlOn(true);
            System.out.println("Authority<0.1");
        }
        else if(vitalCheck1()!=vitalCheck2()){
            attachedTrain.setPower(0);
            setEmergencyBrakeControlOn(true);
            setServiceBrakeControlOn(true);
            System.out.println("Vitality vitality failed");            
        }
        else{
            setPowerVote();//comment out for testing
            //testing code only, to test if setPower 1 logic works
            /*attachedTrain.setPower(setPower1());
            if(authority-crawlAuth<((float)0.01) && midStop-crawlDistance>((float)0.01)){
                crawlDistance=crawlDistance+(float)0.5*timeStep*(v_curr+v_prev);
            }
            System.out.println("Authority: "+authority);
            System.out.println("CrawlDistance: "+crawlDistance);*/
        }

        //check lights needs to be done
        if(timerMan.getValueSafe().equalsIgnoreCase("06:00:00")){
            setHeadLightsControlOn(false);
            setCabinLightsControlOn(false);
        }
        else if(timerMan.getValueSafe().equalsIgnoreCase("18:00:00")){
            setHeadLightsControlOn(true);
            setCabinLightsControlOn(true);
        }

        //door opening and closing
        
        //announcing 
        if(beaconUpdate){
            announce(beacon.getValueSafe());
        }

        //update controlLaw string for future updating
        stringControl();

        //reset beaconUpdate
        beaconUpdate=false;
        pickup=false;
    }
    public boolean vitalCheck1(){
        boolean vitalFlag=true;
        if(!getEngineWorking().get()){
            vitalFlag=false;
            System.out.println("engine not working");
        }
        if(!getEmergencyBrakeWorking().get()){
            vitalFlag=false;
            System.out.println("emergency brake not working");
        }
        if(!getServiceBrakeWorking().get()){
            vitalFlag=false;
            System.out.println("service brake not working");
        }
        if(!getLeftDoorWorking().get() && !attachedTrain.getLeftDoorState().get()){
            vitalFlag=false;
            System.out.println("left door not closing");
        }
        if(!getRightDoorWorking().get() && !attachedTrain.getRightDoorState().get()){
            vitalFlag=false;
            System.out.println("right door not closing");
        }
        /*if(!getLightWorking().get()){
            vitalFlag=false;
            System.out.println("right door not working");
        }*/ //need to figure out if lights are vital
        
        
        return vitalFlag;
    }

    public boolean vitalCheck2(){
        boolean vitalFlag=false;
        if(getEngineWorking().get() && getEmergencyBrakeWorking().get() && getServiceBrakeWorking().get() && getLeftDoorWorking().get() && getRightDoorWorking().get()){
            vitalFlag=true;
            return vitalFlag;
        }

        System.out.println("Engine Fine: "+getEngineWorking().get() +"\nEBrake Fine: "+getEmergencyBrakeWorking().get() +"SBrake Fine: "+ getServiceBrakeWorking().get() + "LDoors Fine: "+getLeftDoorWorking().get() +"RDoors Fine: "+ getRightDoorWorking().get());
        return vitalFlag;
    }

    public void setPowerVote(){
        float power1=setPower1();
        float power2=setPower2();
        if(power1-power2<((float)0.01) && power1-power2>(float)(-0.01)){
            attachedTrain.setPower(power1);
        }
        else{
            attachedTrain.setPower(0);
            setEmergencyBrakeControlOn(true);
            setServiceBrakeControlOn(true);
            System.out.println("Power vitality failed: P1: "+power1+" P2: "+power2);
            System.out.println("Authority: "+authority);
            System.out.println("Radical Slowdown 1: "+radicalSlowDown1+" 2: "+radicalSlowDown2);
            System.out.println("VCurr 1: "+v_curr+" 2: "+v_curr2);
            System.out.println("VCmd 1: "+v_cmd+" 2 "+v_cmd2);
        }
        if(authority-crawlAuth<((float)0.01) && midStop-crawlDistance>((float)0.01)){
            crawlDistance=crawlDistance+(float)0.5*timeStep*(v_curr+v_prev);
        }
    }
    public float setPower1(){
        //current speed
        v_prev=v_curr;
        if(attachedTrain.getCurrentSpeed().getValueSafe().isEmpty()){
            v_curr=(float)0.0;
        }
        else{
            v_curr=Float.parseFloat(attachedTrain.getCurrentSpeed().getValueSafe().split(" ")[0]);
        }

        //slowdown and crawl
        if (authority-slowdownAuth<((float)0.01) && authority-crawlAuth>((float)0.01)){
            
            v_cmd=slowdownSpeed;
            
            if(v_curr-v_cmd>((float)0.01)){
                setServiceBrakeControlOn(true);
                radicalSlowDown1=true;
            }
            else{
                setServiceBrakeControlOn(false);
                radicalSlowDown1=false;
            }
            
        }
        else if (authority-crawlAuth<((float)0.01) && midStop-crawlDistance>((float)0.01)){
            v_cmd=crawlSpeed;
            if(v_curr-v_cmd>((float)0.01)){
                setServiceBrakeControlOn(true);
                //return (float)0.0;
                radicalSlowDown1=true;
            }
            else{
                setServiceBrakeControlOn(false);
                radicalSlowDown1=false;
            }
            //update crawlDistance
            //crawlDistance=crawlDistance+(float)0.5*timeStep*(v_curr+v_prev);
            
        }//final stop
        else if (authority-crawlAuth<((float)0.01) && midStop-crawlDistance<((float)0.01)){
            v_cmd=(float)0.0;
            setServiceBrakeControlOn(true);
            //return (float)0.0;
            radicalSlowDown1=true;

        }
        //if none of the above, get suggested/driver speed
        else if(manualModeOn.getValue()){
            if(driverSpeed.getValueSafe().isEmpty()){
                v_cmd=(float)0.0;
            }
            else{
                v_cmd=Float.parseFloat(driverSpeed.getValueSafe().split(" ")[0]);
            }
            //safety check: if driver speed greater than suggested speed, use suggested speed
            if(v_cmd>suggestedSpeed){
                v_cmd=suggestedSpeed;
            }
            radicalSlowDown1=false;

            /*if(crawlDistance>(float)(0.0)){
                crawlDistance=(float)0.0;
            }*/

            if (v_curr-v_cmd<=(float)2.0){
                setServiceBrakeControlOn(false);
            }
            else{
                setServiceBrakeControlOn(true);
            }

        }
        else {
            v_cmd=suggestedSpeed;
            radicalSlowDown1=false;
            System.out.println("Triggering here");
           /* if(crawlDistance>(float)(0.0)){
                crawlDistance=(float)0.0;
            }*/

            if (v_curr-v_cmd<=(float)2.0){
                setServiceBrakeControlOn(false);
            }
            else{
                setServiceBrakeControlOn(true);
            }
        }
        
        //braking distance calc (does this violate non-constant-acceleration)
        //aS+0.5*(V_curr-0)^2+g(h1-h2)=0
        //S=(1/a)(-g(h1-h2)-0.5*V_curr^2)
        //biggest grade=5% and -5%
        //acceleration IS NOT A CONSTANT

         //stupid soln (dry track, 5% grade downhill, 19.125 meter change in elevation)
        //S=1.7*(1/(-2.93-2.73))*(-9.81*(19.125)-0.5*V_curr^2)
        //for slightly over top speed (20 m/s), 116.43 m (within 4 blocks) to eBrake 
        //159.56 m (witihn 5 blocks) to sBrake, 224.90 m (within 7 blocks) just powered down to 0

        int eBrakeDistance=(int)(1.7*(1/(-2.93-2.73))*(-9.81*(5)-0.5*v_curr*v_curr));
        int sBrakeDistance=(int)(1.7*(1/(-2.93-1.2))*(-9.81*(5)-0.5*v_curr*v_curr));
        int noPowerDistance= (int) ( 1.7 * (1 / (-2.93)) * (-9.81 * (5) - 0.5 * v_curr * v_curr));


        v_err=v_cmd-v_curr;
        //v_err_prev=v_cmd_prev-v_prev;
        

        
        

        //v_cmd_prev=v_cmd_curr;
        //v_prev=v_curr;
        if(authority<=0){    
            return (float)(0.0);
        }
        else if(!radicalSlowDown1){
            return (float)(v_err*kp+v_curr*ki);
        
        }
        else{
            return (float)(0.0);
        }
        
        //attachedTrain.setPower(power);
        //return power;
    }

    public float setPower2(){
       v_prev2=v_curr2;
        if(!attachedTrain.getCurrentSpeed().getValueSafe().equalsIgnoreCase("")){
            v_curr2=Float.parseFloat(attachedTrain.getCurrentSpeed().getValueSafe().split(" ")[0]);
        }
        else{
            v_curr2=(float)0.0;
        }
       
        //slowdown and crawl
        if (slowdownAuth-authority>((float)(-0.01)) && crawlAuth-authority<((float)(-0.01))){
            
            v_cmd2=slowdownSpeed;
            
            if(v_cmd2-v_curr2<((float)(-0.01))){
                setServiceBrakeControlOn(true);
                radicalSlowDown2=true;
            }
            else{
                setServiceBrakeControlOn(false);
                radicalSlowDown2=false;
            }
            
        }
        else if (crawlAuth-authority>((float)(-0.01)) && crawlDistance-midStop<((float)(-0.01))){
            v_cmd2=crawlSpeed;
            if(v_cmd2-v_curr2<((float)(-0.0001))){
                setServiceBrakeControlOn(true);
                //return (float)0.0;
                radicalSlowDown2=true;
            }
            else{
                setServiceBrakeControlOn(false);
                radicalSlowDown2=false;
            }
            //update crawlDistance
            //crawlDistance=crawlDistance-(((v_curr2)/(float)(-1.0))-v_prev2)/(((float)2.0)/timeStep);
            
        }
        else if (crawlAuth-authority>((float)(-0.01)) && crawlDistance-midStop>((float)(-0.01))){
            v_cmd2=(float)0.0;
            setServiceBrakeControlOn(true);
            //return (float)0.0;
            radicalSlowDown2=true;

        }
        

        /*//TESTING SETPOWER 1 setup
        if (authority-slowdownAuth<((float)0.0001) && authority-crawlAuth>((float)0.0001)){
            
            v_cmd2=slowdownSpeed;
            
            if(v_curr2-v_cmd2>((float)0.0001)){
                setServiceBrakeControlOn(true);
                radicalSlowDown2=true;
            }
            else{
                setServiceBrakeControlOn(false);
                radicalSlowDown2=false;
            }
            
        }
        else if (authority-crawlAuth<((float)0.0001) && midStop-crawlDistance>((float)0.0001)){
            v_cmd2=crawlSpeed;
            if(v_curr2-v_cmd2>((float)0.0001)){
                setServiceBrakeControlOn(true);
                //return (float)0.0;
                radicalSlowDown2=true;
            }
            else{
                setServiceBrakeControlOn(false);
                radicalSlowDown2=false;
            }
            //update crawlDistance
            crawlDistance=crawlDistance+(float)0.5*timeStep*(v_curr2+v_prev2);
            //testing
            ki=crawlDistance;
            
        }
        else if (authority-crawlAuth<((float)0.0001) && midStop-crawlDistance<((float)0.0001)){
            v_cmd2=(float)0.0;
            setServiceBrakeControlOn(true);
            //return (float)0.0;
            radicalSlowDown2=true;
            //testing
            kp=crawlDistance;
        }*/

        //using driver or suggested speed
        else if(!manualModeOn.getValue()){
            v_cmd2=suggestedSpeed;
		    radicalSlowDown2=false;
        }
        else {
            
            if(!driverSpeed.getValueSafe().equalsIgnoreCase("")){ 

                v_cmd2=Float.parseFloat(driverSpeed.getValueSafe().split(" ")[0]);
                
                //safety check: if driver speed greater than suggested speed, use suggested speed
                if(suggestedSpeed<Float.parseFloat(driverSpeed.getValueSafe().split(" ")[0])){
                    v_cmd2=suggestedSpeed;
                }
                radicalSlowDown2=false;
                
                /*if(!(crawlDistance<(float)(0.1))){
                    crawlDistance=(float)0.0;
                }*/
    
                if (v_cmd2+(v_curr2/((float)(-1.0)))<(float)(-1.9)){
                    setServiceBrakeControlOn(true);
                }
                else{
                    setServiceBrakeControlOn(false);
                }

            }
            else{
                v_cmd2=(float)0.0;
                radicalSlowDown2=false;
                
                /*if(!(crawlDistance<(float)(0.1))){
                    crawlDistance=(float)0.0;
                }*/
    
                if (v_cmd2+(v_curr2/((float)(-1.0)))<(float)(-1.9)){
                    setServiceBrakeControlOn(true);
                }
                else{
                    setServiceBrakeControlOn(false);
                }

            }
        }
        
        
        
        //braking distance calc (does this violate non-constant-acceleration)
        //aS+0.5*(V_curr-0)^2+g(h1-h2)=0
        //S=(1/a)(-g(h1-h2)-0.5*V_curr^2)
        //S=(1/a)
        //biggest grade=5% and -5%
        //acceleration IS NOT A CONSTANT

         //stupid soln (dry track, 5% grade downhill, 19.125 meter change in elevation)
        //S=1.7*(1/(-2.93-2.73))*(-9.81*(19.125)-0.5*V_curr^2)
        //for slightly over top speed (20 m/s), 116.43 m (within 4 blocks) to eBrake 
        //159.56 m (witihn 5 blocks) to sBrake, 224.90 m (within 7 blocks) just powered down to 0

        int eBrakeDistance=(int)(1.7*(1/(-2.93-2.73))*(-9.81*(19.125)-0.5*v_curr*v_curr));
        int sBrakeDistance=(int)(1.7*(1/(-2.93-1.2))*(-9.81*(19.125)-0.5*v_curr*v_curr));
        int noPowerDistance= (int) ( 1.7 * (1 / (-2.93)) * (-9.81 * (19.125) - 0.5 * v_curr * v_curr));


        v_err2=v_cmd2+(v_curr2/((float)(-1.0)));
        //v_err_prev=v_cmd_prev-v_prev;
        

        

        //v_cmd_prev=v_cmd_curr;
        //v_prev=v_curr;
        if(authority>(float)0.0 && !radicalSlowDown2){
            return (float)((v_err2/((float)(-1.0)/kp))-(v_curr2/(((float)(1.0))/ki)))/((float)(-1.0));    
        }
        else{
            return (float)0.0;            
        }
        //attachedTrain.setPower(power);
    }

    public void setTrain(float sSpeed, float auth) {
        suggestedSpeed=sSpeed;
        authority=auth;
        pickup=true;
    }

    public void setTestTrain(float sSpeed, float auth) {
        // get set train information from train mode
        attachedTrain.setSpeed(sSpeed);
        attachedTrain.setAuthority(auth);
        
    }

    public void announce(String x){
        System.out.println(x);
    }

    /*public void getSA(){
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
    }*/
    
    public void destroy(){
        attachedTrain=null;
        removeFlag.setValue(true);
        trainControllerModule.destroy(UUID);
    }
    public void nextBlock(){
        authority=authority-(float)1.0;
        if(crawlDistance>(float)(0.0)){
            crawlDistance=(float)0.0;
        }
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
            attachedTrain.setTemperature((float)x);
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
    
        public void setBeacon(String x){
            oldBeacon.setValue(beacon.getValueSafe());
            beacon.setValue(x);
        }

        public StringProperty getBeacon(){
            return beacon;
        }

        public void setKpKi(String x){
            kp=Float.parseFloat(x.split(",")[0]);
            ki=Float.parseFloat(x.split(",")[1]);
        }

        public StringProperty getControlLaw(){
            return controlLaw;
        }

        public void stringControl(){
            controlLaw.setValue(kp+","+ki);
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
