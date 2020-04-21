package src.track_controller;
import src.Module;
import src.track_controller.TrackControllerModule;
import src.track_module.TrackModule;

import javafx.application.Application;
import java.util.HashSet;

public class TrackControllerMain{
	public static void main(String[] args){
		TrackModule trackModule = new TrackModule();
		TrackControllerModule trackControllerModule = new TrackControllerModule();
		HashSet<Module> modules= new HashSet<Module>();
		modules.add(trackControllerModule);
		modules.add(trackModule);
		
		for( Module module : modules) {
			module.setTrackControllerModule(trackControllerModule);
			module.setTrackModule(trackModule);
        }
	
		Thread thread = new Thread() {
            @Override
            public void run() {
                Application.launch(ApplicationTrackControllerUI.class);
            }
        };
        thread.start();

        for( Module module : modules) {
            module.main();
		}
	}
}

