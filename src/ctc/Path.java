package src.ctc;

import java.util.List;

public class Path {

    //private static Map;
    private int startTime;
    private int endTIme;
    private int startBlock;
    private int endBlock;
    //need a list of blocks
    //List<Integer> course;
    
    public Path(){
    }
 
    public Path(int startBlock, int endBlock){
        this.startBlock = startBlock;
        this.endBlock = endBlock;
    }

    /*
    public List<Integer> getCourse(){
        //course = Map.getPath(startBlock, endBlock);
        //return course;    
    }*/


}