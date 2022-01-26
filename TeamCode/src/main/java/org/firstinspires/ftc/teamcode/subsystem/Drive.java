package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drive {

	public DcMotor leftFront;
	public DcMotor leftRear;
	public DcMotor rightRear;
	public DcMotor rightFront;

	public Drive(HardwareMap hardwareMap) {
		leftFront = hardwareMap.get(DcMotor.class, "leftFront");
		leftRear = hardwareMap.get(DcMotor.class, "leftRear");
		rightRear = hardwareMap.get(DcMotor.class, "rightRear");
		rightFront = hardwareMap.get(DcMotor.class, "rightFront");

		rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
		rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
	}

	public void setMotorPowers(double v, double v1, double v2, double v3) {
		leftFront.setPower(v);
		leftRear.setPower(v1);
		rightRear.setPower(v2);
		rightFront.setPower(v3);
	}
}
