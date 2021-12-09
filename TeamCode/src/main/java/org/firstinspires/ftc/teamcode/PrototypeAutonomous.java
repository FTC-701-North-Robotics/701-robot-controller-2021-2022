package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "PrototypeAutonomous")
public class PrototypeAutonomous extends LinearOpMode {

	// TODO: get camera

	// Declare Motors
	private DcMotor lbDrive = null;
	private DcMotor rbDrive = null;
	private DcMotor lfDrive = null;
	private DcMotor rfDrive = null;

	// Declare Motor power values
	private double lbDrivePower;
	private double rbDrivePower;
	private double lfDrivePower;
	private double rfDrivePower;

	private DcMotor spinny = null;

	private DcMotor winch = null;

	private CRServo leftIntake = null;
	private CRServo rightIntake = null;

	@Override
	public void runOpMode() throws InterruptedException {
		lbDrive = hardwareMap.get(DcMotor.class, "leftRear");  // These hardware map names
		rbDrive = hardwareMap.get(DcMotor.class, "rightRear"); // are compatable with RoadRunners
		lfDrive = hardwareMap.get(DcMotor.class, "leftFront"); // Defaults
		rfDrive = hardwareMap.get(DcMotor.class, "rightFront");

		spinny = hardwareMap.get(DcMotor.class, "spinny");
		winch = hardwareMap.get(DcMotor.class, "winch");


		winch.setTargetPosition(0);
		winch.setMode(DcMotor.RunMode.RUN_TO_POSITION);

		lbDrive.setDirection(DcMotor.Direction.FORWARD);
		lfDrive.setDirection(DcMotor.Direction.FORWARD);
		rbDrive.setDirection(DcMotor.Direction.REVERSE);
		rfDrive.setDirection(DcMotor.Direction.REVERSE);

		spinny.setDirection(DcMotor.Direction.REVERSE);

		leftIntake.setDirection(DcMotorSimple.Direction.FORWARD);
		leftIntake.setDirection(DcMotorSimple.Direction.REVERSE);

		waitForStart();

		DuckPosition duck = getDuckPos();

		switch (duck) {
			case TOP:

		}


	}


	public DuckPosition getDuckPos() {
		return DuckPosition.BOTTOM;
	}
}

enum DuckPosition {
	TOP,
	MIDDLE,
	BOTTOM,
}