package src.track_controller;


public class BlockProperties {
    String occupied = "";
    int blockNumber;

    public BlockProperties(int blockNumber, String occupied) {
        this.blockNumber = blockNumber;
        this.occupied = occupied; 
    }

    public int getBlockNumber() {return blockNumber;};
    public String getOccupied() {return occupied;};

    
    public void setBlockNumber(int blockNumber) {this.blockNumber = blockNumber;};
    public void setOccupied(String occupied) {this.occupied = occupied;};

}
