package org.firstinspires.ftc.teamcode.prototype;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.AnalogInput;

@Autonomous(name="weight")
public class WeightTest extends OpMode {

	AnalogInput sensor;

	@Override
	public void init() {
		sensor = hardwareMap.get(AnalogInput.class, "weightSensor");
	}

	@Override
	public void loop() {
		telemetry.addLine(String.valueOf(sensor.getMaxVoltage()));
		telemetry.addLine(String.valueOf(sensor.getVoltage()));
	}
}
