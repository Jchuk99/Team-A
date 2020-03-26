package src.track_controller;

import src.track_module.Block;
import java.util.*;
import java.io.File;
import src.ctc.*;


public class WaysideController {

	private LinkedList<Block> blocks;
	private String id = null;
	private PLC plc;
	Set<CTCTrain> trains;
	//HashMap<UUID, position> switchPositions;
	ArrayList<Block> closedBlocks;

	public WaysideController(){
		this.blocks= new LinkedList<Block>();
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void uploadPLC(File file){
		plc = new PLC(file);

	}

	public void addBlock(Block block){
		blocks.add(block);
	}

	public LinkedList<Block> getBlocks(){
		return blocks;
	}

	public void runPLC(){
		plc.runPLCLogicSwitch(blocks, trains, closedBlocks);
		plc.runPLCLogicCrossing(blocks, trains, closedBlocks);
		plc.runPLCLogicLights(blocks, trains, closedBlocks);
	}

	public void setTrains(Set<CTCTrain> trainsInJuris){
		this.trains = trainsInJuris;
	}

	public Set<CTCTrain> getTrains(){
		return trains;
	}

	/*public void setSwitchPosition(){

	}
	public HashMap<UUID, position> getSwitchPositions(){
		return
	}*/

	public void setClosedBlocks(ArrayList<Block> closedBlocksInJuris){
		this.closedBlocks = closedBlocksInJuris;
	}

	public ArrayList<Block> getClosedBlocks(){
		return closedBlocks;
	}


}
