package src.track_controller;

import java.util.*;
import src.track_module.Block;
import src.track_module.BlockConstructor.Shift;
import src.track_module.BlockConstructor.Crossing;
import src.ctc.CTCBlockConstructor.CTCShift;
import java.io.*;
import src.ctc.*;

public class PLC {
    private String id = null;
    private File file;

    //boolean variables and bitstrings
    private String blockBits = "";
    private String authorityBits = "";
    private String crossingBits = "";
    private boolean switchOn = false;
    private boolean switchOccupied = false;
    private boolean switchBroken = false;
    private boolean crossingOn = false;
    private boolean crossingOccupied = false;
    private boolean crossingBroken = false;
    private boolean lightsOn = false;
    private static boolean oneBlockAwayOccupied = false;
    private static boolean twoBlocksAwayOccupied = false; 
    
    //plc parsing
    private String argumentsTemp[];
    private String authorityExpression;
    private String switchExpression;
    private String crossingExpression;
    private String lightsExpressionRed;
    private String lightsExpressionYellow;
    private String lightsExpressionGreen;

    
    private List<String> authorityBitsMultiple = new ArrayList<String>();
    private int numBlocks = 0;

    //adjustments
	private List<CTCTrain> adjustedTrains = new ArrayList<CTCTrain>();

    //acted on by plc
    private List<Shift> adjustedSwitchPositions;
    
    //sent from wayside
    private LinkedList<Block> blocks;
    private List<CTCTrain> trains;
    private List<Shift> switchPositions;
    private List<UUID> closedBlocks;
    private UUID crossingBlock;
    private List<Block> lightsBlocks;
    
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
        String line;
    	try{
            BufferedReader in = new BufferedReader(new FileReader(file));
            while((line = in.readLine()) != null) {
                argumentsTemp = line.toString().split(":");
                if(argumentsTemp[0].equals("STEP-AU")){
                    authorityExpression = argumentsTemp[1];
                }
                else if(argumentsTemp[0].equals("STEP-SW")){
                    switchExpression = argumentsTemp[1];
                }
                else if(argumentsTemp[0].equals("STEP-CR")){
                    crossingExpression = argumentsTemp[1];
                }
                else if(argumentsTemp[0].equals("STEP-LR")){
                    lightsExpressionRed = argumentsTemp[1];
                }
                else if(argumentsTemp[0].equals("STEP-LR")){
                    lightsExpressionYellow = argumentsTemp[1];
                }
                else if(argumentsTemp[0].equals("STEP-LR")){
                    lightsExpressionGreen = argumentsTemp[1];
                }
            }
        }
        catch(FileNotFoundException e){ 
            System.out.println("file not found");
        }
        catch(IOException e) {
        System.out.println("Error processing file.");
        }      
      
    }

    public void makeBits(LinkedList<Block> blocks, List<CTCTrain> trains, List<Shift> switchPositions, List<UUID> closedBlocks, UUID crossingBlock, List<Block> lightsBlocks){
        this.blocks = blocks;
        this.trains = trains;
        this.switchPositions = switchPositions;
        this.closedBlocks = closedBlocks;
        this.crossingBlock = crossingBlock;
        this.lightsBlocks = lightsBlocks;

		//System.out.println();
        //go through each block and make a bitstring based on occupied ("1") or unoccupied ("0")
        if(blockBits.length() == 0){
            for(Block block : this.blocks){               
                    if(block.getOccupied()){
                        blockBits = blockBits + "1";
                    }
                    else{
                        blockBits = blockBits + "0";
                    }
                    numBlocks++;
            }
        }
        
        //compare the uuids of the list of closed blocks and replace
        int blockIndex = 0;
        for(Block block : this.blocks){
            for(UUID uuid : closedBlocks){
                if(block.getUUID() == uuid){
                    blockBits = blockBits.substring(0, blockIndex) + "1" + blockBits.substring(blockIndex + 1);
                }
            }
            blockIndex++;
        }

   

        //get the authority of the trains in the jurisdiction and make a bitstring of max size 111 (3 authority)
        for(CTCTrain train : this.trains){
            authorityBits = binAndPad(Math.round(train.getAuthority()), numBlocks);
            authorityBitsMultiple.add(authorityBits);
        }

        //check if there is a switch in this jurisdiction that is toggled to that jurisdiction
        if(this.switchPositions != null){
            switchOn = true; 
        }

        if(this.lightsBlocks != null){
            lightsOn = true;
        }

        //check if there is a crossing in this jurisdiction
        if(this.crossingBlock != null){
            crossingOn = true;
            //go through each block and make a bitstring based on occupied ("1") or unoccupied ("0")
            if(crossingBits.length() == 0){
                for(Block block : blocks){
                    if(block.getUUID() == crossingBlock){
                        crossingBits = crossingBits + "1";
                    }
                else{
                    crossingBits = crossingBits + "0";
                }
            }
        } 
        }
        /*System.out.println(crossingBits);
		for(Block block : blocks){
			System.out.print(block.getBlockNumber() + " ");
        }
        System.out.println();*/

    }
    
    public boolean runRedundantLogic(String expression, String blockBits, String authorityBits, int numBlocks){ 
        String boolExpression = "";
        String currentChar = "";
        String nextChar = "";
        boolean pass = false;
        boolean temp = false; 
        //System.out.println(expression);
        for(int i = 0; i<expression.length(); i++){
            currentChar = expression.substring(i, i+1);
            if(i != expression.length()-1){
                nextChar = expression.substring(i+1, i+2);
            }
            if(!isOperator(currentChar)){
                if(currentChar.equals("s")){
                    if(nextChar.equals("o")){
                        if(switchOccupied){
                            boolExpression = boolExpression + "t";
                        }
                        else{
                            boolExpression = boolExpression + "f";
                        }
                    }
                    if(nextChar.equals("b")){
                        if(switchBroken){
                            boolExpression = boolExpression + "t";
                        }
                        else{
                            boolExpression = boolExpression + "f";
                        }
                    }                
                }
                else if(currentChar.equals("c")){
                    if(nextChar.equals("o")){
                        if(crossingOccupied){
                            boolExpression = boolExpression + "t";
                        }
                        else{
                            boolExpression = boolExpression + "f";
                        }
                    }
                    if(nextChar.equals("b")){
                        if(crossingBroken){
                            boolExpression = boolExpression + "t";
                        }
                        else{
                            boolExpression = boolExpression + "f";
                        }
                    }
                }
                else if(currentChar.equals("w")){
                    if(nextChar.equals("a")){
                        if(crossingCheck(blockBits, crossingBits, numBlocks)){
                            boolExpression = boolExpression + "t";
                        }
                        else{
                            boolExpression = boolExpression + "f";
                        }
                    }
                }
                else if(currentChar.equals("a")){
                    if(nextChar.equals("o")){
                        boolExpression = boolExpression + "f";
                    if(authorityAndBlockCheck(blockBits, authorityBits, 12)){
                        boolExpression = boolExpression + "t";
                    }
                    else{
                        boolExpression = boolExpression + "f";
                    }
                    }
                }
                
            }
            else{
                boolExpression = boolExpression + currentChar;
            }
        }
        return parseBoolExpr(boolExpression);
    }
    
    //modify then send
    public List<CTCTrain> runAuthorityLogic(){
        int count = 0;
        String adjustString = "";
        
        for(String bits : authorityBitsMultiple){
            boolean pass = runRedundantLogic(authorityExpression, blockBits, bits, numBlocks);
            if(!pass){
                //set trains to 0
            	adjustString = adjustString + "1";
            }
            else{
            	adjustString = adjustString + "0";
            }
        }
        if(trains != null){
            for(CTCTrain train : trains){
                if(adjustString.substring(count, count + 1).equals("1")){
                    train.setAuthority(0);
                }
                adjustedTrains.add(train);
                count++;     	
            }
        }
        return adjustedTrains;
    }

    //modify then send
    public List<Shift> runSwitchLogic(){
        if(switchOn){
            boolean pass = runRedundantLogic(switchExpression, blockBits, authorityBits, numBlocks);
            if(!pass){
                for(Shift shift : switchPositions){
                    for(Block block : blocks){
                        if(shift.getPosition().getBlockNumber() == block.getBlockNumber()){
                            shift.togglePosition();                       
                        }
                    }
                    adjustedSwitchPositions.add(shift);
                }
            }
        }
        return adjustedSwitchPositions;
    }
    
    //can set on its own
    public Block runCrossingLogic(){
        if(crossingOn){
            boolean pass = runRedundantLogic(crossingExpression, blockBits, authorityBits, numBlocks);
            if(pass){
                for(Block block : blocks){
                    //activate crossing
                    if(block instanceof Crossing){
                    	((Crossing)block).setClosed(true);
                    	((Crossing)block).setLights(true);
                    	return block;
                    }
                }
            }
            else{
                for(Block block : blocks){
                    //activate crossing
                    if(block instanceof Crossing){
                    	((Crossing)block).setClosed(false);
                    	((Crossing)block).setLights(false);
                    	return block;
                    }
                }       
            }
        }

        return null;   
    }
    
    //can set on its own
    public Block runPLCLogicLights(){
        if(lightsOn){
            lightsCycle(blockBits, numBlocks);
            if(oneBlockAwayOccupied){
                for(Block block : blocks){
                    if(block instanceof Shift){
                        block.setSignalLight(2);
                    }
                }
            }
            else if(twoBlocksAwayOccupied){
                for(Block block : blocks){
                    if(block instanceof Shift){
                        block.setSignalLight(1);
                    }
                }               
            }
            else{
                for(Block block : blocks){
                    if(block instanceof Shift){
                        block.setSignalLight(0);
                    }
                }                
            }
        }

        return null;   
    }

    public static void lightsCycle(String blocks, int numBlocks){
		String blockBits = blocks;
		String padded1 = pad("1", numBlocks);
		String padded2 = pad("01", numBlocks);
		if(and(padded1, blockBits)){
			oneBlockAwayOccupied = true;
		}
		else if(and(padded2, blockBits)){
			twoBlocksAwayOccupied = true;
		}
	}

    public static boolean crossingCheck(String blocks, String crossing, int numBlocks){
		boolean pass1 = false;
		boolean pass2 = false;
		String crossingBits = crossing;
		String blockBits = blocks;

		//one side
		for(int i = 0; i<crossing.length(); i++){
			if(!and(crossingBits, "1")){
				crossingBits = shiftRight(crossingBits, 1, numBlocks);
				blockBits = shiftRight(blockBits, 1, numBlocks);
			}
			if(and(crossingBits, "1") && and(blockBits, "1000")){
				pass1 = true; 
            }
            else if(and(crossingBits, "1") && and(blockBits, "0100")){
				pass1 = true; 
            }
            else if(and(crossingBits, "1") && and(blockBits, "0010")){
				pass1 = true; 
			}
		}

		crossingBits = crossing;
		blockBits = blocks;

		//other side
		for(int i = 0; i<crossing.length(); i++){
			if(!and(crossingBits, pad("1", numBlocks))){
				crossingBits = shiftLeft(crossingBits, 1);
				blockBits = shiftLeft(blockBits, 1);
			}
			if(and(crossingBits, pad("1", numBlocks)) && and(blockBits, pad("0001", numBlocks))){
				pass2 = true; 
            }
            else if(and(crossingBits, pad("1", numBlocks)) && and(blockBits, pad("0010", numBlocks))){
				pass2 = true; 
            }
            else if(and(crossingBits, pad("1", numBlocks)) && and(blockBits, pad("0100", numBlocks))){
				pass2 = true; 
			}
		}

		return pass1 || pass2;
	}

	public static boolean authorityAndBlockCheck(String blocks, String author, int numBlocks){
        boolean failure = false;
        int[] occupiedIndices = getOccupiedIndicies(blocks, numBlocks);
        //int[] occupiedIndicesRev = getOccupiedIndiciesRev(blocks, numBlocks);

		/*for(int i = 0; i<occupiedIndicesRev.length; i++){
			if(occupiedIndicesRev[i] != 0){
				if(and(shiftLeft(author, occupiedIndicesRev[i]), blocks)){
					System.out.println("1 fail for: "+ and(shiftLeft(author, occupiedIndicesRev[i]), blocks));
					failure = true;
				}
			}
		}*/

		author = shiftLeft(author, numBlocks-1);
	

		for(int i = 0; i<occupiedIndices.length; i++){
			if(occupiedIndices[i] != 0){
				if(and(shiftRight(author, occupiedIndices[i], numBlocks), blocks)){
					System.out.println("2 fail for: "+ and(shiftRight(author, occupiedIndices[i], numBlocks), blocks));
					failure = true;
				}
			}
		}

		return failure;
    }

	public static String shiftLeft(String bits, int number){
		String blocksShifted = bits;
		for(int i = 0; i<number; i++){
			blocksShifted = blocksShifted + "0";
		}
		return blocksShifted.substring(number);
	}

	public static String shiftRight(String bits, int number, int numBlocks){
		String blocksShifted = bits;
		for(int i = 0; i<number; i++){
			blocksShifted = "0" + blocksShifted;
		}
		return blocksShifted.substring(0, numBlocks);
	}

	public static boolean and(String bits1, String bits2){
		int intBits1 = Integer.parseInt(bits1, 2);
		int intBits2 = Integer.parseInt(bits2, 2);

		int result = intBits1 & intBits2;

		if(result >= 1){
			return true;
		}

		return false;
	}

	public static boolean or(String bits1, String bits2){
		int intBits1 = Integer.parseInt(bits1, 2);
		int intBits2 = Integer.parseInt(bits2, 2);

		int result = intBits1 | intBits2;
		if(result >= 1){
			return true;
		}

		return false;
    }
    
    public static String pad(String bits, int numBlocks){
		String padded = bits;
		while(padded.length() < numBlocks){
			padded = padded + "0";
		}

		return padded;
	}

	public static String binAndPad(int result, int numBlocks){
		String padded = Integer.toBinaryString(result);
		while(padded.length() < numBlocks+1){
			padded = "0" + padded;
		}
		return padded;
    }

    public static boolean isOperator(String chara){
		if(!chara.equals("&") && !chara.equals("|") && !chara.equals("(") && !chara.equals(")") && !chara.equals("!") && !chara.equals(",")){
			return false;
		}
		return true;
    }

    
    public static boolean parseBoolExpr(String expression) {
        if (expression == null || expression.length() == 0) return false;
        //System.out.println(expression);
        Stack<Character> ops = new Stack<>();
        Stack<Boolean> operands = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (ch == 'f')
                operands.push(false);
            else if (ch == 't')
                operands.push(true);
            else if (ch == '(')
                operands.push(null);
            else if (ch == ')') {
                char operator = ops.pop();

                boolean res = true;
                if (operator == '|')
                    res = false;

                while (!operands.isEmpty() && operands.peek() != null) {
                    if (operator == '|') 
                        res |= operands.pop();
                    else if (operator == '&')
                        res &= operands.pop();
                    else if (operator == '!')
                        res = !operands.pop(); 
                }
                operands.pop();
                operands.push(res);

            } else if (ch == ',') 
                continue;
            else {
                ops.push(ch);
            }
        }
        return operands.peek();
    }

    public static int[] getOccupiedIndicies(String blocks, int numBlocks){
	    int maxBlocks = 28;
		int[] occupiedIndices = new int[maxBlocks];
		int overlap = 0;

		for(int i = 0; i<blocks.length(); i++){
			if(blocks.substring(i, i+1).equals("1") && i < numBlocks-overlap){
				occupiedIndices[i] = i + 1;
			}
		}

		return occupiedIndices;
	}

	/*public static int[] getOccupiedIndiciesRev(String blocks, int numBlocks){
	    int maxBlocks = 28;
		int[] occupiedIndicesRev = new int[maxBlocks];
		int overlap = 0;

		for(int i = numBlocks-1; i>0; i--){

			if(blocks.substring(i, i+1).equals("1") && numBlocks - i < numBlocks-overlap){
				occupiedIndicesRev[i] = numBlocks - i;
			}
		}

		return occupiedIndicesRev;
	}*/


}