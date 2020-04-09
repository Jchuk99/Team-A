package src.ctc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.Pair;
import src.UICommon;
import src.track_module.Block;
import src.track_module.BlockConstructor.Station;

import java.text.DecimalFormat;
import java.util.HashMap;

//TODO: IMPORT A STYLE GUIDE!!!

public class CTCUI extends Stage {
    static ObservableList<Person> trainData = FXCollections.observableArrayList();
    public static CTCModule ctcOffice;
    static int trainID = 0;

    public static void setCTCModule(CTCModule ctcOffice0){
        ctcOffice = ctcOffice0;
    }

    public CTCUI() {
        setTitle("CTC UI");

        int length = 900;
        int height = 800;

        /****** top half ******/
        HBox timeBox = createTimeBox();

        Text ticketText = new Text("Ticket Sales");
        //TODO: replace ticketLabel with actual value
        Label ticketLabel = new Label("205/h");
        ticketLabel.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-padding: 5;");

        HBox ticketBox = new HBox(10, ticketText, ticketLabel);
        ticketBox.setAlignment(Pos.CENTER);

        Text totalTicketText = new Text("Total Ticket Sales");
        //TODO: replace ticketLabel with actual value
        Label totalTicketLabel = new Label("1000");
        totalTicketLabel.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-padding: 5;");

        HBox totalTicketBox = new HBox(10, totalTicketText, totalTicketLabel);
        totalTicketBox.setAlignment(Pos.CENTER);

        Button manualMode = UICommon.createButton("Manual Input/Schedule", 300, 50);

         //TODO: style
        manualMode.setStyle("-fx-border-color: black;" + "-fx-border-width: 2;" + 
                               "-fx-font-size:20;" + "-fx-text-fill: black;");

        manualMode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getManualDisplay();
            }
        });

        HBox topHalf1 = new HBox(10, ticketBox, createSpacer(), totalTicketBox, createSpacer(), manualMode,
                                    createSpacer(), timeBox);
        TableView<CTCTrain> trainTable = createTrainTable();

        VBox topHalf = new VBox(10, topHalf1, trainTable);
        /****** bottom half ******/

        Pane mapPane = createMapPane();
        
        topHalf.setPrefHeight(height/2);
        VBox fullScreen = new VBox(10, topHalf, mapPane);

        /****full screen *****/
        fullScreen.setPadding(new Insets(10));
        setScene(new Scene(fullScreen, length, height));

    }

    public static void getManualDisplay(){
        
        Stage popupwindow = new Stage();   
        popupwindow.setTitle("CTC UI");  

        int length = 900;
        int height = 800; 
  
        /******top half******/

        //trainBox
        TableView<CTCTrain> statusTable = createStatusTable(length);
        Pair<VBox, TableView<Person>> p = createTrainBox(length, height, statusTable);
        VBox trainBox = p.getKey();
        TableView<Person> trainTable = p.getValue();
        trainBox.setPrefWidth(length/3);
        trainBox.setPrefHeight(height/2);  
        trainBox.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-padding: 5;"); 

        //destBox
        Pair<TableView<Station>, TableView<Block>> bTable = createBlockTables();
        TableView<Station> stationTable = bTable.getKey();
        TableView<Block> blocksTable = bTable.getValue();

        HBox destBox = new HBox(10, stationTable, blocksTable);
        destBox.setPrefWidth(length/3);
        destBox.setPrefHeight(height/2);
        destBox.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-padding: 5;");

        HBox topHalf1 = new HBox(10, trainBox, destBox);

        VBox box1 = new VBox(10, topHalf1, statusTable);
        box1.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-padding: 5;");

        Label speed = new Label(" "); 
        speed.setStyle("-fx-border-color: black;" + "-fx-border-width: 2;" + 
                           "-fx-font-size:12;" + "-fx-text-fill: black;" + "-fx-padding: 5;");
        Slider speedSlider = createSpeedSlider(speed);
    
        VBox sliderBox = new VBox(10, speedSlider, speed);
        sliderBox.setAlignment(Pos.CENTER);

        HBox timeBox = createTimeBox();
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

    private static Pane createMapPane(){
        GUIMap trackMap = new GUIMap();
        Pane graphPane = new Pane();

        //TODO: ask eric about setting this stuff\
        //graphPane.setStyle("-fx-background-color: -fx-focus-color;");
        VBox.setVgrow(graphPane, Priority.ALWAYS);
        graphPane.setViewOrder(1);
        trackMap.buildMap(CTCModule.map.getBlockMap(), graphPane);
        return graphPane;
    }
    
    private static TableView<CTCTrain> createTrainTable(){

        TableView<CTCTrain> trainTable = new TableView<CTCTrain>();
        trainTable.setPlaceholder(new Label("No trains available"));
        trainTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<CTCTrain, String> trains = new TableColumn<>("Train");
        trains.setCellValueFactory(cellData -> cellData.getValue().getTrainIDProperty());

        TableColumn<CTCTrain, String>  currPos = new TableColumn<>("Current Position");
        currPos.setCellValueFactory(cellData -> cellData.getValue().getCurrPosProperty());

        TableColumn<CTCTrain, String>  destination= new TableColumn<>("Current Destination");
        destination.setCellValueFactory(cellData -> cellData.getValue().getDestProperty());

        TableColumn<CTCTrain, String> speed = new TableColumn<>("Suggested Speed(mph)");
        speed.setCellValueFactory(cellData -> cellData.getValue().getSuggestedSpeedProperty());


        trainTable.getColumns().add(trains);
        trainTable.getColumns().add(currPos);
        trainTable.getColumns().add(destination);
        trainTable.getColumns().add(speed);

        ObservableList<CTCTrain> trainData = ctcOffice.getObservableTrains();
        trainTable.setItems(trainData);

        return trainTable;
    }

    private static TableView<CTCTrain> createStatusTable(int length){

        //TODO: This is going to be listening to trains. The trainBox will need this.
        //whichever train is currently clicked @ the moment.
        // if train does not exist on map, show blank, else show all it's information in specified columns
        TableView<CTCTrain> statusTable = new TableView<CTCTrain>();
        statusTable.setPlaceholder(new Label("No information available"));
        //statusTable.setPlaceholder(new Label("No trains selected"));
        statusTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<CTCTrain, String> trainIDStatus = new TableColumn<CTCTrain, String>("Train");
        trainIDStatus.setCellValueFactory(cellData -> cellData.getValue().getTrainIDProperty());

        TableColumn<CTCTrain, String> posStatus = new TableColumn<CTCTrain, String>("Current Position");
        posStatus.setCellValueFactory(cellData -> cellData.getValue().getCurrPosProperty());

        TableColumn<CTCTrain, String> destStatus = new TableColumn<CTCTrain, String>("Destination");
        destStatus.setCellValueFactory(cellData -> cellData.getValue().getDestProperty());

        TableColumn<CTCTrain, String> speedStatus = new TableColumn<CTCTrain, String>("Suggested Speed(mph)");
        speedStatus.setCellValueFactory(cellData -> cellData.getValue().getSuggestedSpeedProperty());
        
        statusTable.getColumns().add(trainIDStatus);
        statusTable.getColumns().add(posStatus);
        statusTable.getColumns().add(destStatus);
        statusTable.getColumns().add(speedStatus);

        statusTable.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-padding: 5;");
        statusTable.setPrefWidth(length/6);

        return statusTable;
    }
    
    private static Pair<VBox, TableView<Person>> createTrainBox(int length, int height, TableView<CTCTrain> statusTable){
        statusTable.setEditable(true);
        TableView<Person> trainTable = new TableView<Person>();
        trainTable.setPlaceholder(new Label("No trains available"));
        trainTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Person, String> trains = new TableColumn<>("Select Train");
        trains.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        trainTable.getColumns().add(trains);

        trainTable.setItems(trainData);
        trainTable.setPrefWidth(length/6);


        
        trainTable.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) -> {
            ObservableList<CTCTrain> trainStatus = FXCollections.observableArrayList();
            statusTable.setItems(trainStatus);
            Person train = trainTable.getSelectionModel().getSelectedItem();
            int id = Integer.parseInt(train.getFirstName().split(" ")[1]);
            HashMap<Integer, CTCTrain> trainMap = ctcOffice.getTrainMap();
            if (trainMap.containsKey(id)){
                trainStatus.add(trainMap.get(id));
            }
        
        });
    
        
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

    private static Pair<TableView<Station>, TableView<Block>> createBlockTables(){
        TableView<Station> stationTable = new TableView<Station>();
        stationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Station, String> stations = new TableColumn<>("Select Destination");
        stations.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        stationTable.getColumns().add(stations);
         
        stationTable.setItems(ctcOffice.getObservableStationList());

        TableView<Block> blockTable = new TableView<Block>();
        blockTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Block, String> blocks = new TableColumn<>("Select Block");
        blocks.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getBlockNumber())));
        blockTable.getColumns().add(blocks);

        blockTable.setItems(ctcOffice.getObservableBlockList());
       
       
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

        return new Pair<TableView<Station>, TableView<Block>>(stationTable, blockTable);
    }

    private static Button createDispatchButton(TableView<Person> trainTable, TableView<Block> blocksTable,TableView<Station> stationTable  ,Slider speedSlider){
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

        dispatch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Block block;
                Person train;
                float speed;

                if (!blocksTable.getSelectionModel().isEmpty()){
                    block = blocksTable.getSelectionModel().getSelectedItem();
                }
                else{
                    block = stationTable.getSelectionModel().getSelectedItem(); 
                }

                train = trainTable.getSelectionModel().getSelectedItem();
                speed = (float) speedSlider.getValue();
                ctcOffice.dispatch(train.getFirstName(), speed, block.getUUID());
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

    private static HBox createTimeBox(){

        Text timeText = new Text("Time");
        Label timeLabel = new Label("");
        timeLabel.textProperty().bind(ctcOffice.timeString);
        //TODO: add style guide for my buttons and my labels
        timeLabel.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-padding: 5;");

        HBox timeBox = new HBox(10, timeText, timeLabel);
        timeBox.setAlignment(Pos.CENTER);
        return timeBox;
    }

    private static Node createSpacer() {
        final Region spacer = new Region();
        // Make it always grow or shrink according to the available space
        VBox.setVgrow(spacer, Priority.ALWAYS);
        return spacer;
    }
}