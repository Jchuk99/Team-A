package src;

import javafx.application.Application;
import javafx.stage.Stage;
import src.ctc.CTCUI;
import src.train_controller.*;
import src.track_controller.*;
import src.train_module.*;
import src.track_module.*;

public class ApplicationUI extends Application {
    @Override
    public void start(Stage primaryStage) {

        CTCUI ctcUI = new CTCUI();
        TrackModuleUI trackModuleUI = new TrackModuleUI();
        WaysideUI trackControllerUI = new WaysideUI();
        TrainModuleUI trainModuleUI = new TrainModuleUI();
        TrainControllerUI trainControllerUI = new TrainControllerUI();

        ctcUI.show();
        trackModuleUI.show();
        trackControllerUI.show();
        trainModuleUI.show();
        trainControllerUI.show();

        
    }
}