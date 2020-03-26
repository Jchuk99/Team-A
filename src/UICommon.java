package src;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class UICommon{
    public static Button createButton(String text, int width, int height) {
        Button button = new Button();
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
        f = (float)(Math.round(f * 100.0) / 100.0);
        return Float.toString(f);
    }
    public static String metersToMiles(Integer i) {
        return metersToMiles((float)i);
    }
    public static String metersToFeet(Float f) {
        f = f * (float)3.28083888;
        f = (float)(Math.round(f * 10.0) / 10.0);
        return Float.toString(f);
    }
    public static String metersToFeet(Integer i) {
        return metersToFeet((float)i);
    }
    public static String metersToYards(Float f) {
        f = f * (float)3.28083888;
        f = (float)(f / 3.0);
        f = (float)(Math.round(f * 10.0) / 10.0);
        return Float.toString(f);
    }
    public static String metersToYards(Integer i) {
        return metersToYards((float)i);
    }
}