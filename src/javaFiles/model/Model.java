package javaFiles.model;

import java.sql.SQLException;

import javaFiles.dao.UserDao;
import javaFiles.dao.UserDaoImpl;

public class Model {
	private UserDao userDao;
	private User currentUser; 
	
	public Model() {
		userDao = new UserDaoImpl();
	}
//	setup for preparing connection
	public void setup() throws SQLException {
		userDao.setup();
	}
//	returns userDao
	public UserDao getUserDao() {
		return userDao;
	}
	
	public User getCurrentUser() {
		return this.currentUser;
	}
	
	public void setCurrentUser(User user) {
		currentUser = user;
	}
	
//	calling update method from implemented userDao to be called later everywhere
	public void UpdateName(String currentUsername, String NewFirstname, String NewLastname) {
		userDao.updateName(currentUsername, NewFirstname, NewLastname);
	}
}
