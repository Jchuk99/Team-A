package src.ctc;

import java.time.Duration;
import java.time.LocalTime;
import java.util.UUID;

import src.track_module.Block;

public class TimePath extends Path {
    private LocalTime startTime;
    private LocalTime endTime;

    //TODO: figure out how to calculate train speed from currentPath
    public TimePath(UUID startBlock, UUID endBlock, LocalTime startTime, LocalTime endTime, UUID prevBlock){
        this.endBlock = endBlock;
        this.startTime = startTime;
        this.endTime = endTime;
        course = findCourse(startBlock, endBlock, prevBlock);
    }
    public float calcSuggestedSpeed(){
        float distance = 0;
        float elapsedSeconds = Duration.between(startTime, endTime).toSeconds();
        for(UUID blockID: course){
            Block block = CTCModule.map.getBlock(blockID);
            distance += block.getLength();
        }
        return (float) (distance/elapsedSeconds);
    }

    public LocalTime getStartTime() {return startTime;};
    public LocalTime getEndTime() {return endTime;};

}