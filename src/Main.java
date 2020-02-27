package src;

import src.track_controller.TrackControllerModule;
import src.track_module.TrackModule;

class Main { 
    public static void main(String args[])
    { 
        try {
            TrackModule tm= new TrackModule();
            TrackControllerModule tc= new TrackControllerModule();
            tm.setTrackControllerModule( tc);
            tm.userInterface();
            int x=0;
        }
        catch( Exception e) {
            System.out.println( e);
        }
    } 
} 