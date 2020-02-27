package src.track_controller;

public class PLC {
    private String id = null;
    private StringBuilder text;
	public PLC(StringBuilder text){
        this.text = text;
	}
	public PLC(String id){
		this.id = id;
	}
	public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public void parseAndCompile(){
    	
    }
}