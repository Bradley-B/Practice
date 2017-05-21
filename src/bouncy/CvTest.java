package bouncy;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import time.ThreadTimeManagerRush;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;


public class CvTest {
	static JFrame frame = new JFrame();
	static JLabel lbl = new JLabel();
	static Mat mat = null;
	static Mat overlay = null;
	static Mat mat_temp = null;
	static BufferedImage bi = null;
	static Random rnd = new Random();
	static final int matXSize = 1920;
	static final int matYSize = 1080;
	
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	enum Directions {
		 UP, DOWN, LEFT, RIGHT
	}
	
	public CvTest() {
		frame.setLayout(new FlowLayout());	
		frame.add(lbl);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("CvTest");
		frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH );
	//	frame.setSize(matXSize, matYSize);
	}
	
	public static void main(String[] args) throws IOException {
		
		new CvTest();
		
		ThreadTimeManagerRush tmgr = new ThreadTimeManagerRush(20);
	//	tmgr.silence();
		tmgr.start();
		tmgr.enableDebug();
		
		bi = ImageIO.read(new File("C:/Users/Bradley/Pictures/deadlyLaser.jpg"));
		overlay = bufferedImage2Mat(bi);
		
		int textPieces = 20;
		
		String[] messages = new String[textPieces];
		Arrays.fill(messages, "the sun is a deadly laser");
		
		TextItem[] textItems = new TextItem[textPieces] ;
		ImageItem imageItem = new ImageItem(rnd.nextInt(matXSize-overlay.cols()), rnd.nextInt(matYSize-overlay.rows()),
				Directions.values()[rnd.nextInt(2)+2], Directions.values()[rnd.nextInt(2)], Math.floor((rnd.nextDouble()*3)+1), Math.floor((rnd.nextDouble()*3)+1), overlay);
		
		for(int i=0;i<messages.length;i++) {
			textItems[i] = new TextItem(rnd.nextInt(matXSize), rnd.nextInt(matYSize), Directions.values()[rnd.nextInt(2)+2],
					Directions.values()[rnd.nextInt(2)], messages[i], Core.FONT_HERSHEY_COMPLEX, rnd.nextDouble()*3, rnd.nextDouble()*3);		
		}
		
		while(!Thread.interrupted()) {
			mat = new Mat(matYSize, matXSize, CvType.CV_8UC3, new Scalar(0));
			
			for(int i=0;i<textItems.length;i++) {
				textItems[i] = overlayText(textItems[i]);	
			}
			
			imageItem = overlayImage(imageItem);
			
			displayImage(frame, mat2BufferedImage(mat));
			mat.release();
			
			tmgr.run();
		}
	}

	public static ImageItem overlayImage(ImageItem item) {
		ImageItem newItem = moveImage(item);
		
		try {
			//System.out.println("currently overlaying image at "+newItem.xcord+", "+newItem.ycord);
			newItem.image.copyTo(mat.submat(new Rect(newItem.xcord, newItem.ycord, newItem.image.cols(), newItem.image.rows())));
		} catch (Exception e) {}
		
		return newItem;
	}
	
	public static TextItem overlayText(TextItem item) {
		Size textSize = Imgproc.getTextSize(item.text, item.font, 1, 2, new int[] {0});
		TextItem newItem = moveText(textSize, item);
		Imgproc.putText(mat, item.text, new Point(newItem.xcord, newItem.ycord), item.font, 1.0, new Scalar(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255)), 2);
		return newItem;
	}
	
	public static ImageItem moveImage(ImageItem item) {
		int tempXMovement = 0;
		int tempYMovement = 0;
		Directions tempXDirection = null;
		Directions tempYDirection = null;
		Size size = new Size(item.image.cols(), item.image.rows());
		
		if(item.ycord+size.height>=matYSize-item.yModifier) { //needs to switch to up
			tempYDirection = Directions.UP;
		} else if(item.ycord<=item.yModifier+1) { //needs to switch to down
			tempYDirection = Directions.DOWN;
		} else {
			tempYDirection = item.yDirection;
		}
		if(tempYDirection.equals(Directions.UP)) {
			tempYMovement+=-2*item.yModifier;
		} else if(tempYDirection.equals(Directions.DOWN)){
			tempYMovement+=2*item.yModifier;
		}
		
		if((item.xcord+size.width+item.xModifier+1>=matXSize)) { //needs to switch to left
			tempXDirection = Directions.LEFT;
		} else if(item.xcord<=item.xModifier-1) {	//needs to switch to right
			tempXDirection = Directions.RIGHT; 
		} else {
			tempXDirection = item.xDirection;
		}
		if(tempXDirection.equals(Directions.LEFT)) {
			tempXMovement+=-2*item.xModifier;
		} else if(tempXDirection.equals(Directions.RIGHT)){
			tempXMovement+=2*item.xModifier;
		}
		
		return new ImageItem(item.xcord+tempXMovement, item.ycord+tempYMovement, tempXDirection, tempYDirection, item.xModifier, item.yModifier, item.image);
	}
	
	public static TextItem moveText(Size size, TextItem item) {
		int tempXMovement = 0;
		int tempYMovement = 0;
		Directions tempXDirection = null;
		Directions tempYDirection = null;
		
		if(item.ycord+(size.height*4)>=matYSize) { //needs to switch to up
			tempYDirection = Directions.UP;
		} else if(item.ycord-(size.height)<=0) { //needs to switch to down
			tempYDirection = Directions.DOWN;
		} else {
			tempYDirection = item.yDirection;
		}
		if(tempYDirection.equals(Directions.UP)) {
			tempYMovement+=-2*item.yModifier;
		} else if(tempYDirection.equals(Directions.DOWN)){
			tempYMovement+=2*item.yModifier;
		}
		
		if((item.xcord+size.width>=matXSize)) { //needs to switch to left
			tempXDirection = Directions.LEFT;
		} else if(item.xcord<=0) {	//needs to switch to right
			tempXDirection = Directions.RIGHT; 
		} else {
			tempXDirection = item.xDirection;
		}
		if(tempXDirection.equals(Directions.LEFT)) {
			tempXMovement+=-2*item.xModifier;
		} else if(tempXDirection.equals(Directions.RIGHT)){
			tempXMovement+=2*item.xModifier;
		}
		
		return new TextItem(item.xcord+tempXMovement, item.ycord+tempYMovement, tempXDirection, tempYDirection,
				item.text, item.font, item.xModifier, item.yModifier);	
	}
	
	public static void drawEllipse(Mat m, int xCenter, int yCenter, int width, int height, int rotation, Scalar color, int thickness) {
		Imgproc.ellipse(m, new RotatedRect(new Point(xCenter, yCenter), new Size(width, height), rotation), color, thickness);
	}
	
	public static Mat bufferedImage2Mat(BufferedImage image) {
		mat_temp = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3, new Scalar(0));
		byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		mat_temp.put(0, 0, pixels);
		return mat_temp;
	}
	
	public static BufferedImage mat2BufferedImage(Mat m) {
		int type = BufferedImage.TYPE_BYTE_GRAY;
		
		if ( m.channels() > 1 ) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		
		int bufferSize = m.channels()*m.cols()*m.rows();
		byte [] b = new byte[bufferSize];
		m.get(0,0,b); // get all the pixels
		BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(b, 0, targetPixels, 0, b.length);  
		return image;

	}

	public static void displayImage(JFrame frame, Image img) {   
		ImageIcon icon=new ImageIcon(img);
		//frame.setSize(img.getWidth(null), img.getHeight(null));     
		lbl.setIcon(icon);	
	}
}