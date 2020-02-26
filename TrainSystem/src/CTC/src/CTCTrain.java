public class CTCTrain{

    private int authority;
    private int suggestedSpeed; // IN METERS PER SECOND INTERALLY
    private int trainID;
    private int destination;

    public CTCTrain(){
    }
    
    
    public void setAuthority(int authority){
        this.authority = authority;
    }
    public void setSuggestedSpeed(int suggestedSpeed){
        this.suggestedSpeed = suggestedSpeed;
    }
    public void setTrainID(int trainID){
        this.trainID = trainID;
    }
    public void setDestination(int destination){
        this.destination = destination;
    }
    public int getAuthority(){
        return authority;
    }
    public int getSuggestedSpeed(){
        return suggestedSpeed;
    }
    public int getTrainID(){
        return trainID;
    }
    public int getDestination(){
        return destination;
    }
}