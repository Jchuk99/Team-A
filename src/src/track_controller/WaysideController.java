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
	List<Block> lightsBlocks;
	UUID crossingBlock;

	List<CTCTrain> adjustedTrains;
	List<Shift> adjustedSwitchPositions;

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

	public void setTrains(List<CTCTrain> trainsInJuris){
		this.trains = trainsInJuris;
	}

	public void setSwitchPosition(List<Shift> switchesInJuris){
		this.switchPositions = switchesInJuris;
	}

	public void setClosedBlocks(List<UUID> closedBlocksInJuris){
		this.closedBlocks = closedBlocksInJuris;
	}

	public void setCrossingBlock(UUID uuid){
		crossingBlock = uuid;
	}

	public void setLightsBlocks(List<Block> lightsInJuris){
		this.lightsBlocks = lightsInJuris;
	}

	public void runPLC(){
		//plc.makeBits(blocks, trains, switchPositions, closedBlocks, crossingBlock, lightsBlocks);
		//adjustedSwitchPositions = plc.runSwitchLogic();
		//adjustedTrains = plc.runAuthorityLogic();
		//

	}

	public List<Shift> getAdjustedSwitchPositions(){
		return adjustedSwitchPositions;
	}

	public List<CTCTrain> getAdjustedTrains(){
		return adjustedTrains;
	}



}
