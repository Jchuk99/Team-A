package src.ctc;

public class SchedulerUI extends Person {

    private String blockID = null;
    private String station = null;
    private String trainOne = null;
    private String trainTwo  = null;
    private String trainThree  = null;

    public SchedulerUI(){
    }

    public SchedulerUI (String blockID, String station, String trainOne, String trainTwo, String trainThree){
        this.blockID = blockID;
        this.station = station;
        this.trainOne = trainOne;
        this.trainTwo = trainTwo;
        this.trainThree = trainThree;
    }

    public String getBlockID() {
        return blockID;
    }

    public void setBlockID(String blockID) {
        this.blockID = blockID;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getTrainOne() {
        return trainOne;
    }

    public void setTrainOne(String trainOne) {
        this.trainOne = trainOne;
    }

    public String getTrainTwo() {
        return trainTwo;
    }

    public void setTrainTwo(String trainTwo) {
        this.trainTwo = trainTwo;
    }
    public String getTrainThree() {
        return trainThree;
    }

    public void setTrainThree(String trainThree) {
        this.trainThree = trainThree;
    }

}


