import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.layout.Priority; 
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.stage.Modality;
 
public class TrainControllerUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }

	@Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Wayside Controller UI");
        int length = 900;
        int height = 800;


        VBox trainInfo = new VBox();
        trainInfo.setPrefWidth(length*2/3);

        Label leftDoor = new Label("Left Doors Closed");
        Label rightDoor = new Label("Right Doors Closed");
        VBox miscControl = new VBox(leftDoor, rightDoor);
        miscControl.setPrefWidth(length/3);

        HBox topHalf = new HBox(10, trainInfo, miscControl);
        topHalf.setPrefHeight(height/2);




        TextArea map  = new TextArea("Test");
        map.setPrefWidth(length*2/3);
        map.setPrefHeight(height/2);
        
        VBox fullScreen = new VBox(10, topHalf, map);

        fullScreen.setPadding(new Insets(10));

        primaryStage.setScene(new Scene(fullScreen, length, height));
        primaryStage.show();
    }
}