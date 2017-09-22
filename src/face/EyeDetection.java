package face;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;


import time.ThreadTimeManagerRush;

public class EyeDetection extends JFrame {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	private static final long serialVersionUID = 1L;
	public static JLabel lbl = new JLabel();
	public static Mat debugImage;
	public static Mat skinCrCbHist = new Mat(new Size(256, 256), CvType.CV_8UC1);
	public static CascadeClassifier faceCascade;
	public static String faceCascadeName = "C:/resources (permanent!)/cascadeclassifier/haarcascade_frontalface_alt.xml";
	public EyeDetection() {
		this.setLayout(new FlowLayout());
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Eye Detection");
		this.setSize(1920, 1080);
		this.add(lbl);
	}
	
	public static void main(String[] args) {
		new EyeDetection();
		VideoCapture camera = new VideoCapture(0);
		ThreadTimeManagerRush tmgr = new ThreadTimeManagerRush(50);
	
		Mat frame = new Mat();
		camera.read(frame);
		
		if(!camera.isOpened()) {
			System.out.println("camera init failed");
		}
		
		faceCascade = new CascadeClassifier();
		if(!faceCascade.load(faceCascadeName)) {System.out.println("--(!)Error loading face cascade, please change faceCascadeName in source code"); System.exit(0);}
		
	
		BufferedImage image = Util.mat2BufferedImage(frame);
		tmgr.start();
		
		while(!Thread.interrupted()) {
			camera.read(frame);
			//Core.flip(frame, frame, 1);
			Imgproc.resize(frame, frame, new Size(1920, 1080), 0, 0, Imgproc.INTER_CUBIC);
			image = Util.mat2BufferedImage(frame);
			displayImage(image);
			tmgr.run();
		}
	}
	
	public static void displayImage(BufferedImage frame) {
		ImageIcon icon = new ImageIcon(frame);
		lbl.setIcon(icon);
	}

	public static void findEyes(Mat frame_gray, Rect face) {
		Mat faceROI = frame_gray.adjustROI(face.y, face.height, face.x, face.width);
		Mat debugFace = faceROI;
		
		if(Constants.kSmoothFaceImage) {
			double sigma = face.width * 0.005;
			Imgproc.GaussianBlur(faceROI, faceROI, new Size(0, 0), sigma);
		}
		
		int eye_region_width = (int) (face.width * .35);
		int eye_region_height = (int) face.width;
	}
	
}
