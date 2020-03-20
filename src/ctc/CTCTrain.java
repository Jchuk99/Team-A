package src.ctc;

public class CTCTrain{

    private int authority;
    private float suggestedSpeed; // IN METERS PER SECOND INTENRALLY
    private int trainID;
    private int destination;
    private int currPos;
    //private int errorStatus; //TODO: make this an enum.
    //private long timeOnBlock; // IN SECONDS INTERNALLY
    private Route route;

    public CTCTrain(){
        // We use 0 for the yard.
        currPos = 0;
    }

    public void dispatchRoute(int blockDest){
        int start = currPos;
        // If train is in yard then start @ first block.
        if (currPos == 0) start = 1;
        //If the train already has a route, do i just want to add this to the end of the route?
        if (route == null){
            route = new Route();
        }
        route.addPath(start, blockDest);
        route.updateCurrPath();
        authority = route.getCurrPath().getCourse().size();
    }

    public int getNextBlockID(){
        return route.getCurrPath().getNextBlockID(currPos);
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