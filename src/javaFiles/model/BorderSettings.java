package javaFiles.model;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public interface BorderSettings {
//	Planned method to be implemented for border settings
	public void enlargeBorderWidth(Node element);
	public void shrinkBorderWidth(Node element);
	public void changeBorderColor(Node element, Color color);
}
