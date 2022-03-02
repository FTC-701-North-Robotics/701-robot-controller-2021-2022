package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.subsystem.Duck;
import org.firstinspires.ftc.teamcode.subsystem.Intake;
import org.firstinspires.ftc.teamcode.subsystem.Outtake;
import org.firstinspires.ftc.teamcode.vision.TargetLevel;
import org.firstinspires.ftc.teamcode.vision.Vision;
import org.firstinspires.ftc.teamcode.vision.Zach;

@Autonomous(name = "Blue Warehouse", group = "Competition")
public class AutoBlueWarehouse extends LinearOpMode {

	SampleMecanumDrive drive;
	Outtake outtake;
	Vision vision;
	TargetLevel targetLevel;
	Duck duck;
	Intake intake;

	@Override
	public void runOpMode() throws InterruptedException {
		drive = new SampleMecanumDrive(hardwareMap);
		outtake = new Outtake(hardwareMap);
		duck = new Duck(hardwareMap);
		intake = new Intake(hardwareMap);
		vision = new Zach(hardwareMap);

		vision.openDashboardStream();

		TrajectorySequence trajectory = drive
			.trajectorySequenceBuilder(new Pose2d(12, -60, Math.toRadians(-90)))
			.splineToConstantHeading(new Vector2d(-13, 44), Math.toRadians(-90))
			.addTemporalMarker(() -> {
				switch (targetLevel) {
					case TOP:
						outtake.Winch.up();
						break;
					case MIDDLE:
						outtake.Winch.middle();
						break;
					case BOTTOM:
						outtake.Winch.bottom();
				}
			})
			.waitSeconds(2)
			.addTemporalMarker(() -> outtake.Box.dump())
			.waitSeconds(0.5)
			.addTemporalMarker(() -> outtake.Box.reset())
			.waitSeconds(0.2)
			.addTemporalMarker(() -> outtake.Winch.bottom())
			.waitSeconds(0.5)
			.back(10)
			.splineToLinearHeading(
				new Pose2d(15, 65, Math.toRadians(180)),
				Math.toRadians(0)
			)
			.addTemporalMarker(() -> {
				intake.down();
				intake.setIntakeSpeed(0.2);
			})
			.back(40)
			.forward(40)
			.splineToConstantHeading(new Vector2d(12, 62), Math.toRadians(0))
			.turn(Math.toRadians(180))
			.splineTo(new Vector2d(15, 65), Math.toRadians(0))
			.addTemporalMarker(() -> outtake.Winch.up())
			.waitSeconds(2)
			.addTemporalMarker(() -> outtake.Box.dump())
			.waitSeconds(0.2)
			.addTemporalMarker(() -> {
				outtake.Box.reset();
				outtake.Winch.bottom();
			})
			.splineToLinearHeading(
				new Pose2d(15, -65, Math.toRadians(-180)),
				Math.toRadians(0)
			)
			.back(30)
			.build();

		waitForStart();
		targetLevel = vision.getTargetLevel();
		vision.closeDashboardStream();
		vision.close();

		drive.followTrajectorySequence(trajectory);
	}
}
