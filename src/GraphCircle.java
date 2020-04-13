package src;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.shape.Circle;
import src.track_module.Block;

public class GraphCircle extends Circle {
    private Block block;
    private Set<GraphLine> edges = new HashSet<GraphLine>();

     public GraphCircle(double centerX, double centerY, double radius){
        super(centerX, centerY, radius);

    }
    public void addBlock(Block block){this.block = block;}
    public void addLine(GraphLine line){edges.add(line);}
    public Set<GraphLine> getEdges(){ return edges;}
}