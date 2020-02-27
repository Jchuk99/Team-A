package src;

import src.track_controller.TrackControllerModule;
import src.track_controller.WaysideUI;
import src.track_module.TrackModule;
import src.train_controller.TrainControllerModule;
import src.train_module.TrainModule;

import java.io.IOException;
import java.util.HashSet;

import javafx.application.Application;
import javafx.stage.Stage;
import src.ctc.CTCModule;
import src.ctc.CTCUI;

import java.util.Timer;
import java.util.TimerTask;

class Main {
    public static void main(String args[])
    {

        TrackModule trackModule= new TrackModule();
        TrainControllerModule trainControllerModule= new TrainControllerModule();
        TrackControllerModule trackControllerModule= new TrackControllerModule();
        TrainModule trainModule= new TrainModule();
        CTCModule ctcModule= new CTCModule();

        HashSet<Module> modules= new HashSet<Module>();
        modules.add( trackModule);
        modules.add( trainControllerModule);
        modules.add( trackControllerModule);
        modules.add( trainModule);
        modules.add( ctcModule);

        for( Module module : modules) {
            module.setCTCModule(ctcModule);
            module.setTrackControllerModule(trackControllerModule);
            module.setTrainControllerModule(trainControllerModule);
            module.setTrackModule(trackModule);
            module.setTrainModule(trainModule);
        }

<<<<<<< HEAD
         new Thread() {
=======
        Thread thread = new Thread() {
>>>>>>> b7b80cef50b2e7ef068588e853adaf037e8e6285
            @Override
            public void run() {
                Application.launch(ApplicationUI.class);
            }
<<<<<<< HEAD
        }.start();

        try{
            trackModule.userInterface();
        }
        catch(IOException e){

        }

        ctcModule.getMap();
=======
        };
        thread.start();
>>>>>>> b7b80cef50b2e7ef068588e853adaf037e8e6285

        for( Module module : modules) {
            module.main();
        }

<<<<<<< HEAD
    } 
=======
        // update modules
        // TODO: variable timer speed
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run () {
                if (!thread.isAlive()) timer.cancel();
                for(Module module: modules) {
                    module.tickTock();
                }
            }
        }, 0, 1000);

    }

>>>>>>> b7b80cef50b2e7ef068588e853adaf037e8e6285
} 