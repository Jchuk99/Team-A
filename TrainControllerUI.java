/*
 * TrainControllerUI
 * Primary Author: Jacob Heilman
 * Secondary Authors: Calvin Yu, Eric Price, Jason Chukwu, Chi Kwok Lee
 * Version 0.5
 *
 * Copyright notice
 * This program provides a GUI for a train controller 
 */


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.layout.Priority; 
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.stage.Modality;
 
public class TrainControllerUI extends Application {
    
	TrainController sttachedTrainController;
	
	/**
	
	*/	
	public TrainControllerUI(TrainController x){
		attachedTrainController=x;
	}
	
	public static void main(String[] args) {
        launch(args);
    }

	@Override
	/**
	
	*/
    public void start(Stage primaryStage) {
        int length = 900;
        int height = 800;

				
						
						Label selectTitle=new Label("Select Train");
							
					VBox select=new VBox();
						
							Label carClock = new Label("Time");
						
						VBox numbersLabels=new VBox(carClock);
						
							Label carClockDat=new Label("00:00:00 AM"); 
						
						VBox numbersData=new VBox(carClockDat);
						
					HBox numbers=new VBox(numbersLabels,numbersData);
					
							
						
						HBox lights=new HBox();
						
							
							ToggleButton manualModeButton=new ToggleButton("Manual Mode");
							manualModeButton.setSelected(attachedTrainController.Controller.getManualModeOn());
							manualModeButton.setOnAction(new EventHandler<ActionEvent>() {
 
								@Override
								public void handle(ActionEvent event) {
									attachedTrainController.setManualModeOn(!attachedTrainController.Controller.getManualModeOn());
								}
							});
							
							
						VBox manControl=new Vbox();
						
							
						
						HBox hvac=new VBox();
						
					VBox middleControls=new VBox(lights,manControl,hvac);
				
				HBox trainInfo=new HBox(select,numbers,middleControls);
				
						Label beaconID=new Label("Beacon");
						Label beaconDat=new Label("STATION; CASTLE SHANNON; BLOCK 96; 05-02-2020 10:32:26");
				
				HBox beacon =new HBox(beaconID,beaconData);
			
			VBox topLeft = new VBox(trainInfo,beacon);
			topLeft.setPrefWidth(length*2/3);
					
					
				VBox miscSlides=new VBox();
					
					
					
				VBox miscLEDs =new VBox(); 
				
					Label leftDoor = new Label("Left Doors Closed");
					Label rightDoor = new Label("Right Doors Closed");
					Label cabinLights = new Label("Cabin Lights On");
					Label headLights = new Label("Headlights On");
					Label servBrake = new Label("Service Brake");		
					Label engine=new Label ("Engine");
					
				VBox miscLabels=newVBox(leftDoor, rightDoor,cabinLights,headLights,servBrake,engine
			HBox miscControl = new VBox(miscLabels,miscLEDs,miscSlides);
			miscControl.setPrefWidth(length/3);
		
        HBox topHalf = new HBox(10, trainInfo, miscControl);
        topHalf.setPrefHeight(height/2);


        TextArea map  = new TextArea("Test");
        map.setPrefWidth(length*2/3);
        map.setPrefHeight(height/2);
        
        VBox fullScreen = new VBox(10, topHalf, map);

        fullScreen.setPadding(new Insets(10));
		
		primaryStage.setTitle("Train Controller UI");
        
        primaryStage.setScene(new Scene(fullScreen, length, height));
        primaryStage.show();
    }
}