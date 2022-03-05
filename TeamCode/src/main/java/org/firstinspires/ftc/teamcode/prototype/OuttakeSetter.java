package org.firstinspires.ftc.teamcode.prototype;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystem.Outtake;

@Config
@TeleOp(name = "Outtake Setter", group = "prototype")
public class OuttakeSetter extends LinearOpMode {
	public static Double outtakeFlipVal = 0.0;
	public static Double outtakeBoxVal = 0.0;
	Outtake outtake;

	@Override
	public void runOpMode() throws InterruptedException {
		outtake = new Outtake(hardwareMap);
		waitForStart();
		while (opModeIsActive()) {
			outtakeFlipVal += gamepad1.left_stick_y / 100;
			outtakeBoxVal += gamepad1.right_stick_y / 100;
			outtake.outtakeFlip.setPosition(outtakeFlipVal);
			outtake.outtakeBox.setPosition(outtakeBoxVal);

			telemetry.addData(
				"outtakeFlipVal",
				outtakeFlipVal
			);
			telemetry.addData(
				"outtakeBoxVal",
				outtakeBoxVal
			);
			telemetry.update();
			sleep(100);
		}
	}
}
