package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Current Winch Position
 */
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

	/**
	 * Outtake Subsystem
	 *
	 * @param hardwareMap Current OpMode's hardwareMap
	 */
	public OutTake(HardwareMap hardwareMap) {
		winch = hardwareMap.get(DcMotor.class, "outtakeWinch");
		outTakeBox = hardwareMap.get(Servo.class, "outtakeBoxServo");

		winchTouch = hardwareMap.get(TouchSensor.class, "winchTouch");

		winch.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

		Box = new OutTake.Box();
	}

	/**
	 * Sub class for Winch control
	 */
	public class Winch {
		public final double MAX_SPEED = 0.9;
		public final double MIDDLE_TARGET = 255;
		public final double MIDDLE_LENIANCY = 10;

		/**
		 * Brings winch to up position
		 */
		public void up() {
			while (winch.getCurrentPosition() < 600) {
				winch.setPower(MAX_SPEED);
			}
			winch.setPower(0.0);
		}

		/**
		 * Brings winch to middle position
		 */
		public void middle() {
			toPosition(MIDDLE_TARGET, MIDDLE_LENIANCY);
		}

		/**
		 * Brings winch to down position
		 */
		public void down() {
			while (!winchTouch.isPressed()) {
				winch.setPower(-MAX_SPEED);
			}
			winch.setPower(0.0);
			winch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
			winch.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		}

		/**
		 * Runs winch to position
		 *
		 * @param target   Target Position
		 * @param leniancy Allowed Deviation from target position
		 */
		public void toPosition(double target, double leniancy) {
			while (winch.getCurrentPosition() - target > leniancy) {
				if (winch.getCurrentPosition() - target > 0) {
					winch.setPower(-MAX_SPEED);
				}
				if (winch.getCurrentPosition() - target < 0) {
					winch.setPower(MAX_SPEED);
				}
			}
		}

		/**
		 * Sets power of winch
		 *
		 * @param power power
		 */
		public void setManualPower(double power) {
			winch.setPower(power);
			winchPosition = WinchPosition.MANUAL;
		}
	}

	/**
	 * Controls outtake box
	 */
	public class Box {
		/**
		 * Dumps contents of box
		 */
		public void dump() {
			outTakeBox.setPosition(0.35);
		}

		/**
		 * Resets box position
		 */
		public void reset() {
			outTakeBox.setPosition(0.9);
		}
	}
}
