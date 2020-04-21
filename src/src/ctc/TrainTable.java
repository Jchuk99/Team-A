package src.ctc;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TrainTable {
    public static int largestID = 0;
    private HashMap<Integer, CTCTrain> trains = new HashMap<Integer, CTCTrain>();
    private ObservableList<CTCTrain> observableTrains = FXCollections.observableArrayList();
    private PriorityQueue<CTCTrain> dispatchQueue = new PriorityQueue<CTCTrain>(11, new dispatchTimeComparator()); 

    public TrainTable(){
    }

    public CTCTrain getTrain(int trainID){
        return trains.get(trainID);   
    }

   /* public void createTrain(int trainID){
        if (!trains.containsKey(trainID)){
            maxID(trainID);
            CTCTrain train = new CTCTrain(trainID);
            trains.put(trainID, train);
            observableTrains.add(train);
            dispatchQueue.add(train);
        }
    }*/

    public void createTrain(int trainID, LocalTime time){
        if (!trains.containsKey(trainID)){
            maxID(trainID);
            CTCTrain train = new CTCTrain(trainID);
            train.setDispatchTime(time);
            trains.put(trainID, train);
            observableTrains.add(train);
            dispatchQueue.add(train);
        }
    }

    public void destroyTrain(CTCTrain train){
        trains.remove(train.getTrainID(), train);
        observableTrains.remove(train);
    }

    public PriorityQueue<CTCTrain> getDispatchQueue(){
        return dispatchQueue;
    }

    public HashMap<Integer, CTCTrain> getTrainMap(){
        return trains;
    }

    public List<Integer> getTrainIDs(){
        return new ArrayList<Integer>(trains.keySet());
    }

    public List<CTCTrain> getTrainsOnMap(){

        List<CTCTrain> trainsOnMap = new ArrayList<CTCTrain>(); 
        for (Map.Entry<Integer, CTCTrain> entry : trains.entrySet()){
            CTCTrain train = entry.getValue();
            if (train.onMap()){
                trainsOnMap.add(train);
            }
        }

        return trainsOnMap;
    }

    public List<CTCTrain> getTrains(){
        List<CTCTrain> allTrains = new ArrayList<CTCTrain>(); 
        for (Map.Entry<Integer, CTCTrain> entry : trains.entrySet()){
            allTrains.add(entry.getValue());
        }
        return allTrains;
    }

    public void maxID(int trainID){
        if (trainID > largestID) largestID = trainID;
    }

    /** FOR GUI */
    public ObservableList<CTCTrain> getObservableTrains(){
        return observableTrains;
    }
}