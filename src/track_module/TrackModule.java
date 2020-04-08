package src.track_module;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import src.Module;
import src.track_controller.WaysideController;
import src.track_module.BlockConstructor.*;
import src.train_module.Train;
import src.ctc.CTCTrain;
import src.ctc.Path;
import src.ctc.Route;

public class TrackModule extends Module {
    HashMap<UUID, Block> blocks;
    Yard yard;

    public TrackModule() {
        super();
        TrackModuleUI.setTrackModule(this);
        blocks= new HashMap<UUID, Block>();
    }
    
    public void update(){
		
	}
	

    public void main() {
        
    }

    public void buildTrack(String csvFile) throws IOException, FileFormatException {
        // TODO deal with my blocks being a separate class than blocks
        HashMap<Integer, Block> myBlocks= new HashMap<Integer, Block>();
        HashMap<Character, WaysideController> waysides= new HashMap<Character, WaysideController>();
        HashSet<int[]> edges= new HashSet<int[]>();

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
        myBlocks.put(0, yard);
        
        int rowNumber = 1;
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
            int connection3;
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
                if( myBlocks.containsKey(blockNumber) || blockNumber < 1) {
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
                length= Integer.parseInt( data[3]);
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
                String[] _directions= data[14].trim().concat( " ").split( " ");
                directions= new HashSet<Integer>();
                for(String s : _directions) directions.add( Integer.valueOf(s));
            }
            catch( Exception e) {
                throw new FileFormatException("Parsing Issue In Switch Column, Row " + Integer.toString(rowNumber));
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
            }
            else {
                block= new Normal( line, section, blockNumber, length, speedLimit, 
                    grade, elevation, cummElevation, underground, xCoordinate, yCoordinate);
            }

            
            int[] edge1= {blockNumber, connection1, (directions.contains( connection1)) ? 1 : 0};
            edges.add( edge1);
            int[] edge2= {blockNumber, connection2, (directions.contains( connection2)) ? 1 : 0};
            edges.add( edge2);

            
            if( shift) {
                try{ 
                    Shift shiftBlock = (Shift) block;
                    connection3= Integer.parseInt( data[13]);
                    //TODO: switch blocks need to know what blocks the switch can potentially go to
                    // this is to enable toggling of switches in CTC
                    shiftBlock.addSwitchID(connection2);
                    shiftBlock.addSwitchID(connection3);
                    int[] edge3= {blockNumber, connection3, (directions.contains( connection3)) ? 1 : 0};
                    edges.add( edge3);
                }
                catch( Exception e) {
                    throw new FileFormatException("Parsing Issue In Switch Column, Row " + Integer.toString(rowNumber));
                }
                
            }
            myBlocks.put( blockNumber, block);
            
            if( !waysides.containsKey( section)) {
                waysides.put( section, this.trackControllerModule.createWayside());
            }
            waysides.get( section).addBlock(block);    
        }
        
        csvReader.close();
        
        for( int[] edge : edges) {
            Block source;
            Block destination;
            try{ 
                source= myBlocks.get( edge[0]);
                destination= myBlocks.get( edge[1]); 
                //TODO: my attempt at adding the end block positions of each switch to switch.
                // can't just be connections, bidirectional track would cause issues.
                if(source instanceof Shift){
                    Shift shiftBlock = (Shift) source;
                    if(shiftBlock.getSwitchIDs().contains(edge[1])){
                        shiftBlock.addSwitchPosition(destination);
                        shiftBlock.setPosition(destination);
                    }
                }
            }
            catch( Exception e) {
                throw new FileFormatException("Connection Does Not Exist, Row " + Integer.toString(rowNumber));
            }

            source.addEdge( destination, edge[2] != 0);
            if(source == yard) {
                yard.addEdge(source, true);
            }
            else if(destination == yard) {
                yard.addEdge(destination, true);
            }
        }
        this.ctcModule.initMap();
        for( Block block : myBlocks.values()) {
            blocks.put( block.getUUID(), block);
        }
    }

    public void dispatchTrain(CTCTrain ctcTrain) {
        UUID uuid = ctcTrain.getRoute().getCurrPath().getStartBlock();
        Block startingBlock= trackModule.getBlockByUUID(uuid);

        Train train = trainModule.createTrain();
        train.setBlock(startingBlock);
        train.setTrain(ctcTrain.getSuggestedSpeed(),ctcTrain.getAuthority());
       
        System.out.println("Suggeted Speed: " + ctcTrain.getSuggestedSpeed() + " Authority: " + ctcTrain.getAuthority());
        System.out.println("Starting Block Number: " + startingBlock.getBlockNumber());

    }
    public Yard getYard() {return yard;};

    public Block getBlockByUUID( UUID uuid) {
        return blocks.get( uuid);
    }

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