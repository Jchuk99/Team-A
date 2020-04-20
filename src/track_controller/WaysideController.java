package src.track_controller;

import src.track_module.Block;
import src.track_module.BlockConstructor.Shift;
import java.util.*;
import java.io.File;
import src.ctc.*;


public class WaysideController {

	private LinkedList<Block> blocks;
	private String id = null;
	private PLC plc;
	List<CTCTrain> trains;
	List<Shift> switchPositions;
	List<UUID> closedBlocks;
	UUID crossingBlock;

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
		plc.makeBits(blocks, trains, switchPositions, closedBlocks, crossingBlock);
	}

	public void setTrains(List<CTCTrain> trainsInJuris){
		this.trains = trainsInJuris;
	}

	public List<CTCTrain> getTrains(){
		return trains;
	}

	public void setSwitchPosition(List<Shift> switchesInJuris){
		this.switchPositions = switchesInJuris;
	}

	public List<Shift> getSwitchPositions(){
		return switchPositions;
	}

	public void setClosedBlocks(List<UUID> closedBlocksInJuris){
		this.closedBlocks = closedBlocksInJuris;
	}

	public List<UUID> getClosedBlocks(){
		return closedBlocks;
	}

	public void setCrossingBlock(UUID uuid){
		crossingBlock = uuid;
	}



}
