package src.track_module;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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

        public Station( String line, char section, int blockNumber, int length,float speedLimit, float grade, 
            float elevation, float cummElevation, boolean underground, String name, int xCoordinate, int yCoordinate) {
            super( line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, xCoordinate, yCoordinate);
            this.name= name;
        }
       public void addTicketsSold( int tickets) {this.tickets+= tickets;};
       public int getTicketsSold() {return tickets;};
       public String getName() {return name;};
    }

    public static class Shift extends Block {  
        private StringProperty positionNumber = new SimpleStringProperty("");  
        Block position= null;
        List<Integer> switchIDs= new ArrayList<Integer>();
        List<Block> switchPositions = new ArrayList<Block>();

        public Shift( String line, char section, int blockNumber, int length, float speedLimit, float grade,
            float elevation, float cummElevation, boolean underground, int xCoordinate, int yCoordinate) {
            super( line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, xCoordinate, yCoordinate);
        }
        public List<Integer> getSwitchIDs(){return switchIDs;}
        public List<Block> getSwitchPositions(){return switchPositions;}
        public void setPosition( Block block) {
            position= block;
            positionNumber.setValue("" + position.getBlockNumber());
        }
        public Block getPosition() {return position;};

        public void togglePosition(){
            int switchIndex = switchPositions.indexOf(position);
            if (switchIndex == 0){
                setPosition(switchPositions.get(1));
            }
            else{
                setPosition(switchPositions.get(0));
            }
        }

        public StringProperty positionProperty() {return positionNumber;};

        public void setSwitchPositions(List<Block> switchPositions){
            this.switchPositions = switchPositions;
        }
        public void addSwitchPosition(Block position){
            switchPositions.add(position);
        }   
        public void addSwitchID(int position){
            switchIDs.add(position);
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
        public boolean getClosesd() {return closed;};
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