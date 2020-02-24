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
 
public class CTCUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public static void getPLCTextBox(){
        Stage popupwindow = new Stage();   
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("PLC Input");        
        TextArea textArea3 = new TextArea("{Enter PLC Code Here} \n\n //sample code \n\n if(~block1){ \n block1 = 1; \n } \n else{ \n block1 = 0; \n } \n\n enableCrossing = block1 & block2;");
        textArea3.setPrefWidth(500);
        textArea3.setPrefHeight(400);         
        Button confirm = new Button("Confirm");           
        confirm.setOnAction(e -> popupwindow.close());
        Button cancel = new Button("Cancel");           
        cancel.setOnAction(e -> popupwindow.close());
        HBox buttons = new HBox(10, confirm, cancel);
        buttons.setStyle("-fx-padding: 5;"); 
        VBox layout= new VBox(10);         
        layout.getChildren().addAll(textArea3, buttons);           
        layout.setAlignment(Pos.CENTER);           
        Scene scene1= new Scene(layout, 300, 250);            
        popupwindow.setScene(scene1);          
        popupwindow.showAndWait();
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("CTC UI");

        int length = 900;
        int height = 800;

        /******top half******/
        //box1
        TableView plcTable = new TableView();
        TableColumn<String, Person> plcs = new TableColumn<>("Select PLC");
        plcs.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        plcTable.getColumns().add(plcs);
        plcTable.getItems().add(new Person("PLC 1"));
        plcTable.getItems().add(new Person("PLC 2"));
        plcTable.getItems().add(new Person("PLC 3"));
        plcTable.getItems().add(new Person("PLC 4"));
        plcTable.getItems().add(new Person("PLC 5"));
        plcTable.setPrefWidth(length/6);

        HBox spacer = new HBox();
        spacer.setPrefHeight(height/6);
        Button plcInput = new Button();
        plcInput.setText("PLC Input");
        plcInput.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                getPLCTextBox();
            }
        });
        Button plcUpload = new Button();
        plcUpload.setText("Upload PLC File");
        VBox buttonGrouper = new VBox(10, spacer, plcInput, plcUpload);
        buttonGrouper.setPrefHeight(height/6); 

        HBox box1 = new HBox(10, plcTable, buttonGrouper);
        box1.setPrefWidth(length/3);
        box1.setPrefHeight(height/2);  
        box1.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-padding: 5;"); 


        //box2
        Label signalLight = new Label("Signal Light:");
        Circle greenCircle = new Circle(); 
        Circle yellowCircle = new Circle(); 
        Circle redCircle = new Circle();
        greenCircle.setRadius(10);  
        redCircle.setRadius(10);  
        yellowCircle.setRadius(10);   
        greenCircle.setFill(Color.GREEN); 
        redCircle.setFill(Color.RED); 
        yellowCircle.setFill(Color.YELLOW); 
        HBox lightGrouper = new HBox(10, signalLight, greenCircle, yellowCircle, redCircle);
        lightGrouper.setPrefHeight(height/6);
        lightGrouper.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 1;" + "-fx-padding: 5;");
        

        Label railwayCrossing = new Label("Railway Crossing Status:");
        Rectangle statusRectangle = new Rectangle(10, 10, 40, 20);
        HBox statusGrouper = new HBox(10, railwayCrossing, statusRectangle);
        statusGrouper.setPrefHeight(height/6);
        statusGrouper.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 1;" + "-fx-padding: 5;");

        TableView switchTable = new TableView();
        TableColumn<String, Person> switchID = new TableColumn<>("Switch ID");
        switchID.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn<String, Person> switchPosition = new TableColumn<>("Switch Position");
        switchPosition.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        switchTable.getColumns().add(switchID);
        switchTable.getColumns().add(switchPosition);
        switchTable.getItems().add(new Person("3", "1"));
        switchTable.getItems().add(new Person("3", "1"));
        switchTable.setPrefWidth(length/6);
        switchTable.setPrefHeight(height/6);

        VBox box2 = new VBox(10, lightGrouper, statusGrouper, switchTable);
        box2.setPrefWidth(length/3);
        box2.setPrefHeight(height/2);
        box2.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-padding: 5;");

        //box3
        TableView blockTable = new TableView();
        TableColumn<String, Person> blockID = new TableColumn<>("Block ID");
        blockID.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn<String, Person> blockStatus = new TableColumn<>("Block Status");
        blockStatus.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableColumn<String, Person> blockOpenClose= new TableColumn<>("Block Open/Close");
        blockOpenClose.setCellValueFactory(new PropertyValueFactory<>("blockOpenClose"));
        TableColumn<String, Person> suggestedSpeed = new TableColumn<>("Suggested Speed (mph)");
        suggestedSpeed.setCellValueFactory(new PropertyValueFactory<>("suggestedSpeed"));
        TableColumn<String, Person> authority = new TableColumn<>("Authority (ft)");
        authority.setCellValueFactory(new PropertyValueFactory<>("authority"));
        blockTable.getColumns().add(blockID);
        blockTable.getColumns().add(blockStatus);
        blockTable.getColumns().add(blockOpenClose);
        blockTable.getColumns().add(suggestedSpeed);
        blockTable.getColumns().add(authority);
        blockTable.getItems().add(new Person("1", "Empty", "Open", 0, 0));
        blockTable.getItems().add(new Person("2", "Occupied", "Closed", 25, 0));
        blockTable.getItems().add(new Person("3", "Occupied", "Closed", 25, 300));
        blockTable.getItems().add(new Person("4", "Empty", "Closed", 0, 0));
        blockTable.getItems().add(new Person("5", "Occupied", "Open", 25, 1000));
        blockTable.setPrefWidth(length/3);
        VBox box3 = new VBox(blockTable);
        box3.setPrefWidth(length/3);
        box3.setPrefHeight(height/2);
        box3.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-padding: 5;");
        //textArea.setMaxWidth(TextArea.USE_PREF_SIZE);
        //textArea.setMinWidth(TextArea.USE_PREF_SIZE);

        /******bottom half******/
        TableView blockTable1 = new TableView();
        TableColumn<String, Person> blockID1 = new TableColumn<>("Block ID");
        blockID1.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn<String, Person> blockStatus1 = new TableColumn<>("Block Status");
        blockStatus.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableColumn<String, Person> blockOpenClose1 = new TableColumn<>("Block Open/Close");
        blockOpenClose.setCellValueFactory(new PropertyValueFactory<>("blockOpenClose"));
        TableColumn<String, Person> suggestedSpeed1 = new TableColumn<>("Suggested Speed (mph)");
        suggestedSpeed1.setCellValueFactory(new PropertyValueFactory<>("suggestedSpeed"));
        TableColumn<String, Person> authority1 = new TableColumn<>("Authority (ft)");
        authority.setCellValueFactory(new PropertyValueFactory<>("authority"));
        blockTable1.getColumns().add(blockID1);
        blockTable1.getColumns().add(blockStatus1);
        blockTable1.getColumns().add(blockOpenClose1);
        blockTable1.getColumns().add(suggestedSpeed1);
        blockTable1.getColumns().add(authority1);
        blockTable1.getItems().add(new Person("1", "Empty", "Open", 0, 0));
        blockTable1.getItems().add(new Person("2", "Occupied", "Closed", 25, 0));
        blockTable1.getItems().add(new Person("3", "Occupied", "Closed", 25, 300));
        blockTable1.getItems().add(new Person("4", "Empty", "Closed", 0, 0));
        blockTable1.getItems().add(new Person("5", "Occupied", "Open", 25, 1000));
        blockTable1.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-padding: 5;");
        //textArea2.setMaxWidth(TextArea.USE_PREF_SIZE);
        //textArea2.setMinWidth(TextArea.USE_PREF_SIZE);
        
        HBox topHalf = new HBox(10, box1, box2, box3);
        VBox fullScreen = new VBox(10, topHalf, blockTable1);

        fullScreen.setPadding(new Insets(10));

        primaryStage.setScene(new Scene(fullScreen, length, height));
        primaryStage.show();
    }
}