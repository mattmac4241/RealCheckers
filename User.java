
public class User {

	
	private int ID;
	private int connectedTo;
	public boolean turn = false;
	private String name;
	private int wins;
	private int losses;

	public User(){
		this.connectedTo = -1;
		this.wins = 0;
		this.ID = 0;
		this.losses = 0;
	}
	//Set users ID
	public void setID(int id){
		this.ID = id;
	}
	//Add a win to the user
	public void won(){
		this.wins +=1;
	}
	//Add a loss to the user
	public void loss(){
		this.losses +=1;
	}
	
	//Return User's losses
	public int getLosses(){
		return this.losses;
	}
	
	//Get users wins
	public  int getWins(){
		return wins;
	}
	
	//Set users name
	public void setName(String n){
		name = n;
		
	}
	
	//Get users name
	public String getName(){
		return name;
	}
	
	//Get user's id
	public int getID(){
		return ID;
	}
	
	//Get the the id of the connected user
	public int getConnected(){
		return connectedTo;
	}
	
	//Set the id of the connected user
	public void conenctTo(int cId){
		this.connectedTo = cId;
	}
	
	//Set turn to true
	public void turn(){
		turn = true;
	}
	//Is opponents turn so sets turn to false
	public void oTurn(){
		turn = false;
	}
	
	//Get the player's turn
	public boolean getTurn(){
		return turn;
	}
	
	public void resetId(){
		this.ID = 0;
	}
}
