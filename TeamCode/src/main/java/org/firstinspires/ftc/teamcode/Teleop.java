package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystem.Drive;
import org.firstinspires.ftc.teamcode.subsystem.Duck;
import org.firstinspires.ftc.teamcode.subsystem.Encoders;
import org.firstinspires.ftc.teamcode.subsystem.Intake;
import org.firstinspires.ftc.teamcode.subsystem.Outtake;
import org.firstinspires.ftc.teamcode.subsystem.WinchPosition;
import org.firstinspires.ftc.teamcode.util.Bulk;

import java.util.Locale;

@Config
@TeleOp(name = "Teleop", group = "main")
public class Teleop extends LinearOpMode {

	public static double INTAKE_SPEED_MAX = 1.0;
	public Drive drive;
	public Duck duck;
	public Intake intake;
	public Outtake outtake;
	public Encoders encoders;

	@Override
	public void runOpMode() {
		Bulk.auto(hardwareMap);

		drive = new Drive(hardwareMap);
		duck = new Duck(hardwareMap);
		outtake = new Outtake(hardwareMap);
		intake = new Intake(hardwareMap);
		encoders = new Encoders(hardwareMap);

		waitForStart();

		while (opModeIsActive()) {
			drive.vectorDrive(
				gamepad1.left_stick_x * 0.8,
				gamepad1.left_stick_y * 0.8,
				gamepad1.right_stick_x * 0.8
			);
			duck.setPower(
				(gamepad2.right_trigger - gamepad2.left_trigger) * 0.5
			);

			if (Math.abs(gamepad2.right_stick_y) > 0.2) {
				outtake.Winch.setManualPower(gamepad2.right_stick_y);
			} else if (outtake.winchPosition == WinchPosition.MANUAL) {
				outtake.Winch.setManualPower(gamepad2.right_stick_y);
			}

			intake.setIntakeSpeed(
				((gamepad2.a ? 1.0 : 0.0) + (gamepad2.b ? -1.0 : 0.0)) *
					INTAKE_SPEED_MAX
			);
			intake.setDropSpeed(gamepad2.left_stick_y * 0.8);

			if (gamepad2.right_bumper) {
				outtake.Box.dump();
			} else if (gamepad2.left_bumper) {
				outtake.Box.reset();
			}

			if (gamepad1.share || gamepad2.share) {
				encoders.Toggle();
				sleep(200);
			}

			if (gamepad2.dpad_down) {
				outtake.Winch.bottom();
			}

			telemetry.addData(
				"Opmode has been active for",
				String.format(Locale.ENGLISH, "%.2f Seconds", this.getRuntime())
			);
			telemetry.addLine("\n----- Robot Information -----");
			telemetry.addData(
				"Encoders",
				encoders.isRetracted ? "Retracted" : "Active"
			);
			telemetry.addData(
				"Winch Position",
				outtake.winch.getCurrentPosition()
			);
			telemetry.addData(
				"Odometry Servo Position",
				encoders.servo0.getPosition()
			);
			telemetry.update();
		}
	}
}
