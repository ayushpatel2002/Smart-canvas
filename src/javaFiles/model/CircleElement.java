package javaFiles.model;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class CircleElement extends Shapes implements Resize, BorderSettings{

// We implement the interface methods 
	public Circle formCircle(Label outputLabel) {
		Circle cir = new Circle();
		cir.setCenterX(100);
		cir.setCenterY(100);
		cir.setRadius(50);
		return cir;
	}

	
	public String enlargeForText(Circle selCir) {
		selCir.setRadius(selCir.getRadius() + 2);
		return "Circle With Radius " + selCir.getRadius() + " Created";
	}

//	We only shrink the circle if its radius is greater than 2
	public void shrink(Circle selCir) {
		selCir.setRadius(selCir.getRadius() > 2 ? selCir.getRadius() - 2 : selCir.getRadius());
	}

//	Change background of circle
	public void changeBGcolor(Circle selCir, Color color) {
		super.ChangeShapeColor(selCir, color);
	}

	// By increasing and decrease radius, we increase size of circle
	@Override
	public void shrink(Node element) {
		((Circle) element).setRadius(((Circle) element).getRadius() > 2 ? ((Circle) element).getRadius() - 2 : ((Circle) element).getRadius());	
	}
	@Override
	public void enlarge(Node element) {
		((Circle) element).setRadius(((Circle) element).getRadius() + 2);
		
	}

// We enlarge and shrink borderwidth by calling getStrokeWidth and setStrokeWidth
	@Override
	public void enlargeBorderWidth(Node shape) {
		((Circle) shape).setStrokeWidth(((Circle) shape).getStrokeWidth() + 2);	
	}
	@Override
	public void shrinkBorderWidth(Node shape) {
		((Circle) shape).setStrokeWidth(((Circle) shape).getStrokeWidth() > 2 ? ((Circle) shape).getStrokeWidth() - 2 : ((Circle) shape).getStrokeWidth());
	}

// We change BorderColor by calling method from superclass
	@Override
	public void changeBorderColor(Node shape, Color color) {
		super.ChangeBorderColor((Shape)shape, color);
		
	}
	
	
	
}