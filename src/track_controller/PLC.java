package src.track_controller;

import java.util.*;
import src.track_module.Block;
import java.io.*;

public class PLC {
    private String id = null;
    private File file;
	public PLC(File file){
        this.file = file;
	}
	public PLC(String id){
		this.id = id;
	}
	public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public void parseAndCompile(){
        StringBuilder plcText = new StringBuilder("");
        String line;
    	try{
            BufferedReader in = new BufferedReader(new FileReader(file));
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

    }

    public void runPLCLogicSwitch(LinkedList<Block> blocks){
        parseAndCompile();
        //toggle switches
    }

    public void runPLCLogicCrossing(LinkedList<Block> blocks){
        parseAndCompile();
    }
    public void runPLCLogicCrossingLights(LinkedList<Block> blocks){
        parseAndCompile();
    }

}