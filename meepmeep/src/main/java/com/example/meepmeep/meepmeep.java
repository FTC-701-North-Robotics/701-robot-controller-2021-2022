package com.example.meepmeep;

import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class meepmeep {
	public static void main(String[] args) {
		MeepMeep meepMeep = new MeepMeep(600);
		RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
			// Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
			.setConstraints(30, 30, Math.toRadians(180), Math.toRadians(180), 15)
			.followTrajectorySequence(drive -> duckClass.altTrajectory(drive).build());

		meepMeep.setBackground(MeepMeep.Background.FIELD_FREIGHTFRENZY_ADI_DARK)
			.setDarkMode(true)
			.setBackgroundAlpha(0.95f)
			.addEntity(myBot)
			.start();
	}
}