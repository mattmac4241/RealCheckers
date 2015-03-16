import java.net.*;
import java.io.*;

public class permDB {

	public volatile User[] ulist = new User[50];
	 
	public permDB(){
		readUsers();
	}

	public void update(User additive, boolean what)  {
		if (what == false){
			readUsers();
		}
		int le = ulist.length;
		String name = additive.getName();
		//boolean exists = false;
		for (int i = 0; i < le; i++){
			//System.out.println(ulist[0]);
			if (ulist[i] != null && name.equals(ulist[i].getName())){
				//exists = true;
				//ulist[i].setPassword(additive.getPassword());
				ulist[i].bulkWon(additive.getWins());
				ulist[i].bulkLoss(additive.getLosses());
				break;
			}
			else if (ulist[i] == null){
				ulist[i] = additive;
			//	System.out.println(ulist[i].getName() + " " + i + le);
				break;
			}
		}
		//	System.out.println("HERE " + additive.getName() + ulist[0].getName());
	}

	public User pwMatch (String name, String input) {
		readUsers();
		
		int le = ulist.length;
		
		for (int i = 0; i < le; i++){
			if (ulist[i] != null && ulist[i].getName().equals(name)){
				if (ulist[i].getPassword().equals(input)){
					return ulist[i];
				}
			}
		}
		return null;
	}
	
	public boolean userExists(String name) {
		readUsers();
		int le = ulist.length;
		
		for (int i = 0; i < le; i++){
			if (ulist[i] != null && ulist[i].getName().equals(name)){
					return true;
			}
		}
		return false;
	}
	

	
	public void saveUsers()  {
		int le = ulist.length;

		try {
			PrintWriter writer = new PrintWriter("userList.txt", "ASCII");
			if (ulist[0] != null){
				for (int i = 0; i < le; i++){
					if (ulist[i] != null){
						writer.println(ulist[i].getID() + " " + ulist[i].getName() + " " + ulist[i].getPassword() + " " +
								+ ulist[i].getWins() + " " + ulist[i].getLosses());
					}
				}
			}
			writer.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
	}

	public void readUsers() {
		File file = new File("userList.txt");
		try {
			BufferedReader file_reader = new BufferedReader( new FileReader(file) );
			String line;
			

			while ( (line = file_reader.readLine() ) != null) {
				User noob = new User();
				String[] sLine = line.split(" ");
				noob.setID(Integer.valueOf(sLine[0]));
				noob.setName(sLine[1]);
				noob.setPassword(sLine[2]);
				noob.bulkWon(Integer.valueOf(sLine[3]));
				noob.bulkLoss(Integer.valueOf(sLine[4]));

				update(noob, true);
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
