package src.track_controller;

import java.util.*;
import src.track_controller.WaysideController;

public class TrackControllerModule{
	
	//LinkedList<Train> trains;
	ArrayList<WaysideController> waysideControllers;

	public TrackControllerModule(){
		waysideControllers= new ArrayList<WaysideController>();
	}

	public WaysideController createWayside(){
		WaysideController waysideController = new WaysideController("PLC");
		waysideControllers.add(waysideController);
		return waysideController;
	}

	public ArrayList<WaysideController> getWaysideControllers(){
		return waysideControllers;
	}

	/*private getTrackStatus(){
		//go through all the waysides 
	}
	private void setTrains(LinkedList<Train> trains){
		this.trains = trains;
	}*/





}