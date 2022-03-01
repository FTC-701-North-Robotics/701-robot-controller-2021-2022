package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.roadrunner.DriveShim;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequenceBuilder;

public class warehouseClass implements classInterface {
	public static TrajectorySequenceBuilder trajectory(DriveShim drive) {
		return drive.trajectorySequenceBuilder(new Pose2d(12, -60, Math.toRadians(90)))
			.splineToConstantHeading(new Vector2d(-13, -44), Math.toRadians(90))
//					Cycle one
			.splineToLinearHeading(new Pose2d(15, -65, Math.toRadians(-180)), Math.toRadians(0))
			.back(40)
			.forward(40)
			.splineTo(new Vector2d(-13, -44), Math.toRadians(90))
//					Cycle Two
			.splineToLinearHeading(new Pose2d(15, -65, Math.toRadians(-180)), Math.toRadians(0))
			.back(40)
			.forward(40)
			.splineTo(new Vector2d(-13, -44), Math.toRadians(90))
//					Parking
			.splineToLinearHeading(new Pose2d(15, -65, Math.toRadians(-180)), Math.toRadians(0))
			.back(30);
	}
}
