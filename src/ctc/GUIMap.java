package src.ctc;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import src.UICommon;
import src.track_module.Block;
import src.track_module.BlockConstructor.Shift;
import src.BaseMap;

public class GUIMap extends BaseMap {
    
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
            customizeLabel(switchLabel0, switchBox);
            
            Label switchLabel1 = UICommon.createLabel("" + shiftBlock.getPosition().getBlockNumber());
            customizeLabel(switchLabel1, switchBox);

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

    private void customizeLabel(Label label, HBox box){
        label.setStyle("-fx-font-size: 12; -fx-border-color: -fx-focus-color;");
        label.setAlignment(Pos.CENTER_LEFT);
        label.setPadding( new Insets(5));
        label.prefWidthProperty().bind(box.widthProperty().divide((2)));

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