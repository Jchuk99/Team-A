package src.track_module;

import java.util.concurrent.CountDownLatch;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.beans.value.ChangeListener; 
import javafx.beans.value.ObservableValue; 
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text; 
import javafx.scene.input.MouseEvent;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TrackModuleUI extends Application {
    final int width = 900;
    final int height = 800;

    VBox crossingBox;
    VBox stationBox;

    public static final CountDownLatch latch = new CountDownLatch(1);
    public static TrackModuleUI trackModuleUI = null;
    public static TrackModule trackModule= null;

    public static void main(final String[] args) {
        launch(args);
    }

    public static TrackModuleUI waitForStartUpTest() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return trackModuleUI;
    }
            
    public static void setStartUpTest(TrackModuleUI trackModuleUI0) {
        trackModuleUI = trackModuleUI0;
        latch.countDown();
    }
    
    public TrackModuleUI(){
        setStartUpTest(this);
    }
    
    public void setCTCModule(TrackModule trackModule0){
        trackModule = trackModule0;
    }

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("TrackModel UI");

        /****** temperature and track file ******/
        VBox temperatureLabel = createLabelBox("47 F");
        final HBox temperatureBox = new HBox(10, createTextBox("Temperature"), temperatureLabel);

        // select track file button
        VBox buttonBox = createButtonBox("Select Track File", 150, 30);
        Button button = (Button)buttonBox.getChildren().get(0);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO: button handle
            }
        });
        final HBox topBox = new HBox(10, temperatureBox, createHSpacer(), buttonBox, createHSpacer());
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
        final HBox beaconBox = new HBox(10, createTextBox("Beacon"), createLabelBox("STATION; CASTLE SHANNON; BLOCK 96;"));
        beaconBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 1; -fx-padding: 10;");
        /****** beacon ******/
        
        // combining boxes
        final VBox box1 = new VBox(10, signalLightBox, blockIDBox);
        final HBox box2 = new HBox(10, box1, infrastructureBox);
        final VBox box3 = new VBox(10, box2, beaconBox);
        final HBox box4 = new HBox(10, trackInfoBox, box3);

        final VBox topHalf = new VBox(10, topBox, box4);
        final TableView mapTable = new TableView();

        topHalf.setPrefHeight(height / 2);
        mapTable.setPrefHeight(height / 2);

        final VBox fullScreen = new VBox(10, topHalf, mapTable);

        fullScreen.setPadding(new Insets(10));

        primaryStage.setScene(new Scene(fullScreen, width, height));
        primaryStage.show();
 
    }

    private HBox createTrackInfoBox() {
        // TODO: pull data from module
        VBox name1 = createTextBox("Length");
        VBox name2 = createTextBox("Speed Limit");
        VBox name3 = createTextBox("Grade");
        VBox name4 = createTextBox("Elevation");
        VBox name5 = createTextBox("Underground");
        VBox nameBox1 = new VBox(10, name1, name2, name3, name4, name5);

        VBox name6 = createTextBox("Track Heater");
        VBox name7 = createTextBox("Block Status");
        VBox nameBox2 = new VBox(10, name6, name7);

        VBox label1 = createLabelBox("330 ft");
        VBox label2 = createLabelBox("24 mph");
        VBox label3 = createLabelBox("1.5 %");
        VBox label4 = createLabelBox("3 ft");
        VBox label5 = createLabelBox("false");
        VBox labelBox1 = new VBox(10, label1, label2, label3, label4, label5);

        VBox label6 = createLabelBox("OFF");
        VBox label7 = createLabelBox("OCCUPIED");
        VBox buttonBox = createButtonBox("Insert Failure", 100, 30);
        Button button = (Button)buttonBox.getChildren().get(0);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO: button handle
            }
        });
        VBox labelBox2 = new VBox(10, label6, label7, buttonBox);


        HBox trackInfoBox = new HBox(10, createHSpacer(), nameBox1 , createHSpacer(), labelBox1, createHSpacer(), nameBox2, createHSpacer(), labelBox2, createHSpacer());
        return trackInfoBox;
    }

    private HBox createSignalLightBox() {
        Circle circleG = createCircle(10, Color.GREEN);
        Circle circleY = createCircle(10, Color.YELLOW);
        Circle circleR = createCircle(10, Color.RED);
        final HBox circleBox = new HBox(10, circleG, circleY, circleR);
        circleBox.setAlignment(Pos.CENTER);
        circleBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 2; -fx-padding: 10;");

        final HBox SignalLightBox = new HBox(10, createTextBox("Signal Light"), circleBox);
        return SignalLightBox;
    }

    private HBox createBlockIDBox() {
        VBox label = createLabelBox("12");
        HBox blockIDBox = new HBox(10, createHSpacer(), createTextBox("Block ID"), label, createHSpacer());
        blockIDBox.setAlignment(Pos.CENTER);
        return blockIDBox;
    }

    private HBox createInfrastructureBox() {
        VBox crossingLabel = createLabelBox("Crossing");

        VBox statusLabel = createLabelBox("");
        HBox statusBox = new HBox(10, createTextBox("Status"), statusLabel);

        crossingBox = new VBox(10, crossingLabel, statusBox, createVSpacer());
        crossingBox.setAlignment(Pos.CENTER);

        VBox stationLabel = createLabelBox("Station");

        VBox stationNameLabel = createLabelBox("Central");
        HBox stationNameBox = new HBox(10, createTextBox("Station Name"), stationNameLabel);

        VBox ticketSaleLabel = createLabelBox("30 / h");
        HBox ticketSaleBox = new HBox(10, createTextBox("Ticket Sale"), ticketSaleLabel);

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
    
    private VBox createVBox() {
        // all VBox create function are unified to 40px height
        VBox box = new VBox(0);
        box.setPrefHeight(40);
        box.setAlignment(Pos.CENTER_LEFT);
        return box;
    }

    private Button createButton(String text, int width, int height) {
        Button button = new Button();
        button.setText(text);
        button.setPrefWidth(width);
        button.setPrefHeight(height);
        return button;
    }

    private VBox createButtonBox(String text, int width, int height) {
        // all VBox create function are unified to 40px height
        VBox buttonBox = new VBox(0, createButton(text, width, height));
        buttonBox.setPrefHeight(40);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        return buttonBox;
    }

    private Text createText(String text) {
        Text t = new Text(text);
        return t;
    }

    private VBox createTextBox(String text) {
        // all VBox create function are unified to 40px height
        VBox textBox = new VBox(0, createText(text));
        textBox.setPrefHeight(40);
        textBox.setAlignment(Pos.CENTER_LEFT);
        return textBox;
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-border-style: solid inside; -fx-border-width: 2; -fx-padding: 5;");
        return label;
    }

    private VBox createLabelBox(String text) {
        // all VBox create function are unified to 40px height
        VBox labelBox = new VBox(0, createLabel(text));
        labelBox.setPrefHeight(40);
        labelBox.setAlignment(Pos.CENTER_LEFT);
        return labelBox;
    }

    private Circle createCircle(int radius, Color color) {
        Circle circle = new Circle();
        circle.setRadius(radius);
        circle.setFill(color);
        return circle;
    }

    private VBox createCircleBox(int radius, Color color) {
        // all VBox create function are unified to 40px height
        VBox circleBox = new VBox(createCircle(radius, color));
        circleBox.setPrefHeight(40);
        circleBox.setAlignment(Pos.CENTER_LEFT);
        return circleBox;
    }

    private static Node createVSpacer() {
        final Region spacer = new Region();
        // Make it always grow or shrink according to the available space
        VBox.setVgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    private static Node createHSpacer() {
        final Region spacer = new Region();
        // Make it always grow or shrink according to the available space
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

}