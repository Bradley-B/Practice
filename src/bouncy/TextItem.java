package bouncy;

import bouncy.CvTest.Directions;

public class TextItem extends Drawable {

	public String text = null;
	
	public TextItem(int xcord, int ycord, Directions xDirection, Directions yDirection, String text) {
		super(xcord, ycord, xDirection, yDirection);
		this.text = text;
	}

}
