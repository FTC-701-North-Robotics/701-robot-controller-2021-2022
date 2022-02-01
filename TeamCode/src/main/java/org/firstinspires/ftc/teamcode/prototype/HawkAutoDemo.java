package org.firstinspires.ftc.teamcode.prototype;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;

@Autonomous(name = "mendricken", group = "prototype")
public class HawkAutoDemo extends LinearOpMode {

	Pose2d startPose = new Pose2d(-64, -54, Math.toRadians(0));

	@Override
	public void runOpMode() throws InterruptedException {
		SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
		drive.setPoseEstimate(startPose);

		waitForStart();
		if (isStopRequested()) return;

		Trajectory moveToPowerShotLaunch = drive
			.trajectoryBuilder(startPose)
			.splineTo(new Vector2d(-6, -54), 0)
			.build();

		Trajectory moveToDropZoneNoRings = drive
			.trajectoryBuilder(moveToPowerShotLaunch.end())
			.splineTo(new Vector2d(0, -58), Math.toRadians(-30))
			.build();

		Trajectory backUpFromPowerShot = drive
			.trajectoryBuilder(moveToDropZoneNoRings.end())
			.back(12)
			.build();

		Trajectory moveToSecondWobbleGoalNoRings = drive
			.trajectoryBuilder(backUpFromPowerShot.end())
			.splineTo(new Vector2d(-31, -36.5), Math.toRadians(170))
			.build();

		Trajectory pickUpSecondWobbleGoalNoRings = drive
			.trajectoryBuilder(moveToSecondWobbleGoalNoRings.end())
			.forward(4)
			.build();

		Trajectory moveToDropZoneNoRingsSecond = drive
			.trajectoryBuilder(pickUpSecondWobbleGoalNoRings.end())
			.splineTo(new Vector2d(12, -40), Math.toRadians(-70))
			.build();

		Trajectory parkNoRings = drive
			.trajectoryBuilder(moveToDropZoneNoRingsSecond.end())
			.back(12)
			.build();

		Trajectory moveToDropZoneFirstOneRing = drive
			.trajectoryBuilder(moveToPowerShotLaunch.end())
			.splineTo(new Vector2d(20, -40), 0)
			.build();

		Trajectory moveToRingsOneRing = drive
			.trajectoryBuilder(moveToDropZoneFirstOneRing.end())
			.back(48)
			.build();

		Trajectory moveToSecondWobbleGoalOneRing = drive
			.trajectoryBuilder(moveToRingsOneRing.end())
			.splineTo(new Vector2d(-26, -36), Math.toRadians(180))
			.forward(9)
			.build();

		Trajectory moveToDropZoneSecondOneRing = drive
			.trajectoryBuilder(moveToSecondWobbleGoalOneRing.end())
			.splineTo(new Vector2d(-40, -38), Math.toRadians(0))
			.splineTo(new Vector2d(20, -32), 0)
			.build();

		Trajectory parkOneRings = drive
			.trajectoryBuilder(moveToDropZoneSecondOneRing.end())
			.back(12)
			.build();

		Trajectory moveToDropZoneFirstQuadRings = drive
			.trajectoryBuilder(moveToPowerShotLaunch.end())
			.splineTo(new Vector2d(40, -58), 0)
			.build();

		Trajectory moveToRingsQuadRings = drive
			.trajectoryBuilder(moveToDropZoneFirstQuadRings.end(), true)
			.splineTo(new Vector2d(18, -38), Math.toRadians(180))
			.back(34)
			.build();

		Trajectory moveToRingsQuadRingsSecond = drive
			.trajectoryBuilder(moveToRingsQuadRings.end())
			.back(6)
			.build();

		Trajectory moveToRingsQuadRingsForward = drive
			.trajectoryBuilder(moveToRingsQuadRingsSecond.end())
			.forward(6)
			.build();

		Trajectory moveToRingsQuadRingsThird = drive
			.trajectoryBuilder(moveToRingsQuadRingsForward.end())
			.back(12)
			.build();

		Trajectory moveToRingsQuadRingsFourth = drive
			.trajectoryBuilder(moveToRingsQuadRingsThird.end())
			.back(6)
			.build();

		Trajectory moveToSecondWobbleGoalQuadRings = drive
			.trajectoryBuilder(moveToRingsQuadRingsFourth.end())
			.splineTo(new Vector2d(-26, -28), Math.toRadians(220))
			.forward(8)
			.build();

		Trajectory moveToDropZoneSecondQuadRings = drive
			.trajectoryBuilder(moveToSecondWobbleGoalQuadRings.end())
			.splineTo(new Vector2d(36, -54), Math.toRadians(0))
			.build();

		Trajectory parkQuadRings = drive
			.trajectoryBuilder(moveToRingsQuadRingsFourth.end())
			.forward(48)
			.build();

		drive.followTrajectory(moveToPowerShotLaunch);

		drive.followTrajectory(moveToDropZoneFirstQuadRings);

		drive.followTrajectory(moveToRingsQuadRings);
		sleep(750);
		drive.followTrajectory(moveToRingsQuadRingsSecond);
		sleep(750);
		drive.followTrajectory(moveToRingsQuadRingsThird);
		sleep(750);

		//Shoot High Goal
		drive.turn(Math.toRadians(4));
		sleep(100);

		drive.followTrajectory(moveToRingsQuadRingsFourth);
		sleep(1200);

		//Shoot High Goal
		drive.turn(Math.toRadians(4));
	}
}
