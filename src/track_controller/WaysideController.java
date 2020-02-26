package src.track_controller;

import src.track_module.Block;
import src.track_controller.PLC;
import java.util.*;


public class WaysideController {

	private LinkedList<Block> blocks;
	//private LinkedList<Train> trains;
	private int id;
	private PLC plc;


	public WaysideController(){

	}

	private void uploadPLC(){
		plc = new PLC("PLC 1");

	}

	private void addBlock(Block block){
		blocks.add(block);
	}

	private void getBlocks(){
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