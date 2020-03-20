package src.ctc;
import java.util.*;

public class Route {

    // Instead of list maybe the paths should be a queue.
    private Queue<Path> paths = new LinkedList<Path>();
    private Path currPath;

    public Route(){
    }
    
    public void updateCurrPath(){
        if (!paths.isEmpty()){
            currPath = paths.poll();
        }
    }

    public Path getCurrPath(){
        return currPath;
    }

    public void addPath(int start, int end){
        paths.add(new Path(start, end));
    }

    /*public void addPath(UUID start, UUID end){
        paths.add(new Path(start, end));
    }*/
}

