package src.track_module;

import java.util.HashMap;
import java.util.UUID;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import src.UICommon;


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
            circle.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                // Title Example: Green Line: 64
                String title = block.getLine() + " Line: " + Integer.toString(block.getBlockNumber());
                Text t0 = UICommon.createText(title);
                VBox titleBox = new VBox(t0);
                titleBox.setAlignment( Pos.TOP_CENTER);

                String blockType = "Type: " + block.getClass().getSimpleName();
                Text t1 = UICommon.createText(blockType);

                String blockLength = "Length: " + Integer.toString(block.getLength());
                Text t2 = UICommon.createText(blockLength);

                String blockSpeed = "Speed Limit: " + Float.toString(block.getSpeedLimit());
                Text t3 = UICommon.createText(blockSpeed);

                String blockGrade = "Grade: " + Float.toString(block.getGrade());
                Text t4 = UICommon.createText(blockGrade);

                String blockElevation = "Elevation: " + Float.toString(block.getElevation());
                Text t5 = UICommon.createText(blockElevation);

                VBox tableBox = new VBox(t1,t2,t3,t4,t5);
                tableBox.setPadding( new Insets(5));

                VBox totalBox = new VBox( titleBox, tableBox);
                Scene scene = new Scene(totalBox, POPUP_WIDTH, POPUP_HEIGHT);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            });
        }
    }
}
