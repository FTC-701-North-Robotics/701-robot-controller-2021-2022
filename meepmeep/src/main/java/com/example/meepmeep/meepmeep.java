package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class meepmeep {
	public static void main(String[] args) {
		MeepMeep meepMeep = new MeepMeep(600);
		RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
			// Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
			.setConstraints(30, 30, Math.toRadians(180), Math.toRadians(180), 15)
			.followTrajectorySequence(drive ->
				drive.trajectorySequenceBuilder(new Pose2d(12, -60, Math.toRadians(90)))
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
					.back(30)

					.build()
			);

		meepMeep.setBackground(MeepMeep.Background.FIELD_FREIGHTFRENZY_ADI_DARK)
			.setDarkMode(true)
			.setBackgroundAlpha(0.95f)
			.addEntity(myBot)
			.start();
	}
}