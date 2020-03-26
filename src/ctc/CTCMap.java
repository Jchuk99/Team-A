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
   private UUID yardID;
   private Map<UUID, Station> stationMap = new HashMap<UUID, Station>();
   private Map<UUID, Block> blockMap = new HashMap<UUID, Block>();
   private List<UUID> switchList = new ArrayList<UUID>();
   private List<UUID> closedBlocks = new ArrayList<UUID>();
   
    public CTCMap(TrackControllerModule trackControllerModule, TrackModule trackModule){
        this.trackControllerModule = trackControllerModule;
        this.trackModule = trackModule;
    }

    public Block getBlock(UUID block){return blockMap.get(block);}
    public UUID getYardID(){return yardID;}
    public int size(){return blockMap.size();}
    public Set<UUID> getBlockIDs(){ return blockMap.keySet();}
    public List<Block> getBlockList(){ return new ArrayList<Block>(blockMap.values());}
    public Map<UUID, Block> getBlockMap(){ return blockMap;}
    public List<Station> getStationList(){ return new ArrayList<Station>(stationMap.values());}
    public Map<UUID, Station> getStationMap(){ return stationMap;}
    public List<UUID> getSwitchList(){ return switchList;}

    //TODO: gonna have to rework this when there's multiple lines, and multiple diffrent exits from the yard.
    public UUID getStartingBlockID(){
        // get the blocks right that come out of the yard, which will always be block number 0
        List<Block> blockList = getBlockList();
        Block startingBlock = null;
        for (Block block: blockList){
            for (Edge edge: block.getEdges()){
                if ((edge.getBlock().getBlockNumber() == 0) && (!edge.getConnected())){
                    startingBlock = block;
                }
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

    public void initMap(){
        /*
        TODO I commented this out. you are calling this before the track has been uploaded and 
        there is no map to generate, causing compile errors.

        - Eric


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
                        switchList.add(block.getUUID());
                        Shift myBlock = new Shift(line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, xCoordinate, yCoordinate);
                        myBlock.setUUID(block.getUUID());
                        blockMap.put(block.getUUID(), myBlock);
                    }
                    else{
                        Normal myBlock = new Normal(line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, xCoordinate, yCoordinate);
                        myBlock.setUUID(block.getUUID());
                        blockMap.put(block.getUUID(), myBlock);
                    }
                }   
        }

        Yard myYard = new Yard();
        yardID = trackModule.getYard().getUUID();
        myYard.setUUID(yardID);
        blockMap.put(yardID, myYard);
        

        // need an extra for loop to put in edges now that I have all the blocks
        for(WaysideController wayside: waysides){
            List<Block> blockList = wayside.getBlocks();
            for(Block block : blockList){
               Block myBlock = blockMap.get(block.getUUID());
               myBlock.setEdges(block.getEdges());
            }
        }
        */

    }

    // need to create second method that doesn't just reinitialize the map, but updates it's current attributes
    // AKA occupied and switch positions
    public void updateMap(){
        /*
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
        */
        
    }

}