package src.track_controller;
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
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import java.io.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
 
public class WaysideUI extends Application {
    public static final CountDownLatch latch = new CountDownLatch(1);
    public static WaysideUI waysideUI = null;
    public static TrackControllerModule trackControllerModule;

    public WaysideUI(){

    }

    public static WaysideUI waitForStartUpTest(){
        try{
            latch.await();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return waysideUI;
    }

    public static void setStartUpTest(WaysideUI waysideUI0){
        waysideUI = waysideUI0;
        latch.countDown();
    }

    public void setTrackControllerModule(TrackControllerModule trackControllerModule0){
        trackControllerModule = trackControllerModule0;
    }

    public static void getPLCTextBox(int option){
        Stage popupwindow = new Stage();   
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        final TextArea textArea3 = new TextArea();
        TextArea textArea2 = new TextArea();
        if(option == 1){
            popupwindow.setTitle("PLC Input");        
            textArea2 = new TextArea("{Enter PLC Code Here} \n\n //sample code \n\n if(~block1){ \n block1 = 1; \n } \n else{ \n block1 = 0; \n } \n\n enableCrossing = block1 & block2;");
        }
        else{
            popupwindow.setTitle("Enter the file path");
            //textArea3 = new TextArea();
        }
         
        Button confirm = new Button("Confirm");           
        confirm.setOnAction(new EventHandler<ActionEvent>(){
            StringBuilder plcText = new StringBuilder("");
            String line; 
            public void handle(ActionEvent event){
                System.out.println(textArea3.getText());
                try{
                    BufferedReader in = new BufferedReader(new FileReader(textArea3.getText()));
                    while((line = in.readLine()) != null) {
                        plcText.append(line);
                    }
                }
                catch(FileNotFoundException e){ 
                    System.out.println("file not found");
                }
                catch(IOException e) {
                System.out.println("Error processing file.");
                }         
                
                popupwindow.close();
            }

        });

        Button cancel = new Button("Cancel");           
        cancel.setOnAction(e -> popupwindow.close());
        HBox buttons = new HBox(10, confirm, cancel);
        buttons.setStyle("-fx-padding: 5;"); 
        VBox layout= new VBox(10);   
        if(option == 1){      
            layout.getChildren().addAll(textArea2, buttons); 
        }
        else{
            layout.getChildren().addAll(textArea3, buttons);     
        }          
        layout.setAlignment(Pos.CENTER);           
        Scene scene1= new Scene(layout, 300, 250);            
        popupwindow.setScene(scene1);          
        popupwindow.showAndWait();
    }

    
    @Override
    public void start(Stage primaryStage) {
        
        primaryStage.setTitle("Wayside Controller UI");

        int length = 1200;
        int height = 800;

        
        //HashMap<Character, WaysideController> waysideControllers = trackControllerModule.getWaysideControllers()

        /******top half******/
        //box1
        /*
        TableView plcTable = new TableView();
        TableColumn<String, Person> plcs = new TableColumn<>("Select PLC");
        plcs.setCellValueFactory(new PropertyValueFactory<>("firstName"));

       for(Map.Entry mapElement : waysideControllers.entrySet()){
            plc.setCellValueFactory(waysideControllers.get(section)
        }
        
        
        */
        
        //plcTable.getColumns().add(plcs);
        //plcTable.getItems().add(new Person("3", "1"));
        /*plcTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
            //Check whether item is selected and set value of selected item to Label
            if(plcTable.getSelectionModel().getSelectedItem() != null) 
            {    
               TableViewSelectionModel selectionModel = plcTable.getSelectionModel();
               ObservableList selectedCells = plcTable.getSelectedCells();
               TablePosition tablePosition = (TablePosition) selectedCells.get(0);
               Object val = tablePosition.getTableColumn().getCellData(newValue);
               System.out.println("Selected Value" + val);
             }
             }
         });*/
        //plcTable.setPrefWidth(length/6);

        HBox spacer = new HBox();
        spacer.setPrefHeight(height/6);
        Button plcInput = new Button();
        plcInput.setText("PLC Input");
        plcInput.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                getPLCTextBox(1);
            }
        });
        Button plcUpload = new Button();
        plcUpload.setText("Upload PLC File");
        plcUpload.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                getPLCTextBox(2);
            }
        });
        VBox buttonGrouper = new VBox(10, spacer, plcInput, plcUpload);
        buttonGrouper.setPrefHeight(height/6); 

        /*
        HBox box1 = new HBox(10, plcTable, buttonGrouper);
        box1.setPrefWidth(length/3);
        box1.setPrefHeight(height/2);  
        box1.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-padding: 5;"); 
        */

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
        TextArea textArea3 = new TextArea("Test");
        textArea3.setPrefWidth(length*2/3);
        textArea3.setPrefHeight(height/2);
        HBox bottomHalf = new HBox(textArea3);
        bottomHalf.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-padding: 5;");
        //textArea2.setMaxWidth(TextArea.USE_PREF_SIZE);
        //textArea2.setMinWidth(TextArea.USE_PREF_SIZE);
        
        //HBox topHalf = new HBox(10, box1, box2, box3);
        //VBox fullScreen = new VBox(10, topHalf, bottomHalf);

        //fullScreen.setPadding(new Insets(10));

        //primaryStage.setScene(new Scene(fullScreen, length, height));
        //primaryStage.show();
    }
}
