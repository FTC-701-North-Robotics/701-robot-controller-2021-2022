package org.firstinspires.ftc.teamcode.tools;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.subsystem.Led;

@TeleOp(name = "LED showcase", group = "tools")
public class LEDShowcase extends LinearOpMode {

	Led LED;

	@Override
	public void runOpMode() throws InterruptedException {
		LED = new Led(hardwareMap);

		waitForStart();
		while (opModeIsActive()) {
			for (int i = 0; i < 100; i++) {
				LED.setPattern(
					RevBlinkinLedDriver.BlinkinPattern.fromNumber(i)
				);
				telemetry.addLine(String.valueOf(i));
				telemetry.update();
				while (opModeIsActive()) {
					if (gamepad1.a) {
						break;
					}
				}
				if (!opModeIsActive()) {
					break;
				}
				sleep(1000);
			}
		}
	}
}
