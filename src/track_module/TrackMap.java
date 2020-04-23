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
import src.track_module.BlockConstructor.Shift;
import src.track_module.BlockConstructor.Crossing;
import src.track_module.BlockConstructor.Station;


public class TrackMap extends BaseMap {
    @Override
    public Scene buildPopUp(Block block) {
        Circle circleGreen = UICommon.createCircle(10, Color.GRAY);
        Circle circleYellow = UICommon.createCircle(10, Color.GRAY);
        Circle circleRed = UICommon.createCircle(10, Color.GRAY);
        Button powerFailureMode = UICommon.createButton("Power Fail", 100, 10);
        Button brokenFailureMode = UICommon.createButton("Rail Fail", 100, 10);
        Button circuitFailureMode = UICommon.createButton("Circuit Fail", 100, 10);
        statusUpdate(block, circleRed, circleYellow, circleGreen);

        powerFailureMode.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            block.setFunctional(!block.getFunctional());
        });
        brokenFailureMode.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            block.setFunctional(!block.getFunctional());
        });
        circuitFailureMode.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            block.setOccupied(!block.getOccupied());
        });

        HBox failureModes = new HBox(powerFailureMode, brokenFailureMode, circuitFailureMode);
        
        Label statusLabel = UICommon.createLabel("Status");
        statusLabel.setStyle("-fx-font-size: 18;");
        statusLabel.setAlignment(Pos.BOTTOM_CENTER);
        HBox statusBox = new HBox(10, statusLabel, circleGreen, circleYellow, circleRed);
        statusBox.setAlignment(Pos.CENTER);

        VBox headerBox = new VBox(5, statusBox, failureModes);
        headerBox.setStyle("-fx-background-color: -fx-title-color;");
        headerBox.setPadding( new Insets(5));
        headerBox.setAlignment(Pos.CENTER);

        VBox tableBox = new VBox();

        //////////
        // Functional Box
        //////////
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

        //////////
        // Occupied Box
        //////////
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

        //////////
        // Heater Box
        //////////
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
            heaterLabel1.setText(UICommon.booleanToOnOff(newText));
        });        

        //////////
        // Signal Box
        //////////
        HBox signalBox= new HBox();
        signalBox.setAlignment(Pos.CENTER);

        Label signalLabel0 = UICommon.createLabel("Signal Light");
        signalLabel0.setStyle("-fx-font-size: 12; -fx-border-color: -fx-focus-color;");
        signalLabel0.setAlignment(Pos.CENTER_LEFT);
        signalLabel0.setPadding( new Insets(5));
        signalLabel0.prefWidthProperty().bind(signalBox.widthProperty().divide((2)));
        
        Label signalLabel1 = UICommon.createLabel(UICommon.intToColorString( block.getSignalLight()));
        signalLabel1.setStyle("-fx-font-size: 12; -fx-border-color: -fx-focus-color;");
        signalLabel1.setAlignment(Pos.CENTER_LEFT);
        signalLabel1.setPadding( new Insets(5));
        signalLabel1.prefWidthProperty().bind(signalBox.widthProperty().divide((2)));

        signalBox.getChildren().addAll(signalLabel0,signalLabel1);
        tableBox.getChildren().add(signalBox);

        block.heaterProperty().addListener((obs, oldText, newText) -> {
            heaterLabel1.setText(UICommon.booleanToOnOff(newText));
        });     

        //////////
        // Final Values
        //////////
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

        //////////
        // Properties unique to shift block
        //////////
        if (block.getClass().getSimpleName().toLowerCase().equals("shift")) {
            Shift shift = (Shift)block;
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);

            Label l0 = UICommon.createLabel("Position");
            l0.setStyle("-fx-font-size: 12; -fx-border-color: -fx-focus-color;");
            l0.setAlignment(Pos.CENTER_LEFT);
            l0.setPadding( new Insets(5));
            l0.prefWidthProperty().bind(hBox.widthProperty().divide((2)));

            Label l1 = UICommon.createLabel("Block #" + shift.getPosition().getBlockNumber());
            l1.setStyle("-fx-font-size: 12; -fx-border-color: -fx-focus-color;");
            l1.setAlignment(Pos.CENTER_LEFT);
            l1.setPadding( new Insets(5));
            l1.prefWidthProperty().bind(hBox.widthProperty().divide((2)));

            shift.positionProperty().addListener((obs, oldText, newText) -> {
                l1.setText("Block #" + shift.getPosition().getBlockNumber());
            });
            hBox.getChildren().addAll(l0,l1);
            tableBox.getChildren().add(hBox);
        }

        //////////
        // Properties unique to crossing block
        //////////
        if (block.getClass().getSimpleName().toLowerCase().equals("crossing")) {
            Crossing crossing = (Crossing)block;
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);

            Label l0 = UICommon.createLabel("Lights");
            l0.setStyle("-fx-font-size: 12; -fx-border-color: -fx-focus-color;");
            l0.setAlignment(Pos.CENTER_LEFT);
            l0.setPadding( new Insets(5));
            l0.prefWidthProperty().bind(hBox.widthProperty().divide((2)));

            Label l1 = UICommon.createLabel( UICommon.booleanToOnOff(crossing.getLights()));
            l1.setStyle("-fx-font-size: 12; -fx-border-color: -fx-focus-color;");
            l1.setAlignment(Pos.CENTER_LEFT);
            l1.setPadding( new Insets(5));
            l1.prefWidthProperty().bind(hBox.widthProperty().divide((2)));

            /*crossing.lightsProperty().addListener((obs, oldText, newText) -> {
                l1.setText(UICommon.booleanToOnOff(crossing.getLights()));
            });*/
            hBox.getChildren().addAll(l0,l1);
            tableBox.getChildren().add(hBox);

            hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);

            Label l2 = UICommon.createLabel("Crossing Closed");
            l2.setStyle("-fx-font-size: 12; -fx-border-color: -fx-focus-color;");
            l2.setAlignment(Pos.CENTER_LEFT);
            l2.setPadding( new Insets(5));
            l2.prefWidthProperty().bind(hBox.widthProperty().divide((2)));

            Label l3 = UICommon.createLabel( UICommon.booleanToYesNo(crossing.getClosed()));
            l3.setStyle("-fx-font-size: 12; -fx-border-color: -fx-focus-color;");
            l3.setAlignment(Pos.CENTER_LEFT);
            l3.setPadding( new Insets(5));
            l3.prefWidthProperty().bind(hBox.widthProperty().divide((2)));

           /* crossing.lightsProperty().addListener((obs, oldText, newText) -> {
                l3.setText(UICommon.booleanToYesNo(crossing.getClosed()));
            });*/
            hBox.getChildren().addAll(l2,l3);
            tableBox.getChildren().add(hBox);
        }

        //////////
        // Properties unique to station block
        //////////
        if (block.getClass().getSimpleName().toLowerCase().equals("station")) {
            Station station = (Station)block;
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);

            Label l0 = UICommon.createLabel("Station Name");
            l0.setStyle("-fx-font-size: 12; -fx-border-color: -fx-focus-color;");
            l0.setAlignment(Pos.CENTER_LEFT);
            l0.setPadding( new Insets(5));
            l0.prefWidthProperty().bind(hBox.widthProperty().divide((2)));

            Label l1 = UICommon.createLabel( station.getName());
            l1.setStyle("-fx-font-size: 12; -fx-border-color: -fx-focus-color;");
            l1.setAlignment(Pos.CENTER_LEFT);
            l1.setPadding( new Insets(5));
            l1.prefWidthProperty().bind(hBox.widthProperty().divide((2)));

            hBox.getChildren().addAll(l0,l1);
            tableBox.getChildren().add(hBox);

            hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);

            Label l2 = UICommon.createLabel("Tickets Sold");
            l2.setStyle("-fx-font-size: 12; -fx-border-color: -fx-focus-color;");
            l2.setAlignment(Pos.CENTER_LEFT);
            l2.setPadding( new Insets(5));
            l2.prefWidthProperty().bind(hBox.widthProperty().divide((2)));

            Label l3 = UICommon.createLabel( Integer.toString(station.getTicketsSold()));
            l3.setStyle("-fx-font-size: 12; -fx-border-color: -fx-focus-color;");
            l3.setAlignment(Pos.CENTER_LEFT);
            l3.setPadding( new Insets(5));
            l3.prefWidthProperty().bind(hBox.widthProperty().divide((2)));

            /*station.ticketsProperty().addListener((obs, oldText, newText) -> {
                l3.setText(Integer.toString(station.getTicketsSold()));
            });*/
            hBox.getChildren().addAll(l2,l3);
            tableBox.getChildren().add(hBox);
        }

        

        VBox totalBox = new VBox( headerBox, tableBox);
        Scene scene = new Scene(totalBox);
        return scene;
    }

    private void statusUpdate( Block block, Circle circleRed, Circle circleYellow, Circle circleGreen) {
        // Updates the circle colors depending on the state of the block
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