package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.roadrunner.DriveShim;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequenceBuilder;

public class duckClass implements classInterface {
	public static TrajectorySequenceBuilder trajectory(DriveShim drive) {
		return drive.trajectorySequenceBuilder(new Pose2d(-24, 60, Math.toRadians(-90)))
			.splineToConstantHeading(new Vector2d(-13, 44), Math.toRadians(-90))
//			to Ducks
			.back(10)
			.splineToLinearHeading(new Pose2d(-55, 55, Math.toRadians(-135)), Math.toRadians(-135))
			.splineTo(new Vector2d(-60, 36), Math.toRadians(-90))
			;

	}

	public static TrajectorySequenceBuilder altTrajectory(DriveShim drive) {
		return drive.trajectorySequenceBuilder(new Pose2d(-24, 60, Math.toRadians(-90)))
			.splineToConstantHeading(new Vector2d(-13, 44), Math.toRadians(-90))
//			to Ducks
			.back(10)
			.splineToLinearHeading(new Pose2d(-55, 55, Math.toRadians(45)), Math.toRadians(45))
			.waitSeconds(12)
			.splineTo(new Vector2d(-15, 63), Math.toRadians(0))
			.forward(65)
			;

	}

}
