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
        
        Circle circleGreen = UICommon.createCircle(10, Color.WHITE);
        Circle circleYellow = UICommon.createCircle(10, Color.WHITE);
        Circle circleRed = UICommon.createCircle(10, Color.WHITE);
        if(block.getFunctional() == false) {
            circleRed.setFill(Color.RED);
        }
        else if(block.getOccupied() == true) {
            circleYellow.setFill(Color.YELLOW);
        }
        else {
            circleGreen.setFill(Color.GREEN);
        }
        Button failureMode = UICommon.createButton("Set Failure", 200, 10);
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

        List<String[]> strLists = new ArrayList<String[]>();
        String[] strs0 = {"Type", block.getClass().getSimpleName()};
        String[] strs1 = {"Length", UICommon.metersToYards(block.getLength()) + " Yards"};
        String[] strs2 = {"Speed Limit", UICommon.metersToMiles(block.getSpeedLimit()) + " MPH"};
        String[] strs3 = {"Grade", Float.toString(block.getGrade()) + "°"};
        String[] strs4 = {"Elevation", UICommon.metersToFeet(block.getCummElevation()) + " Feet"};
        String[] strs5 = {"Underground", UICommon.booleanToYesNo(block.getUndeground())};
        String[] strs6 = {"Heater", UICommon.booleanToOnOff(block.getHeater())};
        String[] strs7 = {"Functional", UICommon.booleanToYesNo(block.getFunctional())};
        String[] strs8 = {"Occupied", UICommon.booleanToYesNo(block.getOccupied())};
        
        strLists.add( strs0);
        strLists.add( strs1);
        strLists.add( strs2);
        strLists.add( strs3);
        strLists.add( strs4);
        strLists.add( strs5);
        strLists.add( strs6);
        strLists.add( strs7);
        strLists.add( strs8);

        VBox tableBox = new VBox();

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
}