package src.ctc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import src.track_controller.TrackControllerModule;
import src.track_controller.WaysideController;
import src.track_module.Block;
import src.track_module.BlockConstructor.Station;
import src.track_module.BlockConstructor.Shift;
import src.track_module.BlockConstructor.Normal;

public class CTCMap{

   private TrackControllerModule trackControllerModule;
   private Map<String, Integer> stationMap = new HashMap<String, Integer>();
   private Map<Integer, Block> blockMap = new HashMap<Integer, Block>();
   private List<Integer> switchList = new ArrayList<Integer>();
   
    public CTCMap(TrackControllerModule trackControllerModule){
        this.trackControllerModule = trackControllerModule;
    }

    public Block getBlock(int block){return blockMap.get(block);}
    public int size(){return blockMap.size();}
    public Map<Integer, Block> getBlockMap(){ return blockMap;}
    public Map<String, Integer> getStationMap(){ return stationMap;}
    public List<Integer> getSwitchList(){ return switchList;}
    //TODO: need a method to get occupied blocks in in the hash map, iterate through everything in hashmap and check if block is occupied, if so add to occupied collection.

    // this method is more of a map initialization method
    public void initMap(){
        
         //length, number, edges
         //hashmap of blocks with they're blocknumber so that I can access any one.
         ArrayList<WaysideController> waysides = trackControllerModule.getWaysideControllers();
         for (WaysideController wayside : waysides) { 
                List<Block> blockList = wayside.getBlocks();
                 for(Block block : blockList){
                    //TODO: don't use block number in case of multiple blocks on same line.
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
                        stationMap.put(blockStation.getName(), block.getBlockNumber());
                        Station myBlock = new Station(line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, blockStation.getName(), xCoordinate, yCoordinate);
                        blockMap.put(block.getBlockNumber(), myBlock);
                    }
                    else if (block instanceof Shift){
                        switchList.add(block.getBlockNumber());
                        Shift myBlock = new Shift(line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, xCoordinate, yCoordinate);
                        blockMap.put(block.getBlockNumber(), myBlock);
                    }
                    else{
                        Normal myBlock = new Normal(line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, xCoordinate, yCoordinate);
                        blockMap.put(block.getBlockNumber(), myBlock);
                    }
                }   
        }
        // need an extra for loop to put in edges now that I have all the blocks
        for(WaysideController wayside: waysides){
            List<Block> blockList = wayside.getBlocks();
            for(Block block : blockList){
               Block myBlock = blockMap.get(block.getBlockNumber());
               myBlock.setEdges(block.getEdges());
            }
        }

    }

    // need to create second method that doesn't just reinitialize the map, but updates it's current attributes
    // occupied and switch positions
    public void updateMap(){
        ArrayList<WaysideController> waysides = trackControllerModule.getWaysideControllers();

        for (WaysideController wayside : waysides) { 
            List<Block> blockList = wayside.getBlocks();

            for(Block block : blockList){
                Block myBlock = blockMap.get(block.getBlockNumber());
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