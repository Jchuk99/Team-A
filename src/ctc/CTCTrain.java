package src.ctc;

public class CTCTrain{

    private int authority;
    private float suggestedSpeed; // IN METERS PER SECOND INTERALLY
    private int trainID;
    private int destination;
    private int currPos;
    private Route route;

    public CTCTrain(){
        // We use 0 for the yard.
        currPos = 0;
    }

    public void dispatchRoute(int blockDest){
        int start = 1;
        if (currPos != 0) start = currPos;
        //If the train already has a route, do i just want to add this to the end of the route?
        route = new Route();
        route.addPath(start, blockDest);
        authority = route.getCurrPath().getCourse().size();
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