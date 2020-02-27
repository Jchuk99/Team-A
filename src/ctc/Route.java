package src.ctc;
import java.util.*;

public class Route {

    private List<Path> paths = new ArrayList<Path>();

    public Route(){
    }
    
    public Path getCurrPath(){
        if (!paths.isEmpty()){
            return paths.get(0);
        }
        return null;
    }

    public void addPath(UUID start, UUID end){
        paths.add(new Path(start, end));
    }
}
