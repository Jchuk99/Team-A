package src;

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
import src.ctc.CTCUI;
import src.train_controller.*;
import src.track_controller.*;
import src.train_module.*;
import src.track_module.*;

public class ApplicationUI extends Application {
    final int width = 900;
    final int height = 800;

    VBox crossingBox;
    VBox stationBox;

    @Override
    public void start(Stage primaryStage) {

        CTCUI ctcUI = new CTCUI();
        WaysideUI trackControllerUI = new WaysideUI();
        TrackModuleUI trackModuleUI = new TrackModuleUI();
        TrainModuleUI trainModuleUI = new TrainModuleUI();
        TrainControllerUI trainControllerUI = new TrainControllerUI();

        ctcUI.show();
        //trackControllerUI.show();
        trackModuleUI.show();
        trainModuleUI.show();
        trainControllerUI.show();
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