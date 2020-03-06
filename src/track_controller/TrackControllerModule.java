package src.track_controller;

import java.util.*;
import src.track_controller.WaysideController;
import src.track_controller.WaysideUI;
import src.Module;

public class TrackControllerModule extends Module {
	private float suggestedSpeed;
	private int trainId;
	private int authority;
	//LinkedList<Train> trains;
	ArrayList<WaysideController> waysideControllers;

	public TrackControllerModule(){
		super();
		waysideControllers= new ArrayList<WaysideController>();
		WaysideUI.setTrackControllerModule(this);
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
	}*/
	public void setTrainInfo(int trainId, float suggestedSpeed, int authority){
		this.suggestedSpeed = suggestedSpeed;
		this.trainId = trainId;
		this.authority = authority;
	}

	public float getSuggestedSpeed(){
		return suggestedSpeed;
	}

	public int getTrainId(){
		return trainId;
	}

	public int getAuthority(){
		return authority;
	}






}