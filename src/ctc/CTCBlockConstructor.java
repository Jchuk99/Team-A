package src.ctc;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import src.track_module.Block;


public class CTCBlockConstructor {
    public static class CTCNormal extends CTCBlock {
        public CTCNormal( String line, char section, int blockNumber, int length, float speedLimit, float grade, 
            float elevation, float cummElevation, boolean underground, int xCoordinate, int yCoordinate) {
            super( line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, xCoordinate, yCoordinate);
        }
    }

    public static class CTCStation extends CTCBlock {
        int tickets= 0;
        String name;

        public CTCStation( String line, char section, int blockNumber, int length,float speedLimit, float grade, 
            float elevation, float cummElevation, boolean underground, String name, int xCoordinate, int yCoordinate) {
            super( line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, xCoordinate, yCoordinate);
            this.name= name;
        }
        
        public void setTicketsSold( int tickets) {this.tickets = tickets;};
        public int getTicketsSold() {return tickets;}
        public String getName() {return name;};
    }

    public static class CTCShift extends CTCBlock {  
        private SimpleObjectProperty<Block> position= new SimpleObjectProperty<Block>();
        private List<Block> switchPositions = new ArrayList<Block>();

        public CTCShift( String line, char section, int blockNumber, int length, float speedLimit, float grade,
            float elevation, float cummElevation, boolean underground, int xCoordinate, int yCoordinate) {
            super( line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, xCoordinate, yCoordinate);
        }

        public SimpleObjectProperty<Block> positionProperty() {return position;};

        public void togglePosition(){
            int switchIndex = switchPositions.indexOf(getPosition());
            if (switchIndex == 0){
                setPosition(switchPositions.get(1));
            }
            else{
                setPosition(switchPositions.get(0));
            }
        }

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

    public static class CTCYard extends CTCBlock {

        public CTCYard(int xCoordinate, int yCoordinate) {
            super( "YARD", ' ', 0, 0, 0, 0, 0, 0, false, xCoordinate, yCoordinate);
        }
            
        }
    }
