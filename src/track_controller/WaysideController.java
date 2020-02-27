package src.track_controller;

import src.track_module.Block;
import src.track_controller.PLC;
import java.util.*;


public class WaysideController {

	private LinkedList<Block> blocks;
	//private LinkedList<Train> trains;
	private String id;
	private PLC plc;


	public WaysideController(String id){
		this.blocks= new LinkedList<Block>();
	}

	public void setID(String id){
		this.id = id;
	}

	public String getID(){
		return id;
	}

	public void uploadPLC(){
		plc = new PLC("PLC 1");

	}

	public void addBlock(Block block){
		blocks.add(block);
	}

	public LinkedList<Block> getBlocks(){
		return blocks;
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
private void activateLights(LinkedList<Block>){
	plc.runPLC()
}
private void setSuggestedSpeed(LinkedList<Block>){
	plc.runPLC()
}
private void setAuthority(LinkedList<Block>){
	plc.runPLC()
}
private LinkedList<Block> getBlockInfo(LinkedList<Block>){
private
}*/