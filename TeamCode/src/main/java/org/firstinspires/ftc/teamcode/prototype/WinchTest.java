package org.firstinspires.ftc.teamcode.prototype;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@Disabled
@TeleOp(name = "Winch Test", group = "prototype")
public class WinchTest extends LinearOpMode {

	public DcMotor winch;

	@Override
	public void runOpMode() throws InterruptedException {
		winch = hardwareMap.get(DcMotor.class, "outtakeWinch");
		//		winch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

		waitForStart();

		while (opModeIsActive()) {
			winch.setPower(gamepad2.left_stick_y);
			telemetry.addData(
				"Gamepad 2 Left Stick Y: ",
				gamepad2.left_stick_y
			);
			telemetry.addData("Winch: ", winch.getCurrentPosition());
			telemetry.update();
		}
	}
}
