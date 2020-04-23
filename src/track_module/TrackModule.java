package src.track_module;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import src.ctc.CTCBlockConstructor.CTCShift;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import src.Module;
import src.track_controller.WaysideController;
import src.track_module.BlockConstructor.*;
import src.train_module.Train;
import src.ctc.CTCTrain;


public class TrackModule extends Module {
    List<UUID> prevClosedBlocks;
    HashMap<UUID, Block> blocks;
    Yard yard;
    IntegerProperty temperature = new SimpleIntegerProperty(50);

    public TrackModule() {
        super();
        TrackModuleUI.setTrackModule(this);
        blocks= new HashMap<UUID, Block>();
    }
    
    public void update(){
        //Simple implementation of what needs to be receivied from the CTC
        //All of this information should be gotten by the waysides, you and Calvin need to work it out.
        //- Jason
        if(this.ctcModule.validMap()){
            List<UUID> closedBlocks = this.trackControllerModule.getCTCClosedBlocks();
            if (closedBlocks != null){
                for (UUID blockID: closedBlocks){
                    blocks.get(blockID).setOccupied(true);
                }
            }
            if (prevClosedBlocks != null){
                for (UUID blockID: prevClosedBlocks){
                    if (!closedBlocks.contains(blockID)){
                        blocks.get(blockID).setOccupied(false);
                    }
                }
            }
            prevClosedBlocks = closedBlocks;

            //List<CTCTrain> trains = this.trackControllerModule.getCTCTrains();
            List<CTCTrain> trains = this.ctcModule.getTrainsOnMap();
            if (trains != null){
                for (CTCTrain ctcTrain: trains){
                    Block currBlock = blocks.get(ctcTrain.getCurrPos());
                    Train train = currBlock.getTrain();
                    if (train != null){
                        train.setTrain(ctcTrain.getSuggestedSpeed(), ctcTrain.getAuthority());
                    }
                }
            }
        }
	}
	

    public void main() {
        
    }

    public void buildTrack(String csvFile) throws IOException, FileFormatException {
        HashMap<String, HashMap<Integer, Block>> myBlocks = new HashMap<String, HashMap<Integer, Block>>();
        myBlocks.put("GREEN", new HashMap<Integer, Block>());
        myBlocks.put("RED", new HashMap<Integer, Block>());

        HashMap<String, HashMap<Character, WaysideController>> myWaysides = new HashMap<String, HashMap<Character, WaysideController>>();
        myWaysides.put("GREEN", new HashMap<Character, WaysideController>());
        myWaysides.put("RED", new HashMap<Character, WaysideController>());

        HashMap<String, HashSet<int[]>> myEdges = new HashMap<String, HashSet<int[]>>();
        myEdges.put("GREEN", new HashSet<int[]>());
        myEdges.put("RED", new HashSet<int[]>());

        if(!csvFile.substring(csvFile.length() - 4, csvFile.length()).equals(".csv"))
            throw new FileFormatException("Incorrect File Type");
        BufferedReader csvReader = new BufferedReader( new FileReader(csvFile));

        String[] data;
        String r;
        List<String> rows = new ArrayList<String>();
        while ((r = csvReader.readLine()) != null) {
            rows.add(r);
        }
        csvReader.close();
        
        
        rows.remove(0); // Header Row

        r = rows.remove(0); // Yard Row
        r= r.concat( " ,");
        data = r.split(",");
        if(!data[0].toUpperCase().equals("YARD")) {
            throw new FileFormatException("Parsing Issue In Yard Row");
        }
        yard = new Yard(Integer.parseInt( data[15].trim()), Integer.parseInt( data[16].trim()));
        myBlocks.get("GREEN").put(0, yard);
        myBlocks.get("RED").put(0, yard);
        
        int rowNumber = 2;
        for(String row : rows) {
            rowNumber++;
            row= row.concat( " ,");
            data = row.split(",");

            String line;
            char section;
            int blockNumber;
            int length;
            float grade;
            float speedLimit;
            boolean underground;
            boolean crossing;
            boolean station;
            float elevation;
            float cummElevation;
            int connection1;
            int connection2;
            int connection3 = -1;
            boolean shift;
            HashSet<Integer> directions;
            int xCoordinate;
            int yCoordinate;

            //////////
            // Parse Line
            //////////
            try{ 
                line= data[0].toUpperCase();
                if(!(line.equals("GREEN") || line.equals("RED"))) {
                    throw new Exception();
            }
            }
            catch( Exception e) {
                throw new FileFormatException("Parsing Issue In Line Column, Row " + Integer.toString(rowNumber));
            }

            //////////
            // Parse Section
            //////////
            try{ 
                section= data[1].charAt(0);
                if(!((section >= 'a' && section <= 'z') || (section >='A' && section <= 'Z'))) {
                    throw new Exception();
                }
            }
            catch( Exception e) {
                throw new FileFormatException("Parsing Issue In Section Column, Row " + Integer.toString(rowNumber));
            }

            //////////
            // Parse Block Number
            //////////
            try{ 
                blockNumber= Integer.parseInt( data[2]);
                if( myBlocks.get(line).containsKey(blockNumber) || blockNumber < 1) {
                    throw new Exception();
                }
            }
            catch( Exception e) {
                throw new FileFormatException("Parsing Issue In Block Number Column, Row " + Integer.toString(rowNumber));
            }

            //////////
            // Parse Length
            //////////
            try{ 
                length= (int)Float.parseFloat(data[3]);
                if( length < 1) {
                    throw new Exception();
                }
            }
            catch( Exception e) {
                throw new FileFormatException("Parsing Issue In Length Column, Row " + Integer.toString(rowNumber));
            }

            //////////
            // Parse Grade
            //////////
            try{ 
                grade= Float.parseFloat( data[4]);
            }
            catch( Exception e) {
                throw new FileFormatException("Parsing Issue In Grade Column, Row " + Integer.toString(rowNumber));
            }
            
            //////////
            // Parse Speed Limit
            //////////
            try{ 
                speedLimit= Float.parseFloat( data[5]) / 60 * 1000; // km/hr * 1hour/60minutes * 1000meters/1km
                if(speedLimit <= 0) {
                    throw new Exception();
                }
            }
            catch( Exception e) {
                throw new FileFormatException("Parsing Issue In Speed Limit Column, Row " + Integer.toString(rowNumber));
            }

            //////////
            // Parse Underground
            //////////
            try{ 
                underground= Boolean.parseBoolean( data[6]);
            }
            catch( Exception e) {
                throw new FileFormatException("Parsing Issue In Underground Column, Row " + Integer.toString(rowNumber));
            }

            //////////
            // Parse Crossing
            //////////
            try{ 
                crossing= Boolean.parseBoolean( data[7]);
            }
            catch( Exception e) {
                throw new FileFormatException("Parsing Issue In Crossing Column, Row " + Integer.toString(rowNumber));
            }

            //////////
            // Parse Station
            //////////
            try{ 
                station= !data[8].trim().equals("");
            }
            catch( Exception e) {
                throw new FileFormatException("Parsing Issue In Station Column, Row " + Integer.toString(rowNumber));
            }

            //////////
            // Parse Elevation
            //////////
            try{ 
                elevation= Float.parseFloat( data[9]);
            }
            catch( Exception e) {
                throw new FileFormatException("Parsing Issue In Elevation Column, Row " + Integer.toString(rowNumber));
            }

            //////////
            // Parse Cummulative Elevation
            //////////
            try{ 
                cummElevation= Float.parseFloat( data[10]);
            }
            catch( Exception e) {
                throw new FileFormatException("Parsing Issue In Cummulative Elevation Column, Row " + Integer.toString(rowNumber));
            }

            //////////
            // Parse Connection One
            //////////
            try{ 
                connection1= Integer.parseInt( data[11]);
            }
            catch( Exception e) {
                throw new FileFormatException("Parsing Issue In Connection 1 Column, Row " + Integer.toString(rowNumber));
            }

            //////////
            // Parse Connection Two
            //////////
            try{ 
                connection2= Integer.parseInt( data[12]);
                if( connection1 == connection2) {
                    throw new Exception();
                }
            }
            catch( Exception e) {
                throw new FileFormatException("Parsing Issue In Connection 2 Column, Row " + Integer.toString(rowNumber));
            }

            //////////
            // Parse Switch
            //////////
            try{ 
                shift= !data[13].trim().equals("");
                if(shift) {
                    connection3= Integer.parseInt( data[13]);
                }
            }
            catch( Exception e) {
                throw new FileFormatException("Parsing Issue In Switch Column, Row " + Integer.toString(rowNumber));
            }

            try {
                String[] _directions= data[14].trim().concat(" ").split(" ");
                directions= new HashSet<Integer>();
                for(String s : _directions) directions.add( Integer.valueOf(s));
            }
            catch(Exception e) {
                throw new FileFormatException("Parsing Issue In Direction Column, Row " + Integer.toString(rowNumber));
            }

            //////////
            // Parse X Coordinate
            //////////
            try{ 
                xCoordinate = Integer.parseInt( data[15].trim());
            }
            catch( Exception e) {
                throw new FileFormatException("Parsing Issue In X Coordinate Column, Row " + Integer.toString(rowNumber));
            }

            //////////
            // Parse Y Coordinate
            //////////
            try{ 
                yCoordinate = Integer.parseInt( data[16].trim());
            }
            catch( Exception e) {
                throw new FileFormatException("Parsing Issue In Y Coordinate Column, Row " + Integer.toString(rowNumber));
            }

            int[] edge1= {blockNumber, connection1, (directions.contains( connection1)) ? 1 : 0, 0};
            myEdges.get(line).add( edge1);

            Block block;
            if( crossing) {
                block= new Crossing( line, section, blockNumber, length, speedLimit, 
                    grade, elevation, cummElevation, underground, xCoordinate, yCoordinate);
            }
            else if( station) {
                String name= data[8].trim();
                block= new Station( line, section, blockNumber, length, speedLimit, 
                grade, elevation, cummElevation, underground, name, xCoordinate, yCoordinate);
            }
            else if( shift) {
                block= new Shift( line, section, blockNumber, length, speedLimit, 
                grade, elevation, cummElevation, underground, xCoordinate, yCoordinate);
                
                int[] edge2= {blockNumber, connection2, (directions.contains( connection2)) ? 1 : 0, 1};
                myEdges.get(line).add( edge2);
                try{ 
                    int[] edge3= {blockNumber, connection3, (directions.contains( connection3)) ? 1 : 0, 1};
                    myEdges.get(line).add( edge3);
                }
                catch( Exception e) {
                    throw new FileFormatException("Parsing Issue In Switch Column, Row " + Integer.toString(rowNumber));
                }
            }
            else {
                block= new Normal( line, section, blockNumber, length, speedLimit, 
                    grade, elevation, cummElevation, underground, xCoordinate, yCoordinate);
            }

            if(!shift) {
                int[] edge2= {blockNumber, connection2, (directions.contains( connection2)) ? 1 : 0, 0};
                myEdges.get(line).add( edge2);
            }
            myBlocks.get(line).put( blockNumber, block);
            
            
            if( !myWaysides.get(line).containsKey( section)) {
                myWaysides.get(line).put( section, this.trackControllerModule.createWayside());
            }
            myWaysides.get(line).get( section).addBlock(block);
            
        }
        csvReader.close();
        
        for(String line : myEdges.keySet()) {
            for( int[] edge : myEdges.get(line)) {
                Block source;
                Block destination;
                try{ 
                    source= myBlocks.get(line).get( edge[0]);
                    destination= myBlocks.get(line).get( edge[1]);
                    if( destination == null) {
                        throw new Exception();
                    }

                }
                catch( Exception e) {
                    throw new FileFormatException("Connection Does Not Exist, Row " + Integer.toString(rowNumber));
                }

                source.addEdge( destination, edge[2] != 0);
                // If this is a switch position node
                if(edge[3] == 1) {
                    ((Shift) source).addSwitchPosition(destination);
                }
                /*if(source == yard) {
                    yard.addEdge(destination, true);
                }*/
                if(destination == yard) {
                    yard.addEdge(source, edge[2] == 0);
                }
            }
        }
        this.ctcModule.initMap();
        for(String line: myBlocks.keySet()) {
            for( Block block : myBlocks.get(line).values()) {
                blocks.put( block.getUUID(), block);
            }
        }
    }

    public void dispatchTrain(CTCTrain ctcTrain) {
        UUID uuid = ctcTrain.getCurrPos();
        Block startingBlock= trackModule.getBlockByUUID(uuid);

        Train train = trainModule.createTrain();
        train.setBlock(startingBlock);
        train.setTrain(ctcTrain.getSuggestedSpeed(), ctcTrain.getAuthority());
       
        System.out.println("Suggeted Speed: " + ctcTrain.getSuggestedSpeed() + " Authority: " + ctcTrain.getAuthority());
        System.out.println("Starting Block Number: " + startingBlock.getBlockNumber());

    }
    public Yard getYard() {return yard;};

    public Block getBlockByUUID( UUID uuid) {
        return blocks.get( uuid);
    }

    public IntegerProperty temperatureProperty(){ return temperature;};
    public int getTemperature() {
        return temperatureProperty().get();
    }
    public void setTemperature(int temperature) {
        temperatureProperty().set(temperature);
        if(getTemperature() == 32) {
            for( Block block : blocks.values()) {
                block.setHeater(true);
            }
        }
        if(getTemperature() == 33) {
            for( Block block : blocks.values()) {
                block.setHeater(false);
            }
        }
    };

    @SuppressWarnings("serial")
    public class FileFormatException extends Exception {
        String error;
        public FileFormatException(String s) {
            error = s;
        }
        public String toString() {
            return error;
        }
    }
}