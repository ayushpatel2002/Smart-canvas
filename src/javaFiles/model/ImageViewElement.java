package javaFiles.model;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class ImageViewElement implements Resize, Rotate{
//	We implement the appropriate interfaces
	
	public ImageView formImageView(Label outputLabel){
		ImageView ImgV = new ImageView();
//		Image image  = new Image("/Images/DefaultPhoto.jpeg");
		ImgV.setFitHeight(100);
		ImgV.setFitWidth(100);
//		ImgV.setImage(image);
		return ImgV;
	}

	

// we use setFitWidth and getFitWidth to play with size of imageView 
	@Override
	public void shrink(Node element) {
		((ImageView) element).setFitWidth(((ImageView) element).getFitWidth() - 2);
		((ImageView) element).setFitHeight(((ImageView) element).getFitHeight() - 2);
		
	}

	@Override
	public void enlarge(Node element) {
		((ImageView) element).setFitWidth(((ImageView) element).getFitWidth() + 2);
		((ImageView) element).setFitHeight(((ImageView) element).getFitHeight() + 2);
		
	}

//	Rotate the image view by setRotate
	@Override
	public void RotateElement(Node element, double degrees) {
		((ImageView) element).setRotate(degrees);
	}
	
	
}
