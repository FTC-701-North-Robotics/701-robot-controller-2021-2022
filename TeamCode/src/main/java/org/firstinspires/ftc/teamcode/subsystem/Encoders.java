package org.firstinspires.ftc.teamcode.subsystem;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.roadrunner.util.Encoder;

@Config
public class Encoders {

	public static Double SERVO_RELEASE = 0.0;
	public static Double SERVO_RETRACT = 1.0;

	public Encoder backEncoder;
	public Encoder middleEncoder;
	public Encoder frontEncoder;

	public boolean isRetracted;

	public Servo servo0;
	public Servo servo1;
	public Servo servo2;

	public Encoders(HardwareMap hardwareMap) {
		backEncoder =
			new Encoder(hardwareMap.get(DcMotorEx.class, "rightFront"));
		middleEncoder =
			new Encoder(hardwareMap.get(DcMotorEx.class, "rightRear"));
		frontEncoder =
			new Encoder(hardwareMap.get(DcMotorEx.class, "leftFront"));

		servo0 = (hardwareMap.get(Servo.class, "servo0"));
		servo1 = (hardwareMap.get(Servo.class, "servo1"));
		servo2 = (hardwareMap.get(Servo.class, "servo2"));
	}

	public void Retract() {
		servo0.setPosition(SERVO_RETRACT);
		servo1.setPosition(SERVO_RETRACT);
		servo2.setPosition(SERVO_RETRACT);
		isRetracted = true;
	}

	public void Release() {
		servo0.setPosition(SERVO_RELEASE);
		servo1.setPosition(SERVO_RELEASE);
		servo2.setPosition(SERVO_RELEASE);
		isRetracted = false;
	}

	public void Toggle() {
		if (isRetracted) {
			Release();
		} else {
			Retract();
		}
	}
}
