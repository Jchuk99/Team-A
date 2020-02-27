package src.track_module;

import src.track_module.Block;
import src.ctc.Route;
import src.ctc.Path;

public class BlockConstructor {
    public static class Normal extends Block {
        public Normal( String line, char section, int blockNumber, int length, float speedLimit, float grade, 
            float elevation, float cummElevation, boolean underground) {
            super( line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground);
        }
    }

    public static class Station extends Block {
        int tickets= 0;
        String name;

        public Station( String line, char section, int blockNumber, int length,float speedLimit, float grade, 
            float elevation, float cummElevation, boolean underground, String name) {
            super( line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground);
            this.name= name;
        }
       public void addTicketsSold( int tickets) {this.tickets+= tickets;};
       public int getTicketSold() {return tickets;};
       public String getName() {return name;};
    }

    public static class Shift extends Block {    
        Block position= null;

        public Shift( String line, char section, int blockNumber, int length, float speedLimit, float grade,
            float elevation, float cummElevation, boolean underground) {
            super( line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground);
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
            float elevation, float cummElevation, boolean underground) {
            super( line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground);
        }
        public boolean getLights() {return lights;};
        public boolean getClosesd() {return closed;};
        public void setLights( boolean set) {lights= set;};
        public void setClosed( boolean set) {lights= set;};
    }
    public static class Yard extends Block {
        public Yard() {
            super( "yard", ' ', 0, 0, 0, 0, 0, 0, false);
        }
        public void createTrain( float suggestedSpeed, float authority, Block block) {
            
        }
    }
}