package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class OuttakeWeightSensor {

	public static Double Weight0 = 0.517;
	public static Double Weight1 = 0.709;
	public static Double Weight2 = 0.796;

	public Led led;
	public AnalogInput analogInput;

	public OuttakeWeightSensor(HardwareMap hardwareMap, Led ledParam) {
		analogInput = hardwareMap.get(AnalogInput.class, "weightSensor");
		led = ledParam;

		led.setPattern(
			RevBlinkinLedDriver.BlinkinPattern.TWINKLES_OCEAN_PALETTE
		);
	}

	public OuttakeWeightSensor(HardwareMap hardwareMap) {
		this(hardwareMap, new Led(hardwareMap));
	}

	public void updateLED() {
		double volt = analogInput.getVoltage();
		if (volt <= Weight0) {
			led.setPattern(RevBlinkinLedDriver.BlinkinPattern.WHITE);
		} else if (volt <= Weight1) {
			led.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
		} else if (volt <= Weight2) {
			led.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
		} else {
			led.setPattern(RevBlinkinLedDriver.BlinkinPattern.STROBE_WHITE);
		}
	}
}
