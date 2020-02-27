package src.track_module;

import src.track_module.Block;
import src.ctc.Route;
import src.ctc.Path;

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
        public void createTrain( float suggestedSpeed, float authority, Block block) {
            
        }
    }
}