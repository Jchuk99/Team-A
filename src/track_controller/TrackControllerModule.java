package src.track_controller;

import java.util.ArrayList;
import java.util.LinkedList;

import src.train_module.Train;
import src.Module;

public class TrackControllerModule extends Module{
	
	LinkedList<Train> trains;
	ArrayList<WaysideController> waysideControllers;

	private void setWaysideControllers(ArrayList<WaysideController> waysideControllers){
		this.waysideControllers = waysideControllers;
	}
	private ArrayList<WaysideController> getWaysideControllers(){
		return waysideControllers;
	}

	private void getTrackStatus(){
		//go through all the waysides 
	}

	private void setTrains(LinkedList<Train> trains){
		this.trains = trains;
	}

	public static WaysideController createWayside(){
		WaysideController waysideController = new WaysideController();
		return waysideController;
	}
}