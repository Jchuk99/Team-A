package src;

import javafx.scene.shape.Line;
import src.track_module.Block;

public class GraphLine extends Line {
    Block destination;

     public GraphLine(double startX, double startY, double endX, double endY){
        super(startX, startY, endX, endY);

    }

    public void setDestination(Block block){destination = block;}
    public Block getDestination(){return destination;}
}