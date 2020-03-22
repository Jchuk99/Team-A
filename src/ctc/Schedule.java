package src.ctc;
import java.util.*;

public class Schedule{

    private HashMap<Integer, CTCTrain> trains = new HashMap<Integer, CTCTrain>();
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
        }

        CTCTrain train = trains.get(trainID);

        train.setTrainID(trainID);
        train.setDestination(destination);
        train.setSuggestedSpeed(suggestedSpeed);

        train.updateRoute(destination);

        return train;
    }
    
    public Set<CTCTrain> getTrains(){
        return new HashSet<CTCTrain>(trains.values());
    }

}