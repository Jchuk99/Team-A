package src.track_module;

import java.util.HashMap;
import java.util.UUID;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.geometry.Pos;


public class Map {
    static final int POPUP_WIDTH= 300;
    static final int POPUP_HEIGHT= 250;

    static final String BLOCKSTYLE = "-fx-fill: rgba(54,215,68,0.8)";
    static final String LINESTYLE = "-fx-stroke-width: 2; -fx-stroke: rgba(160,160,160,0.4); -fx-stroke-dash-array: 10 5;";
    
    static public void buildMap( HashMap<UUID, Block> blocks, Pane pane) {
        for(Block block : blocks.values()) {
            Circle circle = new Circle(block.getX(), block.getY(), 20);
            circle.setStyle(BLOCKSTYLE);
            pane.getChildren().add( circle);
            for(Edge edge: block.edges) {
                Line line= new Line( block.getX(), block.getY(), edge.getBlock().getX(), edge.getBlock().getY());
                line.setStyle(LINESTYLE);
                pane.getChildren().add(line);
            }
        }

        VBox buttonBox = createButtonBox("Select Track File", 150, 30);
        Scene scene = new Scene(buttonBox, POPUP_WIDTH, POPUP_HEIGHT);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        
    }

    private static VBox createButtonBox(String text, int width, int height) {
        // all VBox create function are unified to 40px height
        VBox buttonBox = new VBox(0, createButton(text, width, height));
        buttonBox.setPrefHeight(40);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        return buttonBox;
    }

    private static Button createButton(String text, int width, int height) {
        Button button = new Button();
        button.setText(text);
        button.setPrefWidth(width);
        button.setPrefHeight(height);
        return button;
    }
}
