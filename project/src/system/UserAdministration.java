package system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class UserAdministration {
	private static final String USERFILE = "users.txt";
	
	private static List<User> users = new ArrayList<>();
	private static String filename = null;
	
	private UserAdministration() {}
	
	private static void init() {
		StringBuilder filename = new StringBuilder();
		filename.append(new File(".").getAbsolutePath());
		filename.deleteCharAt(filename.lastIndexOf("."));
		filename.append(USERFILE);
		UserAdministration.filename = filename.toString();
	}
	
	public static void load() throws IOException {
		if(filename == null) init();
		
		try(BufferedReader buffer = new BufferedReader(
				new FileReader(filename))) {
			String line = null;
			while((line = buffer.readLine()) != null) {
				String[] splitter = line.split(" ");
				users.add(new User(
						splitter[0],
						splitter[1],
						splitter[2],
						splitter[3],
						splitter[4]
						));
			}
		}
	}
	
	public static void store() throws IOException {
		if(filename == null) init();
		try(PrintWriter buffer = new PrintWriter(filename)) {
			users.forEach(buffer::println);
		}
	}
	
	public static boolean login(String uname, String psswd) {
		for(User user : users)
			if(uname.equals(user.getUname()) && psswd.equals(user.getPsswd()))
				return true;
		return false;
	}
	
	public static boolean register(
			String lname, String fname,
			String email, String uname,
			String psswd
			) {
		
		if (lname == null || fname == null || 
				email == null || uname == null ||
				psswd == null) return false;
		
		for(User user : users)
			if(uname.equals(user.getUname()) 
					|| email.equals(user.getEmail()))
				return false;
		
		users.add(new User(lname, fname, email, uname, psswd));
		return true;
	}
	
	public static User getUser(String uname) {
		for(User user : users)
			if(uname.equals(user.getUname()))
				return user;
		return null;
	}
}
