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
    public static final int OFFSET = 1;
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    private List<List<String>> destinations = new ArrayList<List<String>>();
    private List<LocalTime> dispatchTimes;
    private int trainSize = 0;
    private TrainTable trainTable;

    public Schedule(TrainTable trainTable){
        this.trainTable = trainTable;
    }

    public void uploadSchedule() throws IOException{
        File scheduleFile  = new File("schedule.txt");
        BufferedReader scheduleReader = new BufferedReader(new FileReader(scheduleFile));
        scheduleReader.readLine();

		String line = scheduleReader.readLine();
        String [] data  = line.split(",");
        trainSize = data.length - 33;

        List<String> dispatchTimeStrings = Arrays.asList(Arrays.copyOfRange(data, 31, 41));
        for (int i = 0; i < dispatchTimeStrings.size(); i++){
            String realTime = "0" + dispatchTimeStrings.get(i);
            LocalTime dateTime = LocalTime.parse(realTime, formatter);
            dispatchTimes.add(dateTime);
        }
        
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
        //createSchedule()
    }

    public void createSchedule(){
        LocalTime startTime;
        LocalTime endTime;
        
        for(int trainID = 1; trainID < trainSize + 1; trainID++){
            trainTable.createTrain(trainID);
            CTCTrain train = trainTable.getTrain(trainID);
            train.setDispatchTime(dispatchTimes.get(trainID - 1));
            startTime = train.getDispatchTime();
            for (int pos = 0; pos < destinations.size(); pos++){
                List<String> destination = destinations.get(pos);

                String line = destination.get(0);
                int blockNumber = Integer.parseInt(destination.get(1));
                endTime = convertToTime(destination.get(OFFSET + trainID));
                Block blockDest = CTCModule.map.getBlock(line, blockNumber);
                //add train's path train.addTimePath(blockDest.getUUID(), starTime, endTime);
                startTime = endTime;
            }

        }

    }

    private LocalTime convertToTime(String time){
        //need to fix due to how schedule strings
        String realTime = "0" + time;
        LocalTime localTime = LocalTime.parse(realTime, formatter);
        return localTime;
    }

    public CTCTrain dispatchTrain(String trainIDString, float suggestedSpeed, UUID destination){
        int trainID = Integer.parseInt(trainIDString.split(" ")[1]);

        trainTable.createTrain(trainID);
        CTCTrain train = trainTable.getTrain(trainID);

        train.setDestination(destination);
        train.setSuggestedSpeed(suggestedSpeed);
        train.addPath(destination);

        return train;
    }

}