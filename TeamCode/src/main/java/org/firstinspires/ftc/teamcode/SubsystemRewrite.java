package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystem.Drive;
import org.firstinspires.ftc.teamcode.subsystem.Duck;
import org.firstinspires.ftc.teamcode.subsystem.Intake;
import org.firstinspires.ftc.teamcode.subsystem.Outtake;
import org.firstinspires.ftc.teamcode.util.Bulk;

@TeleOp(name = "Sub System Rewrite", group = "prototype")
public class SubsystemRewrite extends LinearOpMode {

	public Drive drive;
	public Intake intake;
	public Outtake outtake;
	public Duck duck;
	private ElapsedTime runtime = new ElapsedTime();

	@Override
	public void runOpMode() {
		drive = new Drive(hardwareMap);
		intake = new Intake(hardwareMap);
		outtake = new Outtake(hardwareMap);
		duck = new Duck(hardwareMap);
		Bulk.auto(hardwareMap);
		new Thread(new outtakeThread());
		
		telemetry.addData("Status", "Initialized");
		telemetry.update();

		waitForStart();
		runtime.reset();

		while (opModeIsActive()) {
			double forward = gamepad1.left_stick_y;
			double turn = gamepad1.right_stick_x;
			double strafe = gamepad1.left_stick_x;

			drive.vectorDrive(strafe, forward, turn);

			intake.setDropSpeed(gamepad2.right_stick_y);

			// Intake Speed
			intake.setIntakeSpeed(
				-gamepad2.left_trigger + gamepad2.right_trigger
			);

			duck.setPower(gamepad2.a ? 0.5 : 0);

			// Show the elapsed game time
			telemetry.addData("Status", "Run Time: " + runtime.toString());
			telemetry.update();
		}
	}

	/**
	 * Seperate thread for outtake
	 */
	class outtakeThread implements Runnable {
		@Override
		public void run() {
			waitForStart();
			while (opModeIsActive()) {
				// Outtake Box
				if (gamepad2.left_bumper) {
					outtake.Box.reset();
				} else if (gamepad2.right_bumper) {
					outtake.Box.dump();
				}

				// Outtake Winch
				if (Math.abs(gamepad2.left_stick_y) > 0.05) {
					outtake.Winch.setManualPower(-gamepad2.left_stick_y);
				} else {
					if (gamepad2.dpad_up) {
						outtake.Winch.up();
					} else if (gamepad2.dpad_down) {
						outtake.Winch.down();
					} else if (gamepad2.dpad_left || gamepad2.dpad_right) {
						outtake.Winch.middle();
					}
				}

			}
		}
	}
}
