package src.track_controller;

import java.util.*;
import src.Module;
import src.ctc.*;
import src.track_module.*;
import src.track_module.BlockConstructor.Shift;

public class TrackControllerModule extends Module {
	List<CTCTrain> trains;
	List<Shift> switchPositions;
	List<UUID> closedBlocks;
	UUID crossingBlock = null;
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
		switchPositions = this.ctcModule.getSwitchPositions();
		closedBlocks = this.ctcModule.getClosedBlocks();
		/*for(WaysideController waysideController : waysideControllers){
			LinkedList<Blocks> blocks = waysideController.getBlocks();
			waysideController.setTrains(getTrainsInJuris(blocks));
			waysideController.setSwitches(getSwitchesInJuris(blocks));
			waysideController.setClosedBlocks(getClosedBlocksInJuris(blocks));
			waysideController.setCrossingBlock(getCrossingBlockInJuris(blocks));
			waysideController.runPLC();
		}
		*/
	}
	
	public List<CTCTrain> getTrainsInJuris(LinkedList<Block> blocks){
		List<CTCTrain> trainsInJuris = new ArrayList<CTCTrain>();
		for(CTCTrain train : trains){
			for(Block block : blocks){
				if(train.getCurrPos() == block.getUUID()){
					trainsInJuris.add(train);
				}
			}
		}
		return trainsInJuris;
	}

	public List<Block> getSwitchesInJuris(LinkedList<Block> blocks){
		List<Block> switchesInJuris = new ArrayList<Block>();
		for(Shift switchBlock : switchPositions){
			for(Block block1 : switchBlock.getSwitchPositions()){
				for(Block block2 : blocks){
					if(block1.getUUID() == block2.getUUID()){
						if(switchBlock.getPosition().getBlockNumber() == block2.getBlockNumber()){
							switchesInJuris.add(switchBlock);
						}
						/*else{
							switchesInJuris.put(blocks[k], 0);
						}*/
					}
				}
			}
		}
		return switchesInJuris;
	}

	public List<UUID> getClosedBlocksInJuris(LinkedList<Block> blocks){
		List<UUID> closedBlocksInJuris = new ArrayList<UUID>();
		for(UUID uuid : closedBlocksInJuris){
			for(Block block : blocks){
				if(uuid == block.getUUID()){
					closedBlocksInJuris.add(uuid);
				}
			}
		}
		return closedBlocksInJuris;
	}

	/*public UUID getCrossingBlocksInJuris(LinkedList<Block> blocks){
		
		for(Block block : blocks){
			if(block.getType().equals("crossing")){
				crossingBlock = block.getUUID();
			
		}	
		return closedBlocksInJuris;
	}*/

	public List<CTCTrain> getCTCTrains(){
		return trains; 
	}

	public List<UUID> getCTCClosedBlocks(){
		return closedBlocks; 
	}

	public List<Shift> getCTCSwitchPositions(){
		return switchPositions; 
	}

	/*public void setCTCTrains(){

	}

	public void setCTCTrains(){
		
	}

	public void setCTCTrains(){
		
	}*/







}