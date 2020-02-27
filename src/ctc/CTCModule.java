package src.ctc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import src.Module;
import src.track_controller.TrackControllerModule;
import src.track_controller.WaysideController;
import src.track_module.Block;
import src.track_module.BlockConstructor.*;

public class CTCModule extends Module{
    private HashMap<String, Integer> stationMap;
    private HashMap<Integer, Block> blockMap;
    private ArrayList<Integer> switchList;
    private Schedule schedule = null;
    public int speed;

    public CTCModule(){
        super();
        speed = 20;
    }
    public int getSpeed(){return speed;}

    public void getMap(){
       //length, number, edges
       //hashmap of blocks with they're UUID so that I can access any one.
       ArrayList<WaysideController> waysides = trackControllerModule.getWaysideControllers();
       for (WaysideController wayside : waysides) { 
               List<Block> blockList = wayside.getBlocks();
               for(Block block : blockList){
                    //TODO: don't use block  number in case of multiple blocks.
                    //TODO: get all needed fields from the blocks
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
                        stationMap.put(blockStation.getName(), Integer.valueOf(block.getBlockNumber()));
                        Station myBlock = new Station(line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, blockStation.getName());
                        blockMap.put(Integer.valueOf(block.getBlockNumber()), myBlock);
                    }
                    else if (block instanceof Shift){
                        switchList.add(Integer.valueOf(block.getBlockNumber()));
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

        }
       //for each wayside controller, get list of block UUID's and set to list object
       //go through list of UUID's and do the following:
       //first, check what type of instance it is (station, shift, normal, crossing, yard)
       //then give the UUID to the wayside and get an instance of the block corresponding to UUID
       //depending on what instance it is create that type of block and give it current block
       //
    }

    public void dispatch(String trainID, double suggestedSpeed, String destination){
        if (schedule == null){
           schedule = new Schedule();
        }

        System.out.println(trainID);
        System.out.println(destination);
        System.out.println(suggestedSpeed);
        CTCTrain trainToDispatch = schedule.createTrain(trainID, suggestedSpeed, destination);
        //waysideModule.addTrain(trainToDispatch);

    }
}