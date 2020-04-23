package src.ctc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import src.track_module.Block;

public class Schedule{
    public boolean created = false;
    public static final long HOUR_OFFSET = 8;
    public static final int OFFSET = 1;
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    private List<List<String>> destinations = new ArrayList<List<String>>();
    private int trainSize = 0;
    private TrainTable trainTable;

    public Schedule(TrainTable trainTable){
        this.trainTable = trainTable;
    }

    public void readInSchedule(){
        File scheduleFile = new File(new File(".\\jtests\\ctc\\schedule.txt").getAbsolutePath());
        try{
            BufferedReader scheduleReader = new BufferedReader(new FileReader(scheduleFile));
            String line = scheduleReader.readLine();
            String [] data  = line.split(",");
            trainSize = data.length - 33;

            while ((line = scheduleReader.readLine()) != null) {
                data = line.split(",");
                if (data.length > 26){
                    List<String> destination = new ArrayList<String>();
                    String blockLine = data[0];
                    String blockNum = data[2];
                    destination.add(blockLine);
                    destination.add(blockNum);
                    for (int i = 31; i < 41; i++){
                        destination.add(data[i]);
                    }
                    destinations.add(destination);
                }
            } 
            scheduleReader.close();
        }
        catch (IOException e){

        }
       // createSchedule();
    }

    public void createSchedule(){
        created = true;
        LocalTime startTime = null;
        LocalTime endTime;
        boolean first;
        
        for(int trainID = 1; trainID < trainSize + 1; trainID++){
            trainTable.createTrain(trainID, LocalTime.MAX);
           
            CTCTrain train = trainTable.getTrain(trainID);

            for (int pos = 0; pos < destinations.size(); pos++){
                first = false;
                if (pos == 0){
                    first = true;
                }

                List<String> destination = destinations.get(pos);
                String line = destination.get(0);
                int blockNumber = Integer.parseInt(destination.get(1));
                endTime = convertToTime(destination.get(OFFSET + trainID)).plusHours(HOUR_OFFSET);
                Block blockDest = CTCModule.map.getBlock(line, blockNumber);
                if (first){
                    train.addTimePath(blockDest.getUUID(), endTime);
                    TimePath currPath = (TimePath) train.getRoute().getCurrPath();
                    startTime = currPath.calcStartTime();
                    train.setDispatchTime(startTime);
                }
                else{
                    train.addTimePath(blockDest.getUUID(), startTime, endTime);
                }
                startTime = endTime;
            }
            System.out.println("Done");
        }
        //trainTable.reHeapify();
        System.out.println("Re heapified");

    }

    private LocalTime convertToTime(String time){
        String realTime = "0" + time;
        LocalTime dateTime = LocalTime.parse(realTime, formatter);
        return dateTime;
    }
    
    /* FOR GUI */


}