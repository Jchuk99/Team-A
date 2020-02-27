package src.track_controller;

import java.util.*;
import src.track_controller.WaysideController;
import src.Module;

public class TrackControllerModule extends Module {
	
	//LinkedList<Train> trains;
	ArrayList<WaysideController> waysideControllers;

	public TrackControllerModule(){
		waysideControllers= new ArrayList<WaysideController>();
	}

	public WaysideController createWayside(){
		WaysideController waysideController = new WaysideController();
		waysideControllers.add(waysideController);
		return waysideController;
	}

	public ArrayList<WaysideController> getWaysideControllers(){
		return waysideControllers;
	}
	
	public void main() {

	}

	/*private getTrackStatus(){
		//go through all the waysides 
	}
	private void setTrains(LinkedList<Train> trains){
		this.trains = trains;
	}*/





}