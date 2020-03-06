package src.ctc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import src.Module;
import src.track_controller.TrackControllerModule;
import src.track_controller.WaysideController;
import src.track_module.Block;
import src.track_module.BlockConstructor.*;

public class CTCModule extends Module{
    public static Map<String, Integer> stationMap = new HashMap<String, Integer>();
    public static Map<Integer, Block> blockMap = new HashMap<Integer, Block>();
    public static List<Integer> switchList = new ArrayList<Integer>();
    private Schedule schedule = null;
    public int speed;

    public void main() {

    }

    public CTCModule(){
        super();
        CTCUI.setCTCModule(this);
    }
    
    public int getSpeed(){return speed;}

    public void getMap(){
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
                    int xCorrdinate = block.getX();
                    int yCorrdinate = block.getY();

                    if (block instanceof Station){
                        Station blockStation = (Station) block;
                        stationMap.put(blockStation.getName(), Integer.valueOf(block.getBlockNumber()));
                        Station myBlock = new Station(line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, blockStation.getName(), xCorrdinate, yCorrdinate);
                        blockMap.put(Integer.valueOf(block.getBlockNumber()), myBlock);
                    }
                    else if (block instanceof Shift){
                        switchList.add(Integer.valueOf(block.getBlockNumber()));
                        Shift myBlock = new Shift(line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, xCorrdinate, yCorrdinate);
                        blockMap.put(Integer.valueOf(block.getBlockNumber()), myBlock);
                    }
                    else{
                        Normal myBlock = new Normal(line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, xCorrdinate, yCorrdinate);
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
    }

    public void dispatch(String trainID, float suggestedSpeed, String destination){
        if (schedule == null){
           schedule = new Schedule();
        }
        // need to give speed in meters per second, authority, train ID, and route
        suggestedSpeed = suggestedSpeed/(float)2.237;

        getMap();
        // need to parse destination into block
        int destinationInt = Integer.parseInt(destination);
        System.out.println(trainID);
        System.out.println(destination);
        System.out.println(suggestedSpeed);
        CTCTrain train = schedule.createTrain(trainID, suggestedSpeed, destinationInt);
        this.trackModule.createTrain(train.getSuggestedSpeed(), (float) train.getAuthority(), train.getRoute());

    }
}