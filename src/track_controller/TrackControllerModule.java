package src.track_controller;

import java.util.*;
import src.Module;
import src.ctc.*;
import src.track_module.*;

public class TrackControllerModule extends Module {
	Set<CTCTrain> trains;
	//HashMap<UUID, position> switchPositions;
	//ArrayList<Block> closedBlocks;
	ArrayList<WaysideController> waysideControllers;

	public void main() {

	}

	public TrackControllerModule(){
		super();
		waysideControllers= new ArrayList<WaysideController>();
		WaysideUI.setTrackControllerModule(this);
	}

	public WaysideController createWayside(){
		WaysideController waysideController = new WaysideController();
		waysideControllers.add(waysideController);
		return waysideController;
	}

	public ArrayList<WaysideController> getWaysideControllers(){
		return waysideControllers;
	}

	@Override
	public void update(){
		trains = this.ctcModule.getTrains();
		/*switchPositions = this.ctcModule.getSwitchPositions();
		closedBlocks = this.ctcModule.getClosedBlocks();
		for(WaysideController waysideController : waysideControllers){
			Set<CTCTrain> trainsInJuris = getTrainsInJuris(waysideController.getBlocks());
			HashMap<UUID, position> switchesInJuris = getSwitchesInJuris(waysideController.getBlocks());
			ArrayList<Block> closedBlocksInJuris = getClosedBlocksInJuris(waysideController.getBlocks());
			waysideController.setTrains(trainsInJuris);
			waysideController.setSwitches(switchesInJuris);
			waysideController.setClosedBlocks(closedBlocksInJuris);
			waysideController.runPLC();
		}
		*/
	}
	
	public Set<CTCTrain> getTrainsInJuris(LinkedList<Block> blocks){
		//I know this is O(n^2) but whatever
		Set<CTCTrain> trainsInJuris = new HashSet<CTCTrain>();
		for(CTCTrain train : trains){
			for(Block block : blocks){
				/*if(train.getCurrPos() == block.getBlockNumber()){
					trainsInJuris.add(train);
				}*/
				if(train.getCurrPos() == block.getUUID()){
					trainsInJuris.add(train);
				}
			}
		}
		return trainsInJuris;
	}

	/*public HashMap<UUID, position> getSwitchesInJuris(LinkedList<Block> blocks){
		HashMap<UUID, position> switchesInJuris = new HashMap<UUID, position>();	
		for(Block block : blocks){
			if(switchesInJuris.containsKey(block.getBlockNumber)){
				switchesInJuris.put(blockNumber, switchesInJuris.get(block.getBlockNumber));
			}
		}
		return switchesInJuris;
	}

	public ArrayList<Block> getClosedBlocksInJuris(LinkedList<Block> blocks){
		ArrayList<Block> closedBlocksInJuris = new ArrayList<Block>();
		for(Block block : blocks){
			
		}
		return trainsInJuris;
	}*/







}