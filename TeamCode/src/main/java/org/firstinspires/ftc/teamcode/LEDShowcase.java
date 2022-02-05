package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class LEDShowcase extends LinearOpMode {

	RevBlinkinLedDriver LED;

	@Override
	public void runOpMode() throws InterruptedException {
		LED = hardwareMap.get(RevBlinkinLedDriver.class, "LED");

		waitForStart();
		while(opModeIsActive()) {
			for(int i = 0; i<100; i++){
				LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(i));
				telemetry.addLine(String.valueOf(i));
				telemetry.update();
				while(opModeIsActive()) {
					if (gamepad1.a) {
						break;
					}
				}
				if(!opModeIsActive()) {
					break;
				}
				sleep(1000);
			}
		}
	}




}
