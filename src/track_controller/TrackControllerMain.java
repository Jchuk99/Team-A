package src.track_controller;

import javafx.application.Application;

public class TrackControllerMain{
	public static void main(String[] args){
		new Thread(){
			@Override
			public void run(){
				javafx.application.Application.launch(WaysideUI.class);
			}
	}.start();
			
		WaysideUI waysideUI = WaysideUI.waitForStartUpTest();
		TrackControllerModule trackControllerModule = new TrackControllerModule();
		waysideUI.setTrackControllerModule(trackControllerModule);


	}
}