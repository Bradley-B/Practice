package bouncy;

import bouncy.CvTest.Directions;

public class TextItem extends Drawable {

	public String text = null;
	public int font;
	
	public TextItem(int xcord, int ycord, Directions xDirection, Directions yDirection, String text, int font, double xModifier, double yModifier) {
		super(xcord, ycord, xDirection, yDirection, xModifier, yModifier);
		this.text = text;
		this.font = font;
	}

}
