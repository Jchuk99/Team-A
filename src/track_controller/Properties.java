package src.track_controller;


public class Properties {
    String occupied = "";
    int blockNumber;
    float authority;
    float suggestedSpeed;
    String blockOpenClose;
    int position = 1;
    int switchNumber = 0;


    public Properties(int blockNumber, String occupied, String blockOpenClose, float suggestedSpeed, float authority, int switchNumber, int position)  {
        this.blockNumber = blockNumber;
        this.occupied = occupied; 
        this.authority = authority;
        this.suggestedSpeed = suggestedSpeed;
        this.blockOpenClose = blockOpenClose; 
        this.switchNumber = switchNumber;
        this.position = position;
    }

    public int getBlockNumber() {return blockNumber;};
    public String getOccupied() {return occupied;};
    public float getAuthority() {return authority;};
    public float getSuggestedSpeed() {return suggestedSpeed;};
    public String getBlockOpenClose() {return blockOpenClose;};
    public int getPosition() {return position;};
    public int getSwitchNumber() {return switchNumber;};
    
    public void setBlockNumber(int blockNumber) {this.blockNumber = blockNumber;};
    public void setOccupied(String occupied) {this.occupied = occupied;};
    public void setAuthority(int authority) {this.authority = authority;};
    public void setSuggestedSpeed(int suggestedSpeed) {this.suggestedSpeed = suggestedSpeed;};
    public void setBlockOpenClose(String blockOpenClose) {this.blockOpenClose = blockOpenClose;};
    public void setPosition(int position) {this.position = position;};
    public void setSwitchNumber(int switchNumber) {this.switchNumber = switchNumber;};


}
