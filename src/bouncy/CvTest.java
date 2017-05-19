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
		tmgr.silence();
		tmgr.start();
		
		bi = ImageIO.read(new File("C:/cool-beans.jpg"));
		
		int textPieces = 50;
		
		String[] messages = new String[textPieces];
		Arrays.fill(messages, "yes haha very good");
		
		TextItem[] textItems = new TextItem[textPieces] ;
		
		for(int i=0;i<messages.length;i++) {
			textItems[i] = new TextItem(rnd.nextInt(matXSize), rnd.nextInt(matYSize), Directions.values()[rnd.nextInt(2)+2], Directions.values()[rnd.nextInt(2)], messages[i]);		
		}
		
		while(!Thread.interrupted()) {
			mat = new Mat(matYSize, matXSize, CvType.CV_8UC3, new Scalar(0));
			
			for(int i=1;i<textItems.length-1;i++) {
				textItems[i] = overlayText(messages[i], Core.FONT_HERSHEY_COMPLEX, textItems[i]);	
			}
			
		//	screenItems[0] = overlayImage(bi, screenItems[0]);
			
			displayImage(frame, mat2BufferedImage(mat));
			mat.release();
			
			tmgr.run();
		}
	}

	public static TextItem overlayImage(BufferedImage bi, TextItem screenItem) {
		Size imageSize = new Size(bi.getWidth(), bi.getHeight());
		TextItem newItem = moveText(imageSize, screenItem);
		mat_temp = bufferedImage2Mat(bi);
		
		try {
			mat_temp.copyTo(mat.submat(new Rect(newItem.xcord, newItem.ycord, mat_temp.cols(), mat_temp.rows())));
		} catch (Exception e) {}
		
		return newItem;
	}
	
	public static TextItem overlayText(String text, int font, TextItem item) {
		Size textSize = Imgproc.getTextSize(text, font, 1, 2, new int[] {0});
		TextItem newItem = moveText(textSize, item);
		Imgproc.putText(mat, text, new Point(newItem.xcord, newItem.ycord), font, 1.0, new Scalar(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255)), 2);
		return newItem;
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
			tempYDirection = item.getDirection()[1];
		}
		if(tempYDirection.equals(Directions.UP)) {
			tempYMovement+=-2;
		} else if(tempYDirection.equals(Directions.DOWN)){
			tempYMovement+=2;
		}
		
		if((item.xcord+size.width>=matXSize)) { //needs to switch to left
			tempXDirection = Directions.LEFT;
		} else if(item.xcord<=0) {	//needs to switch to right
			tempXDirection = Directions.RIGHT; 
		} else {
			tempXDirection = item.getDirection()[0];
		}
		if(tempXDirection.equals(Directions.LEFT)) {
			tempXMovement+=-2;
		} else if(tempXDirection.equals(Directions.RIGHT)){
			tempXMovement+=2;
		}
		
		return new TextItem(item.xcord+tempXMovement, item.ycord+tempYMovement, tempXDirection, tempYDirection, item.text);	
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