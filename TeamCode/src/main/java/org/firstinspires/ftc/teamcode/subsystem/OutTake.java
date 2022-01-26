package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

enum WinchPosition {
	UP,
	MIDDLE,
	DOWN,
	MANUAL,
}

public class OutTake {

	public static OutTake.Box Box;
	public static OutTake.Winch Winch;

	public DcMotor winch = null;
	public Servo outTakeBox = null;
	public WinchPosition winchPosition = WinchPosition.DOWN;
	public TouchSensor winchTouch;

	public OutTake(HardwareMap hardwareMap) {
		winch = hardwareMap.get(DcMotor.class, "outtakeWinch");
		outTakeBox = hardwareMap.get(Servo.class, "outtakeBoxServo");

		winchTouch = hardwareMap.get(TouchSensor.class, "winchTouch");

		winch.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

		Box = new OutTake.Box();
	}

	public class Winch {

		public final double MAX_SPEED = 0.9;

		public final double MIDDLE_TARGET = 255;
		public final double MIDDLE_LENIANCY = 10;

		public void up() {
			while (winch.getCurrentPosition() < 600) {
				winch.setPower(MAX_SPEED);
			}
			winch.setPower(0.0);
		}

		public void middle() {
			Integer pos = winch.getCurrentPosition();
			while (pos - MIDDLE_TARGET > MIDDLE_LENIANCY) {
				if (pos - MIDDLE_TARGET > 0) {
					winch.setPower(-MAX_SPEED);
				}
				if (pos - MIDDLE_TARGET < 0) {
					winch.setPower(MAX_SPEED);
				}
			}
		}

		public void down() {
			while (!winchTouch.isPressed()) {
				winch.setPower(-MAX_SPEED);
			}
			winch.setPower(0.0);
			winch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
			winch.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		}

		public void setManualPower(double power) {
			winch.setPower(power);
			winchPosition = WinchPosition.MANUAL;
		}
	}

	public class Box {
		public void Dump() {
			outTakeBox.setPosition(0.35);
		}

		public void Reset() {
			outTakeBox.setPosition(0.9);
		}
	}
}
