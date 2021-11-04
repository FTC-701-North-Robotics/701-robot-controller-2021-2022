package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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


	@Override
	public void runOpMode() {
		telemetry.addData("Status", "Initialized");
		telemetry.update();

		lbDrive = hardwareMap.get(DcMotor.class, "leftRear");  // These hardware map names
		rbDrive = hardwareMap.get(DcMotor.class, "rightRear"); // are compatable with RoadRunners
		lfDrive = hardwareMap.get(DcMotor.class, "leftFront"); // Defaults
		rfDrive = hardwareMap.get(DcMotor.class, "rightFront");

		spinny = hardwareMap.get(DcMotor.class, "spinny");

		lbDrive.setDirection(DcMotor.Direction.FORWARD);
		lfDrive.setDirection(DcMotor.Direction.FORWARD);
		rbDrive.setDirection(DcMotor.Direction.REVERSE);
		rfDrive.setDirection(DcMotor.Direction.REVERSE);

		spinny.setDirection(DcMotor.Direction.REVERSE);

		waitForStart();

		// run until the end of the match (driver presses STOP)
		while (opModeIsActive()) {

			double drive = -gamepad1.left_stick_y;
			double turn = gamepad1.right_stick_x;
			double strafe = gamepad1.left_stick_x;

			double spinInput = gamepad1.right_trigger;

			lbDrivePower = drive - turn - strafe;
			rbDrivePower = drive + turn + strafe;
			lfDrivePower = drive - turn + strafe;
			rfDrivePower = drive + turn - strafe;

			lbDrive.setPower(lbDrivePower);
			rbDrive.setPower(rbDrivePower);
			lfDrive.setPower(lfDrivePower);
			rfDrive.setPower(rfDrivePower);

			spinny.setPower(spinInput);

			telemetry.addData("Motors", "left back (%.2f), right back (%.2f), left front (%.2f), right front (%.2f)", lbDrivePower, rbDrivePower, lfDrivePower, rfDrivePower);
			telemetry.addData("Spinny Thing Motor", spinInput);

			telemetry.update();
		}
	}
}
