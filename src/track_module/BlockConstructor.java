package src.track_module;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import javafx.beans.property.SimpleObjectProperty;
import src.ctc.CTCTrain;
import src.track_module.Block;
import src.train_module.Train;

public class BlockConstructor {
    public static class Normal extends Block {
        public Normal( String line, char section, int blockNumber, int length, float speedLimit, float grade, 
            float elevation, float cummElevation, boolean underground, int xCoordinate, int yCoordinate) {
            super( line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, xCoordinate, yCoordinate);
        }
    }

    public static class Station extends Block {
        int tickets= 0;
        String name;
        String beacon;

        public Station( String line, char section, int blockNumber, int length,float speedLimit, float grade, 
            float elevation, float cummElevation, boolean underground, String name, int xCoordinate, int yCoordinate) {
            super( line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, xCoordinate, yCoordinate);
            this.name= name;
            this.beacon = name;
        }
       public void addTicketsSold( int tickets) {this.tickets+= tickets;};
       public int getTicketsSold() {return tickets;};
       public String getName() {return name;};
       public String getBeacon() {return beacon;};
    }

    public static class Shift extends Block {  
        private SimpleObjectProperty<Block> position= new SimpleObjectProperty<Block>();
        private List<Block> switchPositions = new ArrayList<Block>();

        public Shift( String line, char section, int blockNumber, int length, float speedLimit, float grade,
            float elevation, float cummElevation, boolean underground, int xCoordinate, int yCoordinate) {
            super( line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, xCoordinate, yCoordinate);
        }
        public void togglePosition(){
            int switchIndex = switchPositions.indexOf(getPosition());
            if (switchIndex == 0){
                setPosition(switchPositions.get(1));
            }
            else{
                setPosition(switchPositions.get(0));
            }
        }

        public SimpleObjectProperty<Block> positionProperty() {return position;};

        public List<Block> getSwitchPositions(){return switchPositions;}
        public Block getPosition() {return position.get();};

        public void setSwitchPositions(List<Block> switchPositions){
            for(Block block : switchPositions) {
                addSwitchPosition(block);
            }
        }
        public void setPosition( Block block) {
            positionProperty().set(block);
            for (Edge edge : getEdges()) {
                if(switchPositions.contains(edge.getBlock())){
                    if (edge.getBlock().getUUID().equals(block.getUUID())) {
                        // if the blocks were originally connected then
                        // set connected, however if they weren't go the destination block, and change it's connectedness
                        if (edge.getOriginalConnection()){
                            edge.setConnected(true);
                        }
                        else{
                            for (Edge e : edge.getBlock().getEdges()){
                                if (e.getBlock().getUUID().equals(this.getUUID())){
                                    e.setConnected(true);
                                }
                            }
                        } 
                    } 
                    else {
                        if (edge.getOriginalConnection()){
                            edge.setConnected(false);
                        }
                        else{
                            for (Edge e : edge.getBlock().getEdges()){
                                if (e.getBlock().getUUID().equals(this.getUUID())){
                                    e.setConnected(false);
                                }
                            }   
                        }
                    }
                }
            }
        }
        public void addSwitchPosition(Block block){
            for (Edge edge : getEdges()) {
                if (edge.getBlock().getUUID().equals(block.getUUID())) {
                    edge.setIsSwitch(true);
                    switchPositions.add(block);
                    setPosition(block);
                }
            }
        }   
    }

    public static class Crossing extends Block {
        private boolean lights= false;
        private boolean closed= false;

        public Crossing( String line, char section, int blockNumber,int length, float speedLimit, float grade, 
            float elevation, float cummElevation, boolean underground, int xCoordinate, int yCoordinate) {
            super( line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, xCoordinate, yCoordinate);
        }
        public boolean getLights() {return lights;};
        public boolean getClosed() {return closed;};
        public void setLights( boolean set) {lights= set;};
        public void setClosed( boolean set) {lights= set;};
    }
    public static class Yard extends Block {

        private Set<Train> trains = new HashSet<Train>();
        public Yard(int xCoordinate, int yCoordinate) {
            super( "YARD", ' ', 0, 0, 0, 0, 0, 0, false, xCoordinate, yCoordinate);
        }
        public void createTrain(CTCTrain ctcTrain, Block startingBlock) {
        //new Train(ctcTrain.getTrainID(), new TrainController(), this);
        
            
        }
    }
}