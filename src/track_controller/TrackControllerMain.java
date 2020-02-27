package src.track_controller;
import src.track_controller.TrackControllerModule;
import src.track_module.TrackModule;

import javafx.application.Application;

public class TrackControllerMain{
	public static void main(String[] args){
		TrackControllerModule trackControllerModule = new TrackControllerModule();
		try {
			TrackModule tm= new TrackModule();
			
			
			tm.setTrackControllerModule(trackControllerModule);
			tm.userInterface();
		}
		catch( Exception e) {
			System.out.println( e);
		}

		new Thread(){
			@Override
			public void run(){
				javafx.application.Application.launch(WaysideUI.class);
			}
	}.start();

	WaysideUI waysideUI = WaysideUI.waitForStartUpTest();
	waysideUI.setTrackControllerModule(trackControllerModule);

	}
}

