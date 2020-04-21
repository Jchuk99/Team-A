package src.track_controller;

import javafx.application.Application;
import javafx.stage.Stage;
import src.track_module.*;


public class ApplicationTrackControllerUI extends Application {
    @Override
    public void start(Stage primaryStage) {
        TrackModuleUI trackModelUI = new TrackModuleUI();
        trackModelUI.showAndWait();
        WaysideUI trackControllerUI = new WaysideUI();
        trackControllerUI.show();      
    }
}