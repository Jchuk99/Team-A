package src.track_controller;

import java.util.*;
import src.track_controller.WaysideController;

public class TrackControllerModule{
	
	//LinkedList<Train> trains;
	HashMap<Character, WaysideController> waysideControllers;

	public TrackControllerModule(){
		waysideControllers= new HashMap<Character, WaysideController>();
	}

	public void createWayside(){
		WaysideController waysideController = new WaysideController();
	}

	public void setWaysideControllers(HashMap<Character, WaysideController> waysideControllers){
		this.waysideControllers = waysideControllers;
	}
	public HashMap<Character, WaysideController> getWaysideControllers(){
		return waysideControllers;
	}

	/*private getTrackStatus(){
		//go through all the waysides 
	}
	private void setTrains(LinkedList<Train> trains){
		this.trains = trains;
	}*/





}
