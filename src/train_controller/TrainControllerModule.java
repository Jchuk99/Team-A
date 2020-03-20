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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TrainControllerModule extends Module {

	public ObservableList<TrainController> controllerList;
	
	public TrainControllerModule(){
		TrainControllerUI.setModule(this);
		controllerList=FXCollections.observableArrayList();
		//TrainController tc=createTrainController();
	}

	public void main(){
		
	}

	@Override
	public void update() {
		for (TrainController controller : controllerList) {
			controller.update();
		}
	}

	public ObservableList<TrainController> getList(){
		return controllerList;
	}
	
	public TrainController createTrainController(){//
		TrainController tc=new TrainController();
		controllerList.add(tc);
		return tc;
	}
	
	
}