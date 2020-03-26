package src.ctc;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;
import java.time.LocalDateTime;

import src.track_module.Block;
import src.track_module.Edge;

public class Path {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LinkedList<UUID> course;
    private UUID startBlock;
    private UUID endBlock;

    
    public Path(){
    }
 
    public Path(UUID startBlock, UUID endBlock){
        this.startBlock = startBlock;
        this.endBlock = endBlock;
        course = findCourse(startBlock, endBlock);
    }

    public LocalDateTime getStartTime() {return startTime;};
    public LocalDateTime getEndTime() {return endTime;};
    public UUID getStartBlock() {return startBlock;};
    public UUID getEndBlock() {return endBlock;};


    public UUID getNextBlockID(UUID currBlockID) {
            int currIndex = course.indexOf(currBlockID);
            if (currIndex == (course.size() - 1)){
                return null;
            }
            else{
                return course.get((currIndex + 1));
            }
    } 

    public LinkedList<UUID> getCourse(){
        return course;    
    }
    
    //TODO: Make algorithim account for distance of blocks
    //TODO: Doesn't account for biDirectionality, going in a circle.
    // consider a block being sent to the block behind it, how would you account for this? (firstPath out of the yard and notFirstPath)
    // will need startBlock and the block before it on the previous path
    private LinkedList<UUID> findCourse(UUID start, UUID destination) {
        // maybe i can initialize a hashmap for marked(UUID, boolean), edgeTo(UUID, UUID), and distTo(UUID, int), by using the map to gather a list of all UUID's currently within the map
        // then let the algorithim proceed as it normally does, this should work
        CTCMap map = CTCModule.map;
        Set<UUID> blockIDs = map.getBlockIDs();
        HashMap<UUID, Boolean> marked = new HashMap<UUID, Boolean>();
        HashMap<UUID, UUID> edgeTo = new HashMap<UUID, UUID>();
        HashMap<UUID, Integer> distTo = new HashMap<UUID, Integer>();

        for (UUID blockID: blockIDs){
            marked.put(blockID, false);
            edgeTo.put(blockID, null);
            distTo.put(blockID, Integer.MAX_VALUE);
        }

        Block block = map.getBlock(start);
        Deque<Block> q = new LinkedList<Block>();
        
        distTo.put(block.getUUID(), 0);
        marked.put(block.getUUID(), true);
        q.add(block);

        while (!q.isEmpty()) {
            Block b = q.remove();
            for (Edge e : b.getEdges()) {
                Block edgeBlock = e.getBlock();
                if (!marked.get(edgeBlock.getUUID())) {
                    edgeTo.put(edgeBlock.getUUID(), b.getUUID());
                    distTo.put(edgeBlock.getUUID(), distTo.get(b.getUUID()) + 1);
                    marked.put(edgeBlock.getUUID(), true);
                    q.add(edgeBlock);
                }
            }
        }
        
        
        LinkedList<UUID> course = new LinkedList<UUID>();
		UUID curr = destination;
		while (!curr.equals(start)){
			course.add(0, curr);
			curr = edgeTo.get(curr);
		}
        course.add(0, curr);
        for (UUID blockID: course){
            System.out.println("Block Number: " + map.getBlock(blockID).getBlockNumber());
            System.out.println("Block ID: " + blockID);
        }
		return course;
    }
    


     
    //TODO: Make algorithim account for distance of blocks
   /* private LinkedList<Integer> findCourse(UUID start, UUID destination) {

        CTCMap map = CTCModule.map;
        Block block = map.getBlock(start);

        LinkedList<UUID> course = new LinkedList<Integer>();
		Deque<Block> q = new LinkedList<Block>();
		boolean[] marked = new boolean[map.size() + 1];
        int[] edgeTo = new int[map.size() + 1];      
		int[] distTo = new int[map.size() + 1];
		
        for (int v = 0; v < map.size() + 1 ; v++){
            distTo[v] = Integer.MAX_VALUE;
        }
        
        distTo[block.getBlockNumber()] = 0;
        marked[block.getBlockNumber()] = true;
        q.add(block);

        while (!q.isEmpty()) {
            Block b = q.remove();
            for (Edge e : b.getEdges()) {
				Block edgeBlock = e.getBlock();
                if (!marked[edgeBlock.getBlockNumber()]) {
                    edgeTo[edgeBlock.getBlockNumber()] = b.getBlockNumber();
                    distTo[edgeBlock.getBlockNumber()] = distTo[b.getBlockNumber()] + 1;
                    marked[edgeBlock.getBlockNumber()] = true;
                    q.add(edgeBlock);
                }
            }
        }
		
		int curr = destination;
		while (curr != start){
			course.add(0, curr);
			curr = edgeTo[curr];
		}
		course.add(0, curr);
		return course;
    }
    */
    

    


}