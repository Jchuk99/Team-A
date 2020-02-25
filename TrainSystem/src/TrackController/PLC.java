public class PLC{
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
}