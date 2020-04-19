package src.ctc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import src.ctc.CTCBlockConstructor.CTCNormal;
import src.ctc.CTCBlockConstructor.CTCShift;
import src.ctc.CTCBlockConstructor.CTCStation;
import src.ctc.CTCBlockConstructor.CTCYard;
import src.track_controller.TrackControllerModule;
import src.track_controller.WaysideController;
import src.track_module.Block;
import src.track_module.Edge;
import src.track_module.TrackModule;
import src.track_module.BlockConstructor.Station;
import src.track_module.BlockConstructor.Yard;
import src.track_module.BlockConstructor.Shift;

public class CTCMap{

   private TrackControllerModule trackControllerModule;
   private TrackModule trackModule;
   private CTCYard myYard;
   private Map<UUID, Block> blockMap = new HashMap<UUID, Block>();
   private List<CTCStation> stationList = new ArrayList<CTCStation>();
   private List<CTCShift> switchList = new ArrayList<CTCShift>();
   
    public CTCMap(TrackControllerModule trackControllerModule, TrackModule trackModule){
        this.trackControllerModule = trackControllerModule;
        this.trackModule = trackModule;
    }

    public Block getBlock(UUID block){return blockMap.get(block);}
    //TODO: error check
    public Block getBlock(String line, int blockNumber){
        Block returnBlock = null;
        for (Map.Entry<UUID, Block> entry : blockMap.entrySet()){
            Block currBlock = entry.getValue();
            if (currBlock.getLine().equalsIgnoreCase(line) && currBlock.getBlockNumber() == blockNumber){
                    returnBlock = currBlock;
            }
        }
        return returnBlock;
    }

    public CTCYard  getYard(){return myYard;}
    public int size(){return blockMap.size();}
    public Set<UUID> getBlockIDs(){ return blockMap.keySet();}
    public List<Block> getBlockList(){ return new ArrayList<Block>(blockMap.values());}
    public Map<UUID, Block> getBlockMap(){ return blockMap;}
    public List<CTCStation> getStationList(){ return stationList;}
    public List<CTCShift> getSwitchList(){ return switchList;}

    public UUID getStartingBlockID(String line){
        
        //TODO: made sure line is either red or green
        Block startingBlock = null;
            for (Edge edge: myYard.getEdges()){
                //assumes only one starting block will be found for each line
                if ((edge.getBlock().getLine().equalsIgnoreCase(line) && (edge.getConnected()))){
                    startingBlock = edge.getBlock();
                }
            }
        assert startingBlock != null;
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
            CTCBlock currBlock = (CTCBlock) entry.getValue();
            if (currBlock.getClosed() == true){
                closedBlocks.add(currBlock.getUUID());
            }
        }

        return closedBlocks;
    }
    //TODO: error check
    public int getGreenLineSales(){
        int tickets = 0;
        for (CTCStation station: stationList){
            if (station.getLine().equalsIgnoreCase("green")){
                tickets += station.getTicketsSold();
            }
        }
        return tickets;
    }

    //TODO: error check
    public int getRedLineSales(){
        int tickets = 0;
        for (CTCStation station: stationList){
            if (station.getLine().equalsIgnoreCase("red")){
                tickets += station.getTicketsSold();
            }
        }
        return tickets;
    }



    public void initMap(){

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
                        CTCStation myBlock = new CTCStation(line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, blockStation.getName(), xCoordinate, yCoordinate);
                        myBlock.setUUID(block.getUUID());

                        stationList.add(myBlock);
                        blockMap.put(block.getUUID(), myBlock);
                    }
                    else if (block instanceof Shift){
                        Shift shiftBlock = (Shift)block;
                        CTCShift myBlock = new CTCShift(line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, xCoordinate, yCoordinate);
                        myBlock.setUUID(shiftBlock.getUUID());
                        myBlock.setSwitchPositions(shiftBlock.getSwitchPositions());
                        myBlock.setPosition(shiftBlock.getPosition());
                        
                        switchList.add(myBlock);
                        blockMap.put(shiftBlock.getUUID(), myBlock);
                    }
                    else{
                        CTCNormal myBlock = new CTCNormal(line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, xCoordinate, yCoordinate);
                        myBlock.setUUID(block.getUUID());
                        blockMap.put(block.getUUID(), myBlock);
                    }
                }   
        }

        Yard trackYard =  trackModule.getYard();
        myYard = new CTCYard(trackYard.getX(), trackYard.getY()); 
        myYard.setUUID(trackYard.getUUID());
        myYard.setEdges(trackYard.getEdges());
        blockMap.put(trackYard.getUUID(), myYard);
    
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
    // AKA occupied, switch positions, and tickets sales for each station TODO: functionality
    public void updateMap(){
        
        ArrayList<WaysideController> waysides = trackControllerModule.getWaysideControllers();

        for (WaysideController wayside : waysides) { 
            List<Block> blockList = wayside.getBlocks();

            for(Block block : blockList){
                Block myBlock = blockMap.get(block.getUUID());
                myBlock.setOccupied(block.getOccupied());

                if (block instanceof Station){
                    Station stationBlock = (Station)block;
                    CTCStation myStationBlock = (CTCStation)myBlock;
                    myStationBlock.setTicketsSold(stationBlock.getTicketsSold());
                }

                if (block instanceof Shift){ 
                    Shift shiftBlock = (Shift)block;
                    CTCShift myShiftBlock = (CTCShift)myBlock;
                    myShiftBlock.setPosition(shiftBlock.getPosition());           
                }
            }
        }
        
    }

}