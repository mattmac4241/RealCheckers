import java.net.*;
import java.io.*;

public class permDB {

	static User[] ulist = new User[50];
	
	
	public static void update(User additive){
		int le = ulist.length;
		String name = additive.getName();
		boolean exists = false;
		for (int i = 0; i < le; i++){
			if (ulist[i].getName().equals(name)){
				exists = true;
				ulist[i].bulkWon(additive.getWins());
				ulist[i].bulkLoss(additive.getLosses());
				return;
			}
			else if (ulist[i].getID() == 0 && exists == false){
				ulist[i] = additive;
				return;
			}
		}
	}
	
	public static void saveUsers() {
		int le = ulist.length;
		try {
			PrintWriter writer = new PrintWriter("userList.txt", "UTF-8");
			for (int i = 0; i < le; i++){
				if (ulist[i].getID() != 0){
					writer.println(ulist[i].getID() + " " + ulist[i].getName() + " " 
							+ ulist[i].getWins() + " " + ulist[i].getLosses());
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public static void readUsers() {
		try (BufferedReader br = new BufferedReader(new FileReader("userList.txt"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       String[] sLine = line.split(" ");
		       User noob = new User();
		       noob.setID(Integer.parseInt(sLine[0]));
		       noob.setName(sLine[1]);
		       noob.bulkWon(Integer.parseInt(sLine[2]));
		       noob.bulkLoss(Integer.parseInt(sLine[3]));
		       update(noob);
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
