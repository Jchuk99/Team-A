package src;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import src.track_module.Block;
import src.track_module.Edge;
import src.track_module.BlockConstructor.Yard;

public abstract class BaseMap { 
    public Map<Block, Circle> circleMap;

    public void mapUnavailable(Pane pane) {
        pane.getChildren().setAll();
        Label label = UICommon.createLabel("Valid Track Not Available");
        label.setStyle("-fx-font-size: 24; -fx-text-fill: -fx-title-color;");
        ImageView imageView = new ImageView("/resources/warning.png");
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(250);
        imageView.setFitHeight(250);
        VBox vBox = new VBox(10, imageView, label);
        vBox.setAlignment(Pos.CENTER);
        HBox hBox = new HBox(vBox);
        hBox.setAlignment(Pos.CENTER);
        vBox.prefWidthProperty().bind(pane.widthProperty());
        hBox.prefHeightProperty().bind(pane.heightProperty());
        pane.getChildren().add(hBox);    
    }

    public void buildMap(Map<UUID, Block> blocks, Pane pane) {
        pane.getChildren().setAll();
        circleMap = new HashMap<Block, Circle>();

        for(Block block : blocks.values()) {
            Circle circle = new Circle(block.getX(), block.getY(), 8);
            circle.setFill(Color.GREEN);
            circle.setViewOrder(0);
            circle.setStrokeWidth(6);
            if(block.getLine().equals("RED")) {
                circle.setStroke(Color.FIREBRICK);
            }
            else if(block.getLine().equals("GREEN")) {
                circle.setStroke(Color.LIMEGREEN);
            }
            else if(block instanceof Yard) {
                circle.setFill(Color.BLACK);
                circle.setStroke(Color.BLACK);
            }

            pane.getChildren().add( circle);
            for(Edge edge: block.getEdges()) {
                Line line= new Line( block.getX(), block.getY(), edge.getBlock().getX(), edge.getBlock().getY());
                if(block.getLine().equals("RED")) {
                line.setStroke(Color.FIREBRICK);
                }
                else if(block.getLine().equals("GREEN")) {
                    line.setStroke(Color.LIMEGREEN);
                }
                line.setStyle("-fx-stroke-width: 2");
                line.setViewOrder(1);
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
            circleMap.put(block, circle);
        }
    }
    public abstract Scene buildPopUp(Block block);
}
