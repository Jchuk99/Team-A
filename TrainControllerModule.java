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
	
	public TrainControllerModule(){
		TrainControllerUI.setTC(this);
		
		TrainController tc=createTrainController();
	}

	public void main(){
		
	}

	@Override
	public void update() {
		for (TrainController controller : controllerList) {
			controller.update();
		}
	}

	public Vector<TrainController> getList(){
		return controllerList;
	}
	
	public TrainController createTrainController(){//
		TrainController tc=new TrainController();
		controllerList.add(tc);
		return tc;
	}
	
	
}