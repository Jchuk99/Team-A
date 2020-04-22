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

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TrainControllerModule extends Module {

	private ObservableList<TrainController> controllerList;
	
	public TrainControllerModule(){
		
		controllerList=FXCollections.observableArrayList();
		TrainControllerUI.setModule(this);
		//TrainController tc=createTrainController();
	}

	public void main(){
		
	}

	@Override
	public void update() {
		for (int i=0;i<controllerList.size();i++) {
			controllerList.get(i).update();
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
	public StringProperty getTimeStamp(){
		
		return new SimpleStringProperty("12:04:20 AM");
	}

	public void destroy(int uuid) {
		for (int i=0;i<controllerList.size();i++) {
			if(Integer.parseInt(controllerList.get(i).getName().getValueSafe().split(" ")[1])==uuid){
				controllerList.remove(i);
				break;
			}
		}	
	}
	
}