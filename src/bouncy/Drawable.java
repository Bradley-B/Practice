package bouncy;
import bouncy.CvTest.Directions;

public class Drawable {
	
	public Directions xDirection = null;
	public Directions yDirection = null;
	public int xcord = 0;
	public int ycord = 0;
	public double xDirectionModifier = 0;
	public double yDirectionModifier = 0;
	
	public Drawable(int xcord, int ycord, Directions xDirection, Directions yDirection) {
		this.xcord = xcord;
		this.ycord = ycord;
		this.xDirection = xDirection;
		this.yDirection = yDirection;
	}
	
	public void setCords(int xcord, int ycord) {
		this.xcord = xcord;
		this.ycord = ycord;
	}
	
	public void setDirection(Directions xDirection, Directions yDirection) {
		this.xDirection = xDirection;
		this.yDirection = yDirection;
	}
	
	public Directions[] getDirection() {
		return new Directions[] {xDirection, yDirection};
	}
	
	public void setModifiers(double xDirectionModifier, double yDirectionModifier) {
		this.xDirectionModifier = xDirectionModifier;
		this.yDirectionModifier = yDirectionModifier;
	}
	
	public double[] getModifiers() {
		return new double[] {xDirectionModifier, yDirectionModifier};
	}
	
}
