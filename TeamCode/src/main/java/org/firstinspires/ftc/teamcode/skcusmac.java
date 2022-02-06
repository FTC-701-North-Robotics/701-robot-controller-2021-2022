package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(name = "skcusmac", group = "main")
public class skcusmac extends OpMode {
    private DcMotor lbDrive = null;
    private DcMotor rbDrive = null;
    private DcMotor lfDrive = null;
    private DcMotor rfDrive = null;

    // Declare Motor power values
    private double lbDrivePower;
    private double rbDrivePower;
    private double lfDrivePower;
    private double rfDrivePower;

    private DcMotor spinny = null;
    private DcMotor winch = null;

    private DcMotor dropDown = null;

    private CRServo leftIntake = null;
    private CRServo rightIntake = null;

    private Servo outTakeBox = null;

    DcMotor intake;
    DcMotor duckspin;
    CRServo Lin;
    CRServo Rin;
    CRServo outserv;
    String winchPosition = "down";
    TouchSensor winchTouch;
    TouchSensor inTouch;

    RevBlinkinLedDriver LED = null;

    double scale = 1.0;

    int intakeup = 1;
    boolean outtakeup;
    @Override
    public void init() {

        intake = hardwareMap.get(DcMotor.class, "intakeDrop");
        duckspin = hardwareMap.get(DcMotor.class, "duckSpin");

        Lin = hardwareMap.get(CRServo.class, "leftIntake");
        Rin = hardwareMap.get(CRServo.class, "rightIntake");

        winch = hardwareMap.get(DcMotor.class, "outtakeWinch");
        outTakeBox = hardwareMap.get(Servo.class, "outtakeBoxServo");

        lbDrive = hardwareMap.get(DcMotor.class, "leftRear");  // These hardware map names
        rbDrive = hardwareMap.get(DcMotor.class, "rightRear"); // are compatable with RoadRunners
        lfDrive = hardwareMap.get(DcMotor.class, "leftFront"); // Defaults
        rfDrive = hardwareMap.get(DcMotor.class, "rightFront");

        winchTouch = hardwareMap.get(TouchSensor.class, "winchTouch");
        inTouch = hardwareMap.get(TouchSensor.class, "intakeTouch");

        LED = hardwareMap.get(RevBlinkinLedDriver.class, "LED");

        outtakeup = false;

        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);




        lbDrive.setDirection(DcMotor.Direction.FORWARD);
        lfDrive.setDirection(DcMotor.Direction.FORWARD);
        rbDrive.setDirection(DcMotor.Direction.REVERSE);
        rfDrive.setDirection(DcMotor.Direction.REVERSE);


        winch.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }


    @Override
    public void loop() {

        LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_STROBE);

        telemetry.addLine(String.valueOf(intake.getCurrentPosition()));
        telemetry.addLine(String.valueOf(intakeup));

        if (gamepad2.left_bumper){
            outTakeBox.setPosition(0.35);
        }
        else if (gamepad2.right_bumper){
            outTakeBox.setPosition(0.9);
        }

        if (gamepad1.dpad_down){
            scale = 0.3;
        }
        if (gamepad1.dpad_right || gamepad2.dpad_left){
            scale = 0.6;
        }
        if (gamepad1.dpad_up){
            scale = 1.0;
        }

        //telemetry.addLine(String.valueOf(outTakeBox.getPosition()));


        if (gamepad1.a) {
            duckspin.setPower(0.7);
        }
        else if (gamepad1.y){
            duckspin.setPower(0.0);
        }


        if (gamepad2.right_stick_y < -0.05){
            intake.setPower(0.5 * -gamepad2.right_stick_y);
            intakeup = 2;
        }
        else if (gamepad2.right_stick_y > 0.05){
            intake.setPower(0.5 * -gamepad2.right_stick_y);
            intakeup = 2;
        }
        else {
            if (gamepad2.y) {
                intakeup = 1;
            }
            else if (gamepad2.a) {
                intakeup = 0;
            }



            if (intakeup == 1) {
                if (!inTouch.isPressed()) {
                    intake.setPower(0.6);
                } else {
                    intake.setPower(0.0);
                    intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    intakeup = 2;
                }
//this needs fixing^^^
            }

            if (intakeup == 0) {
                if (intake.getCurrentPosition() >= -90) {
                    intake.setPower(-0.5);
                } else if (intake.getCurrentPosition() < -90 && intake.getCurrentPosition() >= -100) {
                    intake.setPower(-0.4);
                } else if (intake.getCurrentPosition() < -105) {
                    intake.setPower(0.0);

                    intakeup = 2;
                }
            }
            else {
                intake.setPower(0.0);
            }

        }
        if (gamepad2.left_trigger > 0) {
            Lin.setPower(-gamepad2.left_trigger);
            Rin.setPower(gamepad2.left_trigger);
        } else if (gamepad2.right_trigger > 0){
            Lin.setPower(gamepad2.right_trigger);
            Rin.setPower(-gamepad2.right_trigger);
        }
        if(gamepad2.left_trigger > 0 && gamepad2.right_trigger> 0){
            Lin.setPower(0.0);
            Rin.setPower(0.0);
        }




        double drive = gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;
        double strafe = gamepad1.left_stick_x;

        //double outtakeServo = gamepad2.left_trigger * 30 + 110; // trigger + total angle + offset

        lbDrivePower = drive - turn - strafe;
        rbDrivePower = drive + turn + strafe;
        lfDrivePower = drive - turn + strafe;
        rfDrivePower = drive + turn - strafe;

        lbDrive.setPower(lbDrivePower * scale);
        rbDrive.setPower(rbDrivePower * scale);
        lfDrive.setPower(lfDrivePower * scale);
        rfDrive.setPower(rfDrivePower * scale);




        if (gamepad2.left_stick_y < -0.05){
            winch.setPower(-gamepad2.left_stick_y);
            winchPosition = "manual";
        }
        else if (gamepad2.left_stick_y > 0.05){
            winch.setPower(-gamepad2.left_stick_y);
            winchPosition = "manual";
        }
        else {


            // outTakeBox.setPosition(outtakeServo);

            // dropDown.setPower(gamepad2.left_stick_y);

            // Winch Control + telemetry

            if (gamepad2.dpad_up) {
                //winch.setPower(0.5);
                winchPosition = "up";
            } else if (gamepad2.dpad_down) {
                //winch.setPower(-0.2);
                winchPosition = "down";
            } else if (gamepad2.dpad_left || gamepad2.dpad_right) {
                //winch.setPower(0);
                winchPosition = "middle";
            }


            if (winchPosition.equals("down")) {
                if (!winchTouch.isPressed()) {
                    winch.setPower(-1.0);
                } else {
                    winch.setPower(0.0);
                    winch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    winch.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                }
            }

            //CHANGE UPPER LIMIT
            if (winchPosition.equals("up")) {
                if (winch.getCurrentPosition() < 600) {
                    winch.setPower(0.9);
                } else {
                    winch.setPower(0.0);
                }
            }

            //CHANGE MIDDLE LIMIT
            if (winchPosition.equals("middle")) {
                if (winch.getCurrentPosition() < 250) {
                    winch.setPower(0.9);
                } else if (winch.getCurrentPosition() > 260) {
                    winch.setPower(-1.0);
                } else {
                    winch.setPower(0.0);
                }
            }

        }
        //telemetry.update();
    }
}
