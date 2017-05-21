package bouncy;

import org.opencv.core.Mat;

import bouncy.CvTest.Directions;

public class ImageItem extends Drawable{

	public Mat image = null;
	
	public ImageItem(int xcord, int ycord, Directions xDirection, Directions yDirection, double xModifier, double yModifier, Mat image) {
		super(xcord, ycord, xDirection, yDirection, xModifier, yModifier);
		this.image = image;
	}
	
	

}
