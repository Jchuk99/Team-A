package src.ctc;

import java.io.File;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Schedule{

    private HashMap<Integer, CTCTrain> trains = new HashMap<Integer, CTCTrain>();
    private ObservableList<CTCTrain> observableTrains = FXCollections.observableArrayList();
    private File scheduleFile;
    // data structure to hold schedule file
    
    public Schedule(){
    }

    public void uploadSchedule(){
    }

    public CTCTrain dispatchTrain(String trainIDString, float suggestedSpeed, UUID destination){
        int trainID = Integer.parseInt(trainIDString.split(" ")[1]);

        if (!trains.containsKey(trainID)){
            CTCTrain train = new CTCTrain();
            trains.put(trainID, train);
            observableTrains.add(train);
        }

        CTCTrain train = trains.get(trainID);

        train.setTrainID(trainID);
        train.setDestination(destination);
        train.setSuggestedSpeed(suggestedSpeed);

        train.updateRoute(destination);

        return train;
    }
    
    public HashMap<Integer, CTCTrain> getTrainMap(){
        return trains;
    }
    public Set<CTCTrain> getTrains(){
        return new HashSet<CTCTrain>(trains.values());
    }
    /** FOR GUI */
    public ObservableList<CTCTrain> getObservableTrains(){
        return observableTrains;
    }

}