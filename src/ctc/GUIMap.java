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
import src.track_module.Block;
import src.track_module.Edge;
import src.track_module.BlockConstructor.Shift;
import src.track_module.BlockConstructor.Yard;
import src.BaseMap;

public class GUIMap extends BaseMap {
    
    @Override
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
            
            //ADDED listener here to make occupancy dynamically update in CTC
            block.occupiedProperty().addListener((obs, oldText, newText) -> {
                this.circleMap.get(block).setFill(Color.BLUE);
            });
            block.functionalProperty().addListener((obs, oldText, newText) -> {
                this.circleMap.get(block).setFill(Color.RED);
            });

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

    @Override
    public Scene buildPopUp(Block block) {
        
        Circle circleGreen = UICommon.createCircle(10, Color.WHITE);
        Circle circleBlue = UICommon.createCircle(10, Color.WHITE);
        Circle circleRed = UICommon.createCircle(10, Color.WHITE);
        Circle circleOrange = UICommon.createCircle(10, Color.WHITE);
        statusUpdate(block, circleRed, circleOrange, circleBlue, circleGreen);
        Button failureMode = UICommon.createButton("Set Closed", 200, 10);
        failureMode.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            block.setClosed(!block.getClosed());
        });
        Button switchMode = UICommon.createButton("Toggle Switch", 200, 10);
        switchMode.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            Shift shiftBlock = (Shift) block;
            shiftBlock.togglePosition();

        });
        Label statusLabel = UICommon.createLabel("Status");
        statusLabel.setStyle("-fx-font-size: 18;");
        statusLabel.setAlignment(Pos.BOTTOM_CENTER);
        HBox statusBox = new HBox(10, statusLabel, circleGreen, circleBlue, circleRed, circleOrange);
        statusBox.setAlignment(Pos.CENTER);
        VBox headerBox ;
        if (block instanceof Shift){
            headerBox = new VBox(5, statusBox, failureMode, switchMode);
        }
        else{
            headerBox = new VBox(5, statusBox, failureMode);
        }
        headerBox.setStyle("-fx-background-color: -fx-title-color;");
        headerBox.setPadding( new Insets(5));
        headerBox.setAlignment(Pos.CENTER);

        VBox tableBox = new VBox();

        // Functional Box
        HBox functionalBox= new HBox();
        functionalBox.setAlignment(Pos.CENTER);

        Label functionalLabel0 = UICommon.createLabel("Functional");
        functionalLabel0.setStyle("-fx-font-size: 12; -fx-border-color: -fx-focus-color;");
        functionalLabel0.setAlignment(Pos.CENTER_LEFT);
        functionalLabel0.setPadding( new Insets(5));
        functionalLabel0.prefWidthProperty().bind(functionalBox.widthProperty().divide((2)));
        
        Label functionalLabel1 = UICommon.createLabel( UICommon.booleanToOnOff(block.getFunctional()));
        functionalLabel1.setStyle("-fx-font-size: 12; -fx-border-color: -fx-focus-color;");
        functionalLabel1.setAlignment(Pos.CENTER_LEFT);
        functionalLabel1.setPadding( new Insets(5));
        functionalLabel1.prefWidthProperty().bind(functionalBox.widthProperty().divide((2)));

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
        occupiedLabel0.setStyle("-fx-font-size: 12; -fx-border-color: -fx-focus-color;");
        occupiedLabel0.setAlignment(Pos.CENTER_LEFT);
        occupiedLabel0.setPadding( new Insets(5));
        occupiedLabel0.prefWidthProperty().bind(occupiedBox.widthProperty().divide((2)));
        
        Label occupiedLabel1 = UICommon.createLabel( UICommon.booleanToOnOff(block.getOccupied()));
        occupiedLabel1.setStyle("-fx-font-size: 12; -fx-border-color: -fx-focus-color;");
        occupiedLabel1.setAlignment(Pos.CENTER_LEFT);
        occupiedLabel1.setPadding( new Insets(5));
        occupiedLabel1.prefWidthProperty().bind(occupiedBox.widthProperty().divide((2)));

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
        closedLabel0.setStyle("-fx-font-size: 12; -fx-border-color: -fx-focus-color;");
        closedLabel0.setAlignment(Pos.CENTER_LEFT);
        closedLabel0.setPadding( new Insets(5));
        closedLabel0.prefWidthProperty().bind(closedBox.widthProperty().divide((2)));
        
        Label closedLabel1 = UICommon.createLabel( UICommon.booleanToOnOff(block.getOccupied()));
        closedLabel1.setStyle("-fx-font-size: 12; -fx-border-color: -fx-focus-color;");
        closedLabel1.setAlignment(Pos.CENTER_LEFT);
        closedLabel1.setPadding( new Insets(5));
        closedLabel1.prefWidthProperty().bind(closedBox.widthProperty().divide((2)));

        closedBox.getChildren().addAll(closedLabel0, closedLabel1);
        tableBox.getChildren().add(closedBox);

        block.closedProperty().addListener((obs, oldText, newText) -> {
                closedLabel1.setText(UICommon.booleanToYesNo(newText));
                statusUpdate(block, circleRed, circleOrange, circleBlue, circleGreen);
        });

        if (block instanceof Shift){   
            // Switch Box
            Shift shiftBlock = (Shift)block;
            HBox switchBox= new HBox();
            switchBox.setAlignment(Pos.CENTER);

            Label switchLabel0 = UICommon.createLabel("Switch Position");
            switchLabel0.setStyle("-fx-font-size: 12; -fx-border-color: -fx-focus-color;");
            switchLabel0.setAlignment(Pos.CENTER_LEFT);
            switchLabel0.setPadding( new Insets(5));
            switchLabel0.prefWidthProperty().bind(switchBox.widthProperty().divide((2)));
            
            Label switchLabel1 = UICommon.createLabel("" + shiftBlock.getPosition().getBlockNumber());
            switchLabel1.setStyle("-fx-font-size: 12; -fx-border-color: -fx-focus-color;");
            switchLabel1.setAlignment(Pos.CENTER_LEFT);
            switchLabel1.setPadding( new Insets(5));
            switchLabel1.prefWidthProperty().bind(switchBox.widthProperty().divide((2)));

            switchBox.getChildren().addAll(switchLabel0, switchLabel1);
            tableBox.getChildren().add(switchBox);

            shiftBlock.positionProperty().addListener((obs, oldText, newText) -> {
                    switchLabel1.setText("" + newText);
                    statusUpdate(block, circleRed, circleOrange, circleBlue, circleGreen);
            });
        }


        VBox totalBox = new VBox( headerBox, tableBox);
        Scene scene = new Scene(totalBox);
        return scene;
    }

    private void statusUpdate( Block block, Circle circleRed,Circle circleOrange, Circle circleBlue, Circle circleGreen) {
        if(block.getFunctional() == false) {
            circleRed.setFill(Color.RED);
            circleOrange.setFill(Color.GRAY);
            circleBlue.setFill(Color.GRAY);
            circleGreen.setFill(Color.GRAY);
            this.circleMap.get(block).setFill(Color.RED);
        }
        else if(CTCModule.map.getClosedBlocks().contains(block.getUUID())){
            circleRed.setFill(Color.GRAY);
            circleOrange.setFill(Color.ORANGE);
            circleBlue.setFill(Color.GRAY);
            circleGreen.setFill(Color.GRAY);
            this.circleMap.get(block).setFill(Color.GRAY);
        }
        else if(block.getOccupied() == true) {
            circleRed.setFill(Color.GRAY);
            circleOrange.setFill(Color.GRAY);
            circleBlue.setFill(Color.BLUE);
            circleGreen.setFill(Color.GRAY);
            this.circleMap.get(block).setFill(Color.BLUE);
        }
        else {
            circleRed.setFill(Color.GRAY);
            circleOrange.setFill(Color.GRAY);
            circleBlue.setFill(Color.GRAY);
            circleGreen.setFill(Color.GREEN);
            this.circleMap.get(block).setFill(Color.GREEN);
        }
    }
}