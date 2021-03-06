package src;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*; 

import src.ctc.CTCModule;
import src.track_controller.TrackControllerModule;
import src.train_controller.TrainControllerModule;
import src.train_module.TrainModule;
import src.track_module.TrackModule;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Module{ 
    final int YEAR = 2020;
    final int MONTH = 1;
    final int DAY = 1;
    final int HOUR = 8;
    final int MINUTE = 0;
    final int SECOND = 0;

    public Boolean crashed = false;

    public TrainModule trainModule;
    public TrainControllerModule trainControllerModule;
    public TrackModule trackModule;
    public TrackControllerModule trackControllerModule;
    public CTCModule ctcModule;
    
    LocalDateTime startTime;
    protected LocalDateTime date;
    public StringProperty timeString = new SimpleStringProperty("");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    // in millisecond
    public final static int TIMESTEP = 50;

    public Module() {
        this.date= LocalDateTime.of(YEAR, MONTH, DAY, HOUR, MINUTE, SECOND);
        this.startTime = date;
    }

    public void tickTock() {
        date = date.plus(TIMESTEP, ChronoUnit.MILLIS);
        timeString.set(date.format(timeFormatter));
        update();
    }

    public abstract void update();
    // module update logic for every clock tick
    public void setTrainModule( TrainModule trainModule) {this.trainModule= trainModule;};
    public void setTrainControllerModule( TrainControllerModule trainControllerModule) {this.trainControllerModule= trainControllerModule;};
    public void setTrackModule( TrackModule trackModule) {this.trackModule= trackModule;};
    public void setTrackControllerModule( TrackControllerModule trackControllerModule) {this.trackControllerModule= trackControllerModule;};
    public void setCTCModule( CTCModule ctcModule) {this.ctcModule= ctcModule;};
    public abstract void main();
}