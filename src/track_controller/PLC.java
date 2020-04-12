package src.track_controller;

import java.util.*;
import src.track_module.Block;
import src.track_module.BlockConstructor.Shift;
import java.io.*;
import src.ctc.*;

public class PLC {
    private String id = null;
    private File file;
    private String blockBits = "";
    private boolean switchOn;
    private String authorityBits = "";
    
	public PLC(File file){
        this.file = file;
	}
	public PLC(String id){
		this.id = id;
	}
	public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public void parseAndCompile(){
        StringBuilder plcText = new StringBuilder("");
        String line;
    	try{
            BufferedReader in = new BufferedReader(new FileReader(file));
            while((line = in.readLine()) != null) {
                plcText.append(line);
            }
        }
        catch(FileNotFoundException e){ 
            System.out.println("file not found");
        }
        catch(IOException e) {
        System.out.println("Error processing file.");
        }      

    }

    public void makeBits(LinkedList<Block> blocks, List<CTCTrain> trains, List<Shift> switchPositions, List<UUID> closedBlocks){
        for(Block block : blocks){
            if(block.getOccupied()){
                blockBits = blockBits + "1";
            }
            else{
                blockBits = blockBits + "0";
            }
        }
        int blockIndex = 0;

        for(Block block : blocks){
            for(UUID uuid : closedBlocks){
                if(block.getUUID() == uuid){
                    blockBits = blockBits.substring(0, blockIndex) + "1" + blockBits.substring(blockIndex + 1);
                }
            }
            blockIndex++;
        }

        for(CTCTrain train : trains){
            authorityBits = binAndPad(Math.round(train.getAuthority()));
        }

        if(switchPositions.size() > 0){
            switchOn = true; 
        }


    }

    public void runPLCLogicSwitch(LinkedList<Block> blocks, List<CTCTrain> trains, List<Shift> switchPositions, List<UUID> closedBlocks){
       
    }

    public void runPLCLogicCrossing(LinkedList<Block> blocks, List<CTCTrain> trains, List<Shift> switchPositions, List<UUID> closedBlocks){

    }
    public void runPLCLogicLights(LinkedList<Block> blocks, List<CTCTrain> trains, List<Shift> switchPositions, List<UUID> closedBlocks){

    }
    public static String shiftLeft(String bits, int number){
		String blocksShifted = bits;
		for(int i = 0; i<number; i++){
			blocksShifted = blocksShifted + "0";
		}
		return blocksShifted.substring(number);
	}

	public static String shiftRight(String bits, int number){
		String blocksShifted = bits;
		for(int i = 0; i<number; i++){
			blocksShifted = "0" + blocksShifted;
		}
		return blocksShifted.substring(0, 14);
	}

	public static int and(String bits1, String bits2){
		int intBits1 = Integer.parseInt(bits1, 2);
		int intBits2 = Integer.parseInt(bits2, 2);

		int result = intBits1 & intBits2;
		return result;
	}


	public static int or(String bits1, String bits2){
		int intBits1 = Integer.parseInt(bits1, 2);
		int intBits2 = Integer.parseInt(bits2, 2);

		int result = intBits1 | intBits2;
		return result;
	}

	public static String binAndPad(int result){
		String padded = Integer.toBinaryString(result);
		while(padded.length() < 15){
			padded = "0" + padded;
		}
		return padded;
	}

}