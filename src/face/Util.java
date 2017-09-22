package face;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.Point;
import org.opencv.core.Rect;

public class Util {
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
	
	public static boolean rectInImage(Rect rect, Mat image) {
		return (rect.x > 0) && (rect.y > 0) && (rect.x+rect.width < image.cols()) && (rect.y+rect.height < image.rows());
	}
	
	public static boolean inMat(Point p, Mat mat) {
		return (p.x >= 0) && (p.x < mat.cols()) && (p.y >= 0) && (p.y < mat.rows());
	}
	
	public static Mat matrixMagnitude(Mat matX, Mat matY) {
	    Mat mags=new Mat(matX.size(),CvType.CV_64F);
	    for (int y = 0; y < matX.size().height; ++y) {
	        for (int x = 0; x < matX.size().width; ++x) {
	            double gX = matX.get(y,x)[0];
	            double gY = matY.get(y,x)[0];
	            double magnitude = Math.sqrt((gX * gX) + (gY * gY));

	            mags.put(y,x,magnitude);
	        }
	    }
	    return mags;
	}
			
	public static Mat computeXGradient(Mat mat) {
	    //Mat output = new Mat(mat.rows(), mat.cols(), CvType.CV_64F);
	    Mat output = new Mat(mat.rows(), mat.cols(), CvType.CV_32F);
	    for (byte y = 0; y < mat.rows(); ++y) {
	        Mat mr = mat.row(y);
	        output.put(y,0, mr.get(0,1)[0] - mr.get(0,0)[0]);
	        for (byte x = 1; x < mat.cols() - 1; ++x) {
	            output.put(y,x, (mr.get(0,x+1)[0] - mr.get(0,x-1)[0])/2.0);
	        }
	    }
	    return output;
	}
	
	public static double computeDynamicThreshold(Mat mat, double stdDevFactor) {
	    MatOfDouble stdMagnGrad=new MatOfDouble();
	    MatOfDouble meanMagnGrad=new MatOfDouble();
	    Core.meanStdDev(mat, meanMagnGrad, stdMagnGrad);

	    double stdDev = stdMagnGrad.get(0,0)[0] / Math.sqrt(mat.size().height*mat.size().width);
	    return stdDevFactor * stdDev + meanMagnGrad.get(0,0)[0];
	}
}
