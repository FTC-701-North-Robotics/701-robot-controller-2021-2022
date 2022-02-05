package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Subsystem for controlling rev blinkin led
 */
public class Led {
	RevBlinkinLedDriver LED;

	public Led(HardwareMap hardwareMap) {
		LED = hardwareMap.get(RevBlinkinLedDriver.class, "LED");
	}

	/**
	 * sets led pattern
	 * @param blinkinPattern pattern
	 */
	public void setPattern(RevBlinkinLedDriver.BlinkinPattern blinkinPattern) {
		LED.setPattern(blinkinPattern);
	}
}
