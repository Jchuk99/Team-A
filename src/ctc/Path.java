package src.ctc;

import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

public class Path {

    //private static Map;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private UUID startBlock;
    private UUID endBlock;
    //need a list of blocks
    //List<Integer> course;
    
    public Path(){
    }
 
    public Path(UUID startBlock, UUID endBlock){
        this.startBlock = startBlock;
        this.endBlock = endBlock;
    }

    public LocalDateTime getStartTime() {return startTime;};
    public LocalDateTime getEndTime() {return endTime;};
    public UUID getStartBlock() {return startBlock;};
    public UUID getEndBlock() {return endBlock;};

    /*
    public List<Integer> getCourse(){
        //course = Map.getPath(startBlock, endBlock);
        //return course;    
    }*/


}