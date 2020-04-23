package src;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Circle;

import src.track_module.Block;

public class UICommon{
    public static Button createButton(String text, int width, int height) {
        Button button = new Button();
        button.setText(text);
        button.setPrefWidth(width);
        button.setPrefHeight(height);
        return button;
    }
    public static ToggleButton createToggleButton(String text, int width, int height) {
        ToggleButton button = new ToggleButton();
        button.setText(text);
        button.setPrefWidth(width);
        button.setPrefHeight(height);
        return button;
    }
    public static Text createText(String text) {
        Text t = new Text(text);
        return t;
    }
    public static Label createLabel(String text) {
        Label label = new Label(text);
        return label;
    }
    public static Circle createCircle(int radius, Color color) {
        Circle circle = new Circle();
        circle.setRadius(radius);
        circle.setFill(color);
        return circle;
    }
    public static Node createVSpacer() {
        final Region spacer = new Region();
        // Make it always grow or shrink according to the available space
        VBox.setVgrow(spacer, Priority.ALWAYS);
        return spacer;
    }
    public static Node createHSpacer() {
        final Region spacer = new Region();
        // Make it always grow or shrink according to the available space
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    // USEFUL STRING CONVERSIONS
    public static String booleanToYesNo(Boolean b) {
        if(b) return "Yes";
        return "No";
    }
    public static String booleanToOnOff(Boolean b) {
        if(b) return "On";
        return "Off";
    }
    public static String metersToMiles(Float f) {
        f = f * (float)0.000621371;
        return Float.toString(roundToTwoDecimal(f));
    }
    public static String metersToMiles(Integer i) {
        return metersToMiles((float)i);
    }
    public static String metersToFeet(Float f) {
        f = f * (float)3.28083888;
        return Float.toString(roundToOneDecimal(f));
    }
    public static String metersToFeet(Integer i) {
        return metersToFeet((float)i);
    }
    public static String metersToYards(Float f) {
        f = f * (float)3.28083888;
        f = (float)(f / 3.0);
        return Float.toString(roundToOneDecimal(f));
    }
    public static String metersToYards(Integer i) {
        return metersToYards((float)i);
    }
    public static String metersPerSecondToMilesPerHour(float f) {
        f = f * (float)2.23693629;
        return Float.toString(roundToOneDecimal(f));
    }
    public static String metersPerSecondToMilesPerHour(int i) {
        return metersPerSecondToMilesPerHour((float)i);
    }
    public static String metersPerSecondSquaredToFeetPerSecondSquared(float f) {
        f = f * (float)3.2808399;
        return Float.toString(roundToTwoDecimal(f));
    }
    public static String metersPerSecondSquaredToFeetPerSecondSquared(int i) {
        return metersPerSecondSquaredToFeetPerSecondSquared((float)i);
    }
    public static float roundToOneDecimal(float f) {
        return (float)(Math.round(f * 10.0) / 10.0);
    }
    public static float roundToTwoDecimal(float f) {
        return (float)(Math.round(f * 100.0) / 100.0);
    }

    public static String intToColorString(int color) {
        if(color == Block.GREEN_SIGNAL) {
            return "Green";
        }
        else if(color == Block.YELLOW_SIGNAL) {
            return "Yellow";
        }
        else if(color == Block.RED_SIGNAL) {
            return "Red";
        }
        else {
            return null;
        }
    }
}