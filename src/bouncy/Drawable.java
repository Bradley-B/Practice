package bouncy;
import bouncy.CvTest.Directions;

public class Drawable {
	
	public Directions xDirection = null;
	public Directions yDirection = null;
	public int xcord = 0;
	public int ycord = 0;
	public double xModifier = 0;
	public double yModifier = 0;
	
	public Drawable(int xcord, int ycord, Directions xDirection, Directions yDirection, double xModifier, double yModifier) {
		this.xcord = xcord;
		this.ycord = ycord;
		this.xDirection = xDirection;
		this.yDirection = yDirection;
		this.xModifier = xModifier;
		this.yModifier = yModifier;
	}
	
	public void setCords(int xcord, int ycord) {
		this.xcord = xcord;
		this.ycord = ycord;
	}
	
	public void setDirection(Directions xDirection, Directions yDirection) {
		this.xDirection = xDirection;
		this.yDirection = yDirection;
	}
	
	public void setModifiers(double xDirectionModifier, double yDirectionModifier) {
		this.xModifier = xDirectionModifier;
		this.yModifier = yDirectionModifier;
	}
}
