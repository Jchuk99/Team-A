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
import src.track_module.BlockConstructor.Shift;
import src.track_module.BlockConstructor.Yard;
public abstract class BaseMap { 
    public Map<Block, Circle> circleMap;
    public Map<Edge, Line> edgeMap;

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
        edgeMap = new HashMap<Edge, Line>();

        for(Block block : blocks.values()) {
            Circle circle = new Circle(block.getX(), block.getY(), 6);
            circle.setFill(Color.GREEN);
            circle.setViewOrder(0);
            circle.setStrokeWidth(4);
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
                edgeMap.put(edge, line);
                line.setViewOrder(2);
                line.setStyle("-fx-stroke-width: 2");

                if(block.getLine().equals("RED")) {
                    line.setStroke(Color.FIREBRICK);
                }
                else if(block.getLine().equals("GREEN")) {
                    line.setStroke(Color.LIMEGREEN);
                }
                else if(block instanceof Yard) {
                    if(edge.getBlock().getLine().equals("RED")) {
                        line.setStroke(Color.FIREBRICK);
                        }
                    else if((edge.getBlock().getLine().equals("GREEN"))) {
                            line.setStroke(Color.LIMEGREEN);
                        }
                }
                if(block instanceof Shift) {
                    Shift shift = (Shift)block;
                    if (edge.getBlock().equals(shift.getPosition())){
                        line = edgeMap.get(edge);
                        line.setStroke(Color.YELLOW);
                        line.setViewOrder(1);
                        line.setStyle("-fx-stroke-width: 4");
                    }
                }
                pane.getChildren().add(line);
            }
            
            block.occupiedProperty().addListener((obs, oldText, newText) -> {
                Circle circleBlock = this.circleMap.get(block);
                if (circleBlock !=null){
                    if (block.getOccupied()){
                        circleBlock.setFill(Color.BLUE);
                    }
                    else {
                        circleBlock.setFill(Color.GREEN);
                    }
                }
            });
            block.functionalProperty().addListener((obs, oldText, newText) -> {
                this.circleMap.get(block).setFill(Color.RED);
            });
            
            if (block instanceof Shift){
                Shift shiftBlock = (Shift)block;
                shiftBlock.positionProperty().addListener((obs, oldText, newText) -> {
                    Block position = shiftBlock.getPosition();
                
                    for(Edge edge: block.getEdges()){
                        Line line = edgeMap.get(edge);
                        line.setStyle("-fx-stroke-width: 2");
                        if (edge.getBlock().equals(position)) {
                            line.setStroke(Color.YELLOW);
                            line.setViewOrder(1);
                            line.setStyle("-fx-stroke-width: 4");
                        }
                        else{
                            if(edge.getBlock().getLine().equals("RED")) {
                                line.setStroke(Color.FIREBRICK);
                                }
                            else if(edge.getBlock().getLine().equals("GREEN")) {
                                    line.setStroke(Color.LIMEGREEN);
                                }
                            line.setViewOrder(2);
                        }
                    }
            });
        }
    
            circle.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                String title = block.getLine().charAt(0) + block.getLine().toLowerCase().substring(1) + " Line | Section " + block.getSection() + " | Block " + Integer.toString(block.getBlockNumber());
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
