package src.track_controller;

import java.util.*;
import src.track_controller.WaysideController;

public class TrackControllerModule{
	
	//LinkedList<Train> trains;
	HashSet<WaysideController> waysideControllers;

	public TrackControllerModule(){
		waysideControllers= new HashSet<WaysideController>();
	}

	public WaysideController createWayside(){
		WaysideController waysideController = new WaysideController();
		waysideControllers.add( waysideController);
		return waysideController;
	}
	public HashSet<WaysideController> getWaysideControllers(){
		return waysideControllers;
	}

	/*private getTrackStatus(){
		//go through all the waysides 
	}
	private void setTrains(LinkedList<Train> trains){
		this.trains = trains;
	}*/





}
