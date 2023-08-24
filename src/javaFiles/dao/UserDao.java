package javaFiles.dao;

import java.sql.SQLException;

import javaFiles.model.User;

/**
 * A data access object (DAO) is a pattern that provides an abstract interface 
 * to a database or other persistence mechanism. 
 * the DAO maps application calls to the persistence layer and provides some specific data operations 
 * without exposing details of the database. 
 */
public interface UserDao {
	void setup() throws SQLException;
	User getUser(String username, String password) throws SQLException;
	User createUser(String firstname, String lastname, String username, String password) throws SQLException;
//	public void updateProfilePhoto(String username, String filePath);
	public void updateName(String currentUsername, String NewFirstname, String NewLastname);
}
