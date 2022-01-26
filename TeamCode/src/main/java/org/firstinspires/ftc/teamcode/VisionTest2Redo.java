package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera.AsyncCameraOpenListener;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous
public class VisionTest2Redo extends OpMode {

	OpenCvWebcam webcam = null;
	static boolean left = false;
	static boolean right = false;
	static boolean middle = false;
	double averageXbl = 0.0;
	double averageXor = 0.0;
	int width = 1920;
	int height = 1080;
	Mat outPut = new Mat();
	private int threshold = 25;
	private Random rng = new Random(12345);
	private Mat srcGray = new Mat();

	public void init() {
		WebcamName webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
		int cameraMonitorViewId = hardwareMap.appContext
			.getResources()
			.getIdentifier(
				"cameraMonitorViewId",
				"id",
				hardwareMap.appContext.getPackageName()
			);
		webcam =
			OpenCvCameraFactory
				.getInstance()
				.createWebcam(webcamName, cameraMonitorViewId);
		webcam.setPipeline(new BoundingBox());
		webcam.openCameraDeviceAsync(
			new AsyncCameraOpenListener() {
				public void onOpened() {
					webcam.startStreaming(
						1024,
						576,
						OpenCvCameraRotation.UPRIGHT
					);
				}

				public void onError(int errorCode) {}
			}
		);
	}

	public void loop() {}

	class BoundingBox extends OpenCvPipeline {

		Mat HSV = new Mat();
		Mat edges = new Mat();
		Mat thresh = new Mat();
		Mat hierarchy = new Mat();
		Mat HSV2 = new Mat();
		Mat edges2 = new Mat();
		Mat thresh2 = new Mat();
		Mat hierarchy2 = new Mat();
		int total = 0;
		int total2 = 0;
		int i = 0;
		int i2 = 0;

		public Mat processFrame(Mat input) {
			Imgproc.cvtColor(input, HSV, 41);
			Scalar lowHSV = new Scalar(90.0, 150.0, 70.0);
			Scalar highHSV = new Scalar(120.0, 255.0, 255.0);
			Core.inRange(HSV, lowHSV, highHSV, thresh);
			Imgproc.Canny(thresh, edges, 100.0, 300.0);
			List<MatOfPoint> contours = new ArrayList();
			Imgproc.findContours(
				edges,
				contours,
				hierarchy,
				Imgproc.RETR_TREE,
				Imgproc.CHAIN_APPROX_SIMPLE
			);
			MatOfPoint2f[] contoursPoly = new MatOfPoint2f[contours.size()];
			Rect[] boundRect = new Rect[contours.size()];
			total = 0;

			for (i = 0; i < contours.size(); ++i) {
				contoursPoly[i] = new MatOfPoint2f();
				Imgproc.approxPolyDP(
					new MatOfPoint2f(((MatOfPoint) contours.get(i)).toArray()),
					contoursPoly[i],
					3.0,
					true
				);
				boundRect[i] =
					Imgproc.boundingRect(
						new MatOfPoint(contoursPoly[i].toArray())
					);
				total += boundRect[i].x;
			}

			if (contours.size() > 0) {
				averageXbl = (double) (total / contours.size());
			}

			String avg = String.valueOf(averageXbl);
			telemetry.addLine("Blue Average:" + avg);
			Imgproc.cvtColor(input, HSV2, Imgproc.COLOR_RGB2HSV);
			Scalar lowHSV2 = new Scalar(12.0, 170.0, 90.0);
			Scalar highHSV2 = new Scalar(18.0, 255.0, 255.0);
			Core.inRange(HSV2, lowHSV2, highHSV2, thresh2);
			Imgproc.Canny(thresh2, edges2, 100.0, 300.0);
			List<MatOfPoint> contours2 = new ArrayList();
			Imgproc.findContours(edges2, contours2, hierarchy2, 3, 2);
			MatOfPoint2f[] contoursPoly2 = new MatOfPoint2f[contours2.size()];
			Rect[] boundRect2 = new Rect[contours2.size()];
			total2 = 0;

			for (i2 = 0; i2 < contours2.size(); ++i2) {
				contoursPoly2[i2] = new MatOfPoint2f();
				Imgproc.approxPolyDP(
					new MatOfPoint2f(
						((MatOfPoint) contours2.get(i2)).toArray()
					),
					contoursPoly2[i2],
					3.0,
					true
				);
				boundRect2[i2] =
					Imgproc.boundingRect(
						new MatOfPoint(contoursPoly2[i2].toArray())
					);
				total2 += boundRect2[i2].x;
			}

			if (contours2.size() > 0) {
				averageXor = (double) (total2 / contours2.size());
			}

			String avg2 = String.valueOf("Orange Average:" + averageXor);
			telemetry.addLine(avg2);
			if (averageXbl >= averageXor + 400.0) {
				telemetry.addLine("Left");
			} else if (averageXor >= averageXbl + 400.0) {
				telemetry.addLine("Right");
			} else {
				telemetry.addLine("Middle");
			}

			return edges;
		}
	}
}
