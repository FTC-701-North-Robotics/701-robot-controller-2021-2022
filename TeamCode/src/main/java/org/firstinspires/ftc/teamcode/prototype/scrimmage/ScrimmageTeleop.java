package org.firstinspires.ftc.teamcode.prototype.scrimmage;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.subsystem.Drive;
import org.firstinspires.ftc.teamcode.subsystem.Duck;
import org.firstinspires.ftc.teamcode.subsystem.Intake;
import org.firstinspires.ftc.teamcode.subsystem.Outtake;

@Disabled
@TeleOp(name = "Scrimmage Teleop", group = "main")
public class ScrimmageTeleop extends LinearOpMode {

	public Drive drive;
	public Duck duck;
	public Intake intake;
	public Outtake outtake;

	@Override
	public void runOpMode() {
		drive = new Drive(hardwareMap);
		duck = new Duck(hardwareMap);
		outtake = new Outtake(hardwareMap);
		intake = new Intake(hardwareMap);

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

			outtake.winch.setPower(gamepad2.right_stick_y);

			intake.setIntakeSpeed(
				(gamepad2.a ? 1.0 : 0.0) - (gamepad2.b ? -1.0 : 0.0)
			);
			intake.setDropSpeed(gamepad2.left_stick_y * 0.8);

			if (gamepad2.right_bumper) {
				outtake.Box.dump();
			} else if (gamepad2.left_bumper) {
				outtake.Box.reset();
			}
		}
	}
}
