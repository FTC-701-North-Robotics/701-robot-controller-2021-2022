package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LED {
	RevBlinkinLedDriver LED;

	public LED(HardwareMap hardwareMap){
		LED = hardwareMap.get(RevBlinkinLedDriver.class, "LED");
	}

	public void setLights(RevBlinkinLedDriver.BlinkinPattern blinkinPattern){
		LED.setPattern(blinkinPattern);
	}

}
