package src.track_module;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.UUID;

import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public abstract class BaseMap {

    static final String CIRCLESTYLE = "-fx-fill: rgba(54,215,68,1)";
    static final String LINESTYLE = "-fx-stroke-width: 2; -fx-stroke: rgba(160,160,160,1);";
    
    public void buildMap( HashMap<UUID, Block> blocks, Pane pane) {
        for(Block block : blocks.values()) {
            Circle circle = new Circle(block.getX(), block.getY(), 20);
            circle.setStyle(CIRCLESTYLE);
            pane.getChildren().add( circle);
            for(Edge edge: block.edges) {
                Line line= new Line( block.getX(), block.getY(), edge.getBlock().getX(), edge.getBlock().getY());
                line.setStyle(LINESTYLE);
                pane.getChildren().add(line);
            }
            circle.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                String title = block.getLine() + " Line: " + Integer.toString(block.getBlockNumber());
                Scene scene = buildPopUp( block);
                scene.getStylesheets().add(Paths.get(System.getenv("cssStyleSheetPath")).toUri().toString());
                Stage stage = new Stage();
                stage.setTitle(title);
                stage.setScene(scene);
                stage.sizeToScene();
                stage.show();
            });
        }
    }
    public abstract Scene buildPopUp(Block block);
}
