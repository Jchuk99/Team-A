package src.ctc;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

import src.track_module.Block;
import src.track_module.Edge;

public class Path {

    //private static Map;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int startBlock;
    private int endBlock;
    //private UUID startBlock;
    //private UUID endBlock;
    //need a list of blocks
    //List<Integer> course;
    
    public Path(){
    }
 
   /* public Path(UUID startBlock, UUID endBlock){
        this.startBlock = startBlock;
        this.endBlock = endBlock;
    }*/

    public Path(int startBlock, int endBlock){
        this.startBlock = startBlock;
        this.endBlock = endBlock;
    }

    
    public LinkedList<Integer> getCourse(){
        LinkedList<Integer> course = search(startBlock, endBlock);
        return course;    
    }
    
    //TODO: Make algorithim account for distance of blocks
    private LinkedList<Integer> search(int start, int destination) {
		Block block = CTCModule.blockMap.get(start);
        LinkedList<Integer> course = new LinkedList<Integer>();
		Deque<Block> q = new LinkedList<Block>();
		boolean[] marked = new boolean[CTCModule.blockMap.size()];
		int[] edgeTo = new int[CTCModule.blockMap.size()];      
		int[] distTo = new int[CTCModule.blockMap.size()];
		
        for (int v = 0; v < CTCModule.blockMap.size() ; v++){
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

    public LocalDateTime getStartTime() {return startTime;};
    public LocalDateTime getEndTime() {return endTime;};
    public int getStartBlock() {return startBlock;};
    public int getEndBlock() {return endBlock;};

    
    // public UUID getStartBlock() {return startBlock;};
    // public UUID getEndBlock() {return endBlock;};


}