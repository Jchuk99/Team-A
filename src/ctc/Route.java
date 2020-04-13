package src.ctc;
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

    public int size(){ return paths.size();}

}

