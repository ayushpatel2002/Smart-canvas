package javaFiles.model;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Shapes{
	public void ChangeBorderColor(Shape shape, Color color) {
		(shape).setStroke(color);
	}
	
	
	public void ChangeShapeColor(Shape shape, Color color) {
		shape.setFill(color);
	}
}