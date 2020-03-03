package src.ctc;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.text.DecimalFormat;

 
public class CTCUI extends Stage {
    public static CTCModule ctcOffice;
    static int trainID = 0;

    public CTCUI(){
        setTitle("CTC UI");

        int length = 900;
        int height = 800;

        /******top half******/

        Text timeText = new Text("Time");
        Label timeLabel = new Label("11:00:23 am");
        timeLabel.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-padding: 5;");
 
        HBox timeBox = new HBox(10, timeText, timeLabel);
        timeBox.setAlignment(Pos.CENTER);

        Text ticketText = new Text("Ticket Sales");
        Label ticketLabel = new Label("205/h");
        ticketLabel.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-padding: 5;");
 
        HBox ticketBox = new HBox(10, ticketText , ticketLabel);
        timeBox.setAlignment(Pos.CENTER);

        Text totalTicketText = new Text("Total Ticket Sales");
        Label totalTicketLabel = new Label("1000");
        totalTicketLabel.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-padding: 5;");
 
        HBox totalTicketBox = new HBox(10, totalTicketText, totalTicketLabel);
        timeBox.setAlignment(Pos.CENTER);
 
        Button manualMode = new Button();
        manualMode.setText("Manual Input/Schedule");
        manualMode.setPrefWidth(300);
        manualMode.setPrefHeight(50);
        manualMode.setStyle("-fx-border-color: black;" + "-fx-border-width: 2;" + 
                               "-fx-font-size:20;" + "-fx-text-fill: black;");

        manualMode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getManualDisplay();
            }
         });
                            

        HBox topLine = new HBox(10, ticketBox, createSpacer(), totalTicketBox, createSpacer(), manualMode, createSpacer(), timeBox);
        TableView<Person> trainTable = createTrainTable();

        VBox topHalf = new VBox(10, topLine, trainTable);
        topHalf.setPrefHeight(height/2);

        /******bottom half******/
        TableView<Person> mapTable = new TableView<Person>();;
        mapTable.setPrefHeight(height/2);

        
        /****full scree *****/
        VBox fullScreen = new VBox(10, topHalf, mapTable);
        fullScreen.setPadding(new Insets(10));
        setScene(new Scene(fullScreen, length, height));
 

    }
    
    public static void setCTCModule(CTCModule ctcOffice0){
        ctcOffice = ctcOffice0;
    }

    public static void getManualDisplay(){
        
        Stage popupwindow = new Stage();   
        popupwindow.setTitle("CTC UI");  

        int length = 900;
        int height = 800; 
  
        /******top half******/

        //trainBox
        Pair<VBox, TableView<Person>> p = createTrainBox(length, height);
        VBox trainBox = p.getKey();
        TableView<Person> trainTable = p.getValue();
        trainBox.setPrefWidth(length/3);
        trainBox.setPrefHeight(height/2);  
        trainBox.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-padding: 5;"); 

        //destBox
        Pair<TableView<Person>, TableView<Person>> bTable = createBlockTables();
        TableView<Person> stationTable = bTable.getKey();
        TableView<Person> blocksTable = bTable.getValue();

        HBox destBox = new HBox(10, stationTable, blocksTable);
        destBox.setPrefWidth(length/3);
        destBox.setPrefHeight(height/2);
        destBox.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-padding: 5;");

        HBox topHalf1 = new HBox(10, trainBox, destBox);

        TableView<Person> statusTable = createStatusTable(length);

        VBox box1 = new VBox(10, topHalf1, statusTable);
        box1.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-padding: 5;");

        Label speed = new Label(" "); 
        speed.setStyle("-fx-border-color: black;" + "-fx-border-width: 2;" + 
                           "-fx-font-size:12;" + "-fx-text-fill: black;" + "-fx-padding: 5;");
        Slider speedSlider = createSpeedSlider(speed);
        

        VBox sliderBox = new VBox(10, speedSlider, speed);
        sliderBox.setAlignment(Pos.CENTER);

        Text timeText = new Text("Time");
        //TODO: set time to track global time
        Label timeLabel = new Label("11:00:23 am");
        timeLabel.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-padding: 5;");

        HBox timeBox = new HBox(10, timeText, timeLabel);
        timeBox.setAlignment(Pos.CENTER);
            
        Button dispatch = createDispatchButton(trainTable, blocksTable, stationTable, speedSlider);

        VBox box3 = new VBox(10, timeBox, createSpacer(), sliderBox, createSpacer(),  dispatch);
        box3.setPrefWidth(length/3);
        box3.setPrefHeight(height/2);
        box3.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-padding: 5;");
        box3.setAlignment(Pos.CENTER);

        HBox topHalf = new HBox(10, box1, box3);
        topHalf.setAlignment(Pos.CENTER);
        topHalf.setPrefHeight(height/2);

        /******bottom half******/
        
        Button scheduleButton = createScheduleButton();
        TableView<SchedulerUI> scheduleTable = createScheduleTable();

        VBox bottomHalf = new VBox(10, scheduleButton, scheduleTable);
        bottomHalf.setAlignment(Pos.CENTER);
        bottomHalf.setPrefHeight(height/2);

        /***** FULL SCREEN ****/
        VBox fullScreen = new VBox(10, topHalf, bottomHalf);
        fullScreen.setPadding(new Insets(10));
        popupwindow.setScene(new Scene(fullScreen, length, height));
        popupwindow.show();
    }
    
    private static TableView<Person> createTrainTable(){

        TableView<Person> trainTable = new TableView<Person>();
        trainTable.setPlaceholder(new Label("No trains available"));
        trainTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Person, String> trains = new TableColumn<>("Train");
        trains.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Person, String>  currPos = new TableColumn<>("Current Position");
        currPos.setCellValueFactory(new PropertyValueFactory<>("currPos"));

        TableColumn<Person, String>  destination= new TableColumn<>("Current Destination");
        destination.setCellValueFactory(new PropertyValueFactory<>("destination"));

        TableColumn<Person, String> speed = new TableColumn<>("Suggested Speed(mph)");
        speed.setCellValueFactory(new PropertyValueFactory<>("destination"));

        trainTable.getColumns().add(trains);
        trainTable.getColumns().add(currPos);
        trainTable.getColumns().add(destination);
        trainTable.getColumns().add(speed);

        ObservableList<Person> trainData = FXCollections.observableArrayList(
            new Person("Train 1","EDGEBROOK","SOUTH BANK", "25"),
            new Person("Train 2","Block 12", "BLOCK 4","25"),
            new Person("Train 3","Block 15", "BLOCK 5","25"),
            new Person("Train 4","Block 39", "Block 6","25"),
            new Person("Train 5","Block 44", "Block 7","25")
        );
        
        trainTable.setItems(trainData);

        return trainTable;
    }

    private static TableView<Person> createStatusTable(int length){

        //TODO: This is going to need to access traindate corresponding to
        //whichever train is currently clicked @ the moment.
        // if train does not exist on map, show blank, else show all it's information in specified columns
        TableView<Person> statusTable = new TableView<Person>();
        //statusTable.setPlaceholder(new Label("No trains selected"));
        statusTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Person, String> trainIDStatus = new TableColumn<>("Train");
        trainIDStatus.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Person, String> posStatus = new TableColumn<>("Current Position");
        posStatus.setCellValueFactory(new PropertyValueFactory<>("currPos"));

        TableColumn<Person, String> destStatus = new TableColumn<>("Destination");
        destStatus.setCellValueFactory(new PropertyValueFactory<>("destination"));

        TableColumn<Person, String> speedStatus = new TableColumn<>("Suggested Speed(mph)");
        speedStatus.setCellValueFactory(new PropertyValueFactory<>("suggestedSpeed"));

        statusTable.getColumns().add(trainIDStatus);
        statusTable.getColumns().add(posStatus);
        statusTable.getColumns().add(destStatus);
        statusTable.getColumns().add(speedStatus);
        statusTable.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-padding: 5;");
        statusTable.setPrefWidth(length/6);
        Person currentTrain = (new Person("Train 1", "7", "EDGEBROOK", "25"));
        statusTable.getItems().add(currentTrain);

        return statusTable;
    }
    
    private static Pair<VBox, TableView<Person>> createTrainBox(int length, int height){
        
        TableView<Person> trainTable = new TableView<Person>();
        trainTable.setPlaceholder(new Label("No trains available"));
        trainTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Person, String> trains = new TableColumn<>("Select Train");
        trains.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        trainTable.getColumns().add(trains);

        ObservableList<Person> trainData = FXCollections.observableArrayList();
        trainTable.setItems(trainData);
        trainTable.setPrefWidth(length/6);
        
        Button trainInput = new Button();
        trainInput.setText("Create new train");

        trainInput.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                trainData.add(new Person("Train " + ++trainID));
            }
        });
        
        HBox buttonGrouper = new HBox(10, trainInput);
        buttonGrouper.setAlignment(Pos.CENTER);
        buttonGrouper.setPrefHeight(height/6); 

        VBox trainBox = new VBox(10, trainTable, buttonGrouper);
        return new Pair<VBox, TableView<Person>>(trainBox, trainTable);

    }

    private static TableView<SchedulerUI> createScheduleTable(){

        TableView<SchedulerUI> scheduleTable = new TableView<SchedulerUI>();
        scheduleTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn<SchedulerUI, String> blockID = new TableColumn<>("Block ID");
        blockID.setCellValueFactory(new PropertyValueFactory<>("blockID"));

        TableColumn<SchedulerUI, String>  station = new TableColumn<>("Station");
        station.setCellValueFactory(new PropertyValueFactory<>("station"));

        TableColumn<SchedulerUI, String>  trainOne = new TableColumn<>("Train 1 Arrival Time");
        trainOne.setCellValueFactory(new PropertyValueFactory<>("trainOne"));

        TableColumn<SchedulerUI, String> trainTwo = new TableColumn<>("Train 2 Arrival Time");
        trainTwo.setCellValueFactory(new PropertyValueFactory<>("trainTwo"));

        TableColumn<SchedulerUI, String>  trainThree = new TableColumn<>("Train 3 Arrival Time");
        trainThree.setCellValueFactory(new PropertyValueFactory<>("trainThree"));

        scheduleTable.getColumns().add(blockID);
        scheduleTable.getColumns().add(station);
        scheduleTable.getColumns().add(trainOne);
        scheduleTable.getColumns().add(trainTwo);
        scheduleTable.getColumns().add(trainThree);

        scheduleTable.getItems().add(new SchedulerUI("5", "WHITED", "7:30am", "8:00am", "8:30am"));
        scheduleTable.getItems().add(new SchedulerUI("9", "SOUTH BANK", "8:00am", "8:30am", "9:00am"));
        scheduleTable.getItems().add(new SchedulerUI("11", "CENTRAL", "8:30am", "9:00am", "9:30am"));
        scheduleTable.getItems().add(new SchedulerUI("15", "EDGEBROOK", "9:00am", "9:30am", "10:00am"));
        
        scheduleTable.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-padding: 5;");

        return scheduleTable;
    }

    private static Pair<TableView<Person>, TableView<Person>> createBlockTables(){
        TableView<Person> stationTable = new TableView<Person>();
        stationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Person, String> stations = new TableColumn<>("Select Destination");
        stations.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        stationTable.getColumns().add(stations);
         
        //TODO: Make it so that it gets stations from CTC
        ObservableList<Person> stationData = FXCollections.observableArrayList(
            new Person("PIONEER"),
            new Person("EDGEBROOK")
        );
        stationTable.setItems(stationData);

        TableView<Person> blockTable = new TableView<Person>();
        blockTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Person, String> blocks = new TableColumn<>("Select Block");
        blocks.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        blockTable.getColumns().add(blocks);
         
        //TODO: Make it so that it gets map blocks from the CTC module
        ObservableList<Person> blockData = FXCollections.observableArrayList(
            new Person("1"),
            new Person("2"),
            new Person("3"),
            new Person("4"),
            new Person("5"),
            new Person("6"),
            new Person("7"),
            new Person("8"),
            new Person("9"),
            new Person("10"),
            new Person("11"),
            new Person("12")
        );
        blockTable.setItems(blockData);
       
       
        stationTable.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null) {
                blockTable.getSelectionModel().clearSelection();
            }
        });
    
        blockTable.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null) {
                stationTable.getSelectionModel().clearSelection();
            }
        });

        return new Pair<TableView<Person>, TableView<Person>>(stationTable, blockTable);
    }

    private static Button createDispatchButton(TableView<Person> trainTable, TableView<Person> blocksTable,TableView<Person> stationTable  ,Slider speedSlider){
        Button dispatch = new Button();
        dispatch.setText("DISPATCH");
        dispatch.setPrefWidth(400);
        dispatch.setPrefHeight(100);
        //dispatch.setMaxSize(800, 800);
        dispatch.setStyle("-fx-border-color: black;" + "-fx-border-width: 2;" + 
                                "-fx-background-color: green;" + "-fx-font-size:30;" + "-fx-text-fill: white;");

        DropShadow shadow = new DropShadow();
        //Adding the shadow when the mouse cursor is on
        dispatch.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                dispatch.setEffect(shadow);
            }
        });
        //Removing the shadow when the mouse cursor is off
        dispatch.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                dispatch.setEffect(null);
            }
        });
        //TODO: define a dispatch listener
        dispatch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Person block;
                Person train;
                float speed;

                if (!blocksTable.getSelectionModel().isEmpty()){
                    block = blocksTable.getSelectionModel().getSelectedItem();
                }
                else{
                    // this is a station, so I'm going to need to transform station name into a block number... static hash table for now?
                    block = stationTable.getSelectionModel().getSelectedItem(); 
                    if (block.getFirstName().equalsIgnoreCase("Pioneer"))
                    {
                        block.setFirstName("2");
                    }
                    else
                    {
                        block.setFirstName("9");
                    }
                }

                train = trainTable.getSelectionModel().getSelectedItem();
                speed = (float) speedSlider.getValue();
                ctcOffice.dispatch(train.getFirstName(), speed, block.getFirstName());
            }
            });                                                    

            return dispatch;
    }

    private static Slider createSpeedSlider(Label speed){
        Slider speedSlider = new Slider();
        speedSlider.setMin(0); 
        speedSlider.setMax(40); 
        speedSlider.setValue(20); 

        speedSlider.valueProperty().addListener( new ChangeListener<Number>() { 
 
           public void changed(ObservableValue <? extends Number > observable, Number oldValue, Number newValue) { 
                DecimalFormat df = new DecimalFormat("##.##");
                speed.setText("Miles per Hour: " + df.format(newValue)); 
           } 
       }); 
        return speedSlider;
    }

    private static Button createScheduleButton(){

        Button scheduleButton = new Button();
        scheduleButton.setText("Select Schedule File");
        scheduleButton.setPrefWidth(400);
        scheduleButton.setPrefHeight(100);
        //scheduleButton.setMaxSize(800, 800);
        scheduleButton.setStyle("-fx-border-color: black;" + "-fx-border-width: 2;" + 
                            "-fx-font-size:20;" + "-fx-text-fill: black;");

        //Adding the shadow when the mouse cursor is on
        DropShadow shadow = new DropShadow();
        scheduleButton.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                scheduleButton.setEffect(shadow);
            }
        });
        //Removing the shadow when the mouse cursor is off
        scheduleButton.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent e) {
                scheduleButton.setEffect(null);
            }
        });

        return scheduleButton;
    }

    private static Node createSpacer() {
        final Region spacer = new Region();
        // Make it always grow or shrink according to the available space
        VBox.setVgrow(spacer, Priority.ALWAYS);
        return spacer;
    }
}