package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

enum IntakePosition {
	UP,
	DOWN,
}

public class Intake {
	public TouchSensor inTouch;

	public CRServo leftIntake;
	public CRServo rightIntake;
	public IntakePosition intakePos = IntakePosition.UP;
	private DcMotor dropDown;

	public Intake(HardwareMap hardwareMap) {
		leftIntake = hardwareMap.get(CRServo.class, "leftIntake");
		rightIntake = hardwareMap.get(CRServo.class, "rightIntake");

		leftIntake.setDirection(CRServo.Direction.REVERSE);

		dropDown = hardwareMap.get(DcMotor.class, "intakeDrop");
		dropDown.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
	}

	public void SetIntakeSpeed(double Speed) {
		leftIntake.setPower(Speed);
		rightIntake.setPower(Speed);
	}
}