package src.track_module;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import src.track_module.Block;

public class BlockConstructor {
    public static class Normal extends Block {
        public Normal( String line, char section, int blockNumber, int length, float speedLimit, float grade, 
            float elevation, float cummElevation, boolean underground, int xCoordinate, int yCoordinate) {
            super( line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, xCoordinate, yCoordinate);
        }
    }

    public static class Station extends Block {
        SimpleIntegerProperty tickets= new SimpleIntegerProperty(0);
        String name;

        public Station( String line, char section, int blockNumber, int length,float speedLimit, float grade, 
            float elevation, float cummElevation, boolean underground, String name, int xCoordinate, int yCoordinate) {
            super( line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, xCoordinate, yCoordinate);
            this.name= name;
        }

        public SimpleIntegerProperty ticketsProperty() {return tickets;};

        public void addTicketsSold( int tickets) {this.tickets.set(this.tickets.get() + tickets);};
        public int getTicketsSold() {return tickets.get();};
        public String getName() {return name;};
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
        }
        public void addSwitchPosition(Block position){
            switchPositions.add(position);
            setPosition(position);
        }   
    }

    public static class Crossing extends Block {
        private SimpleBooleanProperty lights= new SimpleBooleanProperty(false);
        private SimpleBooleanProperty closed= new SimpleBooleanProperty(false);

        public Crossing( String line, char section, int blockNumber,int length, float speedLimit, float grade, 
            float elevation, float cummElevation, boolean underground, int xCoordinate, int yCoordinate) {
            super( line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, xCoordinate, yCoordinate);
        }

        public SimpleBooleanProperty lightsProperty() {return lights;};
        public SimpleBooleanProperty closedProperty() {return closed;};

        public boolean getLights() {return lights.get();};
        public boolean getClosesd() {return closed.get();};
        public void setLights( boolean set) {lights.set(set);};
        public void setClosed( boolean set) {lights.set(set);};
    }
    public static class Yard extends Block {
        public Yard(int xCoordinate, int yCoordinate) {
            super( "YARD", ' ', 0, 0, 0, 0, 0, 0, false, xCoordinate, yCoordinate);
        }
    }
}