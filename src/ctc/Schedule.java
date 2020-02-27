package src.ctc;
public class Schedule{

    // hash map of trains
    // data structure to hold schedule file
    private int trainCount = 0;
    
    public Schedule(){
    }

    public void uploadSchedule(){
    }

    public CTCTrain createTrain(String trainID, double suggestedSpeed, String destination){
        CTCTrain train = new CTCTrain();
        
        
        train.setTrainID(Integer.parseInt(trainID.split(" ")[1]));
        //train.setAuthority(authority);
        //train.setDestination(destination);
        //train.setSuggestedSpeed(suggestedSpeed);
        //train.dispatchRoute(destination);

        return train;
    }
    
    public  void getTrains(){
    }

}