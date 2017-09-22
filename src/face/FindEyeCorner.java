package face;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Range;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class FindEyeCorner {
	public Mat leftCornerKernel;
	public Mat rightCornerKernel;

	// not constant because stupid opencv type signatures
	float kEyeCornerKernel[][] = new float[][]{
	  {-1,-1,-1, 1, 1, 1},
	  {-1,-1,-1,-1, 1, 1},
	  {-1,-1,-1,-1, 0, 3},
	  { 1, 1, 1, 1, 1, 1},
	};

	public void createCornerKernels() {
	  rightCornerKernel = new Mat(4, 6, CvType.CV_32F);
	  leftCornerKernel = new Mat(4,6,CvType.CV_32F); 
	  // flip horizontally
	  Core.flip(rightCornerKernel, leftCornerKernel, 1);
	}

	public void releaseCornerKernels() {
	  leftCornerKernel.release();
	  rightCornerKernel.release();
	}

	public Mat eyeCornerMap(final Mat region, boolean left, boolean left2) {
	  Mat cornerMap = new Mat();

	  Size sizeRegion = region.size();
	  Range colRange = new Range((int) sizeRegion.width / 4, (int) sizeRegion.width * 3 / 4);
	  Range rowRange = new Range((int) sizeRegion.height / 4, (int) sizeRegion.height * 3 / 4);

	  Mat miRegion = new Mat(region, rowRange, colRange);

	  Imgproc.filter2D(miRegion, cornerMap, CvType.CV_32F,
	               (left && !left2) || (!left && !left2) ? leftCornerKernel : rightCornerKernel);

	  return cornerMap;
	}

}
