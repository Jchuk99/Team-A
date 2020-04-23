package src.track_controller;

import java.util.*;
import src.Module;
import src.ctc.*;
import src.track_module.*;
import src.track_module.BlockConstructor.Shift;
import src.track_module.BlockConstructor.Crossing;
import src.track_module.BlockConstructor.Station;
import src.ctc.CTCBlockConstructor.CTCShift;
import java.util.Iterator;

public class TrackControllerModule extends Module {
	List<CTCTrain> trains;
	List<CTCShift> switchPositions;
	List<UUID> closedBlocks;
	UUID crossingBlock = null;
	ArrayList<WaysideController> waysideControllers;

	List<CTCTrain> adjustedTrains;
	List<Shift> adjustedSwitchPositions;


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
		if(waysideControllers.size() == 46){
			addOverlaps(waysideControllers);
		}
		return waysideController;
	}
	public void addOverlaps(ArrayList<WaysideController> waysideControllers){
		boolean check = true;
		int count = 0;
		WaysideController prevWayside = new WaysideController();
		WaysideController nextWayside = new WaysideController();
		for(WaysideController waysideController : waysideControllers){
			if(check){
				prevWayside = waysideController;
				check = false;
			}
			else{							
				for(Block block : waysideController.getBlocks()){
					if(count < 4){
						prevWayside.addBlock(block);
						count++;
					}
				}
				count = 0;
				prevWayside = waysideController;
			}
		}

		ListIterator<WaysideController> listItr = waysideControllers.listIterator(waysideControllers.size());
		Iterator<Block> linkedItr; 
		int count2 = 0;

		while(listItr.hasNext()){
			if(check){
				prevWayside = listItr.previous();
				check = false;
			}
			else{
				linkedItr = listItr.previous().getBlocks().descendingIterator();
				while(linkedItr.hasNext()){
					System.out.println(linkedItr.next().getBlockNumber());
					if(count < 4){
						count++;
					}
					else{
						if(count2 < 4){
							System.out.println(linkedItr.next().getBlockNumber());
							prevWayside.addBlock(linkedItr.next());
							count2++;
						}
					}
				}
				count = 0;
				count2 = 0;
				prevWayside = listItr.previous();
			}
		
		}

		this.waysideControllers = waysideControllers;
	}

	public ArrayList<WaysideController> getWaysideControllers(){
		return waysideControllers;
	}

	@Override
	public void update(){
		trains = this.ctcModule.getTrainsOnMap();	
		switchPositions = this.ctcModule.getSwitchPositions();
		closedBlocks = this.ctcModule.getClosedBlocks();
		for(WaysideController waysideController : waysideControllers){
			LinkedList<Block> blocks = waysideController.getBlocks();
			waysideController.setTrains(getTrainsInJuris(blocks));
			waysideController.setSwitchPositions(getSwitchesInJuris(blocks));
			waysideController.setClosedBlocks(getClosedBlocksInJuris(blocks));
			waysideController.setCrossingBlock(getCrossingBlocksInJuris(blocks));
			waysideController.runPLC();
			/*
			for(Shift shift : waysideController.getAdjustedSwitchPosition()){
				adjustedSwitchPositions.add(shift);
			}
			for(CTCTrain train : waysideController.getAdjustedTrains()){
				adjustedTrains.add(train);
			}*/
		}

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

	public List<Shift> getSwitchesInJuris(LinkedList<Block> blocks){
		List<Shift> candidateSwitches = new ArrayList<Shift>();
		List<Shift> switchesInJuris = new ArrayList<Shift>();

		for(CTCShift ctcSwitch : switchPositions){
			for(Block block : blocks){
				if(ctcSwitch.getBlockNumber() == block.getBlockNumber()){
					if (block instanceof Shift){
						if(ctcSwitch.getPosition() != ((Shift)block).getPosition()){
							((Shift)block).togglePosition();
						}
						candidateSwitches.add((Shift) block);
					}
				}
			}
		}


		for(Shift shift : candidateSwitches){
			for(Block block1 : shift.getSwitchPositions()){
				for(Block block2 : blocks){
					if(block1.equals(block2)){
						if(shift.getPosition().getBlockNumber() == block2.getBlockNumber()){
							switchesInJuris.add(shift);
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

	public UUID getCrossingBlocksInJuris(LinkedList<Block> blocks){
		UUID crossingBlock = null;
		for(Block block : blocks){
			if(block instanceof Crossing){
				crossingBlock = block.getUUID();
			}	
		}	
		return crossingBlock;
	}

	public List<Block> getLightsInJuris(LinkedList<Block> blocks){
		//change to lights when there is a lights infrastructure
		List<Block> lights = new ArrayList<Block>();
		for(Block block : blocks){	
			if(block instanceof Station){
				lights.add(block);
			}
		}
		return lights;
	}

	public List<CTCTrain> getCTCTrains(){
		return adjustedTrains; 
	}

	public List<UUID> getCTCClosedBlocks(){
		return closedBlocks; 
	}

	public List<Shift> getCTCSwitchPositions(){
		return adjustedSwitchPositions; 
	}

	public UUID getCrossingBlock(){
		return crossingBlock;
	}

}