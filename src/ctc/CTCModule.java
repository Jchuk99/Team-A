package src.ctc;
import src.track_controller.TrackControllerModule;
import src.track_module.Block;

public class CTCModule{

    //private TrackControllerModule trackController;
    private int test;
    private Schedule schedule = null;
    private TrackControllerModule waysideModule;

    public CTCModule(){

    }

    public CTCModule(TrackControllerModule waysideModule){
        this.waysideModule = waysideModule;
    }

    public int getTest(){
        return test;
    }
    
    public void setTest(int test){
        this.test = test;
    }
    
    public void dispatch(String trainID, double suggestedSpeed, String destination){
        if (schedule == null){
           schedule = new Schedule();
        }
        
        System.out.println(trainID);
        System.out.println(destination);
        System.out.println(suggestedSpeed);
        CTCTrain trainToDispatch = schedule.createTrain(trainID, suggestedSpeed, destination);
        //waysideModule.addTrain(trainToDispatch);

    }
}