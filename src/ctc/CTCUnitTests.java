package src.ctc;

import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import src.Module;
import src.track_controller.TrackControllerModule;
import src.track_controller.WaysideController;
import src.track_module.Block;
import src.track_module.TrackModule;
import src.train_controller.TrainControllerModule;
import src.train_module.TrainModule;
import src.track_module.BlockConstructor.Normal;

public class CTCUnitTests {
        CTCModule ctcModule;
    
        @Before
        public void setup() {
            TrackModule trackModule= new TrackModule();
            TrainControllerModule trainControllerModule= new TrainControllerModule();
            TrackControllerModule trackControllerModule= new TrackControllerModule();
            TrainModule trainModule= new TrainModule();
            ctcModule = new CTCModule();
    
            HashSet<Module> modules= new HashSet<Module>();
            modules.add( trackModule);
            modules.add( trainControllerModule);
            modules.add( trackControllerModule);
            modules.add( trainModule);
            modules.add( ctcModule);
    
            for( Module module : modules) {
                module.setCTCModule(ctcModule);
                module.setTrackControllerModule(trackControllerModule);
                module.setTrainControllerModule(trainControllerModule);
                module.setTrackModule(trackModule);
                module.setTrainModule(trainModule);
            }
        }

    @Test
    public void occupiedBlockTesting() {

        String line = "Green";
        char section = 'A';
        int blockNumber = 1;
        int length = 1;
        float speedLimit = 1;
        float grade = 1;
        float elevation = 1;
        float cummElevation = 1;
        boolean underground = false;
        int xCoordinate = 1;
        int yCoordinate = 1;
        Block myBlock = new Normal(line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, xCoordinate, yCoordinate);
        myBlock.setOccupied(true);
        System.out.println("UUID 1: " + myBlock.getUUID());

        blockNumber = 2;
        Block myBlock2 = new Normal(line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, xCoordinate, yCoordinate);
        myBlock2.setOccupied(true);
        System.out.println("UUID 2: " + myBlock2.getUUID());

        WaysideController wayside = ctcModule.trackControllerModule.createWayside();
        wayside.addBlock(myBlock);
        wayside.addBlock(myBlock2);

        ctcModule.initMap();
        myBlock2.setOccupied(false);
        ctcModule.updateMap();
        List<UUID> occupiedBlocks = ctcModule.getOccupiedBlocks();
        for(UUID blockID: occupiedBlocks){
            System.out.println(blockID);
        }
        if (occupiedBlocks.contains(myBlock.getUUID()) && !occupiedBlocks.contains(myBlock2.getUUID())){

        }
        else{
            fail("Test unsucessful");
        }

    }
}