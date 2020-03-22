package src.track_module;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import src.train_module.Train;

abstract public class Block {
    UUID id;
    Set<Edge> edges= new HashSet<Edge>();
    boolean occupied= false;
    boolean functional= true;
    boolean heater= false;
    Train train;
    final String line;
    final char section;
    final int length;
    int blockNumber;
    final float speedLimit;
    final float grade;
    final float elevation;
    final float cummElevation;
    final boolean underground;
    final int xCorrdinate;
    final int yCorrdinate;

    Block( String line, char section, int blockNumber, int length, float speedLimit, float grade, 
        float elevation, float cummElevation, boolean underground, int xCorrdinate, int yCorrdinate) {
            id= UUID.randomUUID();
            this.line= line;
            this.section= section;
            this.blockNumber= blockNumber;
            this.length= length;
            this.speedLimit= speedLimit;
            this.grade= grade;
            this.elevation= elevation;
            this.cummElevation= cummElevation;
            this.underground= underground;
            this.xCorrdinate= xCorrdinate;
            this.yCorrdinate= yCorrdinate;
        }

    public String getLine() {return line;};
    public char getSection() {return section;};
    public UUID getUUID(){return id;};
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
    public Set<Edge> getEdges(){return edges;};
    public int getX() {return xCorrdinate;};
    public int getY() {return yCorrdinate;};

    public StringProperty getBlockNumberProperty() { return new SimpleStringProperty("" + blockNumber); }

    public void setOccupied(boolean occupied){this.occupied = occupied;};
    public void setUUID(UUID id){this.id = id;};
    public void setFunctional( boolean set) {functional= set;};
    public void setHeater( boolean set) {heater= set;};
    public void setEdges(Set<Edge>edges){this.edges = edges;};
    public void addEdge( Block block, Boolean connected) {
        this.edges.add( new Edge( block, connected));
    }
    public void setTrain( Train train) {
        if( train != null) {
            this.train= train;
            this.occupied= true;
        }
        else {
            this.train= null;
            this.occupied= false;
        }
    }
    public void setTrainProperties( float suggestedSpeed, float authority) {
        if( this.train != null)
            this.train.setTrain( suggestedSpeed, authority);
    }

    @Override
    public String toString() {
        return String.valueOf(this.blockNumber);
    }

}
