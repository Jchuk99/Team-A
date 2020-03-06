package src.track_module;

import java.util.HashMap;
import java.util.UUID;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Map {
    static final String BLOCKSTYLE = "-fx-fill: rgba(54,215,68,0.8)";
    static final String LINESTYLE = "-fx-stroke-width: 2; -fx-stroke: rgba(160,160,160,0.4); -fx-stroke-dash-array: 10 5;";
    
    static public void buildMap( HashMap<UUID, Block> blocks, Pane pane) {
        for(Block block : blocks.values()) {
            Circle circle = new Circle(block.getX(), block.getY(), 20);
            circle.setStyle(BLOCKSTYLE);
            pane.getChildren().add( circle);
            for(Edge edge: block.edges) {
                Line line= new Line( block.getX(), block.getY(), edge.getBlock().getX(), edge.getBlock().getY());
                line.setStyle(LINESTYLE);
                pane.getChildren().add(line);
            }
        }
    }
}
