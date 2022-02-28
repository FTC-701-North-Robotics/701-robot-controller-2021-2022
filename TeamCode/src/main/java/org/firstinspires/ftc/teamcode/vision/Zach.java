package org.firstinspires.ftc.teamcode.vision;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.subsystem.Camera;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

// Note: Auto-generated from .class file please dont try to understand it
public class Zach implements Vision {

	TargetLevel targetPosition;
	Camera webcam;

	public Zach(HardwareMap hardwareMap) {
		webcam = new Camera(hardwareMap);
		webcam.setPipeline(new Pipeline());
	}

	@Override
	public TargetLevel getTargetLevel() {
		return targetPosition;
	}

	@Override
	public void close() {
		webcam.close();
	}

	@Override
	public void openDashboardStream() {
		webcam.openDashboardStream();
	}

	@Override
	public void closeDashboardStream() {
		webcam.closeDashboardStream();
	}

	class Pipeline extends OpenCvPipeline {

		private final Mat YCbCr = new Mat();
		private final Mat outPut = new Mat();
		private final int rect1Y = 1;
		private Mat crop1, crop2, crop3, crop1bl, crop2bl, crop3bl, crop1gr, crop2gr, crop3gr, crop1re, crop2re, crop3re;
		private Scalar colfin1, colfin2, colfin3;
		private double avgfin1re, avgfin2re, avgfin3re, avgfin1gr, avgfin2gr, avgfin3gr, avgfin1bl, avgfin2bl, avgfin3bl =
			0.0;
		private int rect1X, rect2X, rect2Y, rect3X, rect3Y;

		public Mat processFrame(Mat input) {
			rect2X = webcam.streamWidth / 3;
			rect2Y = 1;
			rect3X = webcam.streamWidth / 3 * 2;
			rect3Y = 1;
			colfin1 = new Scalar(0, 0, 0);
			colfin2 = new Scalar(0, 0, 0);
			colfin3 = new Scalar(0, 0, 0);

			input.copyTo(outPut);
			Imgproc.cvtColor(input, YCbCr, Imgproc.COLOR_RGB2YCrCb);
			Rect rect1 = new Rect(
				rect1X,
				rect1Y,
				webcam.streamHeight / 3 - 10,
				webcam.streamHeight - 10
			);
			Rect rect2 = new Rect(
				rect2X,
				rect2Y,
				webcam.streamWidth / 3 - 10,
				webcam.streamHeight - 10
			);
			Rect rect3 = new Rect(
				rect3X,
				rect3Y,
				webcam.streamWidth / 3 - 10,
				webcam.streamHeight - 10
			);
			Scalar rectColor = new Scalar(255.0, 0.0, 0.0);

			Imgproc.rectangle(outPut, rect1, rectColor, 2);
			Imgproc.rectangle(outPut, rect2, rectColor, 2);
			Imgproc.rectangle(outPut, rect3, rectColor, 2);
			crop1 = YCbCr.submat(rect1);
			crop2 = YCbCr.submat(rect2);
			crop3 = YCbCr.submat(rect3);
			crop1gr = YCbCr.submat(rect1);
			crop2gr = YCbCr.submat(rect2);
			crop3gr = YCbCr.submat(rect3);
			crop1bl = YCbCr.submat(rect1);
			crop2bl = YCbCr.submat(rect2);
			crop3bl = YCbCr.submat(rect3);
			crop1re = YCbCr.submat(rect1);
			crop2re = YCbCr.submat(rect2);
			crop3re = YCbCr.submat(rect3);

			Core.extractChannel(crop1, crop1gr, 0);
			Core.extractChannel(crop2, crop2gr, 0);
			Core.extractChannel(crop3, crop3gr, 0);
			Core.extractChannel(crop1, crop1bl, 1);
			Core.extractChannel(crop2, crop2bl, 1);
			Core.extractChannel(crop3, crop3bl, 1);
			Core.extractChannel(crop1, crop1re, 2);
			Core.extractChannel(crop2, crop2re, 2);
			Core.extractChannel(crop3, crop3re, 2);

			Scalar avg1gr = Core.mean(crop1gr);
			Scalar avg2gr = Core.mean(crop2gr);
			Scalar avg3gr = Core.mean(crop3gr);
			Scalar avg1bl = Core.mean(crop1bl);
			Scalar avg2bl = Core.mean(crop2bl);
			Scalar avg3bl = Core.mean(crop3bl);
			Scalar avg1re = Core.mean(crop1re);
			Scalar avg2re = Core.mean(crop2re);
			Scalar avg3re = Core.mean(crop3re);

			avgfin1gr = avg1gr.val[0];
			avgfin2gr = avg2gr.val[0];
			avgfin3gr = avg3gr.val[0];
			avgfin1re = avg1bl.val[0];
			avgfin2re = avg2bl.val[0];
			avgfin3re = avg3bl.val[0];
			avgfin1bl = avg1re.val[0];
			avgfin2bl = avg2re.val[0];
			avgfin3bl = avg3re.val[0];

			if (avgfin1re > avgfin2re && avgfin1re > avgfin3re) {
				targetPosition = TargetLevel.BOTTOM;
			} else if (avgfin2re > avgfin1re && avgfin2re > avgfin3re) {
				targetPosition = TargetLevel.MIDDLE;
			} else {
				targetPosition = TargetLevel.TOP;
			}
			return outPut;
		}
	}
}
