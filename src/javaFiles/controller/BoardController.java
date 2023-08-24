package javaFiles.controller;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.imageio.ImageIO;
import org.controlsfx.dialog.FontSelectorDialog;
import javaFiles.model.Canvas;
import javaFiles.model.CircleElement;
import javaFiles.model.ImageViewElement;
import javaFiles.model.Model;
import javaFiles.model.RectangleElement;
import javaFiles.model.TextElement;
import javaFiles.model.User;
import javafx.css.Style;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
public class BoardController{
	private User user = new User();
	private Stage stage;
	private Stage parentStage;
	private Model model;
	private Node selectedElement;
	private double originX;
	private double originY;
	
//	The following are the attributes declared private so that we can interact with them upon calling the appropriate function and use onAction
	@FXML
	MenuItem borderColorMenu = new MenuItem();
	
	@FXML
	private ColorPicker colorPicker = new ColorPicker();
	
	@FXML
	private ColorPicker colorPickerForBorder = new ColorPicker();
	
	@FXML
	private ColorPicker textBackgroundColor = new ColorPicker();
	
	@FXML
	private MenuBar borderSettingsMenu = new MenuBar();
	
	@FXML
	private Button enlargeElementBtn = new Button();
	
	@FXML
	private Button shrinkElementBtn = new Button();
	
	@FXML
	private Button selectFontButton = new Button();
	
	@FXML
	private Slider rotateSlider;
	
	@FXML
	private Label zoomLevel = new Label();
	
	@FXML
	private Menu smartCanvas = new Menu();
	
	int numTextElements = 0;
	
    public Node getSelectedElement() {
		return selectedElement;
	}
    
//    We declare a array list to store and keep a track of added elemenets on the board or canvas so that we can later select and unselect them
    private ArrayList<Node> ElementList = new ArrayList<Node>();

	
	private String FilePath;
	
	@FXML
    private BorderPane rootPane;
    
    @FXML
    private BorderPane rootLayout;
	
	@FXML
	private MenuItem about;

	@FXML
	private Button canvasButton;

	@FXML
	private Button cirButton;

	@FXML
	private Button imageButton;

	@FXML
	private Button rectButton;

	@FXML
	private Button textButton;
	
	@FXML
	private Pane canvas = new AnchorPane();
	
	@FXML
	private ImageView profilePhotoOnBoard;
	
	@FXML
	private TextField rotationTextField = new TextField();
	
	@FXML
	private Button rotateButton = new Button();
	
	@FXML
	private TextArea textElementTextArea = new TextArea();
	
	@FXML
	private Button textElementEnter = new Button();
	
	@FXML
	private Label coordinatesOutputLabel = new Label();
	
	@FXML
	private ColorPicker fontColorSelector = new ColorPicker();
	
	@FXML
	private RadioButton textRight = new RadioButton();
	
	@FXML
	private RadioButton textCentre = new RadioButton();
	
	@FXML
	private RadioButton textLeft = new RadioButton();
	
	@FXML
	private Button changeImageOnBoard = new Button();
	
	@FXML
	private Label userInfoLabel = new Label();
	
	@FXML
	private Label elementDimensionsLabel = new Label();
	
	@FXML
	private MenuBar textControlsBtn = new MenuBar();
	
	Border labelBorder = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1)));
	
//  We setSelectedElement and call methods which enables to disable and enable control buttons for respective elements
	public void setSelectedElement(Node selectedElement) {
		this.selectedElement = selectedElement;
		enableAppropriateElements();
	}
	
//	We use intanceof method to know which type of element is selected and enable appropriate buttons which are required tweak and play with selcted elemnts
	public void enableAppropriateElements() {
		if(getSelectedElement() instanceof Label) {
			textElementTextArea.setDisable(false);
			borderSettingsMenu.setDisable(false);
			textElementTextArea.setText(((Label)getSelectedElement()).getText());
			enlargeElementBtn.setDisable(false);
			shrinkElementBtn.setDisable(false);
			colorPicker.setDisable(false);
			textControlsBtn.setDisable(false);
			selectFontButton.setDisable(false);
			textElementEnter.setDisable(false);
		}
		else if(getSelectedElement() instanceof ImageView) {
			changeImageOnBoard.setDisable(false);
			borderSettingsMenu.setDisable(true);
			enlargeElementBtn.setDisable(false);
			shrinkElementBtn.setDisable(false);
			colorPicker.setDisable(true);
			borderSettingsMenu.setDisable(true);
			textElementTextArea.setDisable(true);
			textControlsBtn.setDisable(true);
			selectFontButton.setDisable(true);
			textElementEnter.setDisable(true);
		}
		else if(getSelectedElement() instanceof Shape) {
			borderSettingsMenu.setDisable(false);
			enlargeElementBtn.setDisable(false);
			shrinkElementBtn.setDisable(false);
			colorPicker.setDisable(false);
			borderSettingsMenu.setDisable(false);
			textElementEnter.setDisable(false);
			textElementTextArea.setDisable(true);
			textControlsBtn.setDisable(true);
			selectFontButton.setDisable(true);
			
		}
	}
	
	@FXML
	private Slider zoomCanvas = new Slider();
//	User u = new User();
	Image image = new Image(new File(user.getFilePath()).toURI().toString());
	
//	We declare objects of all ements classes to their methods
	private RectangleElement rectangle = new RectangleElement();
	private CircleElement circle = new CircleElement();
	private TextElement textField = new TextElement();
	private ImageViewElement imgVelement = new ImageViewElement();
	
	
//	Canvas object to access its method
	Canvas cns = new Canvas();
	public BoardController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}
	
	
//	Method for showing a pop up scene for creating a new canvas with required dimensions and color
	@FXML
	public void showPopUpForCanvas() {
		final Stage popUpStage = new Stage();
		GridPane pane = new GridPane(); 
		popUpStage.initModality(Modality.APPLICATION_MODAL);
		pane.setAlignment(Pos.CENTER); 
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5)); 
		pane.setHgap(10); 
		pane.setVgap(10); 
// We add labels and textbox for making use of user input 
		pane.add(new Label("Widht"), 0, 0);
		TextField widthTextField = new TextField();
		pane.add(widthTextField, 1, 0);

		pane.add(new Label("Height"), 0, 1);
		TextField heightTextField = new TextField();
		pane.add(heightTextField, 1, 1);
//		color picker for background color canvas
		pane.add(new Label("Height"), 0, 1);
		ColorPicker colorForCanvas = new ColorPicker();
		pane.add(colorForCanvas, 1, 2);

		Button button = new Button("Create Canvas");
		pane.add(button, 1, 4);
		Label output = new Label();
		pane.add(output, 1, 5);
		Scene scene = new Scene(pane, 300, 200); 
		popUpStage.setTitle("Create Canvas"); 
		popUpStage.setScene(scene); 
		popUpStage.show(); 
		button.setOnAction(e -> {
//			We set on action for button adding new canvas to borderpane in centre of user entered values
//			Using try catch to avoid interuption in the application
			try {
				if(widthTextField.getText() == "" && heightTextField.getText() == "") {
					output.setText("Please Enter value");
					output.setTextFill(Color.RED); 
					}
				else if(Float.parseFloat(widthTextField.getText()) < 400 || Float.parseFloat(heightTextField.getText()) < 400) {
					output.setText("Please enter valid size");
					output.setTextFill(Color.RED);
				} else {
					createNewCanvas(colorForCanvas.getValue() ,Float.parseFloat(widthTextField.getText()), Float.parseFloat(heightTextField.getText()));
					popUpStage.close();
					canvasButton.setDisable(false);
				}
//				We catch NumberFormatException if string is not converted to Float
			}catch(NumberFormatException exe) {
				Label errorLabel = new Label("Please enter only decimal values");
				errorLabel.setTextFill(Color.RED);
				exe.printStackTrace();
				pane.add(errorLabel, 1, 5);
			}


		});
	}
	
//	We show a Pop up for about section
	@FXML
	public void ShowPopUpForAboutSection() {
		final Stage popUpStage = new Stage();
		GridPane pane = new GridPane(); 
		popUpStage.initModality(Modality.APPLICATION_MODAL);
		pane.setAlignment(Pos.CENTER); 
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5)); 
		pane.setHgap(10); 
		pane.setVgap(10); 
//		We set gridpane as Pop up and add label to it
//		In the label we add text to be shown
		pane.add(new Label("Application Version:  22.10.02\n"
				+ "This All new Application lets you create beatiful artefects with a variety of elements to add upon in the canvas\n"
				+ "The new version lets you to subscribe to premium version of this application which allows you to use some finely created Templates"), 0, 0);
		Button button = new Button("Close");
		pane.add(button, 1, 4);
		Scene scene = new Scene(pane, 800, 200); 
		popUpStage.setTitle("About Us"); 
		popUpStage.setScene(scene); 
		popUpStage.show(); 
		button.setOnAction(e -> {
//			Upon clicking the close button, Stage is closed
			popUpStage.close();
		});
	}
	
	@FXML
//	We show a pop up stage for enabling premium membership
	public void showPopUpForPremiumSubscription() {
		final Stage popUpStage = new Stage();
		GridPane pane = new GridPane(); 
		popUpStage.initModality(Modality.APPLICATION_MODAL);
		pane.setAlignment(Pos.CENTER); 
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5)); 
		pane.setHgap(10); 
		pane.setVgap(10);
		Label text = new Label();
		text.setText("By Checking the Box, You agree to subcribe to premium membership\n If you leave this box unchecked, you will be unsubscribed to prememium membership");
		pane.add(text, 0, 0);
//		We add checkBob
//		We add label, button and checkbox to the pane
		CheckBox isPremiumUser = new CheckBox();
		pane.add(isPremiumUser, 1, 0);
		Button button = new Button("Done");
		pane.add(button, 0, 2);
		Scene scene = new Scene(pane, 550, 200); 
		popUpStage.setTitle("Subcribe To Premeium Membership"); 
		popUpStage.setScene(scene); 
		popUpStage.show(); 
		button.setOnAction(e -> {
//			We set onAction for button which enables smart canvas button on the board
//			With if else, we decide upon checked and unchecked checkbox to enable and disable
			try {
				if(isPremiumUser.isSelected() == false) {
					smartCanvas.setDisable(true);
					isPremiumUser.setSelected(true);
					user.setPremiumUser(true);
					popUpStage.close();
				}
				else {
					smartCanvas.setDisable(false);
					user.setPremiumUser(false);
					popUpStage.close();
				}
			}catch(Exception exe) {
				exe.printStackTrace();
			}


		});
	}
	
//	Method for creating canvas with dimensions and color
	public void createNewCanvas(Color BGcolor, float width, float height) {
		try {
			canvas = cns.CreateNewCanvas(BGcolor, width, height);
//			We create canvas on the centre 
			rootLayout.setCenter(canvas);
//			We catch the exception
		}catch(RuntimeException rt) {
			System.out.println("Could not create canvas");
			rt.printStackTrace();
		}
	}
	
	public void editCanvas() {
//		We call method for editing the canvas
		showPopUpForCanvas();
//		We remove old canvas from by setting up new edited canvas
		rootLayout.getChildren().remove(canvas);
	}
	
	
	public void displayUserInfo() {
//		We display user info to the label on the board
		String userInfo = "Welcome: " + model.getCurrentUser().getFirstname() + " " + model.getCurrentUser().getLastname() + "\n" + "Username: " + model.getCurrentUser().getUsername();
		userInfoLabel.setText(userInfo);
		userInfoLabel.setWrapText(true);
	}
	
//	We create a moveElement method and use output label for displaying coordinates 
	public void moveElemnt(Node content, double dx, double dy, Label outputLabel) {
//		We set X and Y and get current location
		double x = content.getBoundsInParent().getMinX();
		double y = content.getBoundsInParent().getMinY();
//		We add the new coordinates to old one
		content.relocate(x + dx, y + dy);
//		We use formatted string to display appropriate attributes such as coordinates and rotation angle
		outputLabel.setText(String.format("x: %.2f y: %.2f %10s Rotation: %.2f", x + dx, y + dy, " ", content.getRotate()));
	}
//	this method helps to detect mouse presses and get cordinates from mouse and set to new one by calling it above
	public void move(Node element, Label outputLabel) {
		(element).setOnMousePressed(e -> {
			originX = e.getX();
			originY = e.getY();
		});

		(element).setOnMouseDragged(e -> {
			double dx = e.getX() - originX;
			double dy = e.getY() - originY;
			moveElemnt((element), dx, dy, outputLabel);
		});
		
	}

	

	@FXML
	public void RectangleCreator(ActionEvent event) {
//		We call method from RectangleElement ov er here and add to canvas
		   Rectangle rectOnCanvas = rectangle.formRectagle(coordinatesOutputLabel);
		   canvas.getChildren().add(rectOnCanvas);
		   ElementList.add(rectOnCanvas);
		   rectOnCanvas.setOnMouseClicked(e ->{
//			   we select the rectangle on clicked 
			   setSelectedElement(rectOnCanvas);
			   elementDimensionsLabel.setText("Width: " + ((Rectangle)getSelectedElement()).getWidth() + "    Height: " + ((Rectangle)getSelectedElement()).getHeight());
			   unselectAll(ElementList);
//			   Upon clicking first we unselect all the elemnts first and select this particular one to create a visual effect as well 
			   indicateSelected();
			   enableAppropriateElements();
		   });
		   move(rectOnCanvas, coordinatesOutputLabel);
	}
	
//	Similarly we create Circle, Image view and Text Elements(That is label)
	@FXML
	public void createNewCircle(ActionEvent event){
		Circle cirOnCanvas  = circle.formCircle(coordinatesOutputLabel);
		canvas.getChildren().addAll(cirOnCanvas);
		ElementList.add(cirOnCanvas);
		cirOnCanvas.setOnMouseClicked(e ->{
			   setSelectedElement(cirOnCanvas);
			   System.out.println("Circle selected");
			   elementDimensionsLabel.setText("Radius: " + ((Circle)getSelectedElement()).getRadius());
			   unselectAll(ElementList);
			   indicateSelected();
			   enableAppropriateElements();
		   });
		move(cirOnCanvas, coordinatesOutputLabel);
	}
	
	@FXML
	public void createNewTextField() {
		Label TextFieldOnCanvas = textField.formTextField(coordinatesOutputLabel);
		canvas.getChildren().addAll(TextFieldOnCanvas);
		ElementList.add(TextFieldOnCanvas);
//		We set border to transparent first to avoid nullPointerException in case of border tweaking
		TextFieldOnCanvas.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
		TextFieldOnCanvas.setOnMouseClicked(e ->{
			   setSelectedElement(TextFieldOnCanvas);
			   System.out.println("TextField selected");
			   elementDimensionsLabel.setText("Width: " + ((Label)getSelectedElement()).getWidth() + "    Height: " + ((Label)getSelectedElement()).getHeight());
			   numTextElements = numTextElements + 1;
			   unselectAll(ElementList);
			   indicateSelected();
			   enableAppropriateElements();
		   });
		move(TextFieldOnCanvas, coordinatesOutputLabel);
	}
	
	@FXML
	public void createNewImageView(ActionEvent event){
		ImageView imgVonCanvas  = imgVelement.formImageView(coordinatesOutputLabel);
		canvas.getChildren().addAll(imgVonCanvas);
		ElementList.add(imgVonCanvas);
		imgVonCanvas.setPreserveRatio(true);
		try {
			ChangeImage(imgVonCanvas);
			elementDimensionsLabel.setText("Width: " + (imgVonCanvas).getFitWidth() + "    Height: " + (imgVonCanvas).getFitHeight());

		} catch (IOException e2) {
			e2.printStackTrace();
		}
		imgVonCanvas.setOnMouseClicked(e ->{
			   setSelectedElement(imgVonCanvas);
			   System.out.println("Image View sElected");
			   unselectAll(ElementList);
			   indicateSelected();
			   elementDimensionsLabel.setText("Width: " + ((ImageView)getSelectedElement()).getFitWidth() + "    Height: " + ((ImageView)getSelectedElement()).getFitHeight());
			   enableAppropriateElements();
		   });
		move(imgVonCanvas, coordinatesOutputLabel);
	}
	
	
	

	public void unselectAll(ArrayList<Node> elementList) {
        DropShadow dropUnselected = new DropShadow(); 
        DropShadow dropSelected = new DropShadow();
        dropSelected.setBlurType(BlurType.GAUSSIAN);  
        dropSelected.setColor(Color.RED); 
        dropUnselected.setBlurType(null);  
        dropUnselected.setColor(Color.TRANSPARENT);
//        We loop through the elements of the elementList array list and first reset its effect to null to show that it is unselected  
		for(Node element: elementList) {
			if(element instanceof Shape) {
				((Shape) element).setEffect(dropUnselected);
			}
			else if(element instanceof Label) {
				try {
					((Label) element).setEffect(dropUnselected);
				}
				catch(NullPointerException e) {
					e.getMessage();
				}
			}
			else if(element instanceof ImageView) {
				((ImageView)element).setEffect(dropUnselected);  
			}
		}
	}
	
	public void indicateSelected() {
//		Reference https://www.javatpoint.com/javafx
//		We set effect of selected element upon clicking 
        DropShadow drop = new DropShadow();  
        drop.setBlurType(BlurType.GAUSSIAN);  
        drop.setColor(Color.RED);  
		if(getSelectedElement() instanceof Shape) {
			((Shape)getSelectedElement()).setEffect(drop);
		}
		
		if(getSelectedElement() instanceof Label) {
//			We catch null pointer exception in case its null
			try {
				((Label) getSelectedElement()).setEffect(drop);
			}
			catch(NullPointerException e) {
				e.getMessage();
			}
		}
		if(getSelectedElement() instanceof ImageView) {
			((ImageView)getSelectedElement()).setEffect(drop);  
		}
	}
	
	
	
	
	public void ChangeImage(ImageView imageView) throws IOException {
//		We first initiate a image to null
		Image image = null;
//		Declare a filchooser
		FileChooser fileChooser = new FileChooser();
//		We filter to put on only jpeg, png or jpg
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("*.jpeg", "*.png", "*.jpg"));
		fileChooser.setTitle("Select Profile Photo");
//		We show fileChooser by opening a stage 
		File file = fileChooser.showOpenDialog(new Stage());
		if(file != null) {
			try {
//				We choose Filepath and convert to a String
				FilePath = file.toURI().toURL().toString();
//				We set the image to its filepath 
				image = new Image(FilePath);
//				We set imageView to the newly created image
				imageView.setImage(image);
				
			}
//			We catch the appropriate exception
			catch(MalformedURLException e){
				e.printStackTrace();
			}
		}
	}
//	We change image on Board o calling above mthod over here 
	public void changeImageOnBoard() {
		if(getSelectedElement() instanceof ImageView) {
			try {
				ChangeImage(((ImageView)getSelectedElement()));
				showSelectedElementDimension();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
//	We clear canvas by calling .clear()
	@FXML
	public void clearCanvas() {
		canvas.getChildren().clear();
	}
	
	
	@FXML
	public void enlarge() {
//		We enlarge elements by calling selectedElemnts's appropriate method from its respective class by casting them
		if(getSelectedElement() instanceof Circle) {
			circle.enlarge((Circle)getSelectedElement());
		}
		if(getSelectedElement() instanceof Rectangle) {
			rectangle.enlarge((Rectangle)getSelectedElement());
		}
		if(getSelectedElement() instanceof ImageView) {
			imgVelement.enlarge((ImageView)getSelectedElement());
		}
		if(getSelectedElement() instanceof Label) {
			textField.enlarge((Label)getSelectedElement());
		}
//		We show updated dimensions
		showSelectedElementDimension();
	}
	
	public void shrink() {
		if(getSelectedElement() instanceof Circle) {
			circle.shrink((Circle)getSelectedElement());
		}
		if(getSelectedElement() instanceof Rectangle) {
			rectangle.shrink((Rectangle)getSelectedElement());
		}
		if(getSelectedElement() instanceof ImageView) {
			imgVelement.shrink((ImageView)getSelectedElement());
		}
		if(getSelectedElement() instanceof Label) {
			textField.shrink((Label)getSelectedElement());
		}
		showSelectedElementDimension();
	}
	
//	We do similar thing in case of border width similar to shape size
	public void enlargeBorder() {
		if(getSelectedElement() instanceof Rectangle) {
			rectangle.enlargeBorderWidth((Rectangle)getSelectedElement());
		}
		if(getSelectedElement() instanceof Circle) {
			circle.enlargeBorderWidth((Circle)getSelectedElement());
		}
		if(getSelectedElement() instanceof Label) {
			textField.enlargeBorderWidth((Label)getSelectedElement());
		}
	}
	
	public void shrinkBorder() {
		if(getSelectedElement() instanceof Rectangle) {
			rectangle.shrinkBorderWidth((Rectangle)getSelectedElement());
		}
		if(getSelectedElement() instanceof Circle) {
			circle.shrinkBorderWidth((Circle)getSelectedElement());
		}
		if(getSelectedElement() instanceof Label) {
			textField.shrinkBorderWidth((Label)getSelectedElement());
		}
	}
	
//	We change background color of selected element by getting value of colorpicker and make use of casting 
	@FXML
	public void changeBGcolor() {
		if(getSelectedElement() instanceof Rectangle) {
			rectangle.changeBGcolor((Rectangle)getSelectedElement(), colorPicker.getValue());
		}
		if(getSelectedElement() instanceof Circle) {
			circle.changeBGcolor((Circle)getSelectedElement(), colorPicker.getValue());
		}
		if(getSelectedElement() instanceof Label) {
			textField.ChangeBackgroundColorOfTextBox((Label)getSelectedElement(), colorPicker.getValue());
		}
	}
//	We change border color of selected element by getting value of colorpicker
	public void changeBorderColor() {
		if(getSelectedElement() instanceof Rectangle) {
			rectangle.changeBorderColor((Rectangle)getSelectedElement(), colorPickerForBorder.getValue());
		}
		if(getSelectedElement() instanceof Circle) {
			circle.changeBorderColor((Circle)getSelectedElement(), colorPickerForBorder.getValue());
		}
		if(getSelectedElement() instanceof Label) {
			textField.changeBorderColor((Label)getSelectedElement(), colorPickerForBorder.getValue());
			textField.setCurrentColor(colorPickerForBorder.getValue());
		}
	}
	
//	We take user input from a text field and set the selectedElemnt's rotation
	public void rotateElement() {
		Double enterAngleOfRotation;
		try {
//			make use of try catch in case of string to float
			enterAngleOfRotation = Double.parseDouble(rotationTextField.getText());
			if(getSelectedElement() instanceof Rectangle) {
				rectangle.RotateElement(getSelectedElement(), enterAngleOfRotation);
			}
			if(getSelectedElement() instanceof ImageView) {
				imgVelement.RotateElement(getSelectedElement(), enterAngleOfRotation);
			}
			if(getSelectedElement() instanceof Label) {
				textField.RotateElement(getSelectedElement(), enterAngleOfRotation);
			}
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	
	public void selectFont() {
//		We make use external library called ControlsFX
//		This code was completed with help of a youtube channel named "Cool IT Help"
//		Show a new stage for fonts size, style and family
		FontSelectorDialog dialog = new FontSelectorDialog(null);
		dialog.setTitle("Select Fonts from the list");
		Optional<Font> response = dialog.showAndWait();
		if(getSelectedElement() instanceof Label) {
			try {
				((Label)getSelectedElement()).setFont(response.get());
			}
//			We catch appropriate eception
			catch(NoSuchElementException e) {
				System.err.println();
			}
		}
	}
	
//	We change font color with help of getting value from color picker
	public void changeFontColor() {
		textField.changeTextColor(((Label)getSelectedElement()), fontColorSelector.getValue());
	}
	
//	We edit content of Label by taking user input from a text area 
	public void editContentOfTextElement() {
		textField.editText((Label)getSelectedElement(), textElementTextArea.getText());
		showSelectedElementDimension();
		
	}
	
	public void ZoomCanvas() {
//		We use a slider to zoom the canvas in and out
		if(canvas != null) {
			canvas.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
//			We use listener to the slider 
			zoomCanvas.valueProperty().addListener((ov, oldValue, newValue) -> {
				cns.ChangePerspective(canvas, newValue.intValue());
//				We also show the zoom level to the label
				zoomLevel.setText("Zoom Level: " + newValue.intValue());
			});
//			we call changePerspective method to new value
			cns.ChangePerspective(canvas, 100); 
			zoomCanvas.setValue(100);
		}
		else {
			zoomCanvas.setDisable(true);
		}
	}
	
	
	public void setAllignment() {
//		Making use of radio button  and call the appropriate methods to set the allignment of the selected text
		if(getSelectedElement() instanceof Label) {
			textRight.setDisable(false);
			textCentre.setDisable(false);
			textLeft.setDisable(false);
			if(textLeft.isSelected()) {
				((Label)getSelectedElement()).setAlignment(Pos.BASELINE_LEFT);
			}
			if(textRight.isSelected()) {
				((Label)getSelectedElement()).setAlignment(Pos.BASELINE_RIGHT);
			}
			if(textCentre.isSelected()) {
				((Label)getSelectedElement()).setAlignment(Pos.CENTER);
			}
		}
		else {
			textRight.setDisable(true);
			textCentre.setDisable(true);
			textLeft.setDisable(true);
		}
	}
	public void toLeft() {
		((Label)getSelectedElement()).setTextAlignment(TextAlignment.LEFT);;
	}
	public void toCentre() {
		((Label)getSelectedElement()).setTextAlignment(TextAlignment.CENTER);
	}
	public void toRight() {
		((Label)getSelectedElement()).setTextAlignment(TextAlignment.RIGHT);
	}
	
	public void deleteElement() {
		canvas.getChildren().remove(getSelectedElement());
		ElementList.remove(getSelectedElement());
	}
	
//	on calling logout method, application should close board and open login screen again
	public void Logout() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginPage.fxml"));
			
			// Customize controller instance
			Callback<Class<?>, Object> controllerFactory = param -> {
				return new LoginController(stage, model);
			};

			loader.setControllerFactory(controllerFactory);
			Parent root = loader.load();
			
			LoginController loginController = loader.getController();
			loginController.showStage(root);
//			stage.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	We open a new stage to edit profile by showing editProfileView.fxml
	public void editProfile() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editProfileView.fxml"));
			
			// Customize controller instance
			Callback<Class<?>, Object> controllerFactory = param -> {
				return new profileController(stage, model);
			};

			loader.setControllerFactory(controllerFactory);
			Parent root = loader.load();
			
			profileController profileController = loader.getController();
			profileController.showStage(root);
//			We catch IO exception
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	===========================================Templates=========================================================================================================
	
	
//	On clicking the button of menu, we add manually each and every thing that is from canvas to all other elements such as labels and imageview and rectangle 
	public void AddBirthdayTemplate() {
//		First create a new canvas
//		Set canvas's background image to make template visually look good
//		for lebel we setText and textFill and all other properties over here and let the user edit 
//		We add all elements to the arrayList to follow all the functionalities for all elements within the template
//		We also let all the elements move
//		I have done the same thing for each and every template
		Background backgroundImage = new Background(new BackgroundImage(new Image(new File("Images/BirthdayImage.jpeg").toURI().toString(), 500,500,false,true), null, null, null, null));
		canvas = cns.CreateNewCanvas(Color.BLUEVIOLET, 500, 500);
		rootLayout.setCenter(canvas);
		canvas.setBackground(backgroundImage);
//		   From here, we hardcode and add each elemnent manually by setting its coordiantes and color
			Label textOnCanvasB = textField.formTextField(coordinatesOutputLabel);

			textOnCanvasB.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
			canvas.getChildren().addAll(textOnCanvasB);
			ElementList.add(textOnCanvasB);
			textOnCanvasB.setFont(Font.font("Verdana", FontWeight.BOLD, 35));
			textOnCanvasB.setTextFill(Color.BLACK);
			textOnCanvasB.relocate(95, 60);
			textOnCanvasB.setText("SAVE THE DATE");
			textOnCanvasB.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
			textOnCanvasB.setAlignment(Pos.CENTER);
			textOnCanvasB.setOnMouseClicked(e ->{
				   setSelectedElement(textOnCanvasB);
				   System.out.println("TextField selected");
				   unselectAll(ElementList);
				   indicateSelected();
			   });
			move(textOnCanvasB, coordinatesOutputLabel);
		
			Label textOnCanvasB1 = textField.formTextField(coordinatesOutputLabel);
			textOnCanvasB1.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
			canvas.getChildren().addAll(textOnCanvasB1);
			ElementList.add(textOnCanvasB1);
			textOnCanvasB1.setFont(Font.font("Comic Sans MS", FontPosture.ITALIC, 35 ));
			textOnCanvasB1.setTextFill(Color.BLACK);
			textOnCanvasB1.relocate(115, 170);
			textOnCanvasB1.setText("Samantha Hadid");
			Reflection r = new Reflection();
			r.setFraction(0.7f);
			textOnCanvasB1.setEffect(r);
			textOnCanvasB1.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
			textOnCanvasB1.setAlignment(Pos.CENTER);
			textOnCanvasB1.setOnMouseClicked(e ->{
				   setSelectedElement(textOnCanvasB1);
				   System.out.println("TextField selected");
				   unselectAll(ElementList);
				   indicateSelected();
			   });
			move(textOnCanvasB1, coordinatesOutputLabel);
			
			Label textOnCanvasB2 = textField.formTextField(coordinatesOutputLabel);
			textOnCanvasB2.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
			canvas.getChildren().addAll(textOnCanvasB2);
			ElementList.add(textOnCanvasB2);
			textOnCanvasB2.setFont(Font.font("Verdana", FontPosture.ITALIC, 40 ));
			textOnCanvasB2.setTextFill(Color.BLUE);
			textOnCanvasB2.relocate(130, 260);
			textOnCanvasB2.setText("At Eighteen");
			textOnCanvasB2.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
			textOnCanvasB2.setAlignment(Pos.CENTER);
			textOnCanvasB2.setOnMouseClicked(e ->{
				   setSelectedElement(textOnCanvasB2);
				   System.out.println("TextField selected");
				   unselectAll(ElementList);
				   indicateSelected();
			   });
			move(textOnCanvasB2, coordinatesOutputLabel);
			
			   Rectangle rectOnCanvas1 = rectangle.formRectagle(coordinatesOutputLabel);
			   ElementList.add(rectOnCanvas1);
			   canvas.getChildren().add(rectOnCanvas1);
			   rectOnCanvas1.setFill(Color.BLACK);
			   rectOnCanvas1.setHeight(10);
			   rectOnCanvas1.setWidth(300);
			   rectOnCanvas1.setX(100);
			   rectOnCanvas1.setY(315);
			   rectOnCanvas1.setOnMouseClicked(e ->{
				   setSelectedElement(rectOnCanvas1);
				   System.out.println("Rect selected");
				   unselectAll(ElementList);
				   indicateSelected();
			   });
			   move(rectOnCanvas1, coordinatesOutputLabel);
			   
				Label textOnCanvasB3 = textField.formTextField(coordinatesOutputLabel);
				textOnCanvasB2.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
				canvas.getChildren().addAll(textOnCanvasB3);
				ElementList.add(textOnCanvasB3);
				textOnCanvasB3.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
				textOnCanvasB3.setAlignment(Pos.CENTER);
				textOnCanvasB3.setTextAlignment(TextAlignment.CENTER);
				textOnCanvasB3.setTextFill(Color.BLUE);
				textOnCanvasB3.relocate(105, 360);
				textOnCanvasB3.setText("come and celebrate with us :\n"
						+ "02 / 19 / 2022");
				textOnCanvasB3.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
				textOnCanvasB3.setAlignment(Pos.CENTER);
				textOnCanvasB3.setOnMouseClicked(e ->{
					   setSelectedElement(textOnCanvasB3);
					   System.out.println("TextField selected");
					   unselectAll(ElementList);
					   indicateSelected();
				   });
				move(textOnCanvasB3, coordinatesOutputLabel);
		
		
	}
	
	
	public void AddResumeTemplate() {
		canvas = cns.CreateNewCanvas(Color.AQUA, 350, 500);
		canvas.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		rootLayout.setCenter(canvas);
		
		   Rectangle rectOnCanvas = rectangle.formRectagle(coordinatesOutputLabel);
		   canvas.getChildren().add(rectOnCanvas);
		   ElementList.add(rectOnCanvas);
		   rectOnCanvas.setHeight(120);
		   rectOnCanvas.setWidth(350);
		   rectOnCanvas.setX(0);
		   rectOnCanvas.setY(0);
		   rectOnCanvas.setFill(Color.AQUA);
		   rectOnCanvas.setOnMouseClicked(e ->{
			   setSelectedElement(rectOnCanvas);
			   System.out.println("Rect selected");
			   unselectAll(ElementList);
			   indicateSelected();
		   });
		   move(rectOnCanvas, coordinatesOutputLabel);
		   
		   
			ImageView imgVonCanvas  = imgVelement.formImageView(coordinatesOutputLabel);
			canvas.getChildren().addAll(imgVonCanvas);
			ElementList.add(imgVonCanvas);
			imgVonCanvas.setImage(new Image(new File("Images/DefaultPhoto.jpeg").toURI().toString()));
			imgVonCanvas.setPreserveRatio(true);
			imgVonCanvas.relocate(10, 10);
			imgVonCanvas.setOnMouseClicked(e ->{
				   setSelectedElement(imgVonCanvas);
				   System.out.println("Image View sElected");
				   unselectAll(ElementList);
				   indicateSelected();
			   });
			move(imgVonCanvas, coordinatesOutputLabel);
			
			
			Label textOnCanvas = textField.formTextField(coordinatesOutputLabel);
			canvas.getChildren().addAll(textOnCanvas);
			ElementList.add(textOnCanvas);
			textOnCanvas.relocate(125, 5);
			textOnCanvas.setText("John Doe \n BS Biology Major");
			textOnCanvas.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
			textOnCanvas.setFont(Font.font("Cambria", 25));
			textOnCanvas.setAlignment(Pos.CENTER);
			textOnCanvas.setOnMouseClicked(e ->{
				   setSelectedElement(textOnCanvas);
				   System.out.println("TextField selected");
				   unselectAll(ElementList);
				   indicateSelected();
			   });
			move(textOnCanvas, coordinatesOutputLabel);
			
			Label textOnCanvas1 = textField.formTextField(coordinatesOutputLabel);
			canvas.getChildren().addAll(textOnCanvas1);
			ElementList.add(textOnCanvas1);
			textOnCanvas1.relocate(132, 65);
			textOnCanvas1.setFont(Font.font("Cambria", 10));
			textOnCanvas1.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
			textOnCanvas1.setText("Phone number: 0444 123 123	\nemail: abc@xyz.com \nAddress: 23-25 Power Avenue, Glen 3100");
			textOnCanvas1.setOnMouseClicked(e ->{
				   setSelectedElement(textOnCanvas1);
				   System.out.println("TextField selected");
				   unselectAll(ElementList);
				   indicateSelected();
			   });
			move(textOnCanvas1, coordinatesOutputLabel);
			
			   Rectangle rectOnCanvas1 = rectangle.formRectagle(coordinatesOutputLabel);
			   canvas.getChildren().add(rectOnCanvas1);
			   ElementList.add(rectOnCanvas1);
			   rectOnCanvas1.setHeight(380);
			   rectOnCanvas1.setWidth(110);
			   rectOnCanvas1.setX(0);
			   rectOnCanvas1.setY(120);
			   rectOnCanvas1.setFill(Color.AQUA);
			   rectOnCanvas1.setOnMouseClicked(e ->{
				   setSelectedElement(rectOnCanvas1);
				   System.out.println("Rect selected");
				   unselectAll(ElementList);
				   indicateSelected();
			   });
			   move(rectOnCanvas1, coordinatesOutputLabel);
			   
//			   -fx-border-radius
			   
				Label textOnCanvas2 = textField.formTextField(coordinatesOutputLabel);
				canvas.getChildren().addAll(textOnCanvas2);
				ElementList.add(textOnCanvas2);
				textOnCanvas2.setText("Qualities: -");
				textOnCanvas2.relocate(7, 140);
				textOnCanvas2.setFont(Font.font("Cambria", 18));
				textOnCanvas2.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
				textOnCanvas2.setOnMouseClicked(e ->{
					   setSelectedElement(textOnCanvas2);
					   System.out.println("TextField selected");
					   unselectAll(ElementList);
					   indicateSelected();
				   });
				move(textOnCanvas2, coordinatesOutputLabel);
				
				Label textOnCanvas3 = textField.formTextField(coordinatesOutputLabel);
				canvas.getChildren().addAll(textOnCanvas3);
				ElementList.add(textOnCanvas3);
				textOnCanvas3.setText("-  Leadership \n-  Teamwork \n-  Quick Learner");
				textOnCanvas3.relocate(7, 165);
				textOnCanvas3.setFont(Font.font("Cambria", 15));
				textOnCanvas3.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
				textOnCanvas3.setOnMouseClicked(e ->{
					   setSelectedElement(textOnCanvas3);
					   System.out.println("TextField selected");
					   unselectAll(ElementList);
					   indicateSelected();
				   });
				move(textOnCanvas3, coordinatesOutputLabel);
				
				Label textOnCanvas4 = textField.formTextField(coordinatesOutputLabel);
				canvas.getChildren().addAll(textOnCanvas4);
				ElementList.add(textOnCanvas4);
				textOnCanvas4.setText("Previous Experience: -");
				textOnCanvas4.relocate(115, 135);
				textOnCanvas4.setFont(Font.font("Cambria", 20));
				textOnCanvas4.setBackground(new Background(new BackgroundFill(Color.YELLOWGREEN, null, null)));
				textOnCanvas4.setOnMouseClicked(e ->{
					   setSelectedElement(textOnCanvas4);
					   System.out.println("TextField selected");
					   unselectAll(ElementList);
					   indicateSelected();
				   });
				move(textOnCanvas4, coordinatesOutputLabel);
				
				Label textOnCanvas5 = textField.formTextField(coordinatesOutputLabel);
				canvas.getChildren().addAll(textOnCanvas5);
				ElementList.add(textOnCanvas5);
				textOnCanvas5.setText("Laboratory Assistant: -\n"
						+ "-  Set ups and monitors various \n   laboratory tests\n"
						+ "-  Helps with laboratory \n   maintenance\n"
						+ "-  Provides support to main staff\n"
						+ "-  Processes and analyzes samples");
				textOnCanvas5.relocate(115, 162);
				textOnCanvas5.setFont(Font.font("Cambria", 16));
				textOnCanvas5.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
				textOnCanvas5.setOnMouseClicked(e ->{
					   setSelectedElement(textOnCanvas5);
					   System.out.println("TextField selected");
					   unselectAll(ElementList);
					   indicateSelected();
				   });
				move(textOnCanvas5, coordinatesOutputLabel);
				
				Label textOnCanvas6 = textField.formTextField(coordinatesOutputLabel);
				canvas.getChildren().addAll(textOnCanvas6);
				ElementList.add(textOnCanvas6);
				textOnCanvas6.setText("HS Biology Tutor: -\n"
						+ "-  Helped and guided students \n   gain mastery in basic \n   biology\n"
						+ "-  Interacted with other \n   science tutors to \n   create curriculum\n"
						+ "-  Received \"Tutor of the Year\" \n   award");
				textOnCanvas6.relocate(115, 313);
				textOnCanvas6.setFont(Font.font("Cambria", 16));
				textOnCanvas6.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
				textOnCanvas6.setOnMouseClicked(e ->{
					   setSelectedElement(textOnCanvas6);
					   System.out.println("TextField selected");
					   unselectAll(ElementList);
					   indicateSelected();
				   });
				move(textOnCanvas6, coordinatesOutputLabel);
	}
	
	
	public void AddCoverLetterTemplate() {
		canvas = cns.CreateNewCanvas(Color.WHITE, 350, 500);
		canvas.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		rootLayout.setCenter(canvas);
		
		   Rectangle rectOnCanvas = rectangle.formRectagle(coordinatesOutputLabel);
		   canvas.getChildren().add(rectOnCanvas);
		   ElementList.add(rectOnCanvas);
		   rectOnCanvas.setHeight(100);
		   rectOnCanvas.setWidth(350);
		   rectOnCanvas.setX(0);
		   rectOnCanvas.setY(0);
		   rectOnCanvas.setFill(Color.BLACK);
		   rectOnCanvas.setOnMouseClicked(e ->{
			   setSelectedElement(rectOnCanvas);
			   System.out.println("Rect selected");
			   unselectAll(ElementList);
			   indicateSelected();
		   });
		   move(rectOnCanvas, coordinatesOutputLabel);
		   
		   Rectangle rectOnCanvas1 = rectangle.formRectagle(coordinatesOutputLabel);
		   canvas.getChildren().add(rectOnCanvas1);
		   ElementList.add(rectOnCanvas);
		   rectOnCanvas1.setHeight(30);
		   rectOnCanvas1.setWidth(350);
		   rectOnCanvas1.setX(0);
		   rectOnCanvas1.setY(470);
		   rectOnCanvas1.setFill(Color.BLACK);
		   rectOnCanvas1.setOnMouseClicked(e ->{
			   setSelectedElement(rectOnCanvas1);
			   System.out.println("Rect selected");
			   unselectAll(ElementList);
			   indicateSelected();
		   });
		   move(rectOnCanvas1, coordinatesOutputLabel);
			
			
			Label textOnCanvas = textField.formTextField(coordinatesOutputLabel);
			canvas.getChildren().addAll(textOnCanvas);
			ElementList.add(textOnCanvas);
			textOnCanvas.relocate(5, 30);
			textOnCanvas.setText("John Doe");
			textOnCanvas.setTextFill(Color.WHITE);
			textOnCanvas.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
			textOnCanvas.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
			textOnCanvas.setFont(Font.font("Cambria", 30));
			textOnCanvas.setAlignment(Pos.CENTER);
			textOnCanvas.setOnMouseClicked(e ->{
				   setSelectedElement(textOnCanvas);
				   System.out.println("TextField selected");
				   unselectAll(ElementList);
				   indicateSelected();
			   });
			move(textOnCanvas, coordinatesOutputLabel);
			
			Label textOnCanvas1 = textField.formTextField(coordinatesOutputLabel);
			canvas.getChildren().addAll(textOnCanvas1);
			ElementList.add(textOnCanvas1);
			textOnCanvas1.relocate(169, 58);
			textOnCanvas1.setFont(Font.font("Cambria", 10));
			textOnCanvas1.setTextFill(Color.WHITE);
			textOnCanvas1.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
			textOnCanvas1.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
			textOnCanvas1.setText("Phone number: 0444 123 123	\nemail: abc@xyz.com \nAddress: 23-25 Power Avenue, Glen 3100");
			textOnCanvas1.setOnMouseClicked(e ->{
				   setSelectedElement(textOnCanvas1);
				   System.out.println("TextField selected");
				   unselectAll(ElementList);
				   indicateSelected();
			   });
			move(textOnCanvas1, coordinatesOutputLabel);
			   
//			   -fx-border-radius
			   
				Label textOnCanvas2 = textField.formTextField(coordinatesOutputLabel);
				canvas.getChildren().addAll(textOnCanvas2);
				ElementList.add(textOnCanvas2);
				textOnCanvas2.setText("July 27, 2025\n"
						+ "\n"
						+ "John Doe");
				textOnCanvas2.relocate(225, 110);
				textOnCanvas2.setFont(Font.font("Cambria", FontWeight.EXTRA_BOLD, 10));
				textOnCanvas2.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
				textOnCanvas2.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
				textOnCanvas2.setOnMouseClicked(e ->{
					   setSelectedElement(textOnCanvas2);
					   System.out.println("TextField selected");
					   unselectAll(ElementList);
					   indicateSelected();
				   });
				move(textOnCanvas2, coordinatesOutputLabel);
				
				Label textOnCanvas3 = textField.formTextField(coordinatesOutputLabel);
				canvas.getChildren().addAll(textOnCanvas3);
				ElementList.add(textOnCanvas3);
				textOnCanvas3.setText("Founder Rimberio Co.\n"
						+ "123 Anywhere St., Any City, \nST 12345");
				textOnCanvas3.relocate(225, 155);
				textOnCanvas3.setFont(Font.font("Cambria", 10));
				textOnCanvas3.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
				textOnCanvas3.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
				textOnCanvas3.setMaxWidth(350);
				textOnCanvas3.setOnMouseClicked(e ->{
					   setSelectedElement(textOnCanvas3);
					   System.out.println("TextField selected");
					   unselectAll(ElementList);
					   indicateSelected();
				   });
				move(textOnCanvas3, coordinatesOutputLabel);
//				
				Label textOnCanvas4 = textField.formTextField(coordinatesOutputLabel);
				canvas.getChildren().addAll(textOnCanvas4);
				ElementList.add(textOnCanvas4);
				textOnCanvas4.setText("Dear Ms. Silva, \n"
						+ "\n"
						+ "\n"
						+ "A cover letter allows you to professionally introduce yourself to a prospective employer. Your goal in writing your cover letter should be to encourage the employer to read your resume and consider you for a specific position.\n"
						+ "\n"
						+ "Highlight your achievements, skills, experiences, and training that are relevant to the position you want to get. \nHowever, avoid simply repeating the information you included in your resume. \nTailor your cover letter to each employer and job. Since you are applying for specific roles, \ngive specific examples and events that demonstrate your ability to perform well if given the position.\n"
						+ "\n"
						+ "\n"
						+ "Best Regards,\n"
						+ "Itsuki Takahashi");
				textOnCanvas4.relocate(0, 195);
				textOnCanvas4.setFont(Font.font("Cambria", 10));
				textOnCanvas4.setMaxWidth(350);
				textOnCanvas4.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
				textOnCanvas4.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
				textOnCanvas4.setOnMouseClicked(e ->{
					   setSelectedElement(textOnCanvas4);
					   System.out.println("TextField selected");
					   unselectAll(ElementList);
					   indicateSelected();
				   });
				move(textOnCanvas4, coordinatesOutputLabel);
	}
	
	
	public void exportCanvasAsImage() {
		WritableImage image = canvas.snapshot(new SnapshotParameters(), null);

		File file = new File("Images/canvas.png");

		try {
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showSelectedElementDimension() {
//		We set each selectedElement's dimension with help of instanceof
		try {
			if(getSelectedElement() instanceof Circle) {
				elementDimensionsLabel.setText("Radius: " + ((Circle)getSelectedElement()).getRadius());
				System.out.println("Radius: " + ((Circle)getSelectedElement()).getRadius());
			}
			if(getSelectedElement() instanceof Rectangle) {
				elementDimensionsLabel.setText("Width: " + ((Rectangle)getSelectedElement()).getWidth() + "    Height: " + ((Rectangle)getSelectedElement()).getHeight());
			}
			if(getSelectedElement() instanceof Label) {
				elementDimensionsLabel.setText("Width: " + ((Label)getSelectedElement()).getWidth() + "    Height: " + ((Label)getSelectedElement()).getHeight());
			}
			if(getSelectedElement() instanceof ImageView) {
				elementDimensionsLabel.setText("Width: " + ((ImageView)getSelectedElement()).getFitWidth() + "    Height: " + ((ImageView)getSelectedElement()).getFitHeight());
			}

		elementDimensionsLabel.setWrapText(true);
		}
//		We catch nullPinterexception
		catch(NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	
	public void initialiser() {
//		We call all the methods which are required to be called beforehand of just after loading the stage
		ZoomCanvas();
		displayUserInfo();
		showSelectedElementDimension();
		profilePhotoOnBoard.setOnMouseClicked(e ->{
			try {
				ChangeImage(profilePhotoOnBoard);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}

	public void showStage(Parent root) {;
		initialiser();
		Scene scene = new Scene(root,790,605);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Board");
		stage.show();
	}

}