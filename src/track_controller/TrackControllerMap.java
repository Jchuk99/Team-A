package src.track_controller;

import java.nio.file.Paths;
import java.util.*;

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

public class TrackControllerMap extends BaseMap {
    @Override
    public Scene buildPopUp(Block block) {
        Button toggleSwitch = UICommon.createButton("Toggle Switch", 100, 10);
        toggleSwitch.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            ((Shift)block).togglePosition();
        });
        Scene scene = new Scene(toggleSwitch);
        return scene;
    }
}