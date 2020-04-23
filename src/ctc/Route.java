package src.ctc;

import java.time.LocalTime;
import java.util.*;

public class Route {

    // Instead of list maybe the paths should be a queue.
    private Deque<Path> paths = new LinkedList<Path>();

    public Route(){
    }
    
    public void getNextPath(){
        if (!paths.isEmpty()){
            paths.poll();
        }
    }

    public Path getCurrPath(){
        return paths.peek();
    }

    public Path getLastPath(){
        return paths.peekLast();
    }

    public void addPath(UUID start, UUID end, UUID prevBlock){
        paths.add(new Path(start, end, prevBlock));
    }
    public void addTimePath(UUID start, UUID end, UUID prevBlock, LocalTime startTime, LocalTime endTime){
        paths.add(new TimePath(start, end, startTime, endTime, prevBlock));
    }
    public void addTimePath(UUID start, UUID end, UUID prevBlock, LocalTime endTime){
        paths.add(new TimePath(start, end, endTime, prevBlock));
    }


    public int size(){ return paths.size();}

}

