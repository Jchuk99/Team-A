package src.ctc;
import java.util.*;

public class Schedule{

    // data strucuture to hold trains, could just be a set
    private List<CTCTrain> trains = new ArrayList<CTCTrain>();
    // data structure to hold schedule file
    
    public Schedule(){
    }

    public void uploadSchedule(){
    }

    public CTCTrain createTrain(String trainID, float suggestedSpeed, int destination){
        CTCTrain train = new CTCTrain();
        trains.add(train);

        
        train.setTrainID(Integer.parseInt(trainID.split(" ")[1]));
        train.setDestination(destination);
        train.setSuggestedSpeed(suggestedSpeed);
        train.dispatchRoute(destination);

        return train;
    }
    
    public List<CTCTrain> getTrains(){
        return trains;
    }

}