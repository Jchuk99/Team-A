/*
 * TrainControllerUI
 * Primary Author: Jacob Heilman
 * Secondary Authors: Calvin Yu, Eric Price, Jason Chukwu, Chi Kwok Lee
 * Version 0.7
 *
 * Copyright notice
 * This program provies a GUI for a train controller. 
 */

package src.train_controller;

import java.util.concurrent.*;

import javafx.application.Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;
import javafx.scene.effect.DropShadow;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import src.train_controller.TrainControllerModule.TrainController;
import src.train_module.Train;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.layout.Priority;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;

import java.lang.Boolean;
 
public class TrainControllerUI extends Application {

    final int width = 900;
    final int height = 800;

    
	public static final CountDownLatch latch=new CountDownLatch(1);
	private static TrainControllerUI tcUI;
	private static TrainController attachedTrainController;
	
	public static TrainControllerUI waitForStartUpTest(){
		try{
			latch.await();
		} catch (InterruptedException e){
			e.printStackTrace();
		}
		return tcUI;
	}
	public static void setStartUpTest(TrainControllerUI tcUI0){
		tcUI=tcUI0;
		latch.countDown();
	}
	public TrainControllerUI(){
		setStartUpTest(this);
	}
	
	public void setTC(TrainController tc){
		attachedTrainController=tc;
	}
	
	//public TrainControllerUI(TrainController x){
	//	attachedTrainController=x;
	//}
	 //commented out to deal with UI creation

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("TrainModel UI");

        /****** select train ******/
        // TODO: get train data from module
        ObservableList<Train> trainData;
        trainData = FXCollections.observableArrayList();

        final TableView trainTable = createTrainTable(trainData);
        trainTable.setPrefWidth(width / 8);

        // testing: add a train to test
        trainData.add(new Train(1));
        /****** select train ******/

        /****** beacon ******/
        final HBox beaconBox = new HBox(10, createTextBox("Beacon"), createLabelBox("STATION; CASTLE SHANNON; BLOCK 96"));
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
        final TableView mapTable = new TableView();

        topHalf.setPrefHeight(height / 2);
        mapTable.setPrefHeight(height / 2);

        final VBox fullScreen = new VBox(10, topHalf, mapTable);

        fullScreen.setPadding(new Insets(10));

        primaryStage.setScene(new Scene(fullScreen, width, height));
        primaryStage.show();
 
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
        VBox time = createLabelBox("11:00:23 am");
        final HBox timeBox = new HBox(10, createTextBox("Time"), time);

        Circle circleG = createCircle(10, Color.GREEN);
        Circle circleY = createCircle(10, Color.YELLOW);
        Circle circleR = createCircle(10, Color.RED);
        final HBox signalBox = new HBox(10, circleG, circleY, circleR);
        signalBox.setAlignment(Pos.CENTER);
        signalBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 2; -fx-padding: 10;");

        final HBox topBox = new HBox(10, createHSpacer(), timeBox, createHSpacer(), signalBox, createHSpacer());
        return topBox;
    }

    private HBox createInfoBox() {
        // TODO: pull data from module
        VBox name1 = createTextBox("Suggested Speed");
        VBox name2 = createTextBox("Current Speed");
        VBox name3 = createTextBox("Authority");
        VBox name4 = createTextBox("Acceleration");
        VBox name5 = createTextBox("Engine Power");
        VBox nameBox1 = new VBox(10, name1, name2, name3, name4,name5);

		VBox label1 = createLabelBox("30 mph");
        VBox label2 = createLabelBox("30 mph");
        VBox label3 = createLabelBox("1000 ft");
        VBox label4 = createLabelBox("1.0 mi/h/h");
        VBox label5 = createLabelBox("100 kW");
        VBox labelBox1 = new VBox(10, label1, label2, label3, label4,label5);
		
        /*
		VBox name5 = createTextBox("Passenger Count");
        VBox name6 = createTextBox("Current Weight");
        VBox name7 = createTextBox("Current Acceleration");
        VBox name8 = createTextBox("Current Grade");
        VBox name9 = createTextBox("Temperature Inside");
        VBox nameBox2 = new VBox(10, name5, name6, name7, name8, name9);
		
        VBox label5 = createLabelBox("150");
        VBox label6 = createLabelBox("52.2 tons");
        VBox label7 = createLabelBox("0.44 m/s^2");
        VBox label8 = createLabelBox("0.5 %");
        VBox label9 = createLabelBox("70 F");
        VBox labelBox2 = new VBox(10, label5, label6, label7, label8, label9);
        */
		
		VBox midControlBox=createMidControlBox();
		
		
        final HBox infoBox = new HBox(10, createHSpacer(), nameBox1, labelBox1, createHSpacer(), midControlBox, createHSpacer());
        return infoBox;
    }

    private VBox createStatusBox() {
        /*
        VBox name1 = createTextBox("Left Doors Closed");
        VBox name2 = createTextBox("Right Doors Closed");
        VBox name3 = createTextBox("Cabin Lights On");
        VBox name4 = createTextBox("Headlights On");
        VBox name5 = createTextBox("Service Brake On");
        VBox name6 = createTextBox("Train Engine");
        VBox nameBox = new VBox(10, createVBox(), name1, name2, name3, name4, name5, name6);
        */
        /*
        Color x;
        if(attachedTrainController.getLeftDoorsControlClosed()){
            x=Color.GREEN;
        }
        else{
            x=Color.RED;
        }
        */

        Circle light1 = createCircle(10, Color.GREEN);
        /*attachedTrainController.leftDoorStateTest.addListener( new ChangeListener<Boolean>() { 
           
            public void changed(ObservableValue <? extends Boolean > observable, Boolean oldValue, Boolean newValue) { 
                if(newValue){
                    light1.setFill(Color.GREEN); 
                }
                else{
                    light1.setFill(Color.RED);
                }
            } 
        });*/
        VBox light2 = createCircleBox(10, Color.GREEN);
        VBox light3 = createCircleBox(10, Color.GREEN);
        VBox light4 = createCircleBox(10, Color.GREEN);
        VBox light5 = createCircleBox(10, Color.GREEN);
        VBox light6 = createCircleBox(10, Color.GREEN);
        VBox lightBox = new VBox(10, createTextBox("Status"), light1, light2, light3, light4, light5, light6);

        ToggleButton button1 = createToggleButton("Left Doors Closed", 200, 40);
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO: button handle
                if (button1.isSelected()==false){
                    attachedTrainController.setLeftDoorsControlClosed(false);
                }
                else{
                    attachedTrainController.setLeftDoorsControlClosed(true);
                }
            }
        });

        ToggleButton button2 = createToggleButton("Right Doors Closed", 200, 40);
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO: button handle
                if (button2.isSelected()==false){
                    attachedTrainController.setRightDoorsControlClosed(false);
                }
                else{
                    attachedTrainController.setRightDoorsControlClosed(true);
                }
            }
        });

        ToggleButton button3 = createToggleButton("Cabin Lights On", 200, 40);
        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO: button handle
                if (button3.isSelected()==false){
                    attachedTrainController.setCabinLightsControlOn(false);
                }
                else{
                    attachedTrainController.setCabinLightsControlOn(true);
                }
            }
        });

        
        ToggleButton button4 = createToggleButton("Headlights On", 200, 40);
        button4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO: button handle
                if (button4.isSelected()==false){
                    attachedTrainController.setHeadLightsControlOn(false);
                }
                else{
                    attachedTrainController.setHeadLightsControlOn(true);
                }
            }
        });

        ToggleButton button5 = createToggleButton("Service Brake On", 200, 40);
        button5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO: button handle
                if (button5.isSelected()==false){
                    attachedTrainController.setServiceBrakeControlOn(false);
                }
                else{
                    attachedTrainController.setServiceBrakeControlOn(true);
                }
            }
        });

        //VBox button6 = createToggleButtonBox("TRUE", 100, 30);
        VBox name6 = createLabelBox("Train Engine");
        VBox buttonBox = new VBox(10, createTextBox("Control"), button1, button2, button3, button4, button5,name6);

        HBox box = new HBox(10, createHSpacer(), lightBox, buttonBox);

        Circle light7 = createCircle(20, Color.GREEN);
        /*
        light7.setPrefHeight(40);
		light7.setPrefWidth(85);
        light7.setAlignment(Pos.CENTER);
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
                    attachedTrainController.setEmergencyBrakeControlOn(false);
                    
                }
                else{
                    eBrakeButton.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-font-size:20; -fx-text-fill: black; -fx-background-color: firebrick;");
                    attachedTrainController.setEmergencyBrakeControlOn(true);
                    
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
		
        
		HBox eBrakeBox=new HBox(createHSpacer(),light7, createHSpacer(), eBrakeButton);
        final VBox statusBox = new VBox(10, box, eBrakeBox);
        statusBox.setAlignment(Pos.CENTER);
        return statusBox;
    }

	private VBox createMidControlBox() {
		
		ToggleButton manControlToggle=createToggleButton("Manual Mode",150,40);
        manControlToggle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO: button handle
                if (manControlToggle.isSelected()==false){
                    attachedTrainController.setManualModeOn(false);
                }
                else{
                    attachedTrainController.setManualModeOn(true);
                }
            }
        });
        //HBox manMode=new HBox(manControlToggle)
		Label driverSpeedSetpoint=createLabel("20 mph");
		driverSpeedSetpoint.setAlignment(Pos.CENTER);
		Slider speedSlider = new Slider();
        speedSlider.setMin(0); 
        speedSlider.setMax(43); 
        speedSlider.setValue(20); 
        speedSlider.valueProperty().addListener( new ChangeListener<Number>() { 
           
            public void changed(ObservableValue <? extends Number > observable, Number oldValue, Number newValue) { 
               driverSpeedSetpoint.setText(newValue.intValue()+" mph"); 
           } 
        });
		
		VBox manControlBox= new VBox(10,manControlToggle,driverSpeedSetpoint,speedSlider);
		manControlBox.setPrefHeight(150);
		manControlBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 2; -fx-padding: 10;");
		
		
		VBox hvacTitle=createTextBox("HVAC");
		Slider tempSlider = new Slider();
        tempSlider.setMin(60); 
        tempSlider.setMax(80); 
        tempSlider.setValue(68); 
		Label hvacSetpoint=createLabel("68 deg F");
        tempSlider.valueProperty().addListener( new ChangeListener<Number>() { 
 
           public void changed(ObservableValue <? extends Number > observable, Number oldValue, Number newValue) { 
               hvacSetpoint.setText(newValue.intValue()+" deg F"); 
           } 
       }); 
       VBox setPointBox=new VBox(hvacSetpoint);
       setPointBox.setPrefWidth(120);
       setPointBox.setPrefHeight(40);
		HBox hvacControl=new HBox(hvacTitle,tempSlider,setPointBox);
		
		VBox midControlBox=new VBox(5,manControlBox,hvacControl);
		midControlBox.setPrefHeight(height/4);
		midControlBox.setPrefWidth(height/4);
		midControlBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 2; -fx-padding: 10;");
		return midControlBox;
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
        ToggleButton button = new ToggleButton();
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
	
	private VBox createToggleButtonBox(String text, int width, int height) {
        // all VBox create function are unified to 40px height
        VBox buttonBox = new VBox(0, createToggleButton(text, width, height));
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