package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.drive.StandardTrackingWheelLocalizer;


@TeleOp(name = "TeleOpPrototype", group = "Prototypes")
public class TeleOpPrototypeNoRR extends LinearOpMode {
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

	private DcMotor dropDown = null;

	private CRServo leftIntake = null;
	private CRServo rightIntake = null;

	private Servo outTakeBox = null;


	@Override
	public void runOpMode() {
		telemetry.addData("Status", "Initialized");
		telemetry.update();

		lbDrive = hardwareMap.get(DcMotor.class, "leftRear");  // These hardware map names
		rbDrive = hardwareMap.get(DcMotor.class, "rightRear"); // are compatable with RoadRunners
		lfDrive = hardwareMap.get(DcMotor.class, "leftFront"); // Defaults
		rfDrive = hardwareMap.get(DcMotor.class, "rightFront");

		spinny = hardwareMap.get(DcMotor.class, "spinny");
		winch = hardwareMap.get(DcMotor.class, "winch");

		leftIntake = hardwareMap.get(CRServo.class, "leftIntake");
		leftIntake = hardwareMap.get(CRServo.class, "rightIntake");

		dropDown = hardwareMap.get(DcMotor.class, "dropDown");

		leftIntake.setDirection(DcMotorSimple.Direction.REVERSE);

		//winch.setTargetPosition(300);
		//winch.setMode(DcMotor.RunMode.RUN_TO_POSITION);

		lbDrive.setDirection(DcMotor.Direction.FORWARD);
		lfDrive.setDirection(DcMotor.Direction.FORWARD);
		rbDrive.setDirection(DcMotor.Direction.REVERSE);
		rfDrive.setDirection(DcMotor.Direction.REVERSE);

		spinny.setDirection(DcMotor.Direction.REVERSE);

		winch.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT); // flaccid when no power

		waitForStart();

		// run until the end of the match (driver presses STOP)
		while (opModeIsActive()) {

			double drive = gamepad1.left_stick_y;
			double turn = gamepad1.right_stick_x;
			double strafe = gamepad1.left_stick_x;

			double spinInput = gamepad1.left_trigger;

			double intakeSpeed = gamepad2.right_trigger * gamepad2.right_trigger; // Parabola, works because we only need it to go fowards
			double outtakeServo = gamepad2.left_trigger * 180 + 10; // trigger + total angle + offset

			lbDrivePower = drive - turn - strafe;
			rbDrivePower = drive + turn + strafe;
			lfDrivePower = drive - turn + strafe;
			rfDrivePower = drive + turn - strafe;

			lbDrive.setPower(lbDrivePower);
			rbDrive.setPower(rbDrivePower);
			lfDrive.setPower(lfDrivePower);
			rfDrive.setPower(rfDrivePower);

			leftIntake.setPower(intakeSpeed);
			rightIntake.setPower(intakeSpeed);

//			if (intakeSpeed >= 0.1) {
//				leftIntake.setPower(intakeSpeed);
//				rightIntake.setPower(intakeSpeed);
//			} else if (gamepad2.right_bumper) {
//				leftIntake.setPower(-intakeSpeed);
//				rightIntake.setPower(-intakeSpeed);
//				telemetry.addData("INTAKE REVERSED", "^^");
//			} else {
//				leftIntake.setPower(0);
//				rightIntake.setPower(0);
//			}

			spinny.setPower(spinInput);

			outTakeBox.setPosition(outtakeServo);

			dropDown.setPower(gamepad2.left_stick_y);

			// Winch Control + telemetry
			if (gamepad2.dpad_up) {
				winch.setPower(0.2);
				telemetry.addData("Winch Movement", "UP");
			} else if (gamepad2.dpad_down) {
				winch.setPower(-0.2);
				telemetry.addData("Winch Movement", "DOWN");
			} else {
				winch.setPower(0);
				telemetry.addData("Winch Movement", "NEUTRAL");
			}

			telemetry.addData("Motors", "left back (%.2f), right back (%.2f), left front (%.2f), right front (%.2f)", lbDrivePower, rbDrivePower, lfDrivePower, rfDrivePower);
			telemetry.addData("Spinny Thing Motor", spinInput);
			telemetry.addData("Intake Power", gamepad2.right_trigger);
			telemetry.addData("Drop Down Motor", gamepad2.left_stick_y);


			telemetry.update();
		}
	}
}
