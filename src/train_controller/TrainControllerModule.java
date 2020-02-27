package src.train_controller;

import src.Module;
/*
 * TrainController
 * Primary Author: Jacob Heilman
 * Secondary Authors: Calvin Yu, Eric Price, Jason Chukwu, Chi Kwok Lee
 * Version 0.5
 *
 * Copyright notice
 * This program provies functionality for a train controller. 
 */
//package trainGame;

//import trainGame.Module;
//import trainGame.Graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Vector;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

import java.lang.Boolean;

import src.train_module.Train;
import src.train_module.TrainModule;

public class TrainControllerModule extends Module {

	public Vector<TrainController> controllerList = new Vector<TrainController>();
	
	public TrainControllerModule(Train train){
		TrainControllerUI.setTC(this);
		new Thread(){
			@Override
			public void run(){
				javafx.application.Application.launch(TrainControllerUI.class);
			}
		}.start();
	
		
		TrainController tc=createTrainController(train);
	}
	public void main(){

	}

	public Vector<TrainController> getList(){
		return controllerList;
	}
	
	public TrainController createTrainController(Train train){//
		TrainController tc=new TrainController(train);
		controllerList.add(tc);
		return tc;
	}
	
	public class TrainController{
	public Train attachedTrain;
	public TrainControllerUI attachedUI;
	public boolean leftDoorsControlClosed;
	public boolean rightDoorsControlClosed;
	public boolean manualModeOn;
	public boolean cabinLightsControlOn;
	public boolean headLightsControlOn;
	public int hvacSetpoint;
	public float driverSpeed;
	public boolean emergencyBrakeControlOn;
	public boolean serviceBrakeControlOn;
	public int UUID;
	//public BooleanProperty leftDoorStateTest=new SimpleBooleanProperty(false);
	
	

	/**
	
	*/
	public TrainController(Train train){ //
		//attachedUI = new TrainControllerUI(this);
		attachedTrain=train;
		UUID=Integer.parseInt(train.getName().get().substring(6));
		leftDoorsControlClosed=false;
		rightDoorsControlClosed=false;
		manualModeOn=false;
		cabinLightsControlOn=true;
		headLightsControlOn=true;
		int hvacSetpoint=68;
		float driverSpeed=(float)0.0;
		emergencyBrakeControlOn=true;
		serviceBrakeControlOn=true;
	}
	
	/**
	
	*/
	//public class Controller{
		/**
		
		*/
		public StringProperty getName(){
			return new SimpleStringProperty("Train "+UUID);
			 
		}
		public boolean getManualModeOn(){
			return manualModeOn;
		}
		
		/**
		
		*/
		public void setManualModeOn(boolean x){
			manualModeOn=x;
		}
		
		/**
		
		*/	
		public boolean getLeftDoorsControlClosed(){
			return leftDoorsControlClosed;
		}
		
		/**
		
		*/
		public void setLeftDoorsControlClosed(boolean x){
			leftDoorsControlClosed=x;
		}
		
		/**
		
		*/
		public boolean getRightDoorsControlClosed(){
			return rightDoorsControlClosed;
		}
		
		/**
		
		*/
		public void setRightDoorsControlClosed(boolean x){
			rightDoorsControlClosed=x;
		}
		
		/**
		
		*/
		public boolean getCabinLightsControlOn(){
			return cabinLightsControlOn;
		}
		
		/**
		
		*/
		public void setCabinLightsControlOn(boolean x){
			cabinLightsControlOn=x;
		}
		
		/**
		
		*/
		public boolean getHeadLightsControlOn(){
			return headLightsControlOn;
		}
		
		/**
		
		*/
		public void setHeadLightsControlOn(boolean x){
			headLightsControlOn=x;
		}
		
		/**
		
		*/
		public int getHVACSetpoint(){
			return hvacSetpoint;
		}
		
		/**
		
		*/
		public void setHVACSetpoint(int x){
			hvacSetpoint=x;
		}
		
		/**
		
		*/
		public float getDriverSpeed(){
			return driverSpeed;
		}
		
		/**
		
		*/
		public void setDriverSpeed(float x){
			driverSpeed=x;
		}
		
		/**
		
		*/
		public boolean getEmergencyBrakeControlOn(){
			return emergencyBrakeControlOn;
		}
		
		/**
		
		*/
		public void setEmergencyBrakeControlOn(boolean x){
			emergencyBrakeControlOn=x;
		}
		
		/**
		
		*/
		public boolean getServiceBrakeControlOn(){
			return serviceBrakeControlOn;
		}
		
		/**
		
		*/
		public void setServiceBrakeControlOn(boolean x){
			serviceBrakeControlOn=x;
		}
	
	
	
	// /**
		
	// */
	//// public class Train{
		
		// /**
		
		// */
		// public String getError(){
			// return attachedTrain.getError();
		// }
		
		// /**
		
		// */
		// public float getAuthority(){
			// return attachedTrain.getAuthority();
		// }
		
		// /**
		
		// */
		// public float getSuggestedSpeed(){
			// return attachedTrain.getSuggestedSpeed();
		// }
		
		// /**
		
		// */
		// public float getActualSpeed(){
			// return attachedTrain.getActualSpeed();
		// }
		
		// /**
		
		// */
		// public float getAcceleration(){
			// return attachedTrain.getAcceleration();
		// }
		
		// /**
		
		// */
		// public float getPower(){
			// return attachedTrain.getPower();
		// }
		
		// /**
		
		// */
		// public String getBeacon(){
			// return attachedTrain.getBeacon();
		// }
		
		// /**
		
		// */
		// public String getMap(){
			// return attachedTrain.getMap();
		// }
	// }
	
	// /**
		
	// */
	//// public class Power{
		
		// /**
		
		// */
		// public float calculateNewPower(){//fake sample output
			// if(manualModeOn==true){
				// return 3*(driverSpeed-getActualSpeed());
			// }
			// else{
				// return 3*(getSuggestedSpeed()-getActualSpeed());
			// }
		// }
	// }
	}
	
	public static void main(String[] args){
		/*new Thread(){
			@Override
			public void run(){
				javafx.application.Application.launch(TrainControllerUI.class);
			}
		}.start();*/
		TrainModule t=new TrainModule();
		//Train tr = new Train();
		//TrainControllerModule TCM=new TrainControllerModule(tr);
		//TrainControllerUI tcUI=TrainControllerUI.waitForStartUpTest();
		
	}
	
}