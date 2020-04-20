package src.ctc;

import java.time.LocalTime;
import java.util.UUID;

public class TimePath extends Path {
    private LocalTime startTime;
    private LocalTime endTime;

    
    public TimePath(UUID startBlock, UUID endBlock, LocalTime startTime, LocalTime endTime, UUID prevBlock){
        this.endBlock = endBlock;
        this.startTime = startTime;
        this.endTime = endTime;
        course = findCourse(startBlock, endBlock, prevBlock);
    }

    public LocalTime getStartTime() {return startTime;};
    public LocalTime getEndTime() {return endTime;};

}