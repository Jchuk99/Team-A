package src.track_controller;

import src.track_module.Block;
import java.util.*;
import java.io.File;


public class WaysideController {

	private LinkedList<Block> blocks;
	//private LinkedList<Train> trains;
	private String id = null;
	private PLC plc;

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
		plc.runPLCLogicSwitch(blocks);
		plc.runPLCLogicCrossing(blocks);
		plc.runPLCLogicCrossingLights(blocks);
	}

}
/*private void setTrains()
private LinkedList<Block> getBlockJurisdiction(){
	return blocks
}
private void setBlockJurisdiction(LinkedList<Block> blocks){
	this.blocks = blocks
}
private void toggleSwitch(LinkedList<Block>){
	plc.runPLC()
}
private void closeBlock(LinkedList<Block>){
	plc.runPLC()
}
private void openGate(LinkedList<Block>){
	plc.runPLC()
}

private LinkedList<Block> getBlockInfo(LinkedList<Block>){
private
}*/