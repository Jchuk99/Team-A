package src.train_controller;

import javafx.application.Application;
import src.track_module.BlockConstructor.Normal;
import src.train_module.Train;

public class test {

    public static void main(String[] args){
        
        TrainControllerModule tcm=new TrainControllerModule();
        
        TrainController tc=tcm.createTrainController();
        Normal b=new Normal("RED", 'Z', 0, 123456, (float)89.5, (float)0.0, 
        (float)0.0, (float)0.0, false, 0, 0);
        //Train t=new Train(3,tc);
        //tc.attachTrain(t);
        
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