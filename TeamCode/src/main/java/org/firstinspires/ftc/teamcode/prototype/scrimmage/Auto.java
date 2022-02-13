package org.firstinspires.ftc.teamcode.prototype.scrimmage;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystem.Drive;
import org.firstinspires.ftc.teamcode.subsystem.Duck;
import org.firstinspires.ftc.teamcode.subsystem.Outtake;

public class Auto extends LinearOpMode {
	Drive drive;
	Outtake outtake;
	Duck duck;

	@Override
	public void runOpMode() {
		drive = new Drive(hardwareMap);
		outtake = new Outtake(hardwareMap);
		duck = new Duck(hardwareMap);

		waitForStart();

		drive.vectorDrive(0, 0.2);
		sleep(200);
		drive.vectorDrive(0, 0);

		outtake.Winch.setManualPower(0.3);
		sleep(300);
		outtake.Winch.setManualPower(0);

		outtake.Box.dump();
		sleep(200);
		outtake.Box.reset();
	}
}
