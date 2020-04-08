package src.train_module;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

public class TrainModuleUI extends Stage {
    public static TrainModule trainModule;

    TrainData trainData = new TrainData();

    final int width = 900;
    final int height = 800;

    public TrainModuleUI() {
        setTitle("TrainModel UI");

        /****** select train ******/
        final TableView<Train> trainTable = createTrainTable(trainModule.getTrainList());
        trainTable.setPrefWidth(width / 8);

        trainTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Train>() {
            @Override
            public void changed(ObservableValue observableValue, Train oldValue, Train newValue) {
                //Check whether item is selected and set value of selected item to Label
                Train train = trainTable.getSelectionModel().getSelectedItem();
                trainData.setTrain(train);
            }
        });
        /****** select train ******/

        /****** beacon ******/
        // TODO: beacon update
        final HBox beaconBox = new HBox(10, createTextBox("Beacon"), createLabelBox("STATION; CASTLE SHANNON; BLOCK 96;"));
        beaconBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 1; -fx-padding: 10;");
        /****** beacon ******/

        /****** top box - time and signal ******/
        // TODO: top box update
        final HBox topBox = createTopBox();
        topBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 1; -fx-padding: 10;");
        /****** top box - time and signal ******/

        /****** info ******/
        final HBox infoBox = createInfoBox();
        infoBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 1; -fx-padding: 10;");
        /****** info ******/

        /****** status ******/
        final VBox statusBox = createStatusBox();
        statusBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 1; -fx-padding: 10;");
        /****** status ******/

        // combining boxes
        final VBox box1 = new VBox(10, topBox, infoBox);
        final HBox box2 = new HBox(10, trainTable, box1);
        final VBox box3 = new VBox(10, box2, beaconBox);

        final HBox topHalf = new HBox(10, box3, statusBox);
        // TODO: map
        final TableView mapTable = new TableView();

        topHalf.setPrefHeight(height / 2);
        mapTable.setPrefHeight(height / 2);

        final VBox fullScreen = new VBox(10, topHalf, mapTable);

        fullScreen.setPadding(new Insets(10));

        setScene(new Scene(fullScreen, width, height));
    }

    public static void setModule(TrainModule module) {
        trainModule = module;
    }

    private TableView<Train> createTrainTable(ObservableList<Train> item) {
        final TableView<Train> trainTable = new TableView<Train>();
        trainTable.setPlaceholder(new Label("No trains available"));

        final TableColumn<Train, String> trainTableCol = new TableColumn<Train, String>("Select Train");
        trainTableCol.setCellValueFactory(cellData -> cellData.getValue().getName());
        trainTable.getColumns().add(trainTableCol);

        trainTable.setItems(item);

        return trainTable;
    }

    private HBox createTopBox() {
        VBox time = createLabelBox("11:00:23 am");
        final HBox timeBox = new HBox(10, createTextBox("Time"), time);

        Circle circleG = createCircle(10, Color.GREEN);
        Circle circleY = createCircle(10, Color.YELLOW);
        Circle circleR = createCircle(10, Color.RED);
        final HBox circleBox = new HBox(10, circleG, circleY, circleR);
        circleBox.setAlignment(Pos.CENTER);
        circleBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 2; -fx-padding: 10;");

        final HBox signalBox = new HBox(10, createTextBox("Signal Light"), circleBox);

        final HBox topBox = new HBox(10, createHSpacer(), timeBox, createHSpacer(), signalBox, createHSpacer());
        return topBox;
    }

    private HBox createInfoBox() {
        VBox name1 = createTextBox("Suggested Speed");
        VBox name2 = createTextBox("Current Speed");
        VBox name3 = createTextBox("Authority");
        VBox name4 = createTextBox("Engine Power");
        VBox nameBox1 = new VBox(10, name1, name2, name3, name4);

        VBox name5 = createTextBox("Passenger Count");
        VBox name6 = createTextBox("Current Weight");
        VBox name7 = createTextBox("Current Acceleration");
        VBox name8 = createTextBox("Current Grade");
        VBox name9 = createTextBox("Temperature Inside");
        VBox nameBox2 = new VBox(10, name5, name6, name7, name8, name9);

        VBox label1 = createLabelBox("", trainData.getSuggestedSpeed());
        VBox label2 = createLabelBox("", trainData.getCurrentSpeed());
        VBox label3 = createLabelBox("", trainData.getAuthority());
        VBox label4 = createLabelBox("", trainData.getCurrentPower());
        VBox labelBox1 = new VBox(10, label1, label2, label3, label4);

        VBox label5 = createLabelBox("", trainData.getPassengetCount());
        VBox label6 = createLabelBox("", trainData.getCurrentWeight());
        VBox label7 = createLabelBox("", trainData.getCurrentAcceleration());
        VBox label8 = createLabelBox("", trainData.getCurrentGrade());
        VBox label9 = createLabelBox("", trainData.getTemperatureInside());
        VBox labelBox2 = new VBox(10, label5, label6, label7, label8, label9);
        
        final HBox infoBox = new HBox(10, createHSpacer(), nameBox1, labelBox1, createHSpacer(), nameBox2, labelBox2, createHSpacer());
        return infoBox;
    }

    private VBox createStatusBox() {
        VBox name1 = createTextBox("Left Door");
        VBox name2 = createTextBox("Right Door");
        VBox name3 = createTextBox("Lights");
        VBox name4 = createTextBox("Service Brake");
        VBox name5 = createTextBox("Emergency Brake");
        VBox name6 = createTextBox("Train Engine");
        VBox nameBox = new VBox(10, createVBox(), name1, name2, name3, name4, name5, name6);

        VBox light1 = createCircleBox(10, Color.GREEN, Color.RED, trainData.getLeftDoorWorking());
        VBox light2 = createCircleBox(10, Color.GREEN, Color.RED, trainData.getRightDoorWorking());
        VBox light3 = createCircleBox(10, Color.GREEN, Color.RED, trainData.getLightWorking());
        VBox light4 = createCircleBox(10, Color.GREEN, Color.RED, trainData.getServiceBrakeWorking());
        VBox light5 = createCircleBox(10, Color.GREEN, Color.RED, trainData.getEmergencyBrakeWorking());
        VBox light6 = createCircleBox(10, Color.GREEN, Color.RED, trainData.getEngineWorking());
        VBox lightBox = new VBox(10, createTextBox("Status"), light1, light2, light3, light4, light5, light6);

        Button button1 = createButton("Insert Failure", 100, 30);
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                trainData.setLeftDoorWorking(false);
            }
        });
        Button button2 = createButton("Insert Failure", 100, 30);
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                trainData.setRightDoorWorking(false);
            }
        });
        Button button3 = createButton("Insert Failure", 100, 30);
        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                trainData.setLightWorking(false);
            }
        });
        Button button4 = createButton("Insert Failure", 100, 30);
        button4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                trainData.setServiceBrakeWorking(false);
            }
        });
        Button button5 = createButton("Insert Failure", 100, 30);
        button5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                trainData.setEmergencyBrakeWorking(false);
            }
        });
        Button button6 = createButton("Insert Failure", 100, 30);
        button6.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                trainData.setEngineWorking(false);
            }
        });
        VBox buttonBox = new VBox(10, createTextBox("Insert Failure"), createVBox(button1), createVBox(button2), createVBox(button3), createVBox(button4), createVBox(button5), createVBox(button6));

        HBox box = new HBox(10, createHSpacer(), nameBox, createHSpacer(), lightBox, createHSpacer(), buttonBox, createHSpacer());

        final Button ebrakeButton = createButton("Emergency brake", 200, 50);
        ebrakeButton.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-font-size:20; -fx-text-fill: black; -fx-background-color: red;");
        ebrakeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                trainData.setEmergencyBrake(true);
            }
        });
        // TODO: add effect when button is clicked
        DropShadow shadow = new DropShadow();
        //Adding the shadow when the mouse cursor is on
        ebrakeButton.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                ebrakeButton.setEffect(shadow);
            }
        });
        //Removing the shadow when the mouse cursor is off
        ebrakeButton.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                ebrakeButton.setEffect(null);
            }
        });

        final VBox statusBox = new VBox(10, box, ebrakeButton);
        statusBox.setAlignment(Pos.CENTER);
        return statusBox;
    }

    private VBox createVBox() {
        // all VBox create function are unified to 40px height
        VBox box = new VBox(0);
        box.setPrefHeight(40);
        box.setAlignment(Pos.CENTER_LEFT);
        return box;
    }

    private VBox createVBox(Node node) {
        // all VBox create function are unified to 40px height
        VBox box = new VBox(0, node);
        box.setPrefHeight(40);
        box.setAlignment(Pos.CENTER_LEFT);
        return box;
    }

    private Button createButton(String text, int width, int height) {
        Button button = new Button();
        button.setText(text);
        button.setPrefWidth(width);
        button.setPrefHeight(height);
        return button;
    }

    private VBox createButtonBox(String text, int width, int height) {
        // all VBox create function are unified to 40px height
        return createVBox(createButton(text, width, height));
    }

    private Text createText(String text) {
        Text t = new Text(text);
        return t;
    }

    private VBox createTextBox(String text) {
        // all VBox create function are unified to 40px height
        return createVBox(createText(text));
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-border-style: solid inside; -fx-border-width: 2; -fx-padding: 5;");
        return label;
    }

    private VBox createLabelBox(String text) {
        // all VBox create function are unified to 40px height
        return createVBox(createLabel(text));
    }

    private VBox createLabelBox(String text, StringProperty valueProperty) {
        // all VBox create function are unified to 40px height
        Label label = createLabel(text);
        label.textProperty().bind(valueProperty);
        return createVBox(label);
    }

    private Circle createCircle(int radius, Color color) {
        Circle circle = new Circle();
        circle.setRadius(radius);
        circle.setFill(color);
        return circle;
    }

    private VBox createCircleBox(int radius, Color color) {
        // all VBox create function are unified to 40px height
        return createVBox(createCircle(radius, color));
    }

    private VBox createCircleBox(int radius, Color color1, Color color2, BooleanProperty booleanProperty) {
        // all VBox create function are unified to 40px height
        // boolean true for color1, false for color2
        Circle circle = new Circle();
        circle.setRadius(radius);
        Bindings.when(booleanProperty).then(color1).otherwise(color2);
        booleanProperty.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue o,Boolean oldVal, Boolean newVal){
                if (newVal) {
                    circle.setFill(color1);
                } else {
                    circle.setFill(color2);
                }
            }
        });
        return createVBox(circle);
    }

    private static Node createVSpacer() {
        final Region spacer = new Region();
        // Make it always grow or shrink according to the available space
        VBox.setVgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    private static Node createHSpacer() {
        final Region spacer = new Region();
        // Make it always grow or shrink according to the available space
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }
    
}