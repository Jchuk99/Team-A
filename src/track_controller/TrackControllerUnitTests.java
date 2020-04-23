package src.track_controller;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;


import java.util.*;
import java.io.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import src.track_module.TrackModule.FileFormatException;
import src.train_controller.TrainControllerModule;
import src.train_module.TrainModule;
import src.ctc.CTCModule;
import src.track_controller.TrackControllerModule;
import src.Module;
import src.ctc.CTCTrain;
import src.ctc.CTCBlockConstructor.CTCShift;
import src.track_module.*;
import src.track_module.BlockConstructor.Normal;
import src.track_module.BlockConstructor.Crossing;
import src.track_module.BlockConstructor.Shift;

@RunWith(JUnit4.class)
public class TrackControllerUnitTests {
    
    //NOTE: testMakeBits and parseAndCompile should
    //pass since the output streams are equal but for
    //some reason the test report says they're not

	public static String blockBits = "";
	public static String crossingBits = "";
	public static String authorityBits = "";
	public static boolean switchOn = false;
	public static boolean crossingOn = false;
    public static List<String> authorityBitsMultiple = new ArrayList<String>();

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    //TrackControllerModule Tests
    @Test
	public void testGetTrainsInJuris(){
		////////////////////////////Section L///////////////////////////////////
		Normal block69 = new Normal("green", 'L', 69, 1, 5, 1, 1, 1, false, 1, 1);
		Normal block70 = new Normal("green", 'L', 70, 1, 5, 1, 1, 1, false, 1, 1);
		Normal block71 = new Normal("green", 'L', 71, 1, 5, 1, 1, 1, false, 1, 1);
		Normal block72 = new Normal("green", 'L', 72, 1, 5, 1, 1, 1, false, 1, 1);
		Normal block73 = new Normal("green", 'L', 73, 1, 5, 1, 1, 1, false, 1, 1);

		LinkedList<Block> blocksL = new LinkedList<Block>();
		blocksL.add(block69);
		blocksL.add(block70);
		blocksL.add(block71);
		blocksL.add(block72);
		blocksL.add(block73);

		//sent trains
		CTCTrain train1 = new CTCTrain(1);
		CTCTrain train2 = new CTCTrain(1);
		train1.setAuthority(2);
		train1.setCurrPos(block70.getUUID());
		train2.setAuthority(2);
		List<CTCTrain> trains = new ArrayList<CTCTrain>();
		trains.add(train1);
		trains.add(train2);
		System.out.println("Block 70 UUID: " + block70.getUUID());
        List<CTCTrain> trainsInJuris = getTrainsInJuris(blocksL, trains);
        for(CTCTrain train : trainsInJuris){
            assertEquals(block70.getUUID(), train1.getCurrPos());      
        }  
	}


	public static List<CTCTrain> getTrainsInJuris(LinkedList<Block> blocks, List<CTCTrain> trains){
		List<CTCTrain> trainsInJuris = new ArrayList<CTCTrain>();
		for(CTCTrain train : trains){
			for(Block block : blocks){
				if(train.getCurrPos() == block.getUUID()){
					trainsInJuris.add(train);
				}
			}
		}	

		for(CTCTrain train : trainsInJuris){
			System.out.println(train.getCurrPos());

		}

		return trainsInJuris;
	}

    @Test
	public void testGetSwitchesInJuris(){
		////////////////////////////Section M///////////////////////////////////
		Normal block74 = new Normal("green", 'M', 74, 1, 5, 1, 1, 1, false, 1, 1);
		Normal block75 = new Normal("green", 'M', 75, 1, 5, 1, 1, 1, false, 1, 1);
		CTCShift block76 = new CTCShift("green", 'M', 76, 1, 5, 1, 1, 1, false, 1, 1);
		Normal block77 = new Normal("green", 'N', 77, 1, 5, 1, 1, 1, false, 1, 1);
		LinkedList<Block> blocksM = new LinkedList<Block>();
		blocksM.add(block74);
		blocksM.add(block75);
		blocksM.add(block76);
		blocksM.add(block77);

		//switch positions	
		Normal block101 = new Normal("green", 'R', 101, 1, 5, 1, 1, 1, false, 1, 1);
		List<Block> switchPositions = new ArrayList<Block>();
		switchPositions.add(block77);
		switchPositions.add(block101);	
		((CTCShift)block76).setSwitchPositions(switchPositions);
		((CTCShift)block76).setPosition(block77);
		List<CTCShift> switches = new ArrayList<CTCShift>();
		switches.add(block76);

        List<Shift> switchesInJuris = getSwitchesInJuris(blocksM, switches);
        for(Shift shift : switchesInJuris){
            assertEquals(block76.getBlockNumber(), shift.getPosition());      
        }  
	}

	public static List<Shift> getSwitchesInJuris(LinkedList<Block> blocks, List<CTCShift> switchPositions){
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

	@Test
	public void testGetClosedBlocksInJuris(){
		Normal block69 = new Normal("green", 'L', 69, 1, 5, 1, 1, 1, false, 1, 1);
		Normal block70 = new Normal("green", 'L', 70, 1, 5, 1, 1, 1, false, 1, 1);
		Normal block71 = new Normal("green", 'L', 71, 1, 5, 1, 1, 1, false, 1, 1);
		Normal block72 = new Normal("green", 'L', 72, 1, 5, 1, 1, 1, false, 1, 1);
		Normal block73 = new Normal("green", 'L', 73, 1, 5, 1, 1, 1, false, 1, 1);
		LinkedList<Block> blocksL = new LinkedList<Block>();
		blocksL.add(block69);
		blocksL.add(block70);
		blocksL.add(block71);
		blocksL.add(block72);
		blocksL.add(block73);


		List<UUID> closedBlocks = new ArrayList<UUID>();
		closedBlocks.add(block69.getUUID());
		closedBlocks.add(block73.getUUID());
		//System.out.println(block69.getUUID());
		//System.out.println(block73.getUUID());

		List<UUID> closedBlocksInJuris = getClosedBlocksInJuris(blocksL, closedBlocks);
        boolean check = true;
        for(UUID uuid : closedBlocksInJuris){
            if(check){
                assertEquals(block69.getUUID(), uuid);   
                check = false;
            }   
            else{
                assertEquals(block73.getUUID(), uuid); 
            }
        }      
    }

	public static List<UUID> getClosedBlocksInJuris(LinkedList<Block> blocks, List<UUID> closedBlocks){
		List<UUID> closedBlocksInJuris = new ArrayList<UUID>();
		for(UUID uuid : closedBlocks){
			for(Block block : blocks){
				if(uuid == block.getUUID()){
					closedBlocksInJuris.add(uuid);
				}
			}
		}

		for(UUID uuid : closedBlocksInJuris){
			System.out.println(uuid);

		}
		return closedBlocksInJuris;
	}

    @Test
	public void testGetCrossingBlocksInJuris(){	
		////////////////////////////Section I///////////////////////////////////
		Normal block46 = new Normal("red", 'L', 69, 1, 5, 1, 1, 1, false, 1, 1);
		Crossing block47 = new Crossing("red", 'L', 70, 1, 5, 1, 1, 1, false, 1, 1);
		Normal block48 = new Normal("red", 'L', 71, 1, 5, 1, 1, 1, false, 1, 1);
		LinkedList<Block> blocksI = new LinkedList<Block>();
		blocksI.add(block46);
		blocksI.add(block47);
		blocksI.add(block48);

		UUID uuid1 = block47.getUUID();
		UUID uuid2 = getCrossingBlocksInJuris(blocksI);
		assertEquals(uuid1, uuid2);
	}

	public static UUID getCrossingBlocksInJuris(LinkedList<Block> blocks){	
		UUID crossingBlock = null;	
		for(Block block : blocks){;
			if(block instanceof Crossing){
				crossingBlock = block.getUUID();
				System.out.println(crossingBlock);
			}
			
		}	
		//assertTrue(train.getCurrPos, uuid1);
		return crossingBlock;
	}

	public static void testGetLightsInJuris(){
		Block block74 = new Normal("green", 'M', 74, 1, 5, 1, 1, 1, false, 1, 1);
		Block block75 = new Normal("green", 'M', 75, 1, 5, 1, 1, 1, false, 1, 1);
		Block block76 = new Shift("green", 'M', 76, 1, 5, 1, 1, 1, false, 1, 1);
		Block block77 = new Normal("green", 'N', 77, 1, 5, 1, 1, 1, false, 1, 1);
		LinkedList<Block> blocksM = new LinkedList<Block>();
		blocksM.add(block74);
		blocksM.add(block75);
		blocksM.add(block76);
		blocksM.add(block77);

		//switch positions	
		Block block101 = new Normal("green", 'R', 101, 1, 5, 1, 1, 1, false, 1, 1);
		List<Block> switchPositions = new ArrayList<Block>();
		switchPositions.add(block77);
		switchPositions.add(block101);	
		((Shift)block76).setSwitchPositions(switchPositions);
		((Shift)block76).setPosition(block77);
		List<Block> switches = new ArrayList<Block>();
		switches.add(block76);

		//List<Block> lightsInJuris 
	}

	public static List<Block> getLightsInJuris(LinkedList<Block> blocks){
		List<Block> lightsInJuris = new ArrayList<Block>();
		boolean foundLight = false;
		for(Block block : blocks){

		}

		return lightsInJuris;
	}

    @Before
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    private String getOutput() {
        return testOut.toString();
    }

    @After
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

	//PLC Tests
    @Test
	public void testParseAndCompile(){
        String crossingExpression = "&(!(co),!(cb),!(wa))";
		String authorityExpression = "!(ao)";
		String switchExpression = "&(!(so),!(sb),!(ao))";

		parseAndCompile();
		assertEquals("file not found", getOutput());
	}

	public static void parseAndCompile(){
		//
		File file = new File("jtests/plctest2.txt");
		String authorityExpression;
		String switchExpression;
		String crossingExpression;
		String lightsExpression;
		String[] argumentsTemp;


        String plcText;
        String line;
    	try{
            BufferedReader in = new BufferedReader(new FileReader(file));
            while((line = in.readLine()) != null) {
                argumentsTemp = line.toString().split(":");
                if(argumentsTemp[0].equals("STEP-AU")){
                    authorityExpression = argumentsTemp[1];
                    System.out.println(authorityExpression);
                }
                else if(argumentsTemp[0].equals("STEP-SW")){
                    switchExpression = argumentsTemp[1];
                    System.out.println(switchExpression);
                }
                else if(argumentsTemp[0].equals("STEP-CR")){
                    crossingExpression = argumentsTemp[1];
                    System.out.println(crossingExpression);
                }
                else if(argumentsTemp[0].equals("STEP-LI")){
                    lightsExpression = argumentsTemp[1];
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

    @Test
    public void testRunRedundantLogic(){
    	String switchExpression = "&(!(so),!(sb),!(ao))";
    	String blockBits = "0001001";
    	String authorityBits = "0000011";
    	int numBlocks = 7;
    	String desiredExpression = "&(!(f),!(f),!(f))";
    	boolean desiredBoolean = true;
    	assertEquals(desiredBoolean, runRedundantLogic(switchExpression, blockBits, authorityBits, numBlocks));
    }

    public static boolean runRedundantLogic(String expression, String blockBits, String authorityBits, int numBlocks){ 
        //
	    boolean switchOccupied = false;
	    boolean switchBroken = false;
	    boolean crossingOccupied = false;
	    boolean crossingBroken = false;

        String boolExpression = "";
        String currentChar = "";
        String nextChar = "";
        boolean pass = false;
        boolean temp = false; 

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
                        //switch occupied
                    }
                }
                else if(currentChar.equals("a")){
                    if(authorityAndBlockCheck(blockBits, authorityBits, numBlocks)){
                        boolExpression = boolExpression + "t";
                    }
                    else{
                        boolExpression = boolExpression + "f";
                    }
                }
                
            }
            else{
                boolExpression = boolExpression + currentChar;
            }
        }

        return parseBoolExpr(boolExpression);
    }

    @Test
    public void testRunAuthorityLogic(){
		////////////////////////////Section L///////////////////////////////////
		Block block69 = new Normal("green", 'L', 69, 1, 5, 1, 1, 1, false, 1, 1);
		Block block70 = new Normal("green", 'L', 70, 1, 5, 1, 1, 1, false, 1, 1);
		Block block71 = new Normal("green", 'L', 71, 1, 5, 1, 1, 1, false, 1, 1);
		Block block72 = new Normal("green", 'L', 72, 1, 5, 1, 1, 1, false, 1, 1);
		Block block73 = new Normal("green", 'L', 73, 1, 5, 1, 1, 1, false, 1, 1);

		LinkedList<Block> blocksL = new LinkedList<Block>();
		blocksL.add(block69);
		blocksL.add(block70);
		blocksL.add(block71);
		blocksL.add(block72);
		blocksL.add(block73);

		//sent trains
		CTCTrain train1 = new CTCTrain(1);
		CTCTrain train2 = new CTCTrain(1);
		CTCTrain train3 = new CTCTrain(1);
		train1.setAuthority(2);
		train2.setAuthority(1);
		train3.setAuthority(3);
		List<CTCTrain> trains = new ArrayList<CTCTrain>();
		trains.add(train1);
		trains.add(train2);
		trains.add(train3);
		
		List<CTCTrain> adjustedTrains = runAuthorityLogic(trains);

		/*for(CTCTrain train : adjustedTrains){
			System.out.println(train.getAuthority());
		}*/
        int count = 0;
        for(CTCTrain train : adjustedTrains){
            if(count == 0){
                System.out.println(train.getAuthority());
                assertEquals(2, Math.round(train.getAuthority()));
                count++;  
            }
            else if(count == 1){
                System.out.println(train.getAuthority());
                assertEquals(1, Math.round(train.getAuthority()));
                count++;
            }  
            else{
                System.out.println(train.getAuthority());
                assertEquals(3, Math.round(train.getAuthority()));
            }
        }  
    }

    public static List<CTCTrain> runAuthorityLogic(List<CTCTrain> trains){
    	//
    	List<CTCTrain> adjustedTrains = new ArrayList<CTCTrain>();
    	String blockBits = "01010";
    	List<String> authorityBitsMultiple = new ArrayList<String>();
    	authorityBitsMultiple.add("00011");
    	authorityBitsMultiple.add("00001");
    	authorityBitsMultiple.add("00111");
    	int numBlocks = 5;
    	String authorityExpression = "!(ao)";

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

        for(CTCTrain train : trains){
        	if(adjustString.substring(count, count + 1).equals("1")){
        		train.setAuthority(0);
        	}
        	adjustedTrains.add(train);
        	count++;     	
        }



        return adjustedTrains;
    }

    public static void testRunSwitchLogic(){

    }

    /*public static List<Shift> runSwitchLogic(){


        if(switchOn){
            boolean pass = runRedundantLogic(switchExpression, blockBits, authorityBits, numBlocks);
            if(!pass){
                //toggle switch
            }
        }
        return adjustedSwitchPositions;  	
    }*/


    public static void testRunCrossingLogic(){
		////////////////////////////Section L///////////////////////////////////
		Normal block69 = new Normal("green", 'L', 69, 1, 5, 1, 1, 1, false, 1, 1);
		Normal block70 = new Normal("green", 'L', 70, 1, 5, 1, 1, 1, false, 1, 1);
		Crossing block71 = new Crossing("green", 'L', 71, 1, 5, 1, 1, 1, false, 1, 1);
		Normal block72 = new Normal("green", 'L', 72, 1, 5, 1, 1, 1, false, 1, 1);
		Normal block73 = new Normal("green", 'L', 73, 1, 5, 1, 1, 1, false, 1, 1);

		LinkedList<Block> blocksL = new LinkedList<Block>();
		blocksL.add(block69);
		blocksL.add(block70);
		blocksL.add(block71);
		blocksL.add(block72);
		blocksL.add(block73);

		UUID crossingBlock = block71.getUUID();
		Block block = runCrossingLogic(blocksL, crossingBlock);
    }

    public static Block runCrossingLogic(LinkedList<Block> blocks, UUID crossingBlock){
    	//
    	boolean crossingOn = true;
    	String crossingExpression = "&(!(co),!(cb),!(to))";
    	String blockBits = "0001001";
    	String authorityBits = "0000011";
    	int numBlocks = 7;

        if(crossingOn){
            boolean pass = runRedundantLogic(crossingExpression, blockBits, authorityBits, numBlocks);
            if(pass){
                for(Block block : blocks){
                    //activate crossing
                    if(block instanceof Crossing){
                    	//block.setClosed(true);
                    	//block.setLights(true);
                    	return block;
                    }
                }
            }
        }

        return null;     
    }







    @Test
	public void testMakeBits(){	
		////////////////////////////Section L///////////////////////////////////
		Normal block69 = new Normal("green", 'L', 69, 1, 5, 1, 1, 1, false, 1, 1);
		Normal block70 = new Normal("green", 'L', 70, 1, 5, 1, 1, 1, false, 1, 1);
		block70.setOccupied(true);
		Normal block71 = new Normal("green", 'L', 71, 1, 5, 1, 1, 1, false, 1, 1);
		Normal block72 = new Normal("green", 'L', 72, 1, 5, 1, 1, 1, false, 1, 1);
		block72.setOccupied(true);
		Crossing block73 = new Crossing("green", 'L', 73, 1, 5, 1, 1, 1, false, 1, 1);

		LinkedList<Block> blocksL = new LinkedList<Block>();
		blocksL.add(block69);
		blocksL.add(block70);
		blocksL.add(block71);
		blocksL.add(block72);
		blocksL.add(block73);

		//sent trains
		CTCTrain train1 = new CTCTrain(1);
		CTCTrain train2 = new CTCTrain(1);
		train1.setAuthority(2);
		train1.setCurrPos(block70.getUUID());
		train2.setAuthority(2);
		List<CTCTrain> trains = new ArrayList<CTCTrain>();
		trains.add(train1);
		trains.add(train2);
		//System.out.println("Block 70 UUID: " + block70.getUUID());

		String desiredBlockBits = "01010";
		String desiredAuthorityBits = "00011";
        String desiredCrossingBits = "00100";

        List<UUID> uuids = new ArrayList<UUID>();
        uuids.add(block71.getUUID());
        
        makeBits(blocksL, trains, null, uuids, block73.getUUID());	
        assertEquals("01010", getOutput());

	}

	public static void makeBits(LinkedList<Block> blocks, List<CTCTrain> trains, List<Block> switchPositions, List<UUID> closedBlocks, UUID crossingBlock){
		//////////////////////////////////

        //go through each block and make a bitstring based on occupied ("1") or unoccupied ("0")
        int numBlocks = 0;
        for(Block block : blocks){
            if(block.getOccupied()){
                blockBits = blockBits + "1";
            }
            else{
                blockBits = blockBits + "0";
            }
            numBlocks++;
        }

        System.out.println(blockBits);
        
        //compare the uuids of the list of closed blocks and replace
        int blockIndex = 0;
        for(Block block : blocks){
            for(UUID uuid : closedBlocks){
                if(block.getUUID() == uuid){
                    blockBits = blockBits.substring(0, blockIndex) + "1" + blockBits.substring(blockIndex + 1);
                }
            }
            blockIndex++;
        }

        //go through each block and make a bitstring based on occupied ("1") or unoccupied ("0")
        for(Block block : blocks){
            if(block.getUUID() == crossingBlock){
                crossingBits = crossingBits + "1";
            }
            else{
                crossingBits = crossingBits + "0";
            }
        }    

        //get the authority of the trains in the jurisdiction and make a bitstring of max size 111 (3 authority)
        for(CTCTrain train : trains){
            authorityBits = binAndPad(Math.round(train.getAuthority()), numBlocks);
            authorityBitsMultiple.add(authorityBits);
        }

        //check if there is a switch in this jurisdiction that is toggled to that jurisdiction
        if(switchPositions != null){  
            if(switchPositions.size() > 0){
                switchOn = true; 
            }
        }

        //check if there is a crossing in this jurisdiction
        if(crossingBlock != null){
            crossingOn = true;
        }

        //System.out.println("Block bits with closed: " + blockBits);
        //System.out.println("Authority bits: " + authorityBits);
        //System.out.println("Crossing bits: " + crossingBits);
        //System.out.println("Switch on: " + switchOn);
        //System.out.println("Crossing on: " + crossingOn);
    }

    @Test
    public  void testAuthorityAndBlockCheck(){
        String blockBits = "010101001000";
        String authorBits = "000000001111";
        int numBlocks = 12;

        boolean desiredFailure = true;

        assertEquals(desiredFailure, authorityAndBlockCheck(blockBits, authorBits, 12));
        //assertTrue

        blockBits = "010000000100";
        authorBits = "000000001111";
        numBlocks = 12;

        desiredFailure = false;

        assertEquals(desiredFailure, authorityAndBlockCheck(blockBits, authorBits, 12));
        //assertTrue

        blockBits = "010101001000";
        authorBits = "000000000011";
        numBlocks = 12;

        desiredFailure = true;

        assertEquals(desiredFailure, authorityAndBlockCheck(blockBits, authorBits, 12));
        //assertTrue

        blockBits = "010101001000";
        authorBits = "000000000001";
        numBlocks = 12;

        desiredFailure = false;

        assertEquals(desiredFailure, authorityAndBlockCheck(blockBits, authorBits, 12));
        //assertTrue

        blockBits = "010101001100";
        authorBits = "000000000001";
        numBlocks = 12;

        desiredFailure = true;

        assertEquals(desiredFailure, authorityAndBlockCheck(blockBits, authorBits, 12));
        //assertTrue
    }

    public static boolean authorityAndBlockCheck(String blocks, String author, int numBlocks){
        boolean failure = false;
        int[] occupiedIndices = getOccupiedIndicies(blocks, numBlocks);
        int[] occupiedIndicesRev = getOccupiedIndiciesRev(blocks, numBlocks);

		for(int i = 0; i<occupiedIndicesRev.length; i++){
			if(occupiedIndicesRev[i] != 0){
				if(and(shiftLeft(author, occupiedIndicesRev[i]), blocks)){
					//System.out.println("1 fail for: "+ and(shiftLeft(author, occupiedIndicesRev[i]), blocks));
					failure = true;
				}
			}
		}

		author = shiftLeft(author, numBlocks-1);
	

		for(int i = 0; i<occupiedIndices.length; i++){
			if(occupiedIndices[i] != 0){
				if(and(shiftRight(author, occupiedIndices[i], numBlocks), blocks)){
					//System.out.println("2 fail for: "+ and(shiftRight(author, occupiedIndices[i], numBlocks), blocks));
					failure = true;
				}
			}
		}

		return failure;
    }

    @Test
    public void testCrossingCheck(){
    	String blockBits = "000100000";
    	String crossingBits = "000000100";
    	int numBlocks = 9;

    	boolean desiredBoolean = true;

        assertEquals(desiredBoolean, crossingCheck(blockBits, crossingBits, numBlocks));
    	//assertTrue

    	blockBits = "000100000";
    	crossingBits = "000000010";
    	numBlocks = 9;

    	desiredBoolean = false;

        assertEquals(desiredBoolean, crossingCheck(blockBits, crossingBits, numBlocks));
    	//assertTrue

    	blockBits = "000100000";
    	crossingBits = "100000000";
    	numBlocks = 9;

    	desiredBoolean = true;

        assertEquals(desiredBoolean, crossingCheck(blockBits, crossingBits, numBlocks));
    	//assertTrue

    	blockBits = "000000100";
    	crossingBits = "100000000";
    	numBlocks = 9;

    	desiredBoolean = false;

        assertEquals(desiredBoolean, crossingCheck(blockBits, crossingBits, numBlocks));
    	//assertTrue
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
		}

		return pass1 || pass2;
	}

    @Test
	public void testLightsCycle(){
		String blockBits = "0010001000100";
		String desiredColor = "100";
        assertEquals(desiredColor, lightsCycle(blockBits, 13));

		blockBits = "0100001000100";
		desiredColor = "010";
        assertEquals(desiredColor, lightsCycle(blockBits, 13));

		blockBits = "1000001000100";
		desiredColor = "001";
        assertEquals(desiredColor, lightsCycle(blockBits, 13));

	}

	public static String lightsCycle(String blocks, int numBlocks){
		String blockBits = blocks;
		String padded1 = pad("1", numBlocks);
		String padded2 = pad("01", numBlocks);
		String lights = "000";
		if(and(padded1, blockBits)){
			lights = "001";
		}
		else if(and(padded2, blockBits)){
			lights = "010";
		}
		else{
			lights = "100";
		}

		return lights;
	}



    @Test
    public void testShiftLeft(){
    	String blockBits = "0100100";
    	String desired = "1001000";
    	int shifts = 1;
        assertEquals(desired, shiftLeft(blockBits, shifts));
    }

    public static String shiftLeft(String bits, int number){
		String blocksShifted = bits;
		for(int i = 0; i<number; i++){
			blocksShifted = blocksShifted + "0";
		}
		return blocksShifted.substring(number);
	}

    @Test
    public void testShiftRight(){
    	String blockBits = "0100100";
    	String desired = "0010010";
    	int shifts = 1;
    	int numBlocks = 7;
        assertEquals(desired, shiftRight(blockBits, shifts, numBlocks));
    }

	public static String shiftRight(String bits, int number, int numBlocks){
		String blocksShifted = bits;
		for(int i = 0; i<number; i++){
			blocksShifted = "0" + blocksShifted;
		}
		return blocksShifted.substring(0, numBlocks);
	}

    @Test
	public void testAnd(){
		String first = "000001";
		String second = "010000";
		boolean desiredBoolean = false;

		first = "000001";
		second = "000001";
		desiredBoolean = true;
        assertEquals(desiredBoolean, and(first, second));
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

    @Test
	public void testOr(){
		String first = "000000";
		String second = "000000";
		boolean desiredBoolean = false;

        assertEquals(desiredBoolean, or(first, second));

		first = "000100";
		second = "000001";
		desiredBoolean = true;

        assertEquals(desiredBoolean, or(first, second));
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
    
    @Test
    public void testPad(){
    	String initialBits = "1";
    	int numBlocks = 11;
    	String paddedBits = "10000000000";
        assertEquals(paddedBits, pad(initialBits, 11));
    }

    public static String pad(String bits, int numBlocks){
		String padded = bits;
		while(padded.length() < numBlocks){
			padded = padded + "0";
		}

		return padded;
	}

    @Test
    public void testBinAndPad(){
    	String paddedDesired = "00011";
    	String padded = binAndPad(2, 5);
    	assertEquals(paddedDesired, padded);
    }
    public static String binAndPad(int result, int numBlocks){
    	String padded = "";
    	if(result == 1){
    		padded = "1";
    	}
		else if(result == 2){
			padded = "11";
		}
		else if(result == 3){
			padded = "111";
		}
		while(padded.length() < numBlocks){
			padded = "0" + padded;
		}
		return padded;
    }

    @Test
    public void testParseBoolExpr(){
    	//x is true y is false
    	String expression = "&(t,f)";
    	boolean desiredBoolean = false;
        assertEquals(desiredBoolean, parseBoolExpr(expression));

    	expression = "&(t,!(f))";
    	desiredBoolean = true;
        assertEquals(desiredBoolean, parseBoolExpr(expression));

    	expression = "|(&(t,!(f)),&(!(t),f))";
    	desiredBoolean = true;
        assertEquals(desiredBoolean, parseBoolExpr(expression));

    	expression = "!(|(t,f))";
    	desiredBoolean = false;
        assertEquals(desiredBoolean, parseBoolExpr(expression));

    	expression = "&(!(f),!(f),f)";
    	desiredBoolean = false;
        assertEquals(desiredBoolean, parseBoolExpr(expression));
    }

    public static boolean parseBoolExpr(String expression) {
        if (expression == null || expression.length() == 0) return false;

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

    @Test
    public void testIsOperator(){
    	String and = "&";
    	boolean desiredBoolean = true;
    	assertEquals(desiredBoolean, isOperator(and));

    	desiredBoolean = true;
    	String or = "|";
    	assertEquals(desiredBoolean, isOperator(or));

    	desiredBoolean = false;
    	String t = "t";
    	assertEquals(desiredBoolean, isOperator(t));
    }

    public static boolean isOperator(String chara){
		if(!chara.equals("&") && !chara.equals("|") && !chara.equals("(") && !chara.equals(")") && !chara.equals("!")){
			return false;
		}
		return true;
    }

    public static int[] getOccupiedIndicies(String blocks, int numBlocks){
	    int maxBlocks = 28;
		int[] occupiedIndices = new int[maxBlocks];
		int overlap = 3;

		for(int i = 0; i<blocks.length(); i++){
			if(blocks.substring(i, i+1).equals("1") && i < numBlocks-overlap){
				occupiedIndices[i] = i + 1;
			}
		}

		return occupiedIndices;
	}

	public static int[] getOccupiedIndiciesRev(String blocks, int numBlocks){
	    int maxBlocks = 28;
		int[] occupiedIndicesRev = new int[maxBlocks];
		int overlap = 3;

		for(int i = numBlocks-1; i>0; i--){
			if(blocks.substring(i, i+1).equals("1") && numBlocks - i < numBlocks-overlap){
				occupiedIndicesRev[i] = numBlocks - i;
			}
		}

		return occupiedIndicesRev;
	}

	/*public static void main(String[] args){


		//testGetTrainsInJuris();
		//testGetSwitchesInJuris();
		//testGetClosedBlocksInJuris();
		//testGetCrossingBlocksInJuris();
		//testGetLightsInJuris();

		//testMakeBits();
		//testBinAndPad();	
		//testShiftLeft();
		//testShiftRight();
		//testAnd();
		//testOr();
		//testPad();

		//testAuthorityAndBlockCheck();
		//testLightsCycle();
		//testCrossingCheck();

		//testParseAndCompile();
		//testRunRedundantLogic();
		//testParseBoolExpr();
		//testIsOperator();
		//testRunRedundantLogic();
		//testRunAuthorityLogic();
		//testRunCrossingLogic();
		//testRunLightsLogic();
		//testSystem();



	}*/

	public static void testSystem(){
		String line = "GREEN";
		TrackControllerModule trackControllerModule = new TrackControllerModule();
        HashMap<String, HashMap<Character, WaysideController>> myWaysides = new HashMap<String, HashMap<Character, WaysideController>>();
        myWaysides.put("GREEN", new HashMap<Character, WaysideController>());
        myWaysides.put("RED", new HashMap<Character, WaysideController>());  
        Normal block1 = new Normal("green", 'L', 69, 1, 5, 1, 1, 1, false, 1, 1);
		Normal block2 = new Normal("green", 'L', 70, 1, 5, 1, 1, 1, false, 1, 1);
		Normal block3 = new Normal("green", 'L', 71, 1, 5, 1, 1, 1, false, 1, 1);
		Shift block70 = new Shift("green", 'L', 71, 1, 5, 1, 1, 1, false, 1, 1);
		//wayside 1		
		myWaysides.get("GREEN").put('A', trackControllerModule.createWayside());		
		myWaysides.get("GREEN").get('A').addBlock(block1);
		myWaysides.get("GREEN").get('A').addBlock(block2);
		myWaysides.get("GREEN").get('A').addBlock(block3);
		WaysideController wayside1 = myWaysides.get("GREEN").get('A');

		//trains
		CTCTrain train1 = new CTCTrain(1);
		CTCTrain train2 = new CTCTrain(2);
		train1.setAuthority(2);
		train1.setCurrPos(block1.getUUID());
		train2.setAuthority(2);
		train2.setCurrPos(block70.getUUID());
		List<CTCTrain> trains = new ArrayList<CTCTrain>();
		trains.add(train1);
		trains.add(train2);

		//closed blocks
		List<UUID> closedBlocks = new ArrayList<UUID>();
		closedBlocks.add(block1.getUUID());
		closedBlocks.add(block3.getUUID());

		//crossing
		UUID crossing = block3.getUUID();

		//switch positions
		Normal block77 = new Normal("green", 'N', 77, 1, 5, 1, 1, 1, false, 1, 1);
		Normal block101 = new Normal("green", 'R', 101, 1, 5, 1, 1, 1, false, 1, 1);
		List<Block> switchPositions = new ArrayList<Block>();
		switchPositions.add(block77);
		switchPositions.add(block101);	
		((Shift)block70).setSwitchPositions(switchPositions);
		((Shift)block70).setPosition(block77);
		List<Block> switches = new ArrayList<Block>();
		switches.add(block70);



		//GET EVERYTHING IN JURIS AND GIVE TO WAYSIDE
		LinkedList<Block> blocksA = wayside1.getBlocks();
		List<CTCTrain> trainsInJuris = getTrainsInJuris(blocksA, trains);
		List<UUID> closedBlocksInJuris = getClosedBlocksInJuris(blocksA, closedBlocks);
		UUID crossingInJuris = getCrossingBlocksInJuris(blocksA);
		//List<Block> switchesInJuris = getSwitchesInJuris(blocksA, switches);

		wayside1.setTrains(trainsInJuris);
		wayside1.setClosedBlocks(closedBlocksInJuris);
		wayside1.setCrossingBlock(crossingInJuris);

		//RUN PLC LOGIC (runPLC())
		//makeBits(blocksA, trainsInJuris, switchesInJuris, closedBlocksInJuris, crossingInJuris);
		//List<CTCTrain> adjustedTrains = runAuthorityLogic();
		//List<CTCTrain> adjustedTrains = runAuthorityLogic()


	

	}

}						