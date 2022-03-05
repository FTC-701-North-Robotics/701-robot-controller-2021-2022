package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.subsystem.Duck;
import org.firstinspires.ftc.teamcode.subsystem.Outtake;
import org.firstinspires.ftc.teamcode.vision.TargetLevel;
import org.firstinspires.ftc.teamcode.vision.Vision;
import org.firstinspires.ftc.teamcode.vision.Zach;

@Autonomous(name = "Red Duck to Park", group = "Competition")
public class AutoRedDuckToPark extends LinearOpMode {

	SampleMecanumDrive drive;
	Outtake outtake;
	Vision vision;
	TargetLevel targetLevel;
	Duck duck;

	@Override
	public void runOpMode() throws InterruptedException {
		drive = new SampleMecanumDrive(hardwareMap);
		outtake = new Outtake(hardwareMap);
		duck = new Duck(hardwareMap);
		vision = new Zach(hardwareMap);

		vision.openDashboardStream();

		Pose2d startPose = new Pose2d(-24, -60, Math.toRadians(90));

		drive.setPoseEstimate(startPose);

		TrajectorySequence trajectory = drive
			.trajectorySequenceBuilder(startPose)
			.splineToConstantHeading(new Vector2d(-13, -44), Math.toRadians(90))
			.addTemporalMarker(() -> {
				updateTelemetry();
				vision.getTargetLevel();
				if (targetLevel == TargetLevel.BOTTOM) {
					outtake.Winch.bottom();
				} else if (targetLevel == TargetLevel.MIDDLE) {
					outtake.Winch.middle();
				} else if (targetLevel == TargetLevel.TOP) {
					outtake.Winch.up();
				}
			})
			.waitSeconds(2)
			.addTemporalMarker(() -> outtake.Box.dump())
			.waitSeconds(0.5)
			.addTemporalMarker(() -> outtake.Box.reset())
			.addTemporalMarker(() -> outtake.Winch.bottom())
			.waitSeconds(0.5)
			//			to Ducks
			.back(10)
			.splineToLinearHeading(
				new Pose2d(-55, -55, Math.toRadians(135)),
				Math.toRadians(135)
			)
			.addTemporalMarker(() -> duck.setPower(0.3))
			.waitSeconds(0.5)
			.addTemporalMarker(() -> duck.setPower(0))
			//			to park
			.splineTo(new Vector2d(-60, -36), Math.toRadians(90))
			.build();

		targetLevel = vision.getTargetLevel();
		updateTelemetry();

		waitForStart();
		targetLevel = vision.getTargetLevel();
		vision.closeDashboardStream();
		vision.close();
		updateTelemetry();

		drive.followTrajectorySequence(trajectory);
	}

	void updateTelemetry() {
//		telemetry.addData("Vision", targetLevel.toString());
//		telemetry.update();

	}
}
