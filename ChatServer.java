import java.net.*;
import java.sql.*;
import java.io.*;
public class ChatServer implements Runnable{
	private ChatServerThread clients[] = new ChatServerThread[50];
	private ServerSocket server = null;
	private Thread thread = null;
	private int clientCount = 0;
	private User user[] = new User[50];
	private int cCount = 0;
	
	
	public ChatServer(int port) throws ClassNotFoundException{
		try{
			System.out.println("Binding to port " + port + " , please wait");
			server = new ServerSocket(port);
			System.out.println("Server started " + server);
			start();
			}
		catch(IOException e){
			System.out.println(e.getMessage());
			}
		}
	public void run(){
		while(thread!= null){
			try{
				System.out.println("Waiting for thread");
				addThread(server.accept());
				
			}
			catch(IOException e){
				System.out.println(e.getMessage());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void start() throws ClassNotFoundException{
		if(thread == null){
			thread = new Thread(this);
			thread.start();

			}
		}
	public void stop(){
		if(thread != null){
			thread.interrupt();
			thread = null;
		}
	}
	private int findClient(int ID){
		for(int i =0; i < clientCount;i++){
			if(clients[i].getID() == ID){
				return i;
			}
		}
		return -1;

	}
	
	//Get all clients
	public String getClients(){
		String c = "People connected: " + "\n";
		if(clientCount <= 1){
			return "No one else is connected";
		}
		else{
			for(int i = 0; i < clientCount-1;i++){
				c += clients[i].getID() + "\n";
			}
			
			return c;
		}
	
	}
	
	
	
	
	public synchronized void handle(int ID,String input){
		if(input.equals(".bye")){
			clients[findClient(ID)].send(".bye");
			remove(ID);
		}
		else if(input.equals("Get connected clients")){
			clients[findClient(ID)].send(getClients());
		}
		else if(input.contains("Connect to: ")){
			String id = input.substring(12,input.length());
			System.out.println(id);
			int toId = Integer.valueOf(id);
			addConnections(ID,toId);
			
			}
		
		else{
			User u = getUser(ID);
			if(isConnected(ID) == true){
				clients[findClient(u.getConnected())].send(ID + ": " +  input);
			}
//			for(int i = 0; i < clientCount;i++){
//				clients[i].send(ID + ": " + input);
//			}
		}
	}
	//Connect user to another user
	public void addConnections(int id,int cId){
		if(findClient(cId) == -1){
			clients[findClient(id)].send("Not a valid user");
		}
		else if(isConnected(id) == false && isConnected(cId) == false){
			user[cCount] = new User(id);
			user[cCount].conenctTo(cId);
			cCount+=1;
			user[cCount] = new User(cId);
			user[cCount].conenctTo(id);
			cCount+=1;
		}
		else{
			clients[findClient(id)].send("User already Connected");
		}
		
		
	}
	// See if user is already connected to someone
	public boolean isConnected(int id){
		for(int i = 0; i < cCount;i++){
			if(user[i].getID() == id){
				return true;
			}
		}
		return false;
	}
	// Get user with certain ID
	public User getUser(int id){
		for(int i = 0; i < cCount;i++){
			if(user[i].getID() == id){
				return user[i];
			}
		}
		return null;
	}
	
	// Remove thread when Client leaves
	public synchronized void remove(int ID){
		int pos = findClient(ID);
		if(pos >= 0){
			{  ChatServerThread toTerminate = clients[pos];
	         System.out.println("Removing client thread " + ID + " at " + pos);
	         if (pos < clientCount-1)
	            for (int i = pos+1; i < clientCount; i++)
	               clients[i-1] = clients[i];
	         clientCount--;
	         try
	         {  toTerminate.close(); }
	         catch(IOException ioe)
	         {  System.out.println("Error closing thread: " + ioe); }
	         toTerminate.stop(); }
		}
	}
	   // Add thread when client joins
	   private void addThread(Socket socket) throws ClassNotFoundException
	   {  if (clientCount < clients.length)
	      {  System.out.println("Client accepted: " + socket);
	         clients[clientCount] = new ChatServerThread(this, socket);
	         
	         try
	         {  clients[clientCount].open(); 
	            clients[clientCount].start();  
	            clientCount++; }
	         catch(IOException ioe)
	         {  System.out.println("Error opening thread: " + ioe); } }
	      else
	         System.out.println("Client refused: maximum " + clients.length + " reached.");
	   }
	
	   public static void main(String args[]) throws NumberFormatException, ClassNotFoundException
	   {  ChatServer server = null;
	      if (args.length != 1)
	         System.out.println("Usage: java ChatServer port");
	      else
	         server = new ChatServer(Integer.parseInt(args[0]));
	   }
	
	
	}

