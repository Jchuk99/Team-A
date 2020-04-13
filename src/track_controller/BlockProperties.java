package src.track_controller;


public class BlockProperties {
    String occupied = "";
    int blockNumber;
    int authority;
    int suggestedSpeed;
    String blockOpenClose;
    int position = 1;


    public BlockProperties(int blockNumber, String occupied, String blockOpenClose, int suggestedSpeed, int authority, int position)  {
        this.blockNumber = blockNumber;
        this.occupied = occupied; 
        this.authority = authority;
        this.suggestedSpeed = suggestedSpeed;
        this.blockOpenClose = blockOpenClose; 
        this.position = position;
    }

    public int getBlockNumber() {return blockNumber;};
    public String getOccupied() {return occupied;};
    public int getAuthority() {return authority;};
    public int getSuggestedSpeed() {return suggestedSpeed;};
    public String getBlockOpenClose() {return blockOpenClose;};
    public int getPosition() {return position;};

    
    public void setBlockNumber(int blockNumber) {this.blockNumber = blockNumber;};
    public void setOccupied(String occupied) {this.occupied = occupied;};
    public void setAuthority(int authority) {this.authority = authority;};
    public void setSuggestedSpeed(int suggestedSpeed) {this.suggestedSpeed = suggestedSpeed;};
    public void setBlockOpenClose(String blockOpenClose) {this.blockOpenClose = blockOpenClose;};
    public void setPosition(int position) {this.position = position;};
 

}
