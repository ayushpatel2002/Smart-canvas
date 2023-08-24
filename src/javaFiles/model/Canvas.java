package javaFiles.model;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Canvas{
	
//	Method for reutning a pane with dimensions and color taken as parameter
	public Pane CreateNewCanvas(Color color, float width, float height) {
		Pane canvasPane = new AnchorPane();
		canvasPane.setPrefSize(width, height);
		canvasPane.setBackground(new Background(new BackgroundFill(color, null, null)));
		canvasPane.setMaxWidth(width);
		canvasPane.setMaxHeight(height);
		return canvasPane;
	}
	

// We change perspective of canvas and call in BoardController for zooming in and out 
	public void ChangePerspective(Pane canvas, int percentage) {
		if (canvas != null) {
			canvas.setScaleX(percentage / 100.0);
			canvas.setScaleY(percentage / 100.0);
		}
	}
	
//	Clear method for clearing all elements
	public void Clear(AnchorPane node) {
		node.getChildren().clear();
	}
	
}