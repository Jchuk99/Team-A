package src.track_module;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import src.train_module.Train;

abstract public class Block {
    private Set<Edge> edges= new HashSet<Edge>();
    // https://stackoverflow.com/questions/42786313/javafx-eventhandler-new-alert-if-boolean-equals-true
    private BooleanProperty occupied= new SimpleBooleanProperty(false);
    private BooleanProperty functional= new SimpleBooleanProperty(true);
    private BooleanProperty heater= new SimpleBooleanProperty(false);
    private BooleanProperty closed = new SimpleBooleanProperty(false);
    private Train train;
    private UUID id;
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
    public BooleanProperty occupiedProperty() {return occupied;};
    public BooleanProperty functionalProperty() {return functional;};
    public BooleanProperty heaterProperty() {return heater;};
    public BooleanProperty closedProperty() {return closed;};

    public String getLine() {return line;};
    public char getSection() {return section;};
    public UUID getUUID(){return id;};
    public int getBlockNumber() {return blockNumber;};
    public boolean getClosed() { return closedProperty().get();}
    public boolean getOccupied() {return occupiedProperty().get();};
    public boolean getFunctional() {return functionalProperty().get();};
    public boolean getHeater() {return heaterProperty().get();};
    public boolean getUndeground() {return underground;};
    public int getLength() {return length;};
    public float getSpeedLimit() {return speedLimit;};
    public float getElevation() {return elevation;};
    public float getCummElevation() {return cummElevation;};
    public float getGrade() {return grade;};
    public Set<Edge> getEdges(){return edges;};
    public int getX() {return xCorrdinate;};
    public int getY() {return yCorrdinate;};

    public void setOccupied(boolean occupied){occupiedProperty().set(occupied);};
    public void setHeater(boolean heater){heaterProperty().set(heater);};
    public void setFunctional( boolean functional) {functionalProperty().set(functional);};
    public void setClosed(boolean closed){closedProperty().set(closed);};
    public void setUUID(UUID id){this.id = id;};
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