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

    public void updateRoute(int blockDest){
        int start;
        if (route == null){
            route = new Route();
        }

        //If train does not have any queued paths then will be in yard.
        if (route.size() == 0){
            start = 1;
            route.addPath(start, blockDest);
        }
        else{
            start = route.getLastPath().getEndBlock();
            route.addPath(start, blockDest);
            //Tif train is @ the end of it's path then update the current path.
            if (currPos == route.getCurrPath().getEndBlock()){
                route.updateCurrPath();
            } 
        }

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
    public void setCurrPos(int currPos){
        this.currPos = currPos;
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
    public int getCurrPos(){
        return currPos;
    }
}