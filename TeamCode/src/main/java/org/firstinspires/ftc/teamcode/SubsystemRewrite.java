package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystem.Drive;
import org.firstinspires.ftc.teamcode.subsystem.Intake;
import org.firstinspires.ftc.teamcode.subsystem.OutTake;
import org.firstinspires.ftc.teamcode.subsystem.LED;


@TeleOp(name = "Sub System Rewrite", group = "Linear Opmode")
public class SubsystemRewrite extends LinearOpMode {

	private ElapsedTime runtime = new ElapsedTime();

	private Drive drive = new Drive(hardwareMap);
	private Intake intake = new Intake(hardwareMap);
	private LED led = new LED(hardwareMap);

	@Override
	public void runOpMode() {
		telemetry.addData("Status", "Initialized");
		telemetry.update();

		waitForStart();
		runtime.reset();

		while (opModeIsActive()) {

			led.setLights(RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_PARTY_PALETTE);

			double foward = gamepad1.left_stick_y;
			double turn = gamepad1.right_stick_x;
			double strafe = gamepad1.left_stick_x;

			double lbDrivePower = foward - turn - strafe;
			double rbDrivePower = foward + turn + strafe;
			double lfDrivePower = foward - turn + strafe;
			double rfDrivePower = foward + turn - strafe;

			drive.leftRear.setPower(lbDrivePower);
			drive.leftFront.setPower(lfDrivePower);
			drive.rightRear.setPower(rbDrivePower);
			drive.rightFront.setPower(rfDrivePower);

			// Outtake Box
			if (gamepad2.left_bumper) {
				OutTake.Box.Reset();
			} else if (gamepad2.right_bumper) {
				OutTake.Box.Dump();
			}

			// Outtake Winch
			if (Math.abs(gamepad2.left_stick_y) > 0.05) {
				OutTake.Winch.setManualPower(-gamepad2.left_stick_y);
			} else {
				if (gamepad2.dpad_up) {
					OutTake.Winch.up();
				} else if (gamepad2.dpad_down) {
					OutTake.Winch.down();
				} else if (gamepad2.dpad_left || gamepad2.dpad_right) {
					OutTake.Winch.middle();
				}
			}

			// Intake Speed
			intake.SetIntakeSpeed(-gamepad2.left_trigger + gamepad2.right_trigger);

			// Show the elapsed game time
			telemetry.addData("Status", "Run Time: " + runtime.toString());
			telemetry.update();
		}
	}
}
