import java.applet.Applet;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class Game {
	static int RED = 1;
	static int BLACK = 3;
	
	Checkers c;

	// Start the game for the first player
	public void startPlayer1(){
		c = new Checkers(RED);
		startGame();
	}
	
	// Start the game for the second player
	public void startPlayer2(){
		c = new Checkers(BLACK);
		startGame();
	}

	//Add the game to the frame
	private void startGame(){
		
		SwingUtilities.invokeLater(new Runnable(){
	        @Override
	        public void run(){
	            JFrame frame = new JFrame();
	            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	            frame.getContentPane().add(c);
	            frame.pack();
	            frame.setVisible(true);
	        }
	    });

	}
	// Make a move from another player
	public  void makeMove(CheckersMove m){
		c.board.doMakeMove(m);
     }
	
	//Get the current players move
	public CheckersMove getMove(){
		CheckersMove move = c.board.getMove();
		if(move != null) return move;
		else return null;
		
	}
	public int moveN(){
		return c.board.moves.size();
	}
	
	
	// Turn a String that says Move: () into a Checkers move
	public CheckersMove createMove(String s){
		String s1 =  s.substring(6, 7);
		String s2 =  s.substring(8, 9);
		String s3 =  s.substring(10, 11);
		String s4 =  s.substring(12, 13);
		int x1 = Integer.valueOf(s1);
		int x2 = Integer.valueOf(s2);
		int x3 = Integer.valueOf(s3);
		int x4 = Integer.valueOf(s4);
		CheckersMove m = new CheckersMove(x1,x2,x3,x4);
		return m;
		
		
	}
	
	}
	

