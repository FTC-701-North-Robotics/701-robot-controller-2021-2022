package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Duck {
	public DcMotor motor;

	/**
	 * Duck subsystem
	 *
	 * @param hardwareMap Current OpMode's hardware map
	 */
	public Duck(HardwareMap hardwareMap) {
		motor = hardwareMap.get(DcMotor.class, "duckSpin");

		motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
	}

	/**
	 * Sets power of duck motor
	 *
	 * @param power power
	 */
	public void setPower(double power) {
		motor.setPower(power);
	}


}
