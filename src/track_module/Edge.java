package src.track_module;

public class Edge {
    private Block block;
    private Boolean connected;

    public Edge( Block block, Boolean connected) {
        this.block= block;
        this.connected= connected;
    }
    public Block getBlock(){return block;};
    public Boolean getConnected(){return connected;};
}