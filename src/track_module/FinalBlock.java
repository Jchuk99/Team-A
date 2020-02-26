package src.track_module;

public class FinalBlock {
    boolean occupied= false;
    boolean functional= true;
    boolean heater= false;
    final String line;
    final char section;
    final int length;
    final int blockNumber;
    final float speedLimit;
    final float grade;
    final float elevation;
    final float cummElevation;
    final boolean underground;
    


    FinalBlock( String line, char section, int blockNumber, int length, float speedLimit, float grade, 
        float elevation, float cummElevation, boolean underground) {
        this.line= line;
        this.section= section;
        this.blockNumber= blockNumber;
        this.length= length;
        this.speedLimit= speedLimit;
        this.grade= grade;
        this.elevation= elevation;
        this.cummElevation= cummElevation;
        this.underground= underground;
    }

    public String getLine() {return line;};
    public char getSection() {return section;};
    public int getBlockNumber() {return blockNumber;};
    public boolean getOccupied() {return occupied;};
    public boolean getFunctional() {return functional;};
    public boolean getUndeground() {return underground;};
    public boolean getHeater() {return heater;};
    public int getLength() {return length;};
    public float getSpeedLimit() {return speedLimit;};
    public float getElevation() {return elevation;};
    public float getCummElevation() {return cummElevation;};
    public float getGrade() {return grade;};

    public void setOccupied( boolean set) {occupied= set;};
    public void setFunctional( boolean set) {functional= set;};
    public void setHeater( boolean set) {heater= set;};
}