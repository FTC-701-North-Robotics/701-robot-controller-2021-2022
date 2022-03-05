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

@Autonomous(name = "Blue Duck no Vision", group = "Competition")
public class AutoBlueDuckNoVision extends LinearOpMode {

	SampleMecanumDrive drive;
	Outtake outtake;
	TargetLevel targetLevel;
	Duck duck;

	@Override
	public void runOpMode() throws InterruptedException {
		drive = new SampleMecanumDrive(hardwareMap);
		outtake = new Outtake(hardwareMap);
		duck = new Duck(hardwareMap);

		Pose2d startPose = new Pose2d(-24, 60, Math.toRadians(-90));
		drive.setPoseEstimate(startPose);

		TrajectorySequence trajectory = drive
			.trajectorySequenceBuilder(startPose)
			//			to Ducks
			.splineTo(new Vector2d(-55, 55), Math.toRadians(-135))
			.addTemporalMarker(() -> duck.setPower(0.3))
			.waitSeconds(0.5)
			.addTemporalMarker(() -> duck.setPower(0))
			//			to park
			.splineTo(new Vector2d(-60, 36), Math.toRadians(-90))
			.build();

		waitForStart();

		drive.followTrajectorySequence(trajectory);
	}
}
