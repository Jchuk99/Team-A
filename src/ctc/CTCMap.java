package src.ctc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import src.track_controller.TrackControllerModule;
import src.track_controller.WaysideController;
import src.track_module.Block;
import src.track_module.BlockConstructor.*;

public class CTCMap{

   private TrackControllerModule trackControllerModule;
   private Map<String, Integer> stationMap = new HashMap<String, Integer>();
   private Map<Integer, Block> blockMap = new HashMap<Integer, Block>();
   private List<Integer> switchList = new ArrayList<Integer>();
   private boolean init = true;
   
    public CTCMap(TrackControllerModule trackControllerModule){
        this.trackControllerModule = trackControllerModule;
    }

    public Map<Integer, Block> getBlockMap(){ return blockMap;}
    public Map<String, Integer> getStationMap(){ return stationMap;}
    public List<Integer> getSwitchList(){ return switchList;}

    public void updateMap(){
        
         //length, number, edges
         //hashmap of blocks with they're UUID so that I can access any one.
         ArrayList<WaysideController> waysides = trackControllerModule.getWaysideControllers();
         for (WaysideController wayside : waysides) { 
                List<Block> blockList = wayside.getBlocks();
                 for(Block block : blockList){
                    //TODO: don't use block  number in case of multiple blocks on same line.
                    String line = block.getLine();
                    char section = block.getSection();
                    int blockNumber = block.getBlockNumber();
                    int length = block.getLength();
                    float speedLimit = block.getSpeedLimit();
                    float grade = block.getGrade();
                    float elevation = block.getElevation();
                    float cummElevation = block.getCummElevation();
                    boolean underground = block.getUndeground();

                    if (block instanceof Station){
                        Station blockStation = (Station) block;
                        if (init){
                            stationMap.put(blockStation.getName(), Integer.valueOf(block.getBlockNumber()));
                        }
                        Station myBlock = new Station(line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, blockStation.getName());
                        blockMap.put(Integer.valueOf(block.getBlockNumber()), myBlock);
                    }
                    else if (block instanceof Shift){
                        if (init){
                            switchList.add(Integer.valueOf(block.getBlockNumber()));
                        }
                        Shift myBlock = new Shift(line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground);
                        blockMap.put(Integer.valueOf(block.getBlockNumber()), myBlock);
                    }
                    else{
                        Normal myBlock = new Normal(line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground );
                        blockMap.put(Integer.valueOf(block.getBlockNumber()), myBlock);
                    }
                }   
        }
        // need an extra for loop to put in edges now that I have all the blocks
        for(WaysideController wayside: waysides){
            List<Block> blockList = wayside.getBlocks();
            for(Block block : blockList){
               Block myBlock = blockMap.get(Integer.valueOf(block.getBlockNumber()));
               myBlock.setEdges(block.getEdges());
            }
        }

        init = false;
    }
}