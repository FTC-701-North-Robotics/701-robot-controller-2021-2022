package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.AnalogInput;
@Autonomous
public class WeightTest extends OpMode {

	AnalogInput sensor;
	RevBlinkinLedDriver LED;

	//Light Code
	//White = no block
	//Sky blue = light block
	//Blue = normal block
	//Dark blue = heavy block

	//Almost end = gold strobe
	//Very almost end = Rainbow with glitter

	//During autonomous = something cool (random maybe)

	//During init = blinking white

	//Cool colors
	//Twinkles, hot pink, rainbow palate (twinkles), sinelon, confetti, rainwbow with glitter, strobe)

	@Override
	public void init() {
		sensor = hardwareMap.get(AnalogInput.class, "weightSensor");
		LED = hardwareMap.get(RevBlinkinLedDriver.class, "LED");

		LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.HEARTBEAT_WHITE);
	}

	@Override
	public void loop() {

		telemetry.addLine(String.valueOf(sensor.getMaxVoltage()));
		telemetry.addLine(String.valueOf(sensor.getVoltage()));

		if (sensor.getVoltage() <= 0.517){
			LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.WHITE);
		}
		else if (sensor.getVoltage() > 0.517 && sensor.getVoltage() <= 0.709){
			LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
		}
		else if (sensor.getVoltage() > 0.709 && sensor.getVoltage() <= 0.796){
			LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.ORANGE);
		}
		else if (sensor.getVoltage() > 0.796){
			LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
		}
		else{
			LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.STROBE_WHITE);
		}



	}
}

//if you still get java version error, file -> project structure -> SDK location -> gradle settings -> gradle JDK