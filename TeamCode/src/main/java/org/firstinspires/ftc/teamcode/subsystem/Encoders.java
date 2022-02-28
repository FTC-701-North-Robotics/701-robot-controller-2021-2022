package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.roadrunner.util.Encoder;

public class Encoders {

	public Encoder leftEncoder;
	public Encoder rightEncoder;
	public Encoder frontEncoder;

	public Encoders(HardwareMap hardwareMap) {
		leftEncoder =
			new Encoder(hardwareMap.get(DcMotorEx.class, "rightFront"));
		rightEncoder =
			new Encoder(hardwareMap.get(DcMotorEx.class, "rightRear"));
		frontEncoder =
			new Encoder(hardwareMap.get(DcMotorEx.class, "leftFront"));
	}
}
