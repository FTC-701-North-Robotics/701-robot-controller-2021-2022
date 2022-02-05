package org.firstinspires.ftc.teamcode.subsystem;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

public class Camera {
	public OpenCvWebcam webcam;
	public int streamWidth = 640;
	public int streamHeight = 360;

	
	public Camera(HardwareMap hardwareMap) {
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
		webcam.openCameraDeviceAsync(
			new OpenCvCamera.AsyncCameraOpenListener() {
				public void onOpened() {
					webcam.startStreaming(
						streamWidth,
						streamHeight,
						OpenCvCameraRotation.UPRIGHT
					);
				}

				public void onError(int errorCode) {
				}
			}
		);
	}

	public void openDashboardStream() {
		FtcDashboard.getInstance().startCameraStream(webcam, 0);
	}

	public void closeDashboardStream() {
		FtcDashboard.getInstance().stopCameraStream();
	}

	public void setPipeline(OpenCvPipeline pipeline) {
		webcam.setPipeline(pipeline);
	}

	public void close() {
		webcam.closeCameraDevice();
	}
}
