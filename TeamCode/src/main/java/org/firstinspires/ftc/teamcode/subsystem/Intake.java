package org.firstinspires.ftc.teamcode.subsystem;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_TO_POSITION;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.STOP_AND_RESET_ENCODER;

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
	private final DcMotor dropDown;
	public TouchSensor inTouch;
	public ElapsedTime elapsedTime;
	public CRServo intake;
	public IntakePosition intakePos = IntakePosition.UP;

	/**
	 * Subsystem class for intake
	 *
	 * @param hardwareMap Current OpMode hardware map
	 */
	public Intake(HardwareMap hardwareMap) {
		intake = hardwareMap.get(CRServo.class, "intake");

		intake.setDirection(CRServo.Direction.REVERSE);

		dropDown = hardwareMap.get(DcMotor.class, "intakeDrop");
		dropDown.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		dropDown.setMode(STOP_AND_RESET_ENCODER);
	}

	/**
	 * Sets intake to down position
	 */
	public void down() {
		dropDown.setMode(RUN_TO_POSITION);
		dropDown.setTargetPosition(0);
	}

	/**
	 * Sets intake to up position
	 */
	public void up() {
		dropDown.setMode(RUN_TO_POSITION);
		dropDown.setTargetPosition(100);
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
	 * Manual override of power, useful in teleop
	 *
	 * @param Power
	 */
	public void setDropSpeed(double Power) {
		dropDown.setPower(Power);
	}

	/**
	 * Sets the speed of the intake wheels,
	 * Note: positive value pulls objects in
	 *
	 * @param Speed of wheels, between -1.0 and 1.0
	 */
	public void setIntakeSpeed(double Speed) {
		intake.setPower(Speed);
	}
}
