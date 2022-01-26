package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera.AsyncCameraOpenListener;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous
public class VisionTest1Redo extends OpMode {

	OpenCvWebcam webcam = null;
	int streamWidth = 1024;
	int streamHeight = 576;
	double avgfin1re = 0.0;
	double avgfin2re = 0.0;
	double avgfin3re = 0.0;
	double avgfin1gr = 0.0;
	double avgfin2gr = 0.0;
	double avgfin3gr = 0.0;
	double avgfin1bl = 0.0;
	double avgfin2bl = 0.0;
	double avgfin3bl = 0.0;
	String position = "middle";

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
		webcam.setPipeline(new zachpipeline());
		telemetry.addLine("setting pipeline");
		webcam.openCameraDeviceAsync(
			new AsyncCameraOpenListener() {
				public void onOpened() {
					telemetry.addLine("camera started");
					webcam.startStreaming(
						streamWidth,
						streamHeight,
						OpenCvCameraRotation.UPRIGHT
					);
				}

				public void onError(int errorCode) {}
			}
		);
	}

	public void loop() {}

	class zachpipeline extends OpenCvPipeline {

		Mat YCbCr = new Mat();
		Mat outPut = new Mat();
		int rect1X = 1;
		int rect1Y = 1;
		int rect2X;
		int rect2Y;
		int rect3X;
		int rect3Y;
		Mat crop1;
		Mat crop2;
		Mat crop3;
		Mat crop1bl;
		Mat crop2bl;
		Mat crop3bl;
		Mat crop1gr;
		Mat crop2gr;
		Mat crop3gr;
		Mat crop1re;
		Mat crop2re;
		Mat crop3re;
		Scalar colfin1;
		Scalar colfin2;
		Scalar colfin3;

		public Mat processFrame(Mat input) {
			rect2X = streamWidth / 3;
			rect2Y = 1;
			rect3X = streamWidth / 3 * 2;
			rect3Y = 1;
			colfin1 = new Scalar(0, 0, 0);
			colfin2 = new Scalar(0, 0, 0);
			colfin3 = new Scalar(0, 0, 0);

			input.copyTo(outPut);
			Imgproc.cvtColor(input, YCbCr, Imgproc.COLOR_RGB2YCrCb);
			telemetry.addLine("pipeline running");
			Rect rect1 = new Rect(
				rect1X,
				rect1Y,
				streamWidth / 3 - 10,
				streamHeight - 10
			);
			Rect rect2 = new Rect(
				rect2X,
				rect2Y,
				streamWidth / 3 - 10,
				streamHeight - 10
			);
			Rect rect3 = new Rect(
				rect3X,
				rect3Y,
				streamWidth / 3 - 10,
				streamHeight - 10
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
				telemetry.addLine("Left");
			} else if (avgfin2re > avgfin1re && avgfin2re > avgfin3re) {
				telemetry.addLine("Middle");
			} else {
				telemetry.addLine("Right");
			}

			return outPut;
		}
	}
}
