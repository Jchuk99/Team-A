/*
 * TrainController
 * Primary Author: Jacob Heilman
 * Secondary Authors: Calvin Yu, Eric Price, Jason Chukwu, Chi Kwok Lee
 * Version 0.2
 *
 * Copyright notice
 * This program provies functionality for a train controller. 
 */
//package trainGame;

import trainGame.Module;
import trainGame.Graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class TrainController extends Module {
	//TrainModule attachedTrain;
	TrainControllerUI attachedUI;
	Boolean leftDoorsControlClosed;
	Boolean rightDoorsControlClosed;
	Boolean manualModeOn;
	Boolean cabinLightsControlOn;
	Boolean headLightsControlOn;
	int hvacSetpoint;
	float driverSpeed;
	Boolean emergencyBrakeControlOn;
	Boolean serviceBrakeControlOn;
	
	/**
	
	*/
	public TrainController(){ //add "TrainModule x" when we have a working TrainModule
		attachedUI = new TrainControllerUI(this);
		//attachedTrain=x;
		leftDoorsControlClosed=false;
		rightDoorsControlClosed=false;
		manualModeOn=false;
		cabinLightsControlOn=true;
		headLightsControlOn=true;
		int hvacSetpoint=68;
		float driverSpeed=0.0;
		emergencyBrakeControlOn=true;
		serviceBrakeControlOn=true;
	}
	
	/**
	
	*/
	public class ControllerInternals{
		/**
		
		*/
		public Boolean getManualModeOn(){
			return manualModeOn;
		}
		
		/**
		
		*/
		public void setManualModeOn(Boolean x){
			manualModeOn=x;
		}
		
		/**
		
		*/	
		public Boolean getLeftDoorsControlClosed(){
			return leftDoorsControlClosed;
		}
		
		/**
		
		*/
		public void setLeftDoorsControlClosed(Boolean x){
			leftDoorsControlClosed=x;
		}
		
		/**
		
		*/
		public Boolean getRightDoorsControlClosed(){
			return rightDoorsControlClosed;
		}
		
		/**
		
		*/
		public void setRightDoorsControlClosed(Boolean x){
			rightDoorsControlClosed=x;
		}
		
		/**
		
		*/
		public Boolean getCabinLightsControlOn(){
			return cabinLightsControlOn;
		}
		
		/**
		
		*/
		public void setCabinLightsControlOn(Boolean x){
			cabinLightsControlOn=x;
		}
		
		/**
		
		*/
		public Boolean getHeadLightsControlOn(){
			return headLightsControlOn;
		}
		
		/**
		
		*/
		public void setHeadLightsControlOn(Boolean x){
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
		public Boolean getEmergencyBrakeControlOn(){
			return emergencyBrakeControlOn;
		}
		
		/**
		
		*/
		public void setEmergencyBrakeControlOn(Boolean x){
			emergencyBrakeControlOn=x;
		}
		
		/**
		
		*/
		public Boolean getServiceBrakeControlOn(){
			return serviceBrakeControlOn;
		}
		
		/**
		
		*/
		public void setServiceBrakeControlOn(Boolean x){
			serviceBrakeControlOn=x;
		}
	}
	
	
	
	// /**
		
	// */
	// public class TrainInterface{
		
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
	// }
	
	// /**
		
	// */
	// public class PowerStuff{
		
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