package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.Map;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;

@TeleOp(name = "FirstTeleOp", group = "Development")
public class FirstTeleOp extends OpMode {

    public static final int ARM_COLLAPSED_INTO_ROBOT  = Constants.Scoring.Arm.COLLAPSED_INTO_ROBOT;
    public static final int ARM_COLLECT               = Constants.Scoring.Arm.COLLECT;
    public static final int ARM_CLEAR_BARRIER         = Constants.Scoring.Arm.CLEAR_BARRIER;
    public static final int ARM_SCORE_SPECIMEN        = Constants.Scoring.Arm.SCORE_SPECIMEN;
    public static final int ARM_SCORE_SAMPLE_IN_LOW   = Constants.Scoring.Arm.SCORE_SAMPLE_IN_LOW;
    public static final int ARM_ATTACH_HANGING_HOOK   = Constants.Scoring.Arm.ATTATCH_HANGING_HOOK;
    public static final int ARM_WINCH_ROBOT           = Constants.Scoring.Arm.WINCH_ROBOT;

    public static final int WRIST_FOLDED_IN = Constants.Scoring.Wrist.FOLDED_IN;
    public static final int WRIST_FOLDED_OUT = Constants.Scoring.Wrist.FOLDED_OUT;
    
    public static final double INTAKE_COLLECT    = Constants.Scoring.Intake.COLLECT;
    public static final double INTAKE_OFF        = Constants.Scoring.Intake.INTAKE_OFF;
    public static final double INTAKE_DEPOSIT    = Constants.Scoring.Intake.INTAKE_DEPOSIT;

    // Drivetrain
    public DcMotor leftDrive, rightDrive;

    // Arm
    public DcMotor armMotor;
    public CRServo intake;
    public Servo wrist;

    @Override
    public void init () {
        
        leftDrive  = hardwareMap.get(DcMotor.class, "sinister");
        rightDrive = hardwareMap.get(DcMotor.class, "dexter");
        armMotor   = hardwareMap.get(DcMotor.class, "arm");
        
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        
        leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        ((DcMotorEx) armMotor).setCurrentAlert(5,CurrentUnit.AMPS);

        armMotor.setTargetPosition(0);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        intake = hardwareMap.get(CRServo.class, "intake");
        wrist  = hardwareMap.get(Servo.class, "wrist");

        intake.setPower(INTAKE_OFF);
        wrist.setPosition(WRIST_FOLDED_IN);

        telemetry.addLine("Initialized...");
        telemetry.update();
        
    }

    @Override
    public void init_loop () {

    }

    @Override
    public void start () {

    }

    @Override
    public void loop () {


        double forward = -gamepad1.left_stick_y;
        double rotate = gamepad1.right_stick_x;

        double max = Math.max(1.0, Math.abs(forward + rotate), Math.abs(forward - rotate));
        
        double left = (forward + rotate) / max;
        double right = (forward - rotate) / max;

        leftDrive.setPower(left);
        rightDrive.setPower(right);

        if (gamepad1.a) {
            intake.setPower(INTAKE_COLLECT);
        }
        else if (gamepad1.x) {
            intake.setPower(INTAKE_OFF);
        }
        else if (gamepad1.b) {
            intake.setPower(INTAKE_DEPOSIT);
        }

        if (gamepad1.right_bumper) {
            armPosition = ARM_COLLECT;
            wrist.setPosition(WRIST_FOLDED_OUT);
            intake.setPower(INTAKE_COLLECT);
        }

        else if (gamepad1.left_bumper) {
            /* This is about 20° up from the collecting position to clear the barrier
            Note here that we don't set the wrist position or the intake power when we
            select this "mode", this means that the intake and wrist will continue what
            they were doing before we clicked left bumper. */
            armPosition = ARM_CLEAR_BARRIER;
        }

        else if (gamepad1.y){
            armPosition = ARM_SCORE_SAMPLE_IN_LOW;
        }

        else if (gamepad1.dpad_left) {
            armPosition = ARM_COLLAPSED_INTO_ROBOT;
            intake.setPower(INTAKE_OFF);
            wrist.setPosition(WRIST_FOLDED_IN);
        }

        else if (gamepad1.dpad_right){
            armPosition = ARM_SCORE_SPECIMEN;
            wrist.setPosition(WRIST_FOLDED_IN);
        }

        else if (gamepad1.dpad_up){
            armPosition = ARM_ATTACH_HANGING_HOOK;
            intake.setPower(INTAKE_OFF);
            wrist.setPosition(WRIST_FOLDED_IN);
        }

        else if (gamepad1.dpad_down){
            armPosition = ARM_WINCH_ROBOT;
            intake.setPower(INTAKE_OFF);
            wrist.setPosition(WRIST_FOLDED_IN);
        }
    
        armMotor.setTargetPosition((int) (armPosition + armPositionFudgeFactor));

        ((DcMotorEx) armMotor).setVelocity(2100);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if (((DcMotorEx) armMotor).isOverCurrent()){
            telemetry.addLine("MOTOR EXCEEDED CURRENT LIMIT!");
        }

        telemetry.addData("armTarget: ", armMotor.getTargetPosition());
        telemetry.addData("arm Encoder: ", armMotor.getCurrentPosition());
        telemetry.update();
        
    }

    @Override
    public void stop () {

    }

}
