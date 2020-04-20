package src.ctc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public void createTrain(int trainID){
        if (!trains.containsKey(trainID)){
            maxID(trainID);
            CTCTrain train = new CTCTrain(trainID);
            trains.put(trainID, train);
            observableTrains.add(train);
            dispatchQueue.add(train);
        }
    }

    public void destroyTrain(CTCTrain train){
        trains.remove(train.getTrainID(), train);
        observableTrains.remove(train);
    }
    
    public HashMap<Integer, CTCTrain> getTrainMap(){
        return trains;
    }
    public List<Integer> getTrainIDs(){
        return new ArrayList<Integer>(trains.keySet());
    }
    public List<CTCTrain> getTrains(){
        return observableTrains;
    }
    public void maxID(int trainID){
        if (trainID > largestID) largestID = trainID;
    }
    /** FOR GUI */
    public ObservableList<CTCTrain> getObservableTrains(){
        return observableTrains;
    }
}