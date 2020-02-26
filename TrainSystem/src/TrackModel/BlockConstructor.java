package trainGame.track;

import java.util.HashSet;
import java.util.Set;

public class BlockConstructor {
    static class Normal extends Block {
        Normal( String line, char section, int blockNumber, int length, float speedLimit, float grade, 
            float elevation, float cummElevation, boolean underground) {
            super( line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground);
        }
    }

    static class Station extends Block {
        int tickets= 0;
        String name;

        Station( String line, char section, int blockNumber, int length,float speedLimit, float grade, 
            float elevation, float cummElevation, boolean underground, String name) {
            super( line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground);
            this.name= name;
        }
        void addTicketsSold( int tickets) {this.tickets+= tickets;};
        int getTicketSold() {return tickets;};
        String getName() {return name;};
    }

    static class Shift extends Block {    
        Block position= null;

        Shift( String line, char section, int blockNumber, int length, float speedLimit, float grade,
            float elevation, float cummElevation, boolean underground) {
            super( line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground);
        }
        public void setPosition( Block block) {
            position= block;
        }
        public Block getPosition() {return position;};
    }

    static class Crossing extends Block {
        private boolean lights= false;
        private boolean closed= false;

        Crossing( String line, char section, int blockNumber,int length, float speedLimit, float grade, 
            float elevation, float cummElevation, boolean underground) {
            super( line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground);
        }
        public boolean getLights() {return lights;};
        public boolean getClosesd() {return closed;};
        public void setLights( boolean set) {lights= set;};
        public void setClosed( boolean set) {lights= set;};
    }
    static class Yard extends Block {
        Yard() {
            super( "yard", ' ', 0, 0, 0, 0, 0, 0, false);
        }
        public void addBlock( Block b) {

        }
    }
}

abstract class Block {
    Set<Edge> edges= new HashSet<Edge>();
    boolean occupied= false;
    boolean functional= true;
    String line;
    char section;
    int length;
    int blockNumber;
    float speedLimit;
    float grade;
    float elevation;
    float cummElevation;
    boolean underground;
    boolean heater= false;


    Block( String line, char section, int blockNumber, int length, float speedLimit, float grade, 
        float elevation, float cummElevation, boolean underground) {
        this.line= line;
        this.section= section;
        this.blockNumber= blockNumber;
        this.length= length;
        this.speedLimit= speedLimit;
        this.grade= grade;
        this.elevation= elevation;
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
    public void addEdge( Block block, Boolean connected) {
        this.edges.add( new Edge( block, connected));
    }
}

class Edge {
    Block block;
    Boolean connected;

    Edge( Block block, Boolean connected) {
        this.block= block;
        this.connected= connected;
    }
}