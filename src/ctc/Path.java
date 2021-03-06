package src.ctc;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;
import src.track_module.Block;
import src.track_module.Edge;

public class Path {

    protected LinkedList<UUID> course;
    protected UUID endBlock;

    public Path(){
    }
 
    public Path(UUID startBlock, UUID endBlock, UUID prevBlock){
        this.endBlock = endBlock;
        course = findCourse(startBlock, endBlock, prevBlock);
    }

    public UUID getEndBlock() {return endBlock;};
    
    public void updateCourse(UUID start, UUID prev){
        course = findCourse(start, endBlock, prev);
    }

    public UUID getBeforeEndBlock() {
        //TODO: error check.
            return course.get(course.size() - 2);
    } 

    public UUID getNextBlockID(UUID currBlockID) {
        //TODO: error check.
            int currIndex = course.indexOf(currBlockID);
            if (currIndex == (course.size() - 1) || currIndex == -1){
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
    protected LinkedList<UUID> findCourse(UUID start, UUID destination, UUID prevBlock) {

        LinkedList<UUID> course = null;
        int limit = 0;
        CTCMap map = CTCModule.map;
        
        System.out.println("Start: " + map.getBlock(start).getBlockNumber());
        System.out.println("End: " + map.getBlock(destination).getBlockNumber());
        Set<UUID> blockIDs = map.getBlockIDs();
        HashMap<UUID, Boolean> marked = new HashMap<UUID, Boolean>();
        HashMap<UUID, Deque<UUID>> edgeTo = new HashMap<UUID,  Deque<UUID>>();
        HashMap<UUID, Integer> distTo = new HashMap<UUID, Integer>();

        for (UUID blockID: blockIDs){
            marked.put(blockID, false);
            edgeTo.put(blockID, new ArrayDeque<UUID>() );
            distTo.put(blockID, Integer.MAX_VALUE);
        }

        Block block = map.getBlock(start);
        Deque<Block> q = new LinkedList<Block>();
        
        distTo.put(block.getUUID(), 0);
        Deque<UUID> edgeQueue = edgeTo.get(block.getUUID());
        if (prevBlock != null){
            edgeQueue.addFirst(prevBlock);
        }
        q.add(block);

        while (!q.isEmpty()) {
            Block b = q.remove();
            prevBlock = edgeTo.get(b.getUUID()).peek();
                for (Edge e : b.getEdges()) {
                    Block edgeBlock = e.getBlock();
                    // not the previous block, connected, and edgeTo the block isn't the current block(circle)
                    if (!edgeBlock.getUUID().equals(prevBlock) && e.getOriginalConnection() && !b.getUUID().equals(edgeTo.get(edgeBlock.getUUID()).peek())){
                            edgeTo.get(edgeBlock.getUUID()).addFirst(b.getUUID());
                            q.add(edgeBlock);
                    }
                }
              if (limit >= 10000 || edgeTo.get(destination).size() >=1){
                  break;
              }
              limit++;
        }
        
        course = new LinkedList<UUID>();
        UUID curr = destination;
        while (!curr.equals(start)){
            course.add(0, curr);
            curr = edgeTo.get(curr).pollFirst();
            //TODO: what if I can't find a path?
            if (curr == null){
                break;
            }
        }
        course.add(0, curr);
        
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