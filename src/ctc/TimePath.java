package src.ctc;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
    public TimePath(UUID startBlock, UUID endBlock, LocalTime endTime, UUID prevBlock){
        this.endBlock = endBlock;
        this.endTime = endTime;
        course = findCourse(startBlock, endBlock, prevBlock);
    }

    public LocalTime calcStartTime(){
        long distance = 0; // IN METERS
        long  speed = 10; // IN METERS PER SECOND
        for(UUID blockID: course){
            Block block = CTCModule.map.getBlock(blockID);
            if (block != null){
                distance += block.getLength();
            }
        }

        long timeToDestination =  distance/speed; //IN SECONDS
        startTime = endTime.minusSeconds(timeToDestination);

        return startTime;

    }



    public float calcSuggestedSpeed(){
        float distance = 0;
        float elapsedSeconds = (float) ChronoUnit.SECONDS.between(startTime, endTime);
        for(UUID blockID: course){
            Block block = CTCModule.map.getBlock(blockID);
            if (block != null){
                distance += block.getLength();
            }
        }
        float speed = distance/elapsedSeconds;
        if (speed <= 17.88){
            return speed;
        }
        else{
            return (float) 17.88;
        }
    }

    public LocalTime getStartTime() {return startTime;};
    public LocalTime getEndTime() {return endTime;};

}