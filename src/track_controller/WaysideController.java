package src.track_controller;

import java.util.LinkedList;

import src.track_module.Block;
import src.train_module.Train;

public class WaysideController{

	LinkedList<Block> blocks;
	LinkedList<Train> trains;

	public WaysideController(){

	}

	public void addBlock( Block block){
		
	}
	private void getPLC(){

	}

	private void setTrains(){

	}

	/*private LinkedList<Block> getBlockJurisdiction(){
		return blocks
	}

	private void setBlockJurisdiction(LinkedList<Block> blocks){
		this.blocks = blocks
	}*/

	private void toggleSwitch(LinkedList<Block> blocks){
		//runPLC();
	}

	private void closeBlock(LinkedList<Block> blocks){
		//runPLC();
	}

	private void openGate(LinkedList<Block> blocks){
		//runPLC();
	}

	private void activateLights(LinkedList<Block> blocks){
		//runPLC();
	}

	private void setSuggestedSpeed(LinkedList<Block> blocks){
		//runPLC();
	}

	private void setAuthority(LinkedList<Block> blocks){
		//runPLC();
	}

	private LinkedList<Block> getBlockInfo(LinkedList<Block> blocks){
		return null;
	}
}