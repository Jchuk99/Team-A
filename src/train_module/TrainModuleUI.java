package src.train_module;

import src.UICommon;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

public class TrainModuleUI extends Stage {
    public static TrainModule trainModule;

    TrainData trainData = new TrainData();

    final int width = 900;
    final int height = 800;

    public TrainModuleUI() {
        setTitle("TrainModel UI");

        /****** select train ******/
        final TableView<Train> trainTable = createTrainTable(trainModule.getTrainList());
        trainTable.setPrefWidth(width / 8);

        trainTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Train>() {
            @Override
            public void changed(ObservableValue observableValue, Train oldValue, Train newValue) {
                // Check whether item is selected and set value of selected item to Label
                Train train = trainTable.getSelectionModel().getSelectedItem();
                trainData.setTrain(train);
            }
        });
        /****** select train ******/

        /****** beacon ******/
        final HBox beaconBox = new HBox(10, createTextBox("Beacon"), createLabelBox("",trainData.getBeacon()));
        beaconBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 1; -fx-padding: 10;");
        /****** beacon ******/

        /****** top box - time and signal ******/
        final HBox topBox = createTopBox();
        topBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 1; -fx-padding: 10;");
        /****** top box - time and signal ******/

        /****** info ******/
        final HBox infoBox = createInfoBox();
        infoBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 1; -fx-padding: 10;");
        /****** info ******/

        /****** status ******/
        final VBox statusBox = createStatusBox();
        statusBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 1; -fx-padding: 10;");
        /****** status ******/

        // combining boxes
        final VBox box1 = new VBox(10, topBox, infoBox);
        final HBox box2 = new HBox(10, trainTable, box1);
        final VBox box3 = new VBox(10, box2, beaconBox);

        final HBox topHalf = new HBox(10, box3, statusBox);
        // TODO: map
        final TableView mapTable = new TableView();

        topHalf.setPrefHeight(height / 2);
        mapTable.setPrefHeight(height / 2);

        final VBox fullScreen = new VBox(10, topHalf, mapTable);

        fullScreen.setPadding(new Insets(10));

        setScene(new Scene(fullScreen, width, height));
    }

    public static void setModule(TrainModule module) {
        trainModule = module;
    }

    private TableView<Train> createTrainTable(ObservableList<Train> item) {
        final TableView<Train> trainTable = new TableView<Train>();
        trainTable.setPlaceholder(new Label("No trains available"));

        final TableColumn<Train, String> trainTableCol = new TableColumn<Train, String>("Select Train");
        trainTableCol.setCellValueFactory(cellData -> cellData.getValue().getName());
        trainTable.getColumns().add(trainTableCol);

        trainTable.setItems(item);

        return trainTable;
    }

    private HBox createTopBox() {
        VBox time = createLabelBox("", trainModule.timeString);
        final HBox timeBox = new HBox(10, createTextBox("Time"), time);

        // TODO: signal update
        Circle circleG = UICommon.createCircle(10, Color.GREEN);
        Circle circleY = UICommon.createCircle(10, Color.YELLOW);
        Circle circleR = UICommon.createCircle(10, Color.RED);
        final HBox circleBox = new HBox(10, circleG, circleY, circleR);
        circleBox.setAlignment(Pos.CENTER);
        circleBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 2; -fx-padding: 10;");

        final HBox signalBox = new HBox(10, createTextBox("Signal Light"), circleBox);

        final HBox topBox = new HBox(10, UICommon.createHSpacer(), timeBox, UICommon.createHSpacer(), signalBox, UICommon.createHSpacer());
        return topBox;
    }

    private HBox createInfoBox() {
        VBox name1 = createTextBox("Suggested Speed");
        VBox name2 = createTextBox("Current Speed");
        VBox name3 = createTextBox("Authority");
        VBox name4 = createTextBox("Engine Power");
        VBox nameBox1 = new VBox(10, name1, name2, name3, name4);

        VBox name5 = createTextBox("Passenger Count");
        VBox name6 = createTextBox("Current Weight");
        VBox name7 = createTextBox("Current Acceleration");
        VBox name8 = createTextBox("Current Grade");
        VBox name9 = createTextBox("Temperature Inside");
        VBox nameBox2 = new VBox(10, name5, name6, name7, name8, name9);

        VBox label1 = createLabelBox("", trainData.getSuggestedSpeed());
        VBox label2 = createLabelBox("", trainData.getCurrentSpeed());
        VBox label3 = createLabelBox("", trainData.getAuthority());
        VBox label4 = createLabelBox("", trainData.getCurrentPower());
        VBox labelBox1 = new VBox(10, label1, label2, label3, label4);

        VBox label5 = createLabelBox("", trainData.getPassengetCount());
        VBox label6 = createLabelBox("", trainData.getCurrentWeight());
        VBox label7 = createLabelBox("", trainData.getCurrentAcceleration());
        VBox label8 = createLabelBox("", trainData.getCurrentGrade());
        VBox label9 = createLabelBox("", trainData.getTemperatureInside());
        VBox labelBox2 = new VBox(10, label5, label6, label7, label8, label9);
        
        final HBox infoBox = new HBox(10, UICommon.createHSpacer(), nameBox1, labelBox1, UICommon.createHSpacer(), nameBox2, labelBox2, UICommon.createHSpacer());
        return infoBox;
    }

    private VBox createStatusBox() {
        VBox name1 = createTextBox("Left Door");
        VBox name2 = createTextBox("Right Door");
        VBox name3 = createTextBox("Lights");
        VBox name4 = createTextBox("Service Brake");
        VBox name5 = createTextBox("Emergency Brake");
        VBox name6 = createTextBox("Train Engine");
        VBox nameBox = new VBox(10, createVBox(), name1, name2, name3, name4, name5, name6);

        VBox light1 = createCircleBox(10, Color.GREEN, Color.RED, trainData.getLeftDoorWorking());
        VBox light2 = createCircleBox(10, Color.GREEN, Color.RED, trainData.getRightDoorWorking());
        VBox light3 = createCircleBox(10, Color.GREEN, Color.RED, trainData.getLightWorking());
        VBox light4 = createCircleBox(10, Color.GREEN, Color.RED, trainData.getServiceBrakeWorking());
        VBox light5 = createCircleBox(10, Color.GREEN, Color.RED, trainData.getEmergencyBrakeWorking());
        VBox light6 = createCircleBox(10, Color.GREEN, Color.RED, trainData.getEngineWorking());
        VBox lightBox = new VBox(10, createTextBox("Status"), light1, light2, light3, light4, light5, light6);

        Button button1 = UICommon.createButton("Insert Failure", 100, 30);
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                trainData.setLeftDoorWorking(false);
            }
        });
        Button button2 = UICommon.createButton("Insert Failure", 100, 30);
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                trainData.setRightDoorWorking(false);
            }
        });
        Button button3 = UICommon.createButton("Insert Failure", 100, 30);
        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                trainData.setLightWorking(false);
            }
        });
        Button button4 = UICommon.createButton("Insert Failure", 100, 30);
        button4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                trainData.setServiceBrakeWorking(false);
            }
        });
        Button button5 = UICommon.createButton("Insert Failure", 100, 30);
        button5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                trainData.setEmergencyBrakeWorking(false);
            }
        });
        Button button6 = UICommon.createButton("Insert Failure", 100, 30);
        button6.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                trainData.setEngineWorking(false);
            }
        });
        VBox buttonBox = new VBox(10, createTextBox("Insert Failure"), createVBox(button1), createVBox(button2), createVBox(button3), createVBox(button4), createVBox(button5), createVBox(button6));

        HBox box = new HBox(10, UICommon.createHSpacer(), nameBox, UICommon.createHSpacer(), lightBox, UICommon.createHSpacer(), buttonBox, UICommon.createHSpacer());

        final ToggleButton eBrakeButton = UICommon.createToggleButton("Emergency brake", 200, 50);
        eBrakeButton.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-font-size:20; -fx-text-fill: black; -fx-background-color: red;");
        eBrakeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                trainData.setEmergencyBrake(eBrakeButton.isSelected());
            }
        });
        trainData.getEmergencyBrakeState().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue o,Boolean oldVal, Boolean newVal){
                eBrakeButton.setSelected(newVal);
                if(newVal){
                    eBrakeButton.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-font-size:20; -fx-text-fill: black; -fx-background-color: firebrick;");
                } else {
                    eBrakeButton.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-font-size:20; -fx-text-fill: black; -fx-background-color: red;");
                } 
            }
        });
        // TODO: add effect when button is clicked
        DropShadow shadow = new DropShadow();
        //Adding the shadow when the mouse cursor is on
        eBrakeButton.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                eBrakeButton.setEffect(shadow);
            }
        });
        //Removing the shadow when the mouse cursor is off
        eBrakeButton.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                eBrakeButton.setEffect(null);
            }
        });

        final VBox statusBox = new VBox(10, box, eBrakeButton);
        statusBox.setAlignment(Pos.CENTER);
        return statusBox;
    }

    private VBox createVBox() {
        // all VBox create function are unified to 40px height
        VBox box = new VBox(0);
        box.setPrefHeight(40);
        box.setAlignment(Pos.CENTER_LEFT);
        return box;
    }

    private VBox createVBox(Node node) {
        // all VBox create function are unified to 40px height
        VBox box = new VBox(0, node);
        box.setPrefHeight(40);
        box.setAlignment(Pos.CENTER_LEFT);
        return box;
    }

    private VBox createTextBox(String text) {
        // all VBox create function are unified to 40px height
        return createVBox(UICommon.createText(text));
    }

    private VBox createLabelBox(String text, StringProperty valueProperty) {
        // all VBox create function are unified to 40px height
        Label label = UICommon.createLabel(text);
        label.setStyle("-fx-border-style: solid inside; -fx-border-width: 2; -fx-padding: 5;");
        label.textProperty().bind(valueProperty);
        return createVBox(label);
    }

    private VBox createCircleBox(int radius, Color color1, Color color2, BooleanProperty booleanProperty) {
        // all VBox create function are unified to 40px height
        // boolean true for color1, false for color2
        Circle circle = new Circle();
        circle.setRadius(radius);
        Bindings.when(booleanProperty).then(color1).otherwise(color2);
        booleanProperty.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue o,Boolean oldVal, Boolean newVal){
                if (newVal) {
                    circle.setFill(color1);
                } else {
                    circle.setFill(color2);
                }
            }
        });
        return createVBox(circle);
    }

}