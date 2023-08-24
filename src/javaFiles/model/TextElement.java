package javaFiles.model;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class TextElement implements Resize, Rotate, BorderSettings{
//	we set a border variable to first set the border width 
	private int border = 2;
	
	Color currentColor = Color.BLACK;
	
	public Color getCurrentColor() {
		return currentColor;
	}

	public void setCurrentColor(Color currentColor) {
		this.currentColor = currentColor;
	}

	public Label formTextField(Label outputLabel) {
		Label tf = new Label();
		tf.setText("Sample Text");
		tf.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		tf.setTextFill(Color.BLACK);
		tf.setWrapText(true);
		return tf;
	}
	
//	Changetext color with appropriate parameters
	public void changeTextColor(Label textField, Color color){
		textField.setTextFill(color);
	}
//	Change background color with appropriate parameters
	public void ChangeBackgroundColorOfTextBox(Label textField, Color color) {
		textField.setBackground(new Background(new BackgroundFill(color, null, null)));
	}
// We shrink and enlarge the text element with help of getMaxWidth and getMaxHeight

	@Override
	public void shrink(Node element) {
		((Label) element).setMaxHeight(((Label) element).getMaxHeight() - 2);
		((Label) element).setMaxWidth(((Label) element).getMaxWidth() - 2);
		
	}

	@Override
	public void enlarge(Node element) {
		((Label) element).setMaxHeight(((Label) element).getMaxHeight() + 2);
		((Label) element).setMaxWidth(((Label) element).getMaxWidth() + 2);
		
	}

//	rotate text by using setRotate
	@Override
	public void RotateElement(Node element, double degrees) {
		((Label) element).setRotate(degrees);
	}

//	enlarge and shrink BorderWidth by using setBorder
	@Override
	public void enlargeBorderWidth(Node element) {
		((Label) element).setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(border + 2))));
//		Increase the border variable by 2, so next time when this method is called, its borderWidth is added to increased value
		border = border + 2;
	}


	@Override
	public void shrinkBorderWidth(Node element) {
		((Label) element).setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(border - 2))));
//		decrease the border variable by 2, so next time when this method is called, its borderWidth is added to increased value
		border = border - 2;
	}

//	Change its border color
	@Override
	public void changeBorderColor(Node element, Color color) {
		((Label)element).setBorder(new Border(new BorderStroke(color, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(border))));
	}
	
	public void editText(Label element, String text) {
		((Label)element).setText(text);
	}
	
}