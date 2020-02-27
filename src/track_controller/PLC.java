<<<<<<< HEAD
public class PLC{
=======
package src.track_controller;

public class PLC {
>>>>>>> 302e1f870b13eb86233849ff60d867185de0f24a
	private String id = null;
	public PLC(String[] plcTokens){

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