package src.track_module;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.UUID;

import javafx.application.Application;
import src.Module;
import src.track_controller.WaysideController;
import src.track_module.BlockConstructor.*;
import src.track_module.TrackModuleUI;
import src.ctc.Path;
import src.ctc.Route;

public class TrackModule extends Module {
    HashMap<UUID, Block> blocks;
    Yard yard;

    public TrackModule() {
        super();
        blocks= new HashMap<UUID, Block>();
        TrackModuleUI.setTrackModule(this);
        /*new Thread() {
            @Override
            public void run() {
            Application.launch(TrackModuleUI.class);
            }
            }.start();*/
    }

    public void main() {
        
    }

    public void userInterface() throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter track filepath: ");
        String filepath = scan.nextLine();
        scan.close();
        if (!errorCheck( filepath)) {
            System.out.println( "Parsing errors.");
        }
        this.buildTrack( filepath);
    }

    public void createTrain( float suggestedSpeed, float authority, Route route) {
        Path path= route.getCurrPath();
       
        /* let's talk about this one.
        UUID uuid= path.getStartBlock();
        Block block= trackModule.getBlockByUUID(uuid);
        yard.createTrain( suggestedSpeed, authority, block);*/
    }
    public Yard getYard() {return yard;};

    public Block getBlockByUUID( UUID uuid) {
        return blocks.get( uuid);
    }

    private boolean errorCheck( String filePath) throws IOException {
        //Check for any parsing errors
        return true;
    }

    public void buildTrack( String csvFile) throws IOException {
        // TODO deal with my blocks being a separate class than blocks
        HashMap<Integer, Block> myBlocks= new HashMap<Integer, Block>();
        HashSet<int[]> edges= new HashSet<int[]>();
        HashMap<Character, WaysideController> waysides= new HashMap<Character, WaysideController>();
        
        BufferedReader csvReader = new BufferedReader( new FileReader( csvFile));
        
        String row= csvReader.readLine();
        while ((row = csvReader.readLine()) != null) {
            row= row.concat( " ,");
            String[] data = row.split(",");

            String line= data[0];
            char section= data[1].charAt(0);
            int blockNumber= Integer.parseInt( data[2]);
            int length= Integer.parseInt( data[3]);
            float grade= Float.parseFloat( data[4]);
            float speedLimit= Float.parseFloat( data[5]) / 60 * 1000; // km/hr * 1hour/60minutes * 1000meters/1km
            boolean underground= Boolean.parseBoolean( data[6]);
            boolean crossing= Boolean.parseBoolean( data[7]);
            boolean station= !data[8].trim().equals("");
            float elevation= Float.parseFloat( data[9]);
            float cummElevation= Float.parseFloat( data[10]);
            int connection1= Integer.parseInt( data[11]);
            int connection2= Integer.parseInt( data[12]);
            boolean shift= !data[13].trim().equals("");
            

            // TODO this will need error checking
            String[] _directions= data[14].trim().concat( " ").split( " ");
            HashSet<Integer> directions= new HashSet<Integer>();
            for(String s : _directions) directions.add( Integer.valueOf(s));
            
            int[] edge1= {blockNumber, connection1, (directions.contains( connection1)) ? 1 : 0};
            edges.add( edge1);
            int[] edge2= {blockNumber, connection2, (directions.contains( connection2)) ? 1 : 0};
            edges.add( edge2);

            if( shift) {
                int connection3= Integer.parseInt( data[13]);
                int[] edge3= {blockNumber, connection3, (directions.contains( connection3)) ? 1 : 0};
                edges.add( edge3);
            }

            Block block;
            if( crossing) {
                block= new Crossing( line, section, blockNumber, length, speedLimit, 
                    grade, elevation, cummElevation, underground);
            }
            else if( station) {
                String name= data[8].trim();
                block= new Station( line, section, blockNumber, length, speedLimit, 
                grade, elevation, cummElevation, underground, name);
            }
            else if( shift) {
                block= new Shift( line, section, blockNumber, length, speedLimit, 
                grade, elevation, cummElevation, underground);
            }
            else {
                block= new Normal( line, section, blockNumber, length, speedLimit, 
                    grade, elevation, cummElevation, underground);
            }
            
            myBlocks.put( blockNumber, block);
            
            if( !waysides.containsKey( section)) {
                WaysideController asdf= this.trackControllerModule.createWayside();
                waysides.put( section, asdf);
            }
            WaysideController wayside= waysides.get( section);
            wayside.addBlock(block);
            
        }
        csvReader.close();

        yard= new Yard();
        myBlocks.put( 0, yard);
        
        for( int[] edge : edges) {
            Block source= myBlocks.get( edge[0]);
            Block destination= myBlocks.get( edge[1]);
            source.addEdge( destination, edge[2] != 0);
        }
    }
}