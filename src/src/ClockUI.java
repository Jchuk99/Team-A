package src;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClockUI extends Stage {
    final int YEAR= 2020;
    final int MONTH= 1;
    final int DAY= 1;
    final int HOUR= 0;
    final int MINUTE= 0;
    final int SECOND= 0;

    LocalDateTime date;
    public StringProperty timeString = new SimpleStringProperty("");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    final int width = 300;
    final int height = 200;

    // in milliseconds
    public long clockTimeStep = 50;

    public float clockTimeSpeed = 1;
    public int clockStepAmount = 1;

    public ClockUI() {
        setTitle("Clock UI");
        this.date = LocalDateTime.of(YEAR, MONTH, DAY, HOUR, MINUTE, SECOND);
        clockTimeStep = Module.TIMESTEP;

        VBox time = createLabelBox("", timeString);
        final HBox timeBox = new HBox(10, createTextBox("Time"), time);
        timeBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 1; -fx-padding: 10;");

        Label speed = UICommon.createLabel("1.00");
        ToggleButton button = UICommon.createToggleButton("x100 speed", 100, 50);
        Slider slider = createSlider(1, 100, 1);

        final HBox labelBox = new HBox(10, createTextBox("Clock Speed: "), createVBox(speed), UICommon.createHSpacer(), createVBox(button));
        
        ObjectProperty<Float> clockSpeedMultiplier = new SimpleObjectProperty<Float>((float)1.0);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (button.isSelected()) {
                    clockSpeedMultiplier.setValue((float)100.0);
                } else {
                    clockSpeedMultiplier.setValue((float)1.0);
                }
            }
        });

        slider.valueProperty().addListener(new ChangeListener<Number>() { 
            public void changed(ObservableValue <? extends Number > observable, Number oldValue, Number newValue) { 
                clockTimeSpeed = newValue.floatValue() * clockSpeedMultiplier.getValue();
                speed.setText("" + UICommon.roundToTwoDecimal(clockTimeSpeed));
                float step = ((float)Module.TIMESTEP) / clockTimeSpeed;
                if (step < 10) {
                    int multiplier = (int)Math.ceil((float)10.0 / step);
                    step = step * multiplier;
                    clockStepAmount = multiplier;
                } else {
                    clockStepAmount = 1;
                }
                clockTimeStep = (long)step;
            } 
        });

        clockSpeedMultiplier.addListener(new ChangeListener<Float>() {
            @Override
            public void changed(ObservableValue <? extends Float> observable, Float oldValue, Float newValue) {
                double value = slider.getValue();
                // to invoke update
                if (value == 100) {
                    slider.setValue(value - 0.1);
                } else {
                    slider.setValue(value + 0.1);
                }
                slider.setValue(value);
            }
        });
        final VBox sliderBox = new VBox(10, createVBox(labelBox), createVBox(slider));
        sliderBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 1; -fx-padding: 10;");
        
        final VBox fullScreen = new VBox(10, timeBox, sliderBox);

        fullScreen.setPadding(new Insets(10));
        
        setScene(new Scene(fullScreen, width, height));
    }

    public void tickTock() {
        date = date.plus(Module.TIMESTEP, ChronoUnit.MILLIS);
        timeString.set(date.format(timeFormatter));
    }

    private VBox createVBox(Node node) {
        // all VBox create function are unified to 40px height
        VBox box = new VBox(0, node);
        box.setPrefHeight(40);
        box.setAlignment(Pos.CENTER_LEFT);
        return box;
    }

    private VBox createTextBox(String text) {
        // all VBox create function are unified to 40px height
        return createVBox(UICommon.createText(text));
    }

    private VBox createLabelBox(String text, StringProperty valueProperty) {
        // all VBox create function are unified to 40px height
        Label label = UICommon.createLabel(text);
        label.setStyle("-fx-border-style: solid inside; -fx-border-width: 2; -fx-padding: 5;");
        label.textProperty().bind(valueProperty);
        return createVBox(label);
    }

    private static Slider createSlider(double min, double max, double value){
        Slider slider = new Slider();
        slider.setMin(min); 
        slider.setMax(max); 
        slider.setValue(value); 
        return slider;
    }
}