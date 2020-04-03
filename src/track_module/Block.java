package src.track_module;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableBooleanValue;
import src.train_module.Train;

abstract public class Block {
    private UUID id;
    private Set<Edge> edges= new HashSet<Edge>();
    private BooleanProperty occupied= new SimpleBooleanProperty();
    // https://stackoverflow.com/questions/42786313/javafx-eventhandler-new-alert-if-boolean-equals-true
    private boolean functional= true;
    private boolean heater= false;
    private Train train;
    private final String line;
    private final char section;
    private final int length;
    private int blockNumber;
    private final float speedLimit;
    private final float grade;
    private final float elevation;
    private final float cummElevation;
    private final boolean underground;
    private final int xCorrdinate;
    private final int yCorrdinate;

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
    public BooleanProperty occupiedProperty() {return occupied;};
    public boolean getOccupied() {return occupiedProperty().get();};
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

    public void setOccupied(boolean occupied){occupiedProperty().set(occupied);};
    public void setUUID(UUID uuid){id = uuid;};
    public void setFunctional( boolean set) {functional= set;};
    public void setHeater( boolean set) {heater= set;};
    public void setEdges(Set<Edge> set){edges = set;};
    public void addEdge( Block block, Boolean connected) {
        this.edges.add( new Edge( block, connected));
    }
    public void setTrain( Train train) {
        if( train != null) {
            this.train= train;
            setOccupied(true);
        }
        else {
            this.train= null;
            setOccupied(false);
        }
    }
    public void setTrainProperties( float suggestedSpeed, float authority) {
        if( this.train != null)
            this.train.setTrain( suggestedSpeed, authority);
    }
    public boolean equals(Block block){
        return id == block.getUUID();
    }
}
