package src.track_module;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import src.UICommon;

public class TrackModuleUI extends Stage {
    static final int WIDTH = 900;
    static final int HEIGHT = 800;
    Pane graphPane;

    VBox crossingBox;
    VBox stationBox;

    public static TrackModule trackModule;

    public static void setTrackModule(TrackModule tm){
        trackModule = tm;
    }

    public TrackModuleUI() {
        setTitle("TrackModel UI");
        
        /****** temperature and track file ******/
        VBox temperatureLabel = UICommon.createLabelBox("47 F");
        final HBox temperatureBox = new HBox(10, UICommon.createTextBox("Temperature"), temperatureLabel);

        // select track file button
        VBox buttonBox = UICommon.createButtonBox("Select Track File", 150, 30);
        Button button = (Button)buttonBox.getChildren().get(0);
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
                Map.buildMap(trackModule.blocks, graphPane);
            }
        });
        final HBox topBox = new HBox(10, temperatureBox, UICommon.createHSpacer(), buttonBox, UICommon.createHSpacer());
        /****** temperature and track file ******/

        /****** Track Information ******/
        final HBox trackInfoBox = createTrackInfoBox();
        trackInfoBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 1; -fx-padding: 10;");
        /****** Track Information ******/

        /****** signal light ******/
        final HBox signalLightBox = createSignalLightBox();
        signalLightBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 1; -fx-padding: 10;");
        /****** signal light ******/
        
        /****** block ID ******/
        final HBox blockIDBox = createBlockIDBox();
        blockIDBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 1; -fx-padding: 10;");
        /****** block ID ******/

        /****** infrastructure ******/
        final HBox infrastructureBox = createInfrastructureBox();
        infrastructureBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 1; -fx-padding: 10;");
        /****** infrastructure ******/

        /****** beacon ******/
        final HBox beaconBox = new HBox(10, UICommon.createLabelBox("STATION; CASTLE SHANNON; BLOCK 96;"));
        beaconBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 1; -fx-padding: 10;");
        /****** beacon ******/
        
        // combining boxes
        final VBox box1 = new VBox(10, signalLightBox, blockIDBox);
        final HBox box2 = new HBox(10, box1, infrastructureBox);
        final VBox box3 = new VBox(10, box2, beaconBox);
        final HBox box4 = new HBox(10, trackInfoBox, box3);

        final VBox topHalf = new VBox(10, topBox, box4);

        graphPane = new Pane();
        VBox fullScreen = new VBox(10, topHalf, graphPane);
        fullScreen.setPadding(new Insets(10));
        setScene(new Scene(fullScreen, WIDTH, HEIGHT));
    }

    private HBox createTrackInfoBox() {
        // TODO: pull data from module
        VBox name1 = UICommon.createTextBox("Length");
        VBox name2 = UICommon.createTextBox("Speed Limit");
        VBox name3 = UICommon.createTextBox("Grade");
        VBox name4 = UICommon.createTextBox("Elevation");
        VBox name5 = UICommon.createTextBox("Underground");
        VBox nameBox1 = new VBox(10, name1, name2, name3, name4, name5);

        VBox name6 = UICommon.createTextBox("Track Heater");
        VBox name7 = UICommon.createTextBox("Block Status");
        VBox nameBox2 = new VBox(10, name6, name7);

        VBox label1 = UICommon.createLabelBox("330 ft");
        VBox label2 = UICommon.createLabelBox("24 mph");
        VBox label3 = UICommon.createLabelBox("1.5 %");
        VBox label4 = UICommon.createLabelBox("3 ft");
        VBox label5 = UICommon.createLabelBox("false");
        VBox labelBox1 = new VBox(10, label1, label2, label3, label4, label5);

        VBox label6 = UICommon.createLabelBox("OFF");
        VBox label7 = UICommon.createLabelBox("OCCUPIED");
        VBox buttonBox = UICommon.createButtonBox("Insert Failure", 100, 30);
        Button button = (Button)buttonBox.getChildren().get(0);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO: button handle
            }
        });
        VBox labelBox2 = new VBox(10, label6, label7, buttonBox);


        HBox trackInfoBox = new HBox(10, UICommon.createHSpacer(), nameBox1 , UICommon.createHSpacer(), labelBox1, UICommon.createHSpacer(), nameBox2, UICommon.createHSpacer(), labelBox2, UICommon.createHSpacer());
        return trackInfoBox;
    }

    private HBox createSignalLightBox() {
        Circle circleG = UICommon.createCircle(10, Color.GREEN);
        Circle circleY = UICommon.createCircle(10, Color.YELLOW);
        Circle circleR = UICommon.createCircle(10, Color.RED);
        final HBox circleBox = new HBox(10, circleG, circleY, circleR);
        circleBox.setAlignment(Pos.CENTER);
        circleBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 2; -fx-padding: 10;");

        final HBox SignalLightBox = new HBox(10, UICommon.createTextBox("Signal Light"), circleBox);
        return SignalLightBox;
    }

    private HBox createBlockIDBox() {
        VBox label = UICommon.createLabelBox("12");
        HBox blockIDBox = new HBox(10, UICommon.createHSpacer(), UICommon.createTextBox("Block ID"), label, UICommon.createHSpacer());
        blockIDBox.setAlignment(Pos.CENTER);
        return blockIDBox;
    }

    private HBox createInfrastructureBox() {
        VBox crossingLabel = UICommon.createLabelBox("Crossing");

        VBox statusLabel = UICommon.createLabelBox("");
        HBox statusBox = new HBox(10, UICommon.createTextBox("Status"), statusLabel);

        crossingBox = new VBox(10, crossingLabel, statusBox, UICommon.createVSpacer());
        crossingBox.setAlignment(Pos.CENTER);

        VBox stationLabel = UICommon.createLabelBox("Station");

        VBox stationNameLabel = UICommon.createLabelBox("Central");
        HBox stationNameBox = new HBox(10, UICommon.createTextBox("Station Name"), stationNameLabel);

        VBox ticketSaleLabel = UICommon.createLabelBox("30 / h");
        HBox ticketSaleBox = new HBox(10, UICommon.createTextBox("Ticket Sale"), ticketSaleLabel);

        stationBox = new VBox(10, stationLabel, stationNameBox, ticketSaleBox);
        stationBox.setAlignment(Pos.CENTER);

        crossingBox.setVisible(false);
        crossingBox.setManaged(false);
        stationBox.setVisible(false);
        stationBox.setManaged(false);
        
        HBox infrastructureBox = new HBox(10, crossingBox, stationBox);
        return infrastructureBox;
    }

    private void switchInfrastructureBox(String infrastructure) {
        crossingBox.setVisible(false);
        crossingBox.setManaged(false);
        stationBox.setVisible(false);
        stationBox.setManaged(false);
        if (infrastructure == "Station") {
            stationBox.setVisible(true);
            stationBox.setManaged(true);
        } else if (infrastructure == "Crossing") {
            crossingBox.setVisible(true);
            crossingBox.setManaged(true);
        }
    }
}