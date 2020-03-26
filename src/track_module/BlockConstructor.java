package src.track_module;

import java.util.HashSet;
import java.util.Set;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import src.ctc.CTCTrain;
import src.track_module.Block;
import src.train_controller.TrainController;
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
       public StringProperty getStationNameProperty() { return new SimpleStringProperty(name); }
       public int getTicketSold() {return tickets;};
       public String getName() {return name;};
    }

    public static class Shift extends Block {    
        Block position= null;

        public Shift( String line, char section, int blockNumber, int length, float speedLimit, float grade,
            float elevation, float cummElevation, boolean underground, int xCoordinate, int yCoordinate) {
            super( line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, xCoordinate, yCoordinate);
        }
        public void setPosition( Block block) {
            position= block;
        }
        public Block getPosition() {return position;};
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
        public Yard() {
            super( "yard", ' ', 0, 0, 0, 0, 0, 0, false, 0, 0);
        }
        public void createTrain(CTCTrain ctcTrain, Block startingBlock) {
        //new Train(ctcTrain.getTrainID(), new TrainController(), this);
        
            
        }
    }
}