package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Current Winch Position
 */
enum WinchPosition {
	UP,
	MIDDLE,
	DOWN,
	MANUAL,
}

public class Outtake {

	public Outtake.Box Box;
	public Outtake.Winch Winch;

	public DcMotor winch = null;
	public Servo outTakeBox = null;
	public WinchPosition winchPosition = WinchPosition.DOWN;

	/**
	 * Outtake Subsystem
	 *
	 * @param hardwareMap Current OpMode's hardwareMap
	 */
	public Outtake(HardwareMap hardwareMap) {
		winch = hardwareMap.get(DcMotor.class, "outtakeWinch");
		outTakeBox = hardwareMap.get(Servo.class, "outtakeBoxServo");

		winch.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		winch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

		Winch = new Outtake.Winch();
		Box = new Outtake.Box();
	}

	/**
	 * Sub class for Winch control
	 */
	public class Winch {

		public final double MAX_SPEED = 0.9;

		/**
		 * Brings winch to up position
		 */
		public void up() {
			winch.setMode(DcMotor.RunMode.RUN_TO_POSITION);
			winch.setTargetPosition(0);
		}

		/**
		 * Brings winch to middle position
		 */
		public void middle() {
			winch.setMode(DcMotor.RunMode.RUN_TO_POSITION);
			winch.setTargetPosition(0);
		}

		/**
		 * Brings winch to down position
		 */
		public void down() {
			winch.setMode(DcMotor.RunMode.RUN_TO_POSITION);
			winch.setTargetPosition(0);
		}

		/**
		 * Runs winch to position
		 *
		 * @param target   Target Position
		 * @param leniancy Allowed Deviation from target position
		 */
		@Deprecated
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
