package org.firstinspires.ftc.teamcode.roadrunner.drive;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.localization.ThreeTrackingWheelLocalizer;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.roadrunner.util.Encoder;
import org.firstinspires.ftc.teamcode.subsystem.Encoders;

import java.util.Arrays;
import java.util.List;

/*
 * Sample tracking wheel localizer implementation assuming the standard configuration:
 *
 *    /--------------\
 *    |     ____     |
 *    |     ----     |
 *    | ||        || |
 *    | ||        || |
 *    |              |
 *    |              |
 *    \--------------/
 *
 */
@Config
public class StandardTrackingWheelLocalizer
	extends ThreeTrackingWheelLocalizer {

	public static double TICKS_PER_REV = 8192;
	public static double WHEEL_RADIUS = 2; // in
	public static double GEAR_RATIO = 1; // output (wheel) speed / input (encoder) speed

	public static double FRONT_X = 8;
	public static double MIDDLE_X = 2.5;
	public static double BACK_X = -3;

	private final Encoder backEncoder;
	private final Encoder middleEncoder;
	private final Encoder frontEncoder;
	private final Encoders encoders;

	public StandardTrackingWheelLocalizer(HardwareMap hardwareMap) {
		// Lmao jankest odometry setup imaginable
		super(
			Arrays.asList(
				new Pose2d(BACK_X, 0, Math.toRadians(-90)), // back
				new Pose2d(MIDDLE_X, 0, 0), // middle
				new Pose2d(FRONT_X, 0, Math.toRadians(-90)) // front
			)
		);
		encoders = new Encoders(hardwareMap);

		backEncoder = encoders.backEncoder;
		middleEncoder = encoders.middleEncoder;
		frontEncoder = encoders.frontEncoder;

		middleEncoder.setDirection(Encoder.Direction.REVERSE);
		// TODO: reverse any encoders using Encoder.setDirection(Encoder.Direction.REVERSE)
	}

	public static double encoderTicksToInches(double ticks) {
		return WHEEL_RADIUS * 2 * Math.PI * GEAR_RATIO * ticks / TICKS_PER_REV;
	}

	@NonNull
	@Override
	public List<Double> getWheelPositions() {
		return Arrays.asList(
			encoderTicksToInches(backEncoder.getCurrentPosition()),
			encoderTicksToInches(middleEncoder.getCurrentPosition()),
			encoderTicksToInches(frontEncoder.getCurrentPosition())
		);
	}

	@NonNull
	@Override
	public List<Double> getWheelVelocities() {
		// TODO: If your encoder velocity can exceed 32767 counts / second (such as the REV Through Bore and other
		//  competing magnetic encoders), change Encoder.getRawVelocity() to Encoder.getCorrectedVelocity() to enable a
		//  compensation method

		return Arrays.asList(
			encoderTicksToInches(backEncoder.getCorrectedVelocity()),
			encoderTicksToInches(middleEncoder.getCorrectedVelocity()),
			encoderTicksToInches(frontEncoder.getCorrectedVelocity())
		);
	}
}
