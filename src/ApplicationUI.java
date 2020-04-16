package src;

import java.util.HashSet;

import src.ctc.*;
import src.train_controller.*;
import src.track_controller.*;
import src.train_module.*;
import src.track_module.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class ApplicationUI extends Application {
    @Override
    public void start(Stage primaryStage) {

        ClockUI clockUI = new ClockUI();

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

        for( Module module : modules) {
            module.main();
        }

        // Nothing else works until it gets map information from the track Module.
        TrackModuleUI trackModuleUI = new TrackModuleUI();
        
        CTCUI ctcUI = new CTCUI();
        WaysideUI trackControllerUI = new WaysideUI();
        TrainModuleUI trainModuleUI = new TrainModuleUI();
        TrainControllerUI trainControllerUI = new TrainControllerUI();

        ctcUI.show();
        trackModuleUI.show();
        trackControllerUI.show();
        trainModuleUI.show();
        trainControllerUI.show();

        clockUI.show();
        
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Runnable updater = new Runnable() {

                    @Override
                    public void run() {
                        for (int i = 0; i < clockUI.clockStepAmount; i++) {
                            clockUI.tickTock();
                            for(Module module: modules) {
                                module.tickTock();
                            }
                        }
                    }
                };

                while (true) {
                    try {
                        Thread.sleep(clockUI.clockTimeStep);
                    } catch (InterruptedException e) {
                    }

                    // UI update is run on the Application thread
                    Platform.runLater(updater);
                }
            }

        });
        // don't let thread prevent JVM shutdown
        thread.setDaemon(true);
        thread.start();
    }

}