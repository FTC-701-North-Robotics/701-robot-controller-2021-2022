package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;



@TeleOp(name = "TeleOpPrototype", group = "Linear Opmode")
public class TeleOpPrototype extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor lbDrive = null;
    private DcMotor rbDrive = null;
    private DcMotor lfDrive = null;
    private DcMotor rfDrive = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        lbDrive = hardwareMap.get(DcMotor.class, "lbdrive");
        rbDrive = hardwareMap.get(DcMotor.class, "rbdrive");
        lfDrive = hardwareMap.get(DcMotor.class, "lfdrive");
        rfDrive = hardwareMap.get(DcMotor.class, "rfdrive");

        lbDrive.setDirection(DcMotor.Direction.FORWARD);
        lfDrive.setDirection(DcMotor.Direction.FORWARD);
        rbDrive.setDirection(DcMotor.Direction.REVERSE);
        rfDrive.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            double lbDrivePower;
            double rbDrivePower;
            double lfDrivePower;
            double rfDrivePower;

            double drive = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;
            double strafe = gamepad1.left_stick_x;

            lbDrivePower = (Range.clip((drive + turn) * strafe, -1.0, 1.0));
            rbDrivePower = (Range.clip((drive - turn) * strafe, -1.0, 1.0));
            lfDrivePower = (Range.clip((drive + turn) * -strafe, -1.0, 1.0));
            rfDrivePower = (Range.clip((drive - turn) * -strafe, -1.0, 1.0));

            lbDrive.setPower(lbDrivePower);
            rbDrive.setPower(rbDrivePower);
            lfDrive.setPower(lfDrivePower);
            rfDrive.setPower(rfDrivePower);

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left back (%.2f), right back (%.2f), left front (%.2f), right front (%.2f)", lbDrivePower, rbDrivePower, lfDrivePower, rfDrivePower);
            telemetry.update();
        }
    }
}
