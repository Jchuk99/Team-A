package src;

import src.track_controller.TrackControllerModule;
import src.track_controller.WaysideUI;
import src.track_module.TrackModule;
import src.train_controller.TrainControllerModule;
import src.train_module.TrainModule;

import java.util.HashSet;

import javafx.application.Application;
import javafx.stage.Stage;
import src.ctc.CTCModule;
import src.ctc.CTCUI;

class Main { 
    public static void main(String args[])
    { 
        TrackModule trackModule= new TrackModule();
        //TrainControllerModule trainControllerModule= new TrainControllerModule();
        TrackControllerModule trackControllerModule= new TrackControllerModule();
        //TrainModule trainModule= new TrainModule();
        CTCModule ctcModule= new CTCModule();

        HashSet<Module> modules= new HashSet<Module>();
        modules.add( trackModule);
        //modules.add( trainControllerModule);
        modules.add( trackControllerModule);
        //modules.add( trainModule);
        modules.add( ctcModule);
        for( Module module : modules) {
            module.setCTCModule(ctcModule);
            module.setTrackControllerModule(trackControllerModule);
            //module.setTrainControllerModule(trainControllerModule);
            module.setTrackModule(trackModule);
            module.setTrainModule(trainModule);
        }
        new Thread() {
            @Override
            public void run() {
                Application.launch(ApplicationUI.class);
            }
        }.start();




        for( Module module : modules) {
            module.main();
        }
    } 
} 