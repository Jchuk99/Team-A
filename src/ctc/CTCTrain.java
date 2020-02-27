package src.ctc;

public class CTCTrain{

    private int authority;
    private float suggestedSpeed; // IN METERS PER SECOND INTERALLY
    private int trainID;
    private int destination;
    private int currPos;
    private Route route;

    public CTCTrain(){
        // to show that train is in the yard
        currPos = -1;
        route = new Route();
    }

    public void dispatchRoute(int blockDest){
        int start = 0;
        if (currPos != -1) start = currPos;
        route = new Route();
        route.addPath(start, blockDest);
        authority = route.getCurrPath().getCourse().size() - 1;
    }
    
    public void setAuthority(int authority){
        this.authority = authority;
    }
    public void setSuggestedSpeed(float suggestedSpeed){
        this.suggestedSpeed = suggestedSpeed;
    }
    public void setTrainID(int trainID){
        this.trainID = trainID;
    }
    public void setDestination(int destination){
        this.destination = destination;
    }
    public Route getRoute(){
        return route;
    }
    public int getAuthority(){
        return authority;
    }
    public float getSuggestedSpeed(){
        return suggestedSpeed;
    }
    public int getTrainID(){
        return trainID;
    }
    public int getDestination(){
        return destination;
    }
}