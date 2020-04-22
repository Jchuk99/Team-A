package src.track_module;

import java.util.UUID;

public class Edge {
    private Block block;
    private Boolean connected;
    private UUID id;

    public Edge( Block block, Boolean connected) {
        id = UUID.randomUUID();
        this.block = block;
        this.connected = connected;
    }
    public Block getBlock(){return block;};
    public Boolean getConnected(){return connected;};
    public void setConnected(Boolean connected){this.connected = connected;};
}