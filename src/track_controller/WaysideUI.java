package src.track_controller;

import src.track_module.Block;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.*;
import javafx.geometry.*; 
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import java.io.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import javafx.stage.FileChooser;
import javafx.scene.layout.Pane;
import javafx.collections.ObservableList;
import src.UICommon;
import javafx.scene.layout.Priority;
import src.track_module.BlockConstructor.Shift;
import src.track_module.BlockConstructor.Crossing;
import src.track_module.BlockConstructor.Station;
import src.ctc.CTCTrain;
 
public class WaysideUI extends Stage{
    public static final CountDownLatch latch = new CountDownLatch(1);
    public static TrackControllerModule trackControllerModule;
    public static ArrayList<WaysideController> waysideControllers;
    public static TableView plcTable;
    public static int length = 1200;
    public static int height = 1200;
    public static Pane graphPane;
    public static TrackControllerMap trackControllerMap = new TrackControllerMap();

    public static void setTrackControllerModule(TrackControllerModule trackControllerModule0){
        trackControllerModule = trackControllerModule0;
    }

    public WaysideUI(){
        setTitle("Wayside Controller UI");
        
        /*******************************top half***************************************/
        /*******************************box1*******************************************/
        
        
        plcTable = new TableView();
        TableColumn<String, WaysideController> plcs = new TableColumn<>("Select Wayside");
        plcs.setCellValueFactory(new PropertyValueFactory<>("id"));
        plcTable.getColumns().add(plcs);
        plcTable.setPrefWidth(length/6);

        //////////////////////BLOCK TABLE///////////////////////////
        TableView blockTable = new TableView();
        TableColumn<String, Properties> blockID = new TableColumn<>("Block ID");
        blockID.setCellValueFactory(new PropertyValueFactory<>("blockNumber"));
        TableColumn<String, Properties> blockStatus = new TableColumn<>("Block Status");
        blockStatus.setCellValueFactory(new PropertyValueFactory<>("occupied"));
        TableColumn<String, Properties> blockOpenClose= new TableColumn<>("Block Open/Close");
        blockOpenClose.setCellValueFactory(new PropertyValueFactory<>("blockOpenClose"));
        blockTable.getColumns().add(blockID);
        blockTable.getColumns().add(blockStatus);
        blockTable.getColumns().add(blockOpenClose);
        blockTable.setPrefWidth(length/3);

        //////////////////////TRAIN TABLE///////////////////////////
        TableView trainTable = new TableView();
        TableColumn<String, Properties> suggestedSpeed = new TableColumn<>("Suggested Speed (mph)");
        suggestedSpeed.setCellValueFactory(new PropertyValueFactory<>("suggestedSpeed"));
        TableColumn<String, Properties> authority = new TableColumn<>("Authority (ft)");
        authority.setCellValueFactory(new PropertyValueFactory<>("authority"));
        trainTable.getColumns().add(suggestedSpeed);
        trainTable.getColumns().add(authority);
        trainTable.setPrefWidth(length/3);
        VBox rightTables = new VBox(blockTable, trainTable);

        //////////////////////SWITCH TABLE//////////////////////////
        TableView switchTable = new TableView();
        TableColumn<String, Properties> switchID = new TableColumn<>("Switch Number");
        switchID.setCellValueFactory(new PropertyValueFactory<>("switchNumber"));
        TableColumn<String, Properties> switchPosition = new TableColumn<>("Switch Position");
        switchPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        switchTable.getColumns().add(switchID);
        switchTable.getColumns().add(switchPosition);
        switchTable.setPrefWidth(length/6);
        switchTable.setPrefHeight(height/6);

        //////////////////////SIGNAL LIGHT//////////////////////////
        Label signalLight = new Label("Signal Light:");
        Circle greenCircle = new Circle(); 
        Circle yellowCircle = new Circle(); 
        Circle redCircle = new Circle();
        greenCircle.setRadius(10);  
        redCircle.setRadius(10);  
        yellowCircle.setRadius(10);   
        greenCircle.setFill(Color.GRAY); 
        redCircle.setFill(Color.GRAY); 
        yellowCircle.setFill(Color.GRAY); 
        HBox lightGrouper = new HBox(10, signalLight, greenCircle, yellowCircle, redCircle);
        lightGrouper.setPrefHeight(height/6);
        lightGrouper.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 1;" + "-fx-padding: 5;");
        

        //////////////////////RAILWAY CROSSING STATUS////////////////
        //String text 
        Label railwayCrossing = new Label("Railway Crossing In Jurisdiction:");
        Label inJuris = new Label("False");
        HBox statusGrouper = new HBox(10, railwayCrossing, inJuris);
        statusGrouper.setPrefHeight(height/6);
        statusGrouper.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 1;" + "-fx-padding: 5;");
        Label railwayCrossingActivated = new Label("Crossing and Lights Activated:");
        Label activated = new Label("False");
        HBox statusGrouper2 = new HBox(10, railwayCrossingActivated, activated);
        statusGrouper2.setPrefHeight(height/6);
        statusGrouper2.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 1;" + "-fx-padding: 5;");
        VBox statusGrouper3 = new VBox(statusGrouper, statusGrouper2);

        VBox box2 = new VBox(10, lightGrouper, statusGrouper3, switchTable);
        box2.setPrefWidth(length/3);
        box2.setPrefHeight(height/2);
        box2.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-padding: 5;");
        


        /******************************************************************************/
 
        HashMap<UUID, Block> blocks = new HashMap<UUID, Block>();
        HBox spacer = new HBox();
        spacer.setPrefHeight(height/6);
        Button viewBlock= new Button();
        viewBlock.setText("View Blocks");
        viewBlock.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                if(!plcTable.getSelectionModel().isEmpty()){
                    redCircle.setFill(Color.GRAY);
                    yellowCircle.setFill(Color.GRAY);
                    greenCircle.setFill(Color.GRAY);
                    inJuris.setText("False");
                    activated.setText("False");
                    blockTable.getItems().clear();
                    switchTable.getItems().clear();
                    trainTable.getItems().clear();
                    String occupiedText = "Empty";
                    WaysideController waysideController = (WaysideController) plcTable.getSelectionModel().getSelectedItem();
                    for(Block block : waysideController.getBlocks()){                      
                        if(block.getOccupied()){
                            occupiedText = "Occupied";
                        }

                        if(block instanceof Shift){
                            Properties shiftFix = new Properties(1, "", "", 1, 1, ((Shift)block).getBlockNumber(), ((Shift)block).getPosition().getBlockNumber());
                            switchTable.getItems().add(shiftFix);
                        }

                        else if(block instanceof Crossing){
                            inJuris.setText("True");
                            if(((Crossing)block).getClosed()){
                                activated.setText("True");
                            }
                            else{
                                activated.setText("False");
                            }

                        }
                        else if(block instanceof Station){
                            /*if(block.getRed() == false) {
                                redCircle.setFill(Color.RED);
                                yellowCircle.setFill(Color.GRAY);
                                greenCircle.setFill(Color.GRAY);
                                this.circleMap.get(block).setFill(Color.RED);
                            }
                            else if(block.getYellow() == true) {
                                redCircle.setFill(Color.GRAY);
                                yellowCircle.setFill(Color.YELLOW);
                                greenCircle.setFill(Color.GRAY);
                                this.circleMap.get(block).setFill(Color.YELLOW);
                            }
                            else {*/
                                redCircle.setFill(Color.GRAY);
                                yellowCircle.setFill(Color.GRAY);
                                greenCircle.setFill(Color.GREEN);
                                //this.circleMap.get(block).setFill(Color.GREEN);
                            //}
                        }
                        
                        Properties blockFix = new Properties(block.getBlockNumber(), occupiedText, "Open", 50, 5, 1, 1);
                        blockTable.getItems().add(blockFix);                    
                        blocks.put(block.getUUID(), block);           
                    } 
                    if(waysideController.getTrains() != null){
                        for(CTCTrain train : waysideController.getTrains()){
                            Properties trainFix = new Properties(1, "", "", train.getSuggestedSpeed(), train.getAuthority(), 1, 1);
                            trainTable.getItems().add(trainFix);
                        }
                    }
                    
                    trackControllerMap.buildMap(blocks, graphPane); 
                    blocks.clear();
                }
            }
        });
        Button plcInput = new Button();
        plcInput.setText("PLC Input");
        /*plcInput.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                getPLCTextBox(1, newWaysideController);
            }
        });*/
        Button plcUpload = new Button();
        plcUpload.setText("Upload PLC File");
        plcUpload.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
               getPLCTextBox(2);
            }
        });
        VBox buttonGrouper = new VBox(10, spacer, viewBlock, plcInput, plcUpload);
        buttonGrouper.setPrefHeight(height/6); 

        
        HBox box1 = new HBox(10, plcTable, buttonGrouper);
        box1.setPrefWidth(length/3);
        box1.setPrefHeight(height/2);  
        box1.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-padding: 5;"); 
        /******************************************************************************/

        



        /*******************************box3*******************************************/
        /*TableView blockTable = new TableView();
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
        blockTable.getItems().add(new Person("5", "Occupied", "Open", 25, 1000));*/
        VBox box3 = new VBox(rightTables);
        box3.setPrefWidth(length/3);
        box3.setPrefHeight(height/2);
        box3.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-padding: 5;");
        //textArea.setMaxWidth(TextArea.USE_PREF_SIZE);
        //textArea.setMinWidth(TextArea.USE_PREF_SIZE);

        /******************************************************************************/

        /******bottom half******/
        Pane mapPane = createMapPane();    
        VBox mapStatus = createMapStatus(length);
        HBox bottomHalf = new HBox(10, mapStatus, mapPane);
        
        HBox topHalf = new HBox(10, box1, box2, box3);
        VBox fullScreen = new VBox(10, topHalf, bottomHalf);

        fullScreen.setPadding(new Insets(10));

        setScene(new Scene(fullScreen, length, height));
        //show();
    }


    public static void getPLCTextBox(int option){
        Stage popupwindow = new Stage();   
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        final TextArea textArea3 = new TextArea();
        TextArea textArea2 = new TextArea();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        File file = null;
        if(option == 1){
            Button confirm = new Button("Confirm");           
            confirm.setOnAction(new EventHandler<ActionEvent>(){
                StringBuilder plcText = new StringBuilder("");
                String line; 
                public void handle(ActionEvent event){
                    
                    /*try{
                    BufferedReader in = new BufferedReader(new FileReader(textArea3.getText()));
                        while((line = in.readLine()) != null) {
                            plcText.append(line);
                        }*/
                
                    /*catch(FileNotFoundException e){ 
                        System.out.println("file not found");
                    }
                    catch(IOException e) {
                    System.out.println("Error processing file.");
                    }*/       
                    
                    //popupwindow.close();
                }
    
            });
    
            popupwindow.setTitle("PLC Input");        
            textArea2 = new TextArea("{Enter PLC Code Here} \n\n //sample code \n\n if(~block1){ \n block1 = 1; \n } \n else{ \n block1 = 0; \n } \n\n enableCrossing = block1 & block2;");
            Button cancel = new Button("Cancel");           
            cancel.setOnAction(e -> popupwindow.close());
            HBox buttons = new HBox(10, confirm, cancel);
            buttons.setStyle("-fx-padding: 5;"); 
            VBox layout= new VBox(10); 
            layout.getChildren().addAll(textArea2, buttons); 
            layout.setAlignment(Pos.CENTER);           
            Scene scene1= new Scene(layout, 300, 250);            
            popupwindow.setScene(scene1);          
            popupwindow.showAndWait();  
        }

        else{       
            file = fileChooser.showOpenDialog(null);
            for(WaysideController waysideController : waysideControllers){
                waysideController.uploadPLC(file);
            }
        }
         



    }

    public static void setWaysideControllerTable(){
        WaysideController waysideController = new WaysideController();
        waysideControllers = trackControllerModule.getWaysideControllers();
        int plcCount = 0;
        for(WaysideController controller : waysideControllers){
            controller.setId("" + ++plcCount);
            plcTable.getItems().add(controller);
        }

    }

    private static Pane createMapPane(){
        graphPane = new Pane();
        VBox.setVgrow(graphPane, Priority.ALWAYS);
        return graphPane;
    }

    public static VBox createMapStatus(int length){        
        Label mapTitle = UICommon.createLabel("Map Key");
        mapTitle.setStyle("-fx-font-weight: bold;");
        mapTitle.setAlignment(Pos.CENTER);
        HBox titleBox = new HBox(10,  UICommon.createHSpacer(), mapTitle, UICommon.createHSpacer());

        Circle circleGreen = UICommon.createCircle(10, Color.GREEN);
        Label emptyLabel = UICommon.createLabel("Empty Block");
        HBox emptyBox = new HBox(10, circleGreen, emptyLabel);

        Circle circleBlue = UICommon.createCircle(10, Color.BLUE);
        Label occupiedLabel = UICommon.createLabel("Occupied Block");
        HBox occupiedBox = new HBox(10, circleBlue, occupiedLabel);

        Circle circleRed = UICommon.createCircle(10, Color.RED);
        Label brokenLabel = UICommon.createLabel("Broken Block");
        HBox brokenBox = new HBox(10, circleRed, brokenLabel);

        Circle circleGray = UICommon.createCircle(10, Color.GRAY);
        Label closedLabel = UICommon.createLabel("Closed Block");
        HBox closedBox = new HBox(10, circleGray, closedLabel);
        
        VBox statusBox = new VBox(10,titleBox , emptyBox, occupiedBox, brokenBox, closedBox);
        statusBox.setPrefWidth(200);
        return statusBox;   
    }

    
}
