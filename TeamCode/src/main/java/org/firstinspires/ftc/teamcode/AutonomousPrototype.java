package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.checkerframework.checker.units.qual.C;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystem.Camera;
import org.firstinspires.ftc.teamcode.vision.Zach;

@Autonomous(name = "AutonomousPrototype", group = "prototype")
public class AutonomousPrototype extends LinearOpMode {

	public static double DISTANCE = 50;

	@Override
	public void runOpMode() throws InterruptedException {
		SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
		Zach camera = new Zach(hardwareMap);
		camera.openDashboardStream();

		Trajectory trajectoryForward = drive
			.trajectoryBuilder(new Pose2d())
			.forward(DISTANCE)
			.build();

		Trajectory trajectoryBackward = drive
			.trajectoryBuilder(trajectoryForward.end())
			.back(DISTANCE)
			.build();

		waitForStart();

		while (opModeIsActive() && !isStopRequested()) {
			drive.followTrajectory(trajectoryForward);
			drive.followTrajectory(trajectoryBackward);
			telemetry.addData("Current Target: ", String.valueOf(camera.getTargetLevel()));
			telemetry.update();
		}
	}
}
