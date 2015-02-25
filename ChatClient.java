import java.net.*;
import java.io.*;

public class ChatClient implements Runnable
{  private Socket socket              = null;
   private Thread thread              = null;
   private DataInputStream  console   = null;
   private DataOutputStream streamOut = null;
   private ChatClientThread client    = null;
   Game g = null;
   boolean playing;
   int number;

   public ChatClient(String serverName, int serverPort)
   {  System.out.println("Establishing connection. Please wait ...");
      try
      {   this.playing = false;
    	  socket = new Socket(serverName, serverPort);
         System.out.println("Connected: " + socket);
         start();
      }
      catch(UnknownHostException uhe)
      {  System.out.println("Host unknown: " + uhe.getMessage()); }
      catch(IOException ioe)
      {  System.out.println("Unexpected exception: " + ioe.getMessage()); }
   }
   public void run()
   {  while (thread != null) 
	   while(playing == false){
		   try{
				streamOut.writeUTF(console.readLine());
                streamOut.flush();
		   }
		   catch(IOException ioe)
	         {  System.out.println("Sending error: " + ioe.getMessage());
	            stop();
	         }
	   }
  
	   
   }
   public void start() throws IOException
   {  console   = new DataInputStream(System.in);
      streamOut = new DataOutputStream(socket.getOutputStream());
      if (thread == null)
      {  client = new ChatClientThread(this, socket);
         thread = new Thread(this);                   
         thread.start();
      }
   }
   
   public void stop()
   {  if (thread != null)
      {  thread.stop();  
         thread = null;
      }
      try
      {  if (console   != null)  console.close();
         if (streamOut != null)  streamOut.close();
         if (socket    != null)  socket.close();
      }
      catch(IOException ioe)
      {  System.out.println("Error closing ..."); }
      client.close();  
      client.stop();
   }
   
   //Get output 
   public DataOutputStream getOuptuStream(){
	   return streamOut;
   }
   
  // If the player is the first player start as player 1
   public void startGameP1(){
	   this.playing = true;	   
	   g = new Game();
	   g.startPlayer1();
	   number = 1;
	  
		 
		   if(g.c.board.currentPlayer == 1){
			   getMove();
		   } 
   }
   
   //If the player is the second player start as player 2
   public void startGameP2(){
	   this.playing = true;
	   g = new Game();
	   g.startPlayer2();
	   number = 3;
	   
	  // getMove();
   }
   
   public static void main(String args[])
   {  ChatClient client = null;
      if (args.length != 2)
         System.out.println("Usage: java ChatClient host port");
      else
         client = new ChatClient(args[0], Integer.parseInt(args[1]));
   }
   
   //What should happen when the Client recieves a certain message
   public void handle(String msg) {
	   if (msg.equals(".bye")) { 
		   System.out.println("Good bye. Press RETURN to exit ...");
        	stop();
		}
	   else if(msg.equals("play 1")){
		   startGameP1();
	   }
	   else if(msg.equals("play 2")){
		  System.out.println("PLAY2 STARTED");
		   startGameP2();
		   
			
		   
	   }
	   
	   //When the player receives a move and should make the move and then get the players next move
	   else if(msg.substring(0, 6).equals("MOVE: ")){
		   CheckersMove m  = g.createMove(msg);
		   g.makeMove(m);
			   if(g.c.board.currentPlayer == number){  // THIS IS WHERE THE PROBLEM IS GET IT TO CONTINUE TO READ IF STILL TURN
				   getMove();
			   }
			   System.out.println(g.c.board.currentPlayer);

			  
		   }
     else
        System.out.println(msg);
  }	
   
   //Get the players turn and write it to the output and send it to the other player
   public void getMove(){
	   CheckersMove m = null;
	   String x = "";
	   while (g.getMove() == null){
		   System.out.print("");
		   m = g.getMove();
		   if(m != null){
			   x = m.toString();
			 
			   break;
		   }

	 }
	   System.out.println(x);
		   try {
				this.getOuptuStream().writeUTF(x);
				this.getOuptuStream().flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
	   }
	  
	   
   }
  
   


