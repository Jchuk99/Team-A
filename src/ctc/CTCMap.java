package src.ctc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import src.track_controller.TrackControllerModule;
import src.track_controller.WaysideController;
import src.track_module.Block;
import src.track_module.Edge;
import src.track_module.TrackModule;
import src.track_module.BlockConstructor.Station;
import src.track_module.BlockConstructor.Yard;
import src.track_module.BlockConstructor.Shift;
import src.track_module.BlockConstructor.Normal;

public class CTCMap{

   private TrackControllerModule trackControllerModule;
   private TrackModule trackModule;
   private Yard myYard;
   //TODO:Change stationMap to stationList
   //MAKE SWITCHLIST AND STATIONLISST RETURN ACTUAL LISTS AND STATIONS!!!
   private Map<UUID, Station> stationMap = new HashMap<UUID, Station>();
   private Map<UUID, Block> blockMap = new HashMap<UUID, Block>();
   private List<Shift> switchList = new ArrayList<Shift>();
   
    public CTCMap(TrackControllerModule trackControllerModule, TrackModule trackModule){
        this.trackControllerModule = trackControllerModule;
        this.trackModule = trackModule;
    }

    public Block getBlock(UUID block){return blockMap.get(block);}
    public Yard  getYard(){return myYard;}
    public int size(){return blockMap.size();}
    public Set<UUID> getBlockIDs(){ return blockMap.keySet();}
    public List<Block> getBlockList(){ return new ArrayList<Block>(blockMap.values());}
    public Map<UUID, Block> getBlockMap(){ return blockMap;}
    public List<Station> getStationList(){ return new ArrayList<Station>(stationMap.values());}
    public List<Shift> getSwitchList(){ return switchList;}

    //TODO: gonna have to rework this when there's multiple lines, and multiple diffrent exits from the yard.
    // WILL NOT WORK IF MULTIPLE LINES
    public UUID getStartingBlockID(String line){
        
        //TODO: made sure line is either red or green
        Block startingBlock = null;
            for (Edge edge: myYard.getEdges()){
                if ((edge.getBlock().getLine().equalsIgnoreCase(line) && (edge.getConnected()))){
                    startingBlock = edge.getBlock();
                }
            }
        return startingBlock.getUUID();
    }


    public List<UUID> getOccupiedBlocks(){

        List<UUID> occupiedBlocks = new ArrayList<UUID>(); // can be a set instead of a list.. doesn't really matter
        for (Map.Entry<UUID, Block> entry : blockMap.entrySet()){
            Block currBlock = entry.getValue();
            if (currBlock.getOccupied() == true){
                occupiedBlocks.add(currBlock.getUUID());
            }
        }

        return occupiedBlocks;
    }

    public List<UUID> getClosedBlocks(){

        List<UUID> closedBlocks = new ArrayList<UUID>(); // can be a set instead of a list.. doesn't really matter
        for (Map.Entry<UUID, Block> entry : blockMap.entrySet()){
            Block currBlock = entry.getValue();
            if (currBlock.getClosed() == true){
                closedBlocks.add(currBlock.getUUID());
            }
        }

        return closedBlocks;
    }
    
    

    public void initMap(){
        /*
        TODO I commented this out. you are calling this before the track has been uploaded and 
        there is no map to generate, causing compile errors.

        - Eric
        */

         //length, number, edges
         //hashmap of blocks with they're blocknumber so that I can access any one.
         ArrayList<WaysideController> waysides = trackControllerModule.getWaysideControllers();
         for (WaysideController wayside : waysides) { 
                List<Block> blockList = wayside.getBlocks();
                 for(Block block : blockList){
                    String line = block.getLine();
                    char section = block.getSection();
                    int blockNumber = block.getBlockNumber();
                    int length = block.getLength();
                    float speedLimit = block.getSpeedLimit();
                    float grade = block.getGrade();
                    float elevation = block.getElevation();
                    float cummElevation = block.getCummElevation();
                    boolean underground = block.getUndeground();
                    int xCoordinate = block.getX();
                    int yCoordinate = block.getY();

                    if (block instanceof Station){
                        Station blockStation = (Station)block;
                        Station myBlock = new Station(line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, blockStation.getName(), xCoordinate, yCoordinate);
                        myBlock.setUUID(block.getUUID());
                        stationMap.put(block.getUUID(), myBlock);
                        blockMap.put(block.getUUID(), myBlock);
                    }
                    else if (block instanceof Shift){
                        System.out.println(blockNumber);
                        Shift shiftBlock = (Shift)block;
                        Shift myBlock = new Shift(line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, xCoordinate, yCoordinate);
                        myBlock.setUUID(shiftBlock.getUUID());
                        switchList.add(myBlock);

                        myBlock.setSwitchPositions(shiftBlock.getSwitchPositions());
                        myBlock.setPosition(shiftBlock.getPosition());
                        blockMap.put(shiftBlock.getUUID(), myBlock);
                    }
                    else{
                        Normal myBlock = new Normal(line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, xCoordinate, yCoordinate);
                        myBlock.setUUID(block.getUUID());
                        blockMap.put(block.getUUID(), myBlock);
                    }
                }   
        }

        Yard trackYard =  trackModule.getYard();
        myYard = new Yard(trackYard.getX(), trackYard.getY()); 
        UUID yardID = trackYard.getUUID();
        myYard.setUUID(yardID);
        myYard.setEdges(trackYard.getEdges());
        blockMap.put(yardID, myYard);
        

        // need an extra for loop to put in edges now that I have all the blocks
        for(WaysideController wayside: waysides){
            List<Block> blockList = wayside.getBlocks();
            for(Block block : blockList){
               Block myBlock = blockMap.get(block.getUUID());
               myBlock.setEdges(block.getEdges());
            }
        }
        

    }

    // need to create second method that doesn't just reinitialize the map, but updates it's current attributes
    // AKA occupied and switch positions
    public void updateMap(){
        
        ArrayList<WaysideController> waysides = trackControllerModule.getWaysideControllers();

        for (WaysideController wayside : waysides) { 
            List<Block> blockList = wayside.getBlocks();

            for(Block block : blockList){
                Block myBlock = blockMap.get(block.getUUID());
                myBlock.setOccupied(block.getOccupied());

                if (block instanceof Shift){ 
                    Shift shiftBlock = (Shift)block;
                    Shift myShiftBlock = (Shift)myBlock;
                    myShiftBlock.setPosition(shiftBlock.getPosition());           
                }
            }
        }
        
        
    }

}