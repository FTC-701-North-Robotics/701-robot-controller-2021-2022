package org.firstinspires.ftc.teamcode.subsystem;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Outtake {

	public static Double FLIP_OUT = 1.0;
	public static Double FLIP_IN = 0.1;
	public static Double BOX_OUT = 0.15;
	public static Double BOX_IN = 0.5;
	public Outtake.Box Box;
	public Outtake.Winch Winch;
	public DcMotorEx winch = null;
	public Servo outtakeBox = null;
	public Servo outtakeFlip = null;
	public WinchPosition winchPosition = WinchPosition.DOWN;


	/**
	 * Outtake Subsystem
	 *
	 * @param hardwareMap Current OpMode's hardwareMap
	 */
	public Outtake(HardwareMap hardwareMap) {
		winch = hardwareMap.get(DcMotorEx.class, "outtakeWinch");
		outtakeBox = hardwareMap.get(Servo.class, "outtakeBoxServo");
		outtakeFlip = hardwareMap.get(Servo.class, "outtakeFlipServo");


		winch.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		winch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		winch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

		winch.setDirection(DcMotorSimple.Direction.REVERSE);

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
		public void bottom() {
			winch.setTargetPosition(400);
			winch.setMode(DcMotor.RunMode.RUN_TO_POSITION);
			winch.setVelocity(50);
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
			winch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
			winch.setPower(power);
			winchPosition = WinchPosition.MANUAL;
		}

		/**
		 * Blocks thread untill motor is done
		 */
		public void block() {
			while (winch.isBusy()) {
			}
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
			outtakeBox.setPosition(BOX_OUT);
			outtakeFlip.setPosition(FLIP_OUT);
		}

		/**
		 * Resets box position
		 */
		public void reset() {
			outtakeBox.setPosition(BOX_IN);
			outtakeFlip.setPosition(FLIP_IN);
		}
	}
}
