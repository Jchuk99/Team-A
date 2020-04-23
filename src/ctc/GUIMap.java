package src.ctc;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import src.UICommon;
import src.ctc.CTCBlockConstructor.CTCShift;
import src.ctc.CTCBlockConstructor.CTCYard;
import src.track_module.Block;
import src.track_module.Edge;
import src.BaseMap;

public class GUIMap extends BaseMap {
    
    @Override
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
            else if(block instanceof CTCYard) {
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
                else if(block instanceof CTCYard) {
                    if(edge.getBlock().getLine().equals("RED")) {
                        line.setStroke(Color.FIREBRICK);
                        }
                    else if((edge.getBlock().getLine().equals("GREEN"))) {
                            line.setStroke(Color.LIMEGREEN);
                        }
                }
                if(block instanceof CTCShift) {
                    CTCShift shift = (CTCShift)block;
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
            
            if (block instanceof CTCShift){
                CTCShift shiftBlock = (CTCShift)block;
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

    @Override
    public Scene buildPopUp(Block block) {
        
        Circle circleGreen = UICommon.createCircle(10, Color.WHITE);
        Circle circleBlue = UICommon.createCircle(10, Color.WHITE);
        Circle circleRed = UICommon.createCircle(10, Color.WHITE);
        Circle circleOrange = UICommon.createCircle(10, Color.WHITE);
        statusUpdate(block, circleRed, circleOrange, circleBlue, circleGreen);

        Button closedMode = UICommon.createButton("Toggle Closed", 200, 10);
        closedMode.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            CTCBlock ctcBlock = (CTCBlock)block;
            ctcBlock.setClosed(!ctcBlock.getClosed());
        });
        Button switchMode = UICommon.createButton("Toggle Switch", 200, 10);
        switchMode.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            CTCShift shiftBlock = (CTCShift) block;
            shiftBlock.togglePosition();

        });
        Label statusLabel = UICommon.createLabel("Status");
        statusLabel.setStyle("-fx-font-size: 18;");
        statusLabel.setAlignment(Pos.BOTTOM_CENTER);
        HBox statusBox = new HBox(10, statusLabel, circleGreen, circleBlue, circleRed);
        statusBox.setAlignment(Pos.CENTER);
        VBox headerBox ;
        if (block instanceof CTCShift){
            headerBox = new VBox(5, statusBox, closedMode, switchMode);
        }
        else{
            headerBox = new VBox(5, statusBox, closedMode);
        }
        headerBox.setStyle("-fx-background-color: -fx-title-color;");
        headerBox.setPadding( new Insets(5));
        headerBox.setAlignment(Pos.CENTER);

        VBox tableBox = new VBox();
        // Block Number Box
        HBox blockNumberBox  = new HBox();
        blockNumberBox.setAlignment(Pos.CENTER);

        Label blockNumberLabel0 = UICommon.createLabel("Block Number");
        customizeLabel(blockNumberLabel0, blockNumberBox);

        Label blockNumberLabel1 = UICommon.createLabel("" + block.getBlockNumber());
        customizeLabel(blockNumberLabel1, blockNumberBox);

        blockNumberBox.getChildren().addAll(blockNumberLabel0, blockNumberLabel1);
        tableBox.getChildren().add(blockNumberBox);


        // Functional Box
        HBox functionalBox= new HBox();
        functionalBox.setAlignment(Pos.CENTER);

        Label functionalLabel0 = UICommon.createLabel("Functional");
        customizeLabel(functionalLabel0, functionalBox);

        Label functionalLabel1 = UICommon.createLabel( UICommon.booleanToOnOff(block.getFunctional()));
        customizeLabel(functionalLabel1, functionalBox);

        functionalBox.getChildren().addAll(functionalLabel0,functionalLabel1);
        tableBox.getChildren().add(functionalBox);

        block.functionalProperty().addListener((obs, oldText, newText) -> {
                functionalLabel1.setText(UICommon.booleanToYesNo(newText));
                statusUpdate(block, circleRed, circleOrange, circleBlue, circleGreen);
        });

         // Occupied Box
        HBox occupiedBox= new HBox();
        occupiedBox.setAlignment(Pos.CENTER);

        Label occupiedLabel0 = UICommon.createLabel("Occupied");
        customizeLabel(occupiedLabel0, occupiedBox);
        
        Label occupiedLabel1 = UICommon.createLabel( UICommon.booleanToOnOff(block.getOccupied()));
        customizeLabel(occupiedLabel1, occupiedBox);

        occupiedBox.getChildren().addAll(occupiedLabel0,occupiedLabel1);
        tableBox.getChildren().add(occupiedBox);

        block.occupiedProperty().addListener((obs, oldText, newText) -> {
                occupiedLabel1.setText(UICommon.booleanToYesNo(newText));
                statusUpdate(block, circleRed, circleOrange, circleBlue, circleGreen);
        });

        // Closed Box
        HBox closedBox= new HBox();
        closedBox.setAlignment(Pos.CENTER);

        Label closedLabel0 = UICommon.createLabel("Closed");
        customizeLabel(closedLabel0, closedBox);
        
        Label closedLabel1 = UICommon.createLabel( UICommon.booleanToOnOff(block.getOccupied()));
        customizeLabel(closedLabel1, closedBox);

        closedBox.getChildren().addAll(closedLabel0, closedLabel1);
        tableBox.getChildren().add(closedBox);

        ((CTCBlock)block).closedProperty().addListener((obs, oldText, newText) -> {
                closedLabel1.setText(UICommon.booleanToYesNo(newText));
                statusUpdate(block, circleRed, circleOrange, circleBlue, circleGreen);
        });

       if (block instanceof CTCShift){   
            // Switch Box
            CTCShift shiftBlock = (CTCShift)block;
            HBox switchBox= new HBox();
            switchBox.setAlignment(Pos.CENTER);

            Label switchLabel0 = UICommon.createLabel("Switch Position");
            customizeLabel(switchLabel0, switchBox);
            
            Label switchLabel1 = UICommon.createLabel("" + shiftBlock.getPosition().getBlockNumber());
            customizeLabel(switchLabel1, switchBox);

            switchBox.getChildren().addAll(switchLabel0, switchLabel1);
            tableBox.getChildren().add(switchBox);
            shiftBlock.positionProperty().addListener((obs, oldBlock, newBlock) -> {
                switchLabel1.setText("" + newBlock.getBlockNumber());
                statusUpdate(block, circleRed, circleOrange, circleBlue, circleGreen);
        });
    
        }

        VBox totalBox = new VBox( headerBox, tableBox);
        Scene scene = new Scene(totalBox);
        return scene;
    }

    private void customizeLabel(Label label, HBox box){
        label.setStyle("-fx-font-size: 12; -fx-border-color: -fx-focus-color;");
        label.setAlignment(Pos.CENTER_LEFT);
        label.setPadding( new Insets(5));
        label.prefWidthProperty().bind(box.widthProperty().divide((2)));
    }
    
    private void statusUpdate( Block block, Circle circleRed,Circle circleOrange, Circle circleBlue, Circle circleGreen) {
        if(block.getFunctional() == false) {
            circleRed.setFill(Color.RED);
            circleBlue.setFill(Color.GRAY);
            circleGreen.setFill(Color.GRAY);
            this.circleMap.get(block).setFill(Color.RED);
        }
        else if(CTCModule.map.getClosedBlocks().contains(block.getUUID())){
            circleRed.setFill(Color.GRAY);
            circleBlue.setFill(Color.GRAY);
            circleGreen.setFill(Color.GRAY);
            this.circleMap.get(block).setFill(Color.GRAY);
        }
        else if(block.getOccupied() == true) {
            circleRed.setFill(Color.GRAY);
            circleBlue.setFill(Color.BLUE);
            circleGreen.setFill(Color.GRAY);
            this.circleMap.get(block).setFill(Color.BLUE);
        }
        else {
            circleRed.setFill(Color.GRAY);
            circleBlue.setFill(Color.GRAY);
            circleGreen.setFill(Color.GREEN);
            this.circleMap.get(block).setFill(Color.GREEN);
        }
    }
}