package src;

import javafx.geometry.Pos;
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
    public static VBox createVBox() {
        // all VBox create function are unified to 40px height
        VBox box = new VBox(0);
        box.setPrefHeight(40);
        box.setAlignment(Pos.CENTER_LEFT);
        return box;
    }

    public static Button createButton(String text, int width, int height) {
        Button button = new Button();
        button.setText(text);
        button.setPrefWidth(width);
        button.setPrefHeight(height);
        return button;
    }

    public static VBox createButtonBox(String text, int width, int height) {
        // all VBox create function are unified to 40px height
        VBox buttonBox = new VBox(0, createButton(text, width, height));
        buttonBox.setPrefHeight(40);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        return buttonBox;
    }

    public static Text createText(String text) {
        Text t = new Text(text);
        return t;
    }

    public static VBox createTextBox(String text) {
        VBox textBox = new VBox(0, createText(text));
        textBox.setPrefHeight(40);
        textBox.setAlignment(Pos.CENTER_LEFT);
        return textBox;
    }

    public static Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-border-style: solid inside; -fx-border-width: 2; -fx-padding: 5;");
        return label;
    }

    public static VBox createLabelBox(String text) {
        // all VBox create function are unified to 40px height
        VBox labelBox = new VBox(0, createLabel(text));
        labelBox.setPrefHeight(40);
        labelBox.setAlignment(Pos.CENTER_LEFT);
        return labelBox;
    }

    public static Circle createCircle(int radius, Color color) {
        Circle circle = new Circle();
        circle.setRadius(radius);
        circle.setFill(color);
        return circle;
    }

    public static VBox createCircleBox(int radius, Color color) {
        // all VBox create function are unified to 40px height
        VBox circleBox = new VBox(createCircle(radius, color));
        circleBox.setPrefHeight(40);
        circleBox.setAlignment(Pos.CENTER_LEFT);
        return circleBox;
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

}