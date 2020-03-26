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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import src.UICommon;

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

        HBox topBox = new HBox(10);

        Label temperatureLabel = UICommon.createLabel( System.getenv("globalTemperature") + "°F");
        temperatureLabel.setAlignment(Pos.CENTER);
        temperatureLabel.setStyle("-fx-font-size: 24;");
        temperatureLabel.prefWidthProperty().bind(topBox.widthProperty().divide((4)));
        
        Label timeLabel = UICommon.createLabel( System.getenv("globalTime"));
        timeLabel.setAlignment(Pos.CENTER);
        timeLabel.setStyle("-fx-font-size: 24;");
        timeLabel.prefWidthProperty().bind(topBox.widthProperty().divide((4)));

        // select track file button
        Button button = new Button("Select Track File");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open File");
                
                File csvFile= fileChooser.showOpenDialog( null);
                try {
                    trackModule.buildTrack(csvFile.getAbsolutePath());
                }
                catch( IOException e) {
                    // TODO THIS
                }
                TrackMap trackMap = new TrackMap();
                trackMap.buildMap(trackModule.blocks, graphPane);
                temperatureLabel.prefWidthProperty().bind(topBox.widthProperty().divide((2)));
                timeLabel.prefWidthProperty().bind(topBox.widthProperty().divide((2)));
                topBox.getChildren().setAll(temperatureLabel, timeLabel);
            }
        });
        button.prefWidthProperty().bind(topBox.widthProperty().divide((2)));
        
        topBox.getChildren().addAll( temperatureLabel, timeLabel, button);
        topBox.setStyle("-fx-background-color: -fx-title-color;");
        topBox.setEffect(new DropShadow(20, new Color(0,0,0,1)));
        topBox.setPadding(new Insets(10));
        topBox.setViewOrder(0);

        graphPane = new Pane();
        graphPane.setStyle("-fx-background-color: -fx-focus-color;");
        VBox.setVgrow(graphPane, Priority.ALWAYS);
        graphPane.setViewOrder(1);

        VBox fullScreen = new VBox(topBox, graphPane);
        Scene scene = new Scene(fullScreen, WIDTH, HEIGHT);
        scene.getStylesheets().add(Paths.get(System.getenv("cssStyleSheetPath")).toUri().toString());
        setScene(scene);
        showAndWait();
    }
}