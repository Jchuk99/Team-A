package src.ctc;

import java.io.File;
import java.util.*;

public class Schedule{

    private File scheduleFile;
    private TrainTable trainTable;
    // MAKE TRAIN TABLE CLASS!!
    // data structure to hold schedule file
    
    public Schedule(TrainTable trainTable){
        this.trainTable = trainTable;
    }

    public void uploadSchedule(){
    }

    public CTCTrain dispatchTrain(String trainIDString, float suggestedSpeed, UUID destination){
        int trainID = Integer.parseInt(trainIDString.split(" ")[1]);

        trainTable.createTrain(trainID);
        CTCTrain train = trainTable.getTrain(trainID);

        train.setDestination(destination);
        train.setSuggestedSpeed(suggestedSpeed);
        train.addPath(destination);

        return train;
    }

}