package src.track_module;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import src.UICommon;
import src.ctc.CTCUI;
import src.track_module.TrackModule.FileFormatException;

public class TrackModuleUI extends Stage {
    static final int WIDTH = 900;
    static final int HEIGHT = 800;
    Pane graphPane;
    public static TrackModule trackModule;

    public static void setTrackModule(TrackModule tm){
        trackModule = tm;
    }

    public TrackModuleUI() {
        setTitle("TrackModel UI");
        TrackMap trackMap = new TrackMap();

        HBox topBox = new HBox(10);

        Label temperatureLabel = UICommon.createLabel( trackModule.getTemperature() + "°F");
        temperatureLabel.setAlignment(Pos.CENTER);
        temperatureLabel.setStyle("-fx-font-size: 24;");
        temperatureLabel.prefWidthProperty().bind(topBox.widthProperty().divide((8)));
        trackModule.temperatureProperty().addListener((obs, oldText, newText) -> {
                temperatureLabel.setText( String.valueOf(trackModule.getTemperature()  + "°F"));
            }
        );
        Button increaseTemperature = UICommon.createButton("+", 24, 24);
        increaseTemperature.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int temp = trackModule.getTemperature();
                if(temp < 120) {
                    trackModule.setTemperature(++temp);
                }
            }
        });
        Button decreaseTemperature = UICommon.createButton("-", 24, 24);
        decreaseTemperature.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int temp = trackModule.getTemperature();
                if(temp > -40) {
                    trackModule.setTemperature(--temp);
                }
            }
        });
        
        Label timeLabel = UICommon.createLabel("9:30:00");
        timeLabel.textProperty().bind(trackModule.timeString);
        timeLabel.setAlignment(Pos.CENTER);
        timeLabel.setStyle("-fx-font-size: 24;");
        timeLabel.prefWidthProperty().bind(topBox.widthProperty().divide((4)));

        // select track file button
        Button selectTrackFile = new Button("Select Track File");
        selectTrackFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open File");
                
                File csvFile= fileChooser.showOpenDialog( null);
                try {
                    trackModule.buildTrack(csvFile.getAbsolutePath());
                }
                catch( IOException | FileFormatException e) {
                    Label errorLabel = UICommon.createLabel(e.toString());
                    errorLabel.setAlignment(Pos.CENTER);
                    errorLabel.setStyle("-fx-font-size: 24; -fx-border-color: -fx-focus-color;");
                    errorLabel.setPadding(new Insets(20));
                    Scene scene = new Scene(errorLabel);
                    scene.getStylesheets().add(Paths.get(System.getenv("cssStyleSheetPath")).toUri().toString());
                    Stage stage = new Stage();
                    stage.setTitle("File Error");
                    stage.setScene(scene);
                    stage.sizeToScene();
                    stage.show();
                    return;
                }
                
                trackMap.buildMap(trackModule.blocks, graphPane);
                CTCUI.buildMap();
                // Removes the select track button and resizes temperature and time boxes
                temperatureLabel.prefWidthProperty().bind(topBox.widthProperty().divide((3)));
                timeLabel.prefWidthProperty().bind(topBox.widthProperty().divide((1.5)));
                topBox.getChildren().setAll(temperatureLabel, increaseTemperature, decreaseTemperature,timeLabel);
            }
        });
        selectTrackFile.prefWidthProperty().bind(topBox.widthProperty().divide((2)));
        
        topBox.getChildren().addAll( temperatureLabel, increaseTemperature, decreaseTemperature, timeLabel, selectTrackFile);
        topBox.setStyle("-fx-background-color: -fx-title-color;");
        topBox.setEffect(new DropShadow(20, new Color(0,0,0,1)));
        topBox.setPadding(new Insets(10));
        topBox.setViewOrder(0);

        graphPane = new Pane();
        graphPane.setStyle("-fx-background-color: -fx-focus-color;");
        VBox.setVgrow(graphPane, Priority.ALWAYS);
        graphPane.setViewOrder(1);
        trackMap.mapUnavailable(graphPane);

        Region spacer = new Region();
        spacer.setMinHeight(30);
        spacer.setViewOrder(1);
        spacer.setStyle("-fx-background-color: -fx-focus-color;");


        VBox fullScreen = new VBox(topBox, spacer, graphPane);
        Scene scene = new Scene(fullScreen, WIDTH, HEIGHT);
        scene.getStylesheets().add(Paths.get(System.getenv("cssStyleSheetPath")).toUri().toString());
        setScene(scene);
        showAndWait();
    }
}