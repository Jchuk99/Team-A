package src.track_controller;

import java.util.*;

public class TrackControllerModule{
	
	//LinkedList<Train> trains;
	HashMap<Character, WaysideController> waysideControllers;

public TrackControllerModule(){

}

private void setWaysideControllers(HashMap<Character, WaysideController> waysideControllers){
	this.waysideControllers = waysideControllers;
}
private HashMap<Character, WaysideController> getWaysideControllers(){
	return waysideControllers;
}

/**private getTrackStatus(){
	//go through all the waysides 
}
private void setTrains(LinkedList<Train> trains){
	this.trains = trains;
}
private void createWayside(){
	WaysideController waysideController = new WaysideController();
}**/




}
