package src.track_module;

public class Edge {
    private Block block;
    private Boolean connected;
    private Boolean originallyConnected = false;
    private Boolean isSwitch = false;

    public Edge( Block block, Boolean connected) {
        this.block = block;
        if(connected){
            originallyConnected = true;
        }
        this.connected = connected;
        
        
    }
    public Block getBlock(){return block;}
    public Boolean getOriginalConnection(){return originallyConnected;}
    public Boolean getConnected(){return connected;}
    public void setConnected(Boolean connected){this.connected = connected;}
    public Boolean getIsSwitch() {return isSwitch;}
    public void setIsSwitch(Boolean isSwitch) {this.isSwitch = isSwitch;}
}