package src.track_module;

import java.util.ArrayList;
import java.util.List;

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
import src.BaseMap;

public class TrackMap extends BaseMap {
    @Override
    public Scene buildPopUp(Block block) {
        Circle circleGreen = UICommon.createCircle(10, Color.GRAY);
        Circle circleYellow = UICommon.createCircle(10, Color.GRAY);
        Circle circleRed = UICommon.createCircle(10, Color.GRAY);
        Button failureMode = UICommon.createButton("Set Failure", 200, 10);
        statusUpdate(block, circleRed, circleYellow, circleGreen);

        failureMode.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            block.setFunctional(!block.getFunctional());
        });
        Label statusLabel = UICommon.createLabel("Status");
        statusLabel.setStyle("-fx-font-size: 18;");
        statusLabel.setAlignment(Pos.BOTTOM_CENTER);
        HBox statusBox = new HBox(10, statusLabel, circleGreen, circleYellow, circleRed);
        statusBox.setAlignment(Pos.CENTER);

        VBox headerBox = new VBox(5, statusBox, failureMode);
        headerBox.setStyle("-fx-background-color: -fx-title-color;");
        headerBox.setPadding( new Insets(5));
        headerBox.setAlignment(Pos.CENTER);

        VBox tableBox = new VBox();

        // Occupied Box
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
                statusUpdate(block, circleRed, circleYellow, circleGreen);
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
                statusUpdate(block, circleRed, circleYellow, circleGreen);
        });

        // Heater Box
        HBox heaterBox= new HBox();
        heaterBox.setAlignment(Pos.CENTER);

        Label heaterLabel0 = UICommon.createLabel("Heater");
        heaterLabel0.setStyle("-fx-font-size: 12; -fx-border-color: -fx-focus-color;");
        heaterLabel0.setAlignment(Pos.CENTER_LEFT);
        heaterLabel0.setPadding( new Insets(5));
        heaterLabel0.prefWidthProperty().bind(heaterBox.widthProperty().divide((2)));
        
        Label heaterLabel1 = UICommon.createLabel( UICommon.booleanToOnOff(block.getHeater()));
        heaterLabel1.setStyle("-fx-font-size: 12; -fx-border-color: -fx-focus-color;");
        heaterLabel1.setAlignment(Pos.CENTER_LEFT);
        heaterLabel1.setPadding( new Insets(5));
        heaterLabel1.prefWidthProperty().bind(heaterBox.widthProperty().divide((2)));

        heaterBox.getChildren().addAll(heaterLabel0,heaterLabel1);
        tableBox.getChildren().add(heaterBox);

        block.heaterProperty().addListener((obs, oldText, newText) -> {
                heaterLabel1.setText(UICommon.booleanToYesNo(newText));
        });        

        // Final Values

        List<String[]> strLists = new ArrayList<String[]>();
        String[] strs0 = {"Type", block.getClass().getSimpleName()};
        String[] strs1 = {"Length", UICommon.metersToYards(block.getLength()) + " Yards"};
        String[] strs2 = {"Speed Limit", UICommon.metersToMiles(block.getSpeedLimit()) + " MPH"};
        String[] strs3 = {"Grade", Float.toString(block.getGrade()) + "°"};
        String[] strs4 = {"Elevation", UICommon.metersToFeet(block.getCummElevation()) + " Feet"};
        String[] strs5 = {"Underground", UICommon.booleanToYesNo(block.getUndeground())};
        
        strLists.add( strs0);
        strLists.add( strs1);
        strLists.add( strs2);
        strLists.add( strs3);
        strLists.add( strs4);
        strLists.add( strs5);

        for(String[] sl : strLists) {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);

            Label l0 = UICommon.createLabel( sl[0]);
            l0.setStyle("-fx-font-size: 12; -fx-border-color: -fx-focus-color;");
            l0.setAlignment(Pos.CENTER_LEFT);
            l0.setPadding( new Insets(5));
            l0.prefWidthProperty().bind(hBox.widthProperty().divide((2)));
            
            Label l1 = UICommon.createLabel(sl[1]);
            l1.setStyle("-fx-font-size: 12; -fx-border-color: -fx-focus-color;");
            l1.setAlignment(Pos.CENTER_LEFT);
            l1.setPadding( new Insets(5));
            l1.prefWidthProperty().bind(hBox.widthProperty().divide((2)));

            hBox.getChildren().addAll(l0,l1);
            tableBox.getChildren().add(hBox);
        }
        VBox totalBox = new VBox( headerBox, tableBox);
        Scene scene = new Scene(totalBox);
        return scene;
    }

    private void statusUpdate( Block block, Circle circleRed, Circle circleYellow, Circle circleGreen) {
        if(block.getFunctional() == false) {
            circleRed.setFill(Color.RED);
            circleYellow.setFill(Color.GRAY);
            circleGreen.setFill(Color.GRAY);
            this.circleMap.get(block).setFill(Color.RED);
        }
        else if(block.getOccupied() == true) {
            circleRed.setFill(Color.GRAY);
            circleYellow.setFill(Color.YELLOW);
            circleGreen.setFill(Color.GRAY);
            this.circleMap.get(block).setFill(Color.YELLOW);
        }
        else {
            circleRed.setFill(Color.GRAY);
            circleYellow.setFill(Color.GRAY);
            circleGreen.setFill(Color.GREEN);
            this.circleMap.get(block).setFill(Color.GREEN);
        }
    }
}