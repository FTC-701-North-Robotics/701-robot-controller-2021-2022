package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.roadrunner.util.Encoder;

public class Encoders {

	public Encoder backEncoder;
	public Encoder middleEncoder;
	public Encoder frontEncoder;

	public boolean isRetracted;

	public Encoders(HardwareMap hardwareMap) {
		backEncoder =
			new Encoder(hardwareMap.get(DcMotorEx.class, "rightFront"));
		middleEncoder =
			new Encoder(hardwareMap.get(DcMotorEx.class, "rightRear"));
		frontEncoder =
			new Encoder(hardwareMap.get(DcMotorEx.class, "leftFront"));
	}

	public void Retract() {
		isRetracted = true;
		throw new UnsupportedOperationException("Not implemented");
	}

	public void Release() {
		isRetracted = false;
		throw new UnsupportedOperationException("Not implemented");
	}

	public void Toggle() {
		if (isRetracted) {
			Release();
		} else {
			Retract();
		}
	}
}
