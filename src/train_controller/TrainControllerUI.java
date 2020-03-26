package src.train_controller;

import java.util.ArrayList;
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
import javafx.scene.control.ToggleButton;
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
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

public class TrainControllerUI extends Stage {
    public static TrainControllerModule trainControllerModule;
   
    TrainControllerData trainControllerData = new TrainControllerData();

    private final int width = 900;
    private final int height = 800;

    public TrainControllerUI() {
        setTitle("Train Controller UI");

        /****** select train ******/
        final TableView<TrainController> trainControllerTable = createTrainControllerTable(trainControllerModule.getList());
        trainControllerTable.setPrefWidth(width / 8);

        //final TableView trainTable = createTrainTable(trainData);
        
        final TableView<TrainController> trainTable = new TableView<TrainController>();
        trainTable.setPlaceholder(new Label("No trains available"));

        final TableColumn<TrainController, String> trainTableCol = new TableColumn<TrainController, String>("Select Train");
        trainTableCol.setCellValueFactory(cellData -> cellData.getValue().getName());
        trainTable.getColumns().add(trainTableCol);

        //Error here trainTable.setItems(trainData);

        
        trainTable.setPrefWidth(width / 8);

       // tc=attachedTrainControllerModule.getList().lastElement();

        // testing: add a train to test
        //trainData.add(attachedTrainControllerModule.getList().firstElement());
        
        /*attachedTrainControllerModule.getList().addListener(new ChangeListener<HashSet<TrainControllerModule.TrainController>>() {
            public void changed(ObservableValue <? extends HashSet<TrainControllerModule.TrainController> > observable, HashSet<TrainControllerModule.TrainController> oldValue, HashSet<TrainControllerModule.TrainController> newValue) { 
                
            } 
        });*/
        
        
        
        
        

        trainControllerTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TrainController>() {
            @Override
            public void changed(ObservableValue observableValue, TrainController oldValue, TrainController newValue) {
                //Check whether item is selected and set value of selected item to Label
                TrainController trainController = trainControllerTable.getSelectionModel().getSelectedItem();
                trainControllerData.setTrainController(trainController);
            }
        });

        /****** select train ******/

        /****** beacon ******/
        final HBox beaconBox = new HBox(10, createTextBox("Beacon"), createLabelBox("",trainControllerData.getBeacon()));
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
        final HBox statusBox = createStatusBox();
        statusBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 1; -fx-padding: 10;");
        /****** status ******/

        // combining boxes
        final VBox box1 = new VBox(10, topBox, infoBox);
        final HBox box2 = new HBox(10, trainControllerTable, box1);
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

    public static void setModule(TrainControllerModule module) {
        trainControllerModule = module;
        //trainControllerModule.createTrainController();
    }

    private TableView<TrainController> createTrainControllerTable(ObservableList<TrainController> item) {
        final TableView<TrainController> trainControllerTable = new TableView<TrainController>();
        trainControllerTable.setPlaceholder(new Label("No trains available"));

        final TableColumn<TrainController, String> trainControllerTableCol = new TableColumn<TrainController, String>("Select Train");
        trainControllerTableCol.setCellValueFactory(cellData -> cellData.getValue().getName());
        trainControllerTable.getColumns().add(trainControllerTableCol);

        trainControllerTable.setItems(item);

        return trainControllerTable;
    }

    private HBox createTopBox() {
        VBox time = createLabelBox("11:00:23 am");
        final HBox timeBox = new HBox(10, createTextBox("Time"), time);

        Circle circleG = createCircle(10, Color.GREEN);
        Circle circleY = createCircle(10, Color.YELLOW);
        Circle circleR = createCircle(10, Color.RED);
        final HBox circleBox = new HBox(10, circleG, circleY, circleR);
        circleBox.setAlignment(Pos.CENTER);
        circleBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 2; -fx-padding: 10;");

        final HBox signalBox = new HBox(10, createTextBox("Signal Light"), circleBox);

        final HBox topBox = new HBox(10, createHSpacer(), timeBox, createHSpacer(), signalBox, createHSpacer());
        return topBox;
    }

    private HBox createInfoBox() {
        VBox name1 = createTextBox("Suggested Speed");
        VBox name2 = createTextBox("Current Speed");
        VBox name3 = createTextBox("Authority");
        VBox name4 = createTextBox("Engine Power");
        VBox name5 = createTextBox("Current Acceleration");
        VBox nameBox1 = new VBox(10, name1, name2, name3, name4,name5);
/*
        VBox name5 = createTextBox("Passenger Count");
        VBox name6 = createTextBox("Current Weight");
        
        VBox name8 = createTextBox("Current Grade");
        VBox name9 = createTextBox("Temperature Inside");
        VBox nameBox2 = new VBox(10, name5, name6, name7, name8, name9);
*/
        VBox label1 = createLabelBox("", trainControllerData.getSuggestedSpeed());
        VBox label2 = createLabelBox("", trainControllerData.getCurrentSpeed());
        VBox label3 = createLabelBox("", trainControllerData.getAuthority());
        VBox label4 = createLabelBox("", trainControllerData.getCurrentPower());
        VBox label5 = createLabelBox("", trainControllerData.getCurrentAcceleration());
        VBox labelBox1 = new VBox(10, label1, label2, label3, label4,label5);
/*
        VBox label6 = createLabelBox("", trainData.getPassengetCount());
        VBox label7 = createLabelBox("", trainData.getCurrentWeight());
        VBox label8 = createLabelBox("", trainData.getCurrentGrade());
        VBox label9 = createLabelBox("", trainData.getTemperature());
        VBox labelBox2 = new VBox(10, label5, label6, label7, label8, label9);
  */
        VBox controlBox=createControlBox(trainControllerData.getDriverSpeed(),trainControllerData.getHVACSetpoint());      
        final HBox infoBox = new HBox(10, createHSpacer(), nameBox1, labelBox1, createHSpacer(), controlBox, createHSpacer());
        return infoBox;
    }

    private VBox createControlBox(StringProperty driverSpeed, StringProperty hvacSetpoint){
        ToggleButton manControlToggle=createToggleButton("Manual Mode",150,40);
        manControlToggle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO: button handle
                if (manControlToggle.isSelected()==false){
                    trainControllerData.setManualModeOn(false);
                }
                else{
                    trainControllerData.setManualModeOn(true);
                }
            }
        });
        
        Slider speedSlider = new Slider();
        speedSlider.setMin(0); 
        speedSlider.setMax(43); 
        speedSlider.setValue(20); 
        VBox speedLabel = createLabelBox("0 mph", driverSpeed);
        //speedLabel.setPrefWidth(200);
        speedSlider.valueProperty().addListener( new ChangeListener<Number>() { 
           
            public void changed(ObservableValue <? extends Number > observable, Number oldValue, Number newValue) { 
                trainControllerData.setDriverSpeed(newValue.intValue());
                //System.out.println(trainControllerData.getDriverSpeed()); 
           } 
        });
        VBox speedLabelLabel = createLabelBox("Driver Speed");
        HBox speedStuff=new HBox(10, speedLabel,speedLabelLabel);
        VBox manControlBox= new VBox(10,manControlToggle,speedStuff,speedSlider);
        manControlBox.setPrefHeight(150);
		manControlBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 2; -fx-padding: 10;");
    
        VBox hvacTitle=createTextBox("HVAC");
		Slider tempSlider = new Slider();
        tempSlider.setMin(60); 
        tempSlider.setMax(80); 
        tempSlider.setValue(68); 
		VBox hvacSetpointBox=createLabelBox("68 deg F",hvacSetpoint);
        tempSlider.valueProperty().addListener( new ChangeListener<Number>() { 
           
            public void changed(ObservableValue <? extends Number > observable, Number oldValue, Number newValue) { 
               //hvacSetpoint.setValue(newValue.intValue()+" deg F"); 
               trainControllerData.setHVACSetpoint(newValue.intValue());
               //System.out.println(trainControllerData.getHVACSetpoint());
           } 
       }); 
       hvacSetpointBox.setPrefWidth(120);
		HBox hvacControl=new HBox(hvacTitle,tempSlider,hvacSetpointBox);
		
		VBox midControlBox=new VBox(5,manControlBox,hvacControl);
		midControlBox.setPrefHeight(height/4);
		midControlBox.setPrefWidth(height/4);
		midControlBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 2; -fx-padding: 10;");
		return midControlBox;
	}

    private HBox createStatusBox() {
        /*VBox name1 = createTextBox("Left Door Closed");
        VBox name2 = createTextBox("Right Door Closed");
        VBox name3 = createTextBox("Cabin Lights On");
        VBox name4 = createTextBox("Headlights On");
        VBox name5 = createTextBox("Service Brake");
        */
        VBox name6 = createLabelBox("Train Engine");
        //VBox nameBox = new VBox(10, createVBox(), name1, name2, name3, name4, name5, name6);
        

        VBox light1 = createCircleBox(10, Color.GREEN, Color.RED, trainControllerData.getLeftDoorWorking());
        VBox light2 = createCircleBox(10, Color.GREEN, Color.RED, trainControllerData.getRightDoorWorking());
        VBox light3 = createCircleBox(10, Color.GREEN, Color.RED, trainControllerData.getLightWorking());
        VBox light4 = createCircleBox(10, Color.GREEN, Color.RED, trainControllerData.getLightWorking());
        VBox light5 = createCircleBox(10, Color.GREEN, Color.RED, trainControllerData.getServiceBrakeWorking());
        VBox light6 = createCircleBox(10, Color.GREEN, Color.RED, trainControllerData.getEngineWorking());
        VBox light7 = createCircleBox(20, Color.GREEN, Color.RED, trainControllerData.getEmergencyBrakeWorking());
        
        
        VBox lightBox = new VBox(30, createTextBox("Status"), light1, light2, light3, light4, light5, light6, light7);

        // TODO: working button
                ToggleButton button1 = createToggleButton("Left Doors Closed", 200, 40);
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO: button handle
                if (button1.isSelected()==false){
                    trainControllerData.setLeftDoorsControlClosed(false);
                }
                else{
                    trainControllerData.setLeftDoorsControlClosed(true);
                }
            }
        });

        ToggleButton button2 = createToggleButton("Right Doors Closed", 200, 40);
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO: button handle
                if (button2.isSelected()==false){
                    trainControllerData.setRightDoorsControlClosed(false);
                }
                else{
                    trainControllerData.setRightDoorsControlClosed(true);
                }
            }
        });

        ToggleButton button3 = createToggleButton("Cabin Lights On", 200, 40);
        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO: button handle
                if (button3.isSelected()==false){
                    trainControllerData.setCabinLightsControlOn(false);
                }
                else{
                    trainControllerData.setCabinLightsControlOn(true);
                }
            }
        });

        
        ToggleButton button4 = createToggleButton("Headlights On", 200, 40);
        button4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO: button handle
                if (button4.isSelected()==false){
                    trainControllerData.setHeadLightsControlOn(false);
                }
                else{
                    trainControllerData.setHeadLightsControlOn(true);
                }
            }
        });

        ToggleButton button5 = createToggleButton("Service Brake On", 200, 40);
        button5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO: button handle
                if (button5.isSelected()==false){
                    trainControllerData.setServiceBrakeControlOn(false);
                }
                else{
                    trainControllerData.setServiceBrakeControlOn(true);
                }
            }
        });

        
        /*
        final Button ebrakeButton = createButton("Emergency brake", 200, 50);
        ebrakeButton.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-font-size:20; -fx-text-fill: black; -fx-background-color: red;");
        ebrakeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO: button handle
            }
        });
        // TODO: add effect when button is clicked
        DropShadow shadow = new DropShadow();
        //Adding the shadow when the mouse cursor is on
        ebrakeButton.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                ebrakeButton.setEffect(shadow);
            }
        });
        //Removing the shadow when the mouse cursor is off
        ebrakeButton.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                ebrakeButton.setEffect(null);
            }
        });
        */
        final ToggleButton eBrakeButton = createToggleButton("Emergency Brake", 200, 50);
        eBrakeButton.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-font-size:20; -fx-text-fill: black; -fx-background-color: red;");
        eBrakeButton.setSelected(false);
       
        eBrakeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO: button handle
                if (eBrakeButton.isSelected()==false){
                    eBrakeButton.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-font-size:20; -fx-text-fill: black; -fx-background-color: red;");
                    trainControllerData.setEmergencyBrakeControlOn(false);
                    
                }
                else{
                    eBrakeButton.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-font-size:20; -fx-text-fill: black; -fx-background-color: firebrick;");
                    trainControllerData.setEmergencyBrakeControlOn(true);
                    
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
        VBox buttonBox = new VBox(10, createTextBox("Control"), button1, button2, button3, button4, button5, name6,eBrakeButton);

        HBox box = new HBox(10, createHSpacer(), lightBox, createHSpacer(), buttonBox, createHSpacer());
        //final VBox statusBox = new VBox(10, box, eBrakeButton);
        box.setAlignment(Pos.CENTER);
        return box;
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

    private ToggleButton createToggleButton(String text, int width, int height) {
        ToggleButton toggleButton = new ToggleButton();
        toggleButton.setText(text);
        toggleButton.setPrefWidth(width);
        toggleButton.setPrefHeight(height);
        return toggleButton;
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

    private VBox createLabelBox(String text, StringProperty valueProperty) {
        // all VBox create function are unified to 40px height
        Label label = createLabel(text);
        label.textProperty().bind(valueProperty);
        VBox labelBox = new VBox(0, label);
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

    private VBox createCircleBox(int radius, Color color, Color color2, BooleanProperty booleanProperty) {
        // all VBox create function are unified to 40px height
        // boolean true for color, false for color2
        Circle circle = createCircle(radius, color);
        Bindings.when(booleanProperty).then(color).otherwise(color2);
        VBox circleBox = new VBox(circle);
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