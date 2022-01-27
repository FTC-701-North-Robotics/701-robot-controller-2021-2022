package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

enum IntakePosition {
	UP,
	DOWN,
}

public class Intake {
	private final double DROP_SPEED = 0.2;
	public TouchSensor inTouch;
	public ElapsedTime elapsedTime;
	public CRServo leftIntake;
	public CRServo rightIntake;
	public IntakePosition intakePos = IntakePosition.UP;
	private DcMotor dropDown;

	/**
	 * Subsystem class for intake
	 *
	 * @param hardwareMap Current OpMode hardware map
	 */
	public Intake(HardwareMap hardwareMap) {
		leftIntake = hardwareMap.get(CRServo.class, "leftIntake");
		rightIntake = hardwareMap.get(CRServo.class, "rightIntake");

		leftIntake.setDirection(CRServo.Direction.REVERSE);

		dropDown = hardwareMap.get(DcMotor.class, "intakeDrop");
		dropDown.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
	}

	/**
	 * Sets intake to down position
	 */
	public void down() {
		while (dropDown.getCurrentPosition() < -90) {
			dropDown.setPower(-DROP_SPEED / 2);
		}
		dropDown.setPower(0);
	}

	/**
	 * Sets intake to up position
	 */
	public void up() {
		while (!inTouch.isPressed()) {
			dropDown.setPower(DROP_SPEED);
		}
		dropDown.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		dropDown.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
	}

	/**
	 * Gets the current position of the drop down
	 *
	 * @return current position
	 */
	public double currentPos() {
		return dropDown.getCurrentPosition();
	}

	/**
	 * Manual override of speed, useful in teleop
	 *
	 * @param Speed
	 */
	public void setDropSpeed(double Speed) {
		dropDown.setPower(Speed);
	}

	/**
	 * Sets the speed of the intake wheels,
	 * Note: positive value pulls objects in
	 *
	 * @param Speed of wheels, between -1.0 and 1.0
	 */
	public void setIntakeSpeed(double Speed) {
		leftIntake.setPower(Speed);
		rightIntake.setPower(Speed);
	}
}