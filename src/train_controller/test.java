package src.train_controller;

import javafx.application.Application;
import src.track_module.BlockConstructor.Normal;
import src.train_module.Train;
import src.train_module.TrainModule;

public class test {

    public static void main(String[] args){
        
        TrainControllerModule tcm=new TrainControllerModule();
        
        TrainController tc=tcm.createTrainController();
        Normal b=new Normal("RED", 'Z', 0, 123456, (float)89.5, (float)0.0, 
        (float)0.0, (float)0.0, false, 0, 0);
        Train t=new Train(3,tc,b);
        tc.attachTrain(t);
        t.setSpeed((float) 0.0);
        t.setAuthority((float) 0.0);
        t.setPower((float)0.0);
        t.setTemperature((float)60);
        t.update();
        tcm.update();
        Thread thread = new Thread() {
            @Override
            public void run() {
                Application.launch(TestUI.class);
                //TrainControllerUI tcUI=new TrainControllerUI();
            }
        };
        thread.start();
        
        
    }
}