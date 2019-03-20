package system;

public class User {
	private String lname;
	private String fname;
	private String email;
	private String uname;
	private String psswd;
	
	public User(
			String lname, String fname,
			String email, String uname,
			String psswd) {
		this.lname = lname;
		this.fname = fname;
		this.email = email;
		this.uname = uname;
		this.psswd = psswd;
	}
	
	@Override
	public String toString () {
		return fname + " " + lname + " " + email + " " 
	    		+ uname  + " " + psswd;
	}
	
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPsswd() {
		return psswd;
	}
	public void setPsswd(String psswd) {
		this.psswd = psswd;
	}
}
