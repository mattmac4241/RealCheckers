
public class User {

	
	private int ID;
	private int connectedTo;

	public User(int id){
		this.ID = id;
		this.connectedTo = -1;
	}
	
	
	public int getID(){
		return ID;
	}
	public int getConnected(){
		return connectedTo;
	}
	public void conenctTo(int cId){
		this.connectedTo = cId;
	}
}
