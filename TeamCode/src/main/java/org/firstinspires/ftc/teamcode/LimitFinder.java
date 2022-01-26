package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Limit Finder")
public class LimitFinder extends LinearOpMode {

	// Declare OpMode members.
	private ElapsedTime runtime = new ElapsedTime();
	private Servo serviboy = null;
	private Double currentPosition = 0.0;

	@Override
	public void runOpMode() {
		telemetry.addData("Status", "Initialized");
		telemetry.update();

		serviboy = hardwareMap.get(Servo.class, "capstoneLiftServo");
		// Wait for the game to start (driver presses PLAY)
		serviboy.setPosition(currentPosition);

		waitForStart();

		runtime.reset();
		// run until the end of the match (driver presses STOP)
		while (opModeIsActive()) {
			if (gamepad1.dpad_up) {
				currentPosition += 0.05;
			} else if (gamepad1.dpad_down) {
				currentPosition -= 0.05;
			}

			serviboy.setPosition(currentPosition);
			telemetry.addData("Status", "Run Time: " + runtime.toString());
			telemetry.addData(
				"Servo",
				"Position: " + currentPosition.toString()
			);
			telemetry.update();
		}
	}
}
