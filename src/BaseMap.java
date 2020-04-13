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
import src.track_module.BlockConstructor.Station;
import src.track_module.BlockConstructor.Yard;

public abstract class BaseMap { 
    public Map<Block, GraphCircle> circleMap;

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
        circleMap = new HashMap<Block, GraphCircle>();

        for(Block block : blocks.values()) {
            GraphCircle circle = new GraphCircle(block.getX(), block.getY(), 8);
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
                GraphLine line= new GraphLine( block.getX(), block.getY(), edge.getBlock().getX(), edge.getBlock().getY());
                line.setDestination(edge.getBlock());
                circle.addLine(line);

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
                
                
                line.getStrokeDashArray().addAll();
                
                 //TODO: Figure out why can't initialize switch line
                if (block instanceof Shift){
                    Shift shiftBlock = (Shift)block;
                    Block dest = shiftBlock.getPosition();
                    if (line.getDestination().equals(dest)){
                        line.setStroke(Color.DARKVIOLET);
                    }

                }

                line.setStyle("-fx-stroke-width: 2");
                line.setViewOrder(1);
                pane.getChildren().add(line);
            }
            
            block.occupiedProperty().addListener((obs, oldText, newText) -> {
                if (block.getOccupied()){
                    this.circleMap.get(block).setFill(Color.BLUE);
                }
                else {
                    this.circleMap.get(block).setFill(Color.GREEN);
                }
            });
            block.functionalProperty().addListener((obs, oldText, newText) -> {
                this.circleMap.get(block).setFill(Color.RED);
            });
            if (block instanceof Shift){
                Shift shiftBlock = (Shift)block;
                 // Not working whenever the switch starts on block 8, slightly updates but doesn't change color.
                 // IDK what's causing this issue, works fine for one of the switch lines.
                shiftBlock.positionProperty().addListener((obs, oldText, newText) -> {
                    Block dest = shiftBlock.getPosition();
                
                    GraphCircle circleBlock = this.circleMap.get(block);
                    for(GraphLine line: circleBlock.getEdges()){
                        if (line.getDestination().equals(dest)){
                            line.setStroke(Color.DARKVIOLET);;
                        }
                        else{
                            if(line.getDestination().getLine().equals("RED")) {
                                line.setStroke(Color.FIREBRICK);
                                }
                            else if(line.getDestination().getLine().equals("GREEN")) {
                                    line.setStroke(Color.LIMEGREEN);
                                }
                        }
                    }
            });
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
        //TODO: switch listening isn't working in this method.
            /*
                shiftBlock.positionProperty().addListener((obs, oldText, newText) -> {
                    for(GraphLine line: circle.getEdges()){
                        if (line.getDestination().equals(dest)){
                            line.getStrokeDashArray().addAll(25d, 20d, 5d, 20d);
                        }
                        else{
                            line.getStrokeDashArray().removeAll(25d, 20d, 5d, 20d);
                        }
                    }
                });
            }
        }*/
    }
    public abstract Scene buildPopUp(Block block);
}
