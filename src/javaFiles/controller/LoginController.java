package javaFiles.controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import javaFiles.model.InvalidCredentialsException;
import javaFiles.model.Model;
import javaFiles.model.User;

public class LoginController {
//	Declare private attributes corresponding to the view 
	@FXML
	private TextField usernameLogin;
	@FXML
	private PasswordField passwordLogin;
	@FXML
	private Label statusLabel;
	@FXML
	private Button submitLogin;
	@FXML
	private Button signup;

	private Model model;
	private Stage stage;
	private User u = new User();
	
	public LoginController(Stage stage, Model model) {
		this.stage = stage;
		this.model = model;
	}
	@FXML
	public void initialize() throws InvalidCredentialsException, SQLException{	
		submitLogin.setOnAction(event -> {
//			When button is pressed, we check for various things
			if (!usernameLogin.getText().isEmpty() && !passwordLogin.getText().isEmpty()) {
//				First if the text fields are empty then error message of is shown to the label
//				First we declare a user
				User user;
				try {
//					By calling login method, the user is initialised to currentUser
					user = u.login(usernameLogin.getText(), passwordLogin.getText());
					
					if (user != null) {
//						If the user is not null, currentuser is set to the newly initialised user  
						model.setCurrentUser(user);
//						sucessfull login message is shown
						System.out.println("Login Successful");
						try {
//							If login is sucessful, view of BoardView.fxml is shown
							FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BoardView.fxml"));
							
							// Customize controller instance
							Callback<Class<?>, Object> controllerFactory = param -> {
								return new BoardController(stage, model);
							};

							loader.setControllerFactory(controllerFactory);
							Parent root = loader.load();
							
							BoardController boardController = loader.getController();
							boardController.showStage(root);
							
							statusLabel.setText("");
							usernameLogin.clear();
							passwordLogin.clear();
//							We close stage upon successful login
							stage.close();
						} catch (IOException e) {
//							We catch IOException
							e.printStackTrace();
							statusLabel.setText(e.getMessage());
						}
					} else {
//						If user is still null, we throw a custom Exception that is inavlid credential exception with message
						statusLabel.setText("Wrong username or password");
						statusLabel.setTextFill(Color.RED);
						throw new InvalidCredentialsException("Wrong username or password");
					}
//					We catch InvalidCredential Exception
				} catch (InvalidCredentialsException e) {
					e.printStackTrace();
				}
				
			} else {
//				label is shown with error message if fields are left empty
				statusLabel.setText("Empty username or password");
				statusLabel.setTextFill(Color.RED);
			}
			usernameLogin.clear();
			passwordLogin.clear();
		});
		
		signup.setOnAction(event -> {
//			In case of SignUp button pressed, we show view of RegistrationPage.fxml
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RegistrationPage.fxml"));
				
				// Customize controller instance
				Callback<Class<?>, Object> controllerFactory = param -> {
					return new SignupController(stage, model);
				};

				loader.setControllerFactory(controllerFactory);
				Parent root = loader.load();
				
				SignupController signupController = loader.getController();
				signupController.showStage(root);
				
				statusLabel.setText("");
				usernameLogin.clear();
				passwordLogin.clear();
				
				stage.close();
			} catch (IOException e) {
				e.printStackTrace();
				statusLabel.setText(e.getMessage());
			}
			});
	}
	
	public void showStage(Parent root) {
		Scene scene = new Scene(root, 600, 400);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Welcome");
		stage.show();
	}
}

