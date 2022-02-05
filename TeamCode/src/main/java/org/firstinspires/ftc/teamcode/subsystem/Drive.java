package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drive {

	public DcMotor leftFront;
	public DcMotor leftRear;
	public DcMotor rightRear;
	public DcMotor rightFront;

	/**
	 * Subsystem for mechnum drive train
	 *
	 * @param hardwareMap Current OpMode's hardware map
	 */
	public Drive(HardwareMap hardwareMap) {
		leftFront = hardwareMap.get(DcMotor.class, "leftFront");
		leftRear = hardwareMap.get(DcMotor.class, "leftRear");
		rightRear = hardwareMap.get(DcMotor.class, "rightRear");
		rightFront = hardwareMap.get(DcMotor.class, "rightFront");

		rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
		rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
	}

	/**
	 * Sets the individual motor powers
	 * Power values from -1.0 to 1.0
	 *
	 * @param leftFrontPower
	 * @param leftRearPower
	 * @param rightRearPower
	 * @param rightFrontPower
	 */
	public void setMotorPowers(
		double leftFrontPower,
		double leftRearPower,
		double rightRearPower,
		double rightFrontPower
	) {
		leftFront.setPower(leftFrontPower);
		leftRear.setPower(leftRearPower);
		rightRear.setPower(rightRearPower);
		rightFront.setPower(rightFrontPower);
	}

	/**
	 * Sets power according to x, y and heading
	 *
	 * @param x       srafing value
	 * @param y       foward and backwards
	 * @param heading rotational value
	 */
	public void vectorDrive(double x, double y, double heading) {
		leftRear.setPower(y - heading + x);
		leftFront.setPower(y - heading - x);
		rightRear.setPower(y + heading - x);
		rightFront.setPower(y + heading + x);
	}

	/**
	 * Sets power according to x, y
	 *
	 * @param x strafing value
	 * @param y foward and backwards
	 */
	public void vectorDrive(double x, double y) {
		leftRear.setPower(y - x);
		leftFront.setPower(y + x);
		rightRear.setPower(y + x);
		rightFront.setPower(y - x);
	}
}
