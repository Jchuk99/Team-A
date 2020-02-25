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

        // TODO: get train data from module
        ObservableList<Person> trainData;
        trainData = FXCollections.observableArrayList();

        /****** select train ******/
        final TableView trainTable = createTrainTable(trainData);

        // testing: add a train to test
        trainData.add(new Person("Train 1"));
        /****** select train ******/
        /****** beacon ******/
        /****** beacon ******/
        /****** top box - time and signal ******/
        final HBox topBox = createTopBox();
        /****** top box - time and signal ******/
        /****** info ******/
        final HBox infoBox = createInfoBox();
        /****** info ******/
        /****** status ******/
        /****** status ******/

        // combining boxes
        final VBox box1 = new VBox(10, topBox, infoBox);
        final HBox box2 = new HBox(10, trainTable, box1);


        final HBox topHalf = new HBox(10, box2);
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
        trainTable.setPrefWidth(width / 6);

        final TableColumn<String, Person> trainTable_col = new TableColumn<>("Select Train");
        trainTable_col.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        trainTable.getColumns().add(trainTable_col);

        trainTable.setItems(item);

        return trainTable;
    }

    private HBox createTopBox() {
        final HBox topBox = new HBox(10);
        return topBox;
    }

    private HBox createInfoBox() {
        // TODO: pull data from module
        HBox leftBox1 = createDataField("Suggested Speed", "30 mph");
        HBox leftBox2 = createDataField("Current Speed", "30 mph");
        HBox leftBox3 = createDataField("Authority", "1000 ft");
        HBox leftBox4 = createDataField("Engine Power", "100 kW");

        HBox rightBox1 = createDataField("Passenger Count", "150");
        HBox rightBox2 = createDataField("Current Weight", "52.2 tons");
        HBox rightBox3 = createDataField("Current Acceleration", "0.44 m/s^2");
        HBox rightBox4 = createDataField("Current Grade", "0.5 %");
        HBox rightBox5 = createDataField("Temperature Inside", "70 F");

        VBox leftBox = new VBox(10, leftBox1, leftBox2, leftBox3, leftBox4);
        VBox rightBox = new VBox(10, rightBox1, rightBox2, rightBox3, rightBox4, rightBox5);
        final HBox infoBox = new HBox(10, leftBox, rightBox);
        return infoBox;
    }

    private HBox createDataField(String text, String label) {
        Text textbox = new Text(text);
        Label labelbox = new Label(label);
        labelbox.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-padding: 5;");
        HBox box = new HBox(10, textbox, createHSpacer(), labelbox);
        box.setAlignment(Pos.CENTER_LEFT);
        return box;
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