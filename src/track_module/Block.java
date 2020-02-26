package src.track_module;

import java.util.HashSet;
import java.util.Set;

import src.track_module.FinalBlock;

abstract public class Block extends FinalBlock {
    Set<Edge> edges= new HashSet<Edge>();

    Block( String line, char section, int blockNumber, int length, float speedLimit, float grade, 
        float elevation, float cummElevation, boolean underground) {
        super(line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground);
    }

    public void setOccupied( boolean set) {occupied= set;};
    public void setFunctional( boolean set) {functional= set;};
    public void setHeater( boolean set) {heater= set;};
    public void addEdge( Block block, Boolean connected) {
        this.edges.add( new Edge( block, connected));
    }
}

class Edge {
    FinalBlock block;
    Boolean connected;

    Edge( FinalBlock block, Boolean connected) {
        this.block= block;
        this.connected= connected;
    }
}