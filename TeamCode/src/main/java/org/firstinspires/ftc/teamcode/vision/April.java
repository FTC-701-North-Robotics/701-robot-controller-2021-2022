package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystem.Camera;
import org.openftc.apriltag.AprilTagDetection;

import java.util.ArrayList;

public class April implements Vision {

	static final double FEET_PER_METER = 3.28084;
	public AprilTagDetection marker;
	public Camera camera;
	private double fx = 578.272;
	private double fy = 578.272;
	private double cx = 402.145;
	private double cy = 221.506;
	// UNITS ARE METERS
	private double tagsize = 0.166;
	private int ID_TAG_OF_INTEREST = 18; // Tag ID 18 from the 36h11 family
	private AprilTagDetectionPipeline aprilTagDetectionPipeline;

	April(HardwareMap hardwareMap) {
		camera = new Camera(hardwareMap);
		aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);
		camera.setPipeline(aprilTagDetectionPipeline);

	}

	@Override
	public TargetLevel getTargetLevel() {
		ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();
		marker = currentDetections.get(0);

		if (marker.center.x <= 20) {
			return TargetLevel.TOP;
		} else if (marker.center.x >= 20 && marker.center.x <= 50) {
			return TargetLevel.MIDDLE;
		} else if (marker.center.x >= 50) {
			return TargetLevel.BOTTOM;
		} else  {
			return null;
		}
	}

	@Override
	public void close() {
		camera.close();
	}

	@Override
	public void openDashboardStream() {
		camera.openDashboardStream();
	}

	@Override
	public void closeDashboardStream() {
		camera.closeDashboardStream();
	}
}
