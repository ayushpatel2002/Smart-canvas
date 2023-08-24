package test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import javaFiles.model.Model;
import javaFiles.model.User;
import javaFiles.model.WeakPasswordException;

public class UserTest {
	User user;
	Model model;
	
	@Before
	public void setup() {
		user = new User();
		model = new Model();
	}

	
	@Test
	public void HashPasswordTest() {
		String expectedOutput = "9190fae34d628bf803134260333ebe21"; //Hash of "ayushpatel" using online Hash converter for MD5
		String actualOutput = user.HashPassword("ayushpatel");
		assertEquals(expectedOutput, actualOutput);
	}
	
	@Test
	public void HashPasswordTestFail() {
		String expectedOutput = "9190fae34d628bf803134260333ebe21"; //Hash of "ayushpatel" using online Hash converter for MD5
		String actualOutput = user.HashPassword("ayushpatel1");
		assertNotEquals(expectedOutput, actualOutput);
	}
	
	@Test
	public void LoginTestPass() throws SQLException {
//		As login method returns a user if correct username and password are entered as its parameter, I have created a expectedUser.
//		If both the users are same, then its firstname and lastname should match. for actualuser, lastname and firstname should come from database.
//		If the test passes, then the correct user is returned
		User actualUser = user.login("ayush123", "ayush123");
		String actualUserFname = actualUser.getFirstname();
		String actualUserLname = actualUser.getLastname();
		User expctedUser = new User("Ayush", "Patel", "ayush123", "691c720c3152c8686e0ff812a767c552");
		assertEquals(actualUserFname, expctedUser.getFirstname());
		assertEquals(actualUserLname, expctedUser.getLastname());
	}

	@Test
	public void LoginTestFail() throws SQLException{
//		According to the login method, if the correct username and password are entered as its parameters, it should return a user but
//		if not then it should return null.
//		So I put wrong password for a username and made it assertequals with null as it should return a null in case of wrong credentials

		User actualUser = user.login("ayush123", "ayush1234");
		assertEquals(actualUser, null);
	}
	
	
	@Test(expected = WeakPasswordException.class)
//	If the password length is shorter than 5 characters, it is expected to throw weakpassword exception. 
	public void SignUpWeakPasswordExceptionTest() throws WeakPasswordException, SQLException{
	    User testUser = user.SignUp("Ayush", "Patel", "1", "123");
	}
	
	@Test
	public void SignupTest() throws SQLException, WeakPasswordException {
//		This method tests if SignUp method works properly or not. First we signup with appropriate credentials, and then login with newly username and password.
//		Both the method should return same user and should have same information such as firstname and lastname
		
//		If you run the test by putting a new username, the test will pass otherwise it will throw SQLException
		User testUserBySignUp = user.SignUp("Ayush", "Patel", "11111", "123456");
		User testUserByLogin = user.login("1111", "123456");
		assertEquals(testUserBySignUp.getFirstname(), testUserByLogin.getFirstname());
		assertEquals(testUserBySignUp.getLastname(), testUserByLogin.getLastname());
	}
	
}
