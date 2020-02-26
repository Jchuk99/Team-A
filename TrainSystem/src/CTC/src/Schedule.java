public class Schedule{

    private int trainCount = 0;
    
    public Schedule(){
    }

    public void uploadSchedule(){
    }

    public CTCTrain createTrain(int suggestedSpeed, int destination){
        CTCTrain train = new CTCTrain();
        
        
        train.setTrainID(++trainCount);
        //train.setAuthority(authority);
        train.setDestination(destination);
        train.setSuggestedSpeed(suggestedSpeed);

        return train;
    }
    
    public  void getTrains(){
    }

}