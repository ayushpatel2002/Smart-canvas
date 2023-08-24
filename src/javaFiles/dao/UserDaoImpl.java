package javaFiles.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javaFiles.model.User;

public class UserDaoImpl implements UserDao {
	private final String TABLE_NAME = "Users";

	public UserDaoImpl() {
	}

	@Override
	public void setup() throws SQLException {
		try (Connection connection = Database.getConnection();
				Statement stmt = connection.createStatement();) {
			String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (firstname STRING NOT NULL, " + " lastname STRING NOT NULL, " + " username VARCHAR(10) NOT NULL,"
					+ " password VARCHAR(8) NOT NULL," + "PRIMARY KEY (username))";
			
			stmt.executeUpdate(sql);
		} 
	}
// We implement getUser method by writing a sql query and returning a user if correct
	@Override
	public User getUser(String username, String password) throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ? AND password = ?";
		try (Connection connection = Database.getConnection(); 
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					User user = new User();
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setFirstname(rs.getString("firstname"));
					user.setLastname(rs.getString("lastname"));
					return user;
				}
				return null;
			} 
		}
	}
	

//	We create user by using insert query and return a new created user
	@Override
	public User createUser(String firstname, String lastname, String username, String password) throws SQLException {
		String sql = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?)";
		try (Connection connection = Database.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, firstname);
			stmt.setString(2, lastname);
			stmt.setString(3, username);
			stmt.setString(4, password);
			stmt.executeUpdate();
			return new User(firstname, lastname, username, password);
		} 
	}

//	We write update query for updating firstname and lastname
	@Override
	public void updateName(String currentUsername, String NewFirstname, String NewLastname) {
		String sql = "UPDATE " + TABLE_NAME + " SET firstname = ?, lastname = ? WHERE username = ?";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
				stmt.setString(1, NewFirstname);
				stmt.setString(2, NewLastname);
				stmt.setString(3, currentUsername);
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
