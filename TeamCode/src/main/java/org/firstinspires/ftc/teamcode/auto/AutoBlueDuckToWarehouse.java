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

@Autonomous(name = "Blue Duck to Warehouse", group = "Competition")
public class AutoBlueDuckToWarehouse extends LinearOpMode {
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


		TrajectorySequence trajectory = drive.trajectorySequenceBuilder(new Pose2d(-24, 60, Math.toRadians(-90)))
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
			.addTemporalMarker(() -> outtake.Winch.bottom())
			.waitSeconds(0.5)


//			to Ducks
			.back(10)
			.splineToLinearHeading(new Pose2d(-55, 55, Math.toRadians(45)), Math.toRadians(45))
			.addTemporalMarker(() -> duck.setPower(0.3))
			.waitSeconds(1)
			.addTemporalMarker(() -> duck.setPower(0))

//			to park
			.waitSeconds(11)
			.splineTo(new Vector2d(-15, 63), Math.toRadians(0))
			.forward(65)
			.build();

		waitForStart();
		targetLevel = vision.getTargetLevel();
		vision.closeDashboardStream();
		vision.close();


		drive.followTrajectorySequence(trajectory);


	}
}
