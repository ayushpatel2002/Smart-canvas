package javaFiles.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import javaFiles.model.Model;
import javaFiles.model.User;
import javaFiles.model.WeakPasswordException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SignupController {
	@FXML
	private TextField fname;
	@FXML
	private TextField lname;
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private Button submitButtonRego;
	@FXML
	private Button close;
	@FXML
	private Label status;
	@FXML
	private ImageView ProfilePhoto;


	User user = new User();


	private Stage stage;
	private Stage parentStage;
	private Model model;
	private User u = new User();
//	Constructor
	public SignupController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}

	@FXML
	public void initialize() throws WeakPasswordException, SQLException{
//		On submit button clicked, we check for some things
		submitButtonRego.setOnAction(event -> {
//			If the textfields are not empty
			if (!username.getText().isEmpty() && !password.getText().isEmpty()) {
//				If the password length is less than 5, we throw a new weakPasswordException
				if(password.getText().length() < 5) {
					try {
//						And set label to error message 
						status.setText("Weak Password \nPlease try longer password");
						status.setTextFill(Color.RED);
						throw new WeakPasswordException("Weak Password \nPlease try longer password");
					}
//					we catch weakPasswordException
					catch(WeakPasswordException e) {
						System.out.println(e.getMessage());
						e.printStackTrace();
						e.getMessage();
					}
				}
				else {
					User user;
//					If fields are not empty, we instantiate user to newly created user object
					try {
						user = model.getUserDao().createUser(fname.getText(), lname.getText(), username.getText(), u.HashPassword(password.getText()));
						if (user != null) {
//							If user is not null, we set label to a message
							status.setText("Created " + user.getUsername());
							status.setTextFill(Color.GREEN);
						} else {
//							In case user is still null that means there is SQLException for primary key
//							So we show error message that uswer already taken
							status.setText("User Already Taken \n Please try other Username");
							status.setTextFill(Color.RED);
						}
					} catch (SQLException e) {
						status.setText("Username Already Taken \n Please try other Username");
						status.setTextFill(Color.RED);
						e.printStackTrace();
						System.out.println(e.getMessage());
						;
					} 
				}
				
			} else {
//				In case empty textfield we set label to error message 
				status.setText("Empty username or password");
				status.setTextFill(Color.RED);
			}
		});

		close.setOnAction(event -> {
			stage.close();
			parentStage.show();
		});
	}
	
	@FXML
	private void ChangeProfilePhoto(ActionEvent event) throws IOException {
		ChangeImage(ProfilePhoto);
	}
	public void ChangeImage(ImageView ProfilePhoto) throws IOException {
		Image image;
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("*.jpeg", "*.png", "*.jpg"));
		fileChooser.setTitle("Select Profile Photo");
		File selectedFile = fileChooser.showOpenDialog(new Stage());
//		String userDirectoryString = System.getProperty("user.home");
//		File userDirectory = new File(userDirectoryString);
		if(selectedFile != null) {
			try {
				InputStream fileInputStream = new FileInputStream(selectedFile);
//				FilePath = file.toURI().toURL().toString();
				image = new Image(fileInputStream);
//				u.setProfileImage(image);
				ProfilePhoto.setImage(image);
			}
			catch(FileNotFoundException e){
				System.out.println("File Not Found");
			}

		}
	}
	
	public void showStage(Parent root) {
		Scene scene = new Scene(root, 420, 630);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Sign up");
		stage.show();
	}
}
