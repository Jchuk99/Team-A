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

public class TrainModelUI extends Application {
    final int width = 900;
    final int height = 800;

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("TrainModel UI");

        /****** select train ******/
        // TODO: get train data from module
        ObservableList<Person> trainData;
        trainData = FXCollections.observableArrayList();

        final TableView trainTable = createTrainTable(trainData);
        trainTable.setPrefWidth(width / 8);

        // testing: add a train to test
        trainData.add(new Person("Train 1"));
        /****** select train ******/

        /****** beacon ******/
        final HBox beaconBox = new HBox(10, createTextBox("Beacon"), createLabelBox("STATION; CASTLE SHANNON; BLOCK 96;"));
        beaconBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 1; -fx-padding: 10;");
        /****** beacon ******/

        /****** top box - time and signal ******/
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
        final TableView mapTable = new TableView();

        topHalf.setPrefHeight(height / 2);
        mapTable.setPrefHeight(height / 2);

        final VBox fullScreen = new VBox(10, topHalf, mapTable);

        fullScreen.setPadding(new Insets(10));

        primaryStage.setScene(new Scene(fullScreen, width, height));
        primaryStage.show();
 
    }

    private TableView createTrainTable(ObservableList<Person> item) {
        final TableView trainTable = new TableView();
        trainTable.setPlaceholder(new Label("No trains available"));

        final TableColumn<String, Person> trainTable_col = new TableColumn<>("Select Train");
        trainTable_col.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        trainTable.getColumns().add(trainTable_col);

        trainTable.setItems(item);

        return trainTable;
    }

    private HBox createTopBox() {
        VBox time = createLabelBox("11:00:23 am");
        final HBox timeBox = new HBox(10, createTextBox("Time"), time);

        Circle circleG = createCircle(10, Color.GREEN);
        Circle circleY = createCircle(10, Color.YELLOW);
        Circle circleR = createCircle(10, Color.RED);
        final HBox signalBox = new HBox(10, circleG, circleY, circleR);
        signalBox.setAlignment(Pos.CENTER);
        signalBox.setStyle("-fx-border-style: solid inside; -fx-border-width: 2; -fx-padding: 10;");

        final HBox topBox = new HBox(10, createHSpacer(), timeBox, createHSpacer(), signalBox, createHSpacer());
        return topBox;
    }

    private HBox createInfoBox() {
        // TODO: pull data from module
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

        VBox label1 = createLabelBox("30 mph");
        VBox label2 = createLabelBox("30 mph");
        VBox label3 = createLabelBox("1000 ft");
        VBox label4 = createLabelBox("100 kW");
        VBox labelBox1 = new VBox(10, label1, label2, label3, label4);

        VBox label5 = createLabelBox("150");
        VBox label6 = createLabelBox("52.2 tons");
        VBox label7 = createLabelBox("0.44 m/s^2");
        VBox label8 = createLabelBox("0.5 %");
        VBox label9 = createLabelBox("70 F");
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

        VBox light1 = createCircleBox(10, Color.GREEN);
        VBox light2 = createCircleBox(10, Color.GREEN);
        VBox light3 = createCircleBox(10, Color.GREEN);
        VBox light4 = createCircleBox(10, Color.GREEN);
        VBox light5 = createCircleBox(10, Color.GREEN);
        VBox light6 = createCircleBox(10, Color.GREEN);
        VBox lightBox = new VBox(10, createTextBox("Status"), light1, light2, light3, light4, light5, light6);

        VBox button1 = createButtonBox("Insert Failure", 100, 30);
        VBox button2 = createButtonBox("Insert Failure", 100, 30);
        VBox button3 = createButtonBox("Insert Failure", 100, 30);
        VBox button4 = createButtonBox("Insert Failure", 100, 30);
        VBox button5 = createButtonBox("Insert Failure", 100, 30);
        VBox button6 = createButtonBox("Insert Failure", 100, 30);
        VBox buttonBox = new VBox(10, createTextBox("Insert Failure"), button1, button2, button3, button4, button5, button6);

        HBox box = new HBox(10, createHSpacer(), nameBox, createHSpacer(), lightBox, createHSpacer(), buttonBox, createHSpacer());

        final Button ebrakeButton = createButton("Emergency brake", 200, 50);
        ebrakeButton.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-font-size:20; -fx-text-fill: black; -fx-background-color: red;");
        ebrakeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO: button handle
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

    private Button createButton(String text, int width, int height) {
        Button button = new Button();
        button.setText(text);
        button.setPrefWidth(width);
        button.setPrefHeight(height);
        return button;
    }

    private VBox createButtonBox(String text, int width, int height) {
        // all VBox create function are unified to 40px height
        VBox buttonBox = new VBox(0, createButton(text, width, height));
        buttonBox.setPrefHeight(40);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        return buttonBox;
    }

    private Text createText(String text) {
        Text t = new Text(text);
        return t;
    }

    private VBox createTextBox(String text) {
        // all VBox create function are unified to 40px height
        VBox textBox = new VBox(0, createText(text));
        textBox.setPrefHeight(40);
        textBox.setAlignment(Pos.CENTER_LEFT);
        return textBox;
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-border-style: solid inside; -fx-border-width: 2; -fx-padding: 5;");
        return label;
    }

    private VBox createLabelBox(String text) {
        // all VBox create function are unified to 40px height
        VBox labelBox = new VBox(0, createLabel(text));
        labelBox.setPrefHeight(40);
        labelBox.setAlignment(Pos.CENTER_LEFT);
        return labelBox;
    }

    private Circle createCircle(int radius, Color color) {
        Circle circle = new Circle();
        circle.setRadius(radius);
        circle.setFill(color);
        return circle;
    }

    private VBox createCircleBox(int radius, Color color) {
        // all VBox create function are unified to 40px height
        VBox circleBox = new VBox(createCircle(radius, color));
        circleBox.setPrefHeight(40);
        circleBox.setAlignment(Pos.CENTER_LEFT);
        return circleBox;
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