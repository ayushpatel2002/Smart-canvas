package javaFiles.model;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class RectangleElement extends Shapes implements Resize, Rotate, BorderSettings {
//	Implemented all the methods from interfaces
	public Rectangle formRectagle(Label outputLabel) {
		Rectangle rect = new Rectangle();
		rect.setX(50);
		rect.setY(50);
		rect.setWidth(200);
		rect.setHeight(100);
		return rect;
	}
	
// calling method from super class to change background color 
	public void changeBGcolor(Node shape, Color color) {
		super.ChangeShapeColor((Shape)shape, color);
		
	}

	
//	We enlarge and shrink rectangle by by reducing and increasing width and height 
	@Override
	public void shrink(Node element) {
		((Rectangle) element).setWidth(((Rectangle) element).getWidth() - 2);
		((Rectangle) element).setHeight(((Rectangle) element).getHeight() - 2);
		
	}

	@Override
	public void enlarge(Node element) {
		((Rectangle) element).setWidth(((Rectangle) element).getWidth() + 2);
		((Rectangle) element).setHeight(((Rectangle) element).getHeight() + 2);
		
	}

	
//	We enlrage and shrink StrokeWidth by setStrokeWidth and getStrokeWidth
	@Override
	public void enlargeBorderWidth(Node element) {
		((Rectangle) element).setStrokeWidth(((Rectangle) element).getStrokeWidth() + 2);
	}

	@Override
	public void shrinkBorderWidth(Node element) {
		((Rectangle) element).setStrokeWidth(((Rectangle) element).getStrokeWidth() > 2 ? ((Rectangle) element).getStrokeWidth() - 2 : ((Rectangle) element).getStrokeWidth());
		
	}

	@Override
	public void changeBorderColor(Node shape, Color color) {
		super.ChangeBorderColor((Shape)shape, color);
		
	}

	@Override
	public void RotateElement(Node element, double degrees) {
		((Rectangle) element).setRotate(degrees);
		
	}
	

	
}