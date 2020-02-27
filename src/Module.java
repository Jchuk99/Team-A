package src;
import java.time.LocalDateTime;

import src.ctc.CTCModule;
import src.track_controller.TrackControllerModule;
import src.train_controller.TrainControllerModule;
import src.train_module.TrainModule;
import src.track_module.TrackModule;

public abstract class Module { 
    final int YEAR= 2020;
    final int MONTH= 1;
    final int DAY= 1;
    final int HOUR= 9;
    final int MINUTE= 30;
    final int SECOND= 0;
    final int PERIOD= 1;

    public TrainModule trainModule;
    public TrainControllerModule trainController;
    public TrackModule trackModule;
    public TrackControllerModule trackControllerModule;
    public CTCModule ctcModule;
    
    LocalDateTime date;

    public Module() {
        this.date= LocalDateTime.of(YEAR, MONTH, DAY, HOUR, MINUTE, SECOND);
    }

    public void tickTock() {
        this.date.plusSeconds( PERIOD);
    }

    public void setTrainModule( TrainModule trainModule) {this.trainModule= trainModule;};
    public void setTrainControllerModule( TrainControllerModule trainController) {this.trainController= trainController;};
    public void setTrackModule( TrackModule trackModule) {this.trackModule= trackModule;};
    public void setTrackControllerModule( TrackControllerModule trackControllerModule) {this.trackControllerModule= trackControllerModule;};
    public void setCTCModule( CTCModule ctcModule) {this.ctcModule= ctcModule;};
}