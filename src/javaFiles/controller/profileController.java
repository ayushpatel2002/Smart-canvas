package javaFiles.controller;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javaFiles.model.Model;
public class profileController {

	@FXML
	private Button close;

	@FXML
	private TextField firstname;

	@FXML
	private TextField lastname;

	@FXML
	private Button ok;

	@FXML
	private ImageView profileImg;

	@FXML
	private Label userName;
	private Model model;
	private Stage stage;
	private Stage parentStage;
	public profileController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}
	
	@FXML
	public void initialize() {
//		We set placeholder of textfield to the current firstname and lastname 
		firstname.setPromptText(model.getCurrentUser().getFirstname());
		lastname.setPromptText(model.getCurrentUser().getLastname());
		userName.setText(model.getCurrentUser().getUsername());
	}
	
	@FXML
	public void changeNames() {
//		We call UpdateName method with parameter as input from textfields
		String newName = firstname.getText();
		String newLName = lastname.getText();
//		If fields are not empty, we change the names
		if(!newLName.isEmpty() && !newName.isEmpty()) {
			model.getCurrentUser().setFirstname(newName);
			model.getCurrentUser().setLastname(newLName);
			model.UpdateName(model.getCurrentUser().getUsername(), newName, newLName);
			firstname.setPromptText(newName);
			lastname.setPromptText(newLName);
		}else {
//			else we set the same name
			model.getCurrentUser().setFirstname(model.getCurrentUser().getFirstname());
			model.getCurrentUser().setLastname(model.getCurrentUser().getLastname());
		}
		firstname.setPromptText(model.getCurrentUser().getFirstname());
		lastname.setPromptText(model.getCurrentUser().getLastname());
		stage.close();
	}
	
	public void showStage(Parent root) {
		Scene scene = new Scene(root,295,400);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Profile");
		stage.show();
	}

}


