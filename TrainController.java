/*
 * TrainController
 * Primary Author: Jacob Heilman
 * Secondary Authors: Calvin Yu, Eric Price, Jason Chukwu, Chi Kwok Lee
 * Version 0.1
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
	Boolean LeftDoorsControlClosed;
	Boolean RightDoorsControlClosed;
	Boolean ManualModeOn;
	
	
	/**
	
	*/
	public TrainController(){
		attachedUI =new TrainControllerUI(this);
	}
	
	public Boolean getManualModeOn(){
		return ManualModeOn;
	}
	public void setManualModeOn(Boolean x){
		ManualModeOn=x;
	}
	
}