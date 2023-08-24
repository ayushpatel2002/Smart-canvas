package javaFiles.model;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javafx.scene.image.Image;

public class User {
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private Model model = new Model();
	private String FilePath = "Images/DefaultPhoto.jpeg";
	
	private boolean isPremiumUser = false;

	public boolean isPremiumUser() {
		return isPremiumUser;
	}

	public void setPremiumUser(boolean isPremiumUser) {
		this.isPremiumUser = isPremiumUser;
	}

	public String getFilePath() {
		return FilePath;
	}

	public void setFilePath(String filePath) {
		FilePath = filePath;
	}

	public User() {
	}
	
	public User(String firstname, String lastname, String username, String password) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
	}
	
//	we first call a user object, and if we get correct input from user for username and password, we return a user else we return null
	public User login(String username, String password){
		User user;
		try {
			user = model.getUserDao().getUser(username, HashPassword(password));
			return user;
		} catch (SQLException e) {
//			catching SQLException
			e.printStackTrace();
		}
		return null;
	}
	
	
	public User SignUp(String firstname, String lastname, String username, String password) throws SQLException, WeakPasswordException{
		User user;
		if(password.length() > 5) {
			try {
				user = model.getUserDao().createUser(firstname, lastname, username, HashPassword(password));
				return user;
			} catch (SQLException e) {
				e.getMessage();
//				e.printStackTrace();
			}
		}
		else {
			throw new WeakPasswordException("Password too short");
		}
		return null;
	}
	
//	getters and setters for private attributes

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
//	Hashing the password
	public String HashPassword(String password) {
		String generatedPassword = null;
		try 
	    {
	      // Create MessageDigest instance for MD5
	      MessageDigest md = MessageDigest.getInstance("MD5");

	      // Add password bytes to digest
	      md.update(password.getBytes());

	      // Get the hash's bytes
	      byte[] bytes = md.digest();

	      // This bytes[] has bytes in decimal format. Convert it to hexadecimal format
	      StringBuilder sb = new StringBuilder();
	      for (int i = 0; i < bytes.length; i++) {
	        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	      }

	      // Get complete hashed password in hex format
	      generatedPassword = sb.toString();
	    } catch (NoSuchAlgorithmException e) {
	      e.printStackTrace();
	    }
	    return generatedPassword;
	}
	
}
