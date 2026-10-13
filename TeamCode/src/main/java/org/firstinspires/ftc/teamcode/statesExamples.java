package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import org.firstinspires.ftc.teamcode.mechanisms.Shooter;
import org.firstinspires.ftc.teamcode.mechanisms.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.Flower;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@TeleOp(name="statesExamples")
public class statesExamples extends OpMode {

    Shooter shooter;
    Intake intake;
    Flower flower;

    PinpointLocalizer localizer;
    Follower follower;

    Limelight3A limelight;
    DcMotor frontLeft, frontRight, backLeft, backRight;

    IMU imu;

    enum RobotState {
        IDLE,
        SHOOT,
        PICK_UP,
        EMPTY_FLOWER,
        MOVE,
        PARK
    }

    RobotState currentState = RobotState.IDLE;

    @Override
    public void init() {

        shooter = new Shooter(hardwareMap);
        intake = new Intake(hardwareMap);
        flower = new Flower(hardwareMap);

        localizer = new PinpointLocalizer(hardwareMap, Constants.localizerConfig);
        follower = Constants.create(hardwareMap);

        imu = hardwareMap.get(IMU.class, "imu");
        imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP
        )));

        frontLeft  = hardwareMap.get(DcMotor.class, "leftFront");
        frontRight = hardwareMap.get(DcMotor.class, "rightFront");
        backLeft   = hardwareMap.get(DcMotor.class, "leftBack");
        backRight  = hardwareMap.get(DcMotor.class, "rightBack");

        limelight = hardwareMap.get(Limelight3A.class, "limeLight");
        limelight.pipelineSwitch(0);

        telemetry.setMsTransmissionInterval(11);
        telemetry.addLine("TeleOp State Machine Ready");
        telemetry.update();
    }

    @Override
    public void start() {
        limelight.start();
    }

    @Override
    public void loop() {

        // ALWAYS update sensors
        localizer.update();
        follower.update();
        LLResult ll = limelight.getLatestResult();

    // ---------------------------------------------------------
    // 1. Read raw joystick inputs
    // ---------------------------------------------------------
        double rawForward = -gamepad1.left_stick_y;
        double rawStrafe  = -gamepad1.left_stick_x;
        double rawRotate  = -gamepad1.right_stick_x;

    // ---------------------------------------------------------
    // 2. Smooth driving (input shaping)
    // ---------------------------------------------------------
        double forward = Math.pow(rawForward, 3);
        double strafe  = Math.pow(rawStrafe, 3);
        double rotate  = Math.pow(rawRotate, 3);

        double speedScale = gamepad1.right_bumper ? 0.4 : 1.0;
        forward *= speedScale;
        strafe  *= speedScale;
        rotate  *= speedScale;

    // ---------------------------------------------------------
    // 3. Field-centric transform
    // ---------------------------------------------------------
        double heading = -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        double cosA = Math.cos(heading);
        double sinA = Math.sin(heading);

        double fieldForward = forward * cosA - strafe * sinA;
        double fieldStrafe  = forward * sinA + strafe * cosA;

    // ---------------------------------------------------------
    // 4. Convert to mecanum wheel powers (REAL drive powers)
    // ---------------------------------------------------------
        double fl = fieldForward + fieldStrafe + rotate; // front left
        double fr = fieldForward - fieldStrafe - rotate; // front right
        double bl = fieldForward - fieldStrafe + rotate; // back left
        double br = fieldForward + fieldStrafe - rotate; // back right

    // Normalize so no wheel exceeds |1|
        double max = Math.max(1.0,
                Math.max(Math.abs(fl),
                        Math.max(Math.abs(fr),
                                Math.max(Math.abs(bl), Math.abs(br)))));

        fl /= max;
        fr /= max;
        bl /= max;
        br /= max;

    // ---------------------------------------------------------
    // 5. Send final drive powers (this replaces drivetrain.drive())
    // ---------------------------------------------------------
        frontLeft.setPower(fl);
        frontRight.setPower(fr);
        backLeft.setPower(bl);
        backRight.setPower(br);


        // ---------------------------------------------------------
        // Driver buttons trigger states ANY time
        // ---------------------------------------------------------
        if (gamepad1.a) currentState = RobotState.SHOOT;
        if (gamepad1.b) currentState = RobotState.PICK_UP;
        if (gamepad1.x) currentState = RobotState.EMPTY_FLOWER;
        if (gamepad1.y) currentState = RobotState.PARK;
        if (gamepad1.dpad_up) currentState = RobotState.MOVE;

        // ---------------------------------------------------------
        // State Machine (mechanisms only — NEVER blocks driving)
        // ---------------------------------------------------------
        switch (currentState) {

            case IDLE:
                shooter.stop();
                intake.stop();
                flower.stop();
                break;

            case SHOOT:
                shooter.spinUp();
                /*
                if (shooter.atSpeed()) {
                    shooter.feed();
                    currentState = RobotState.IDLE;
                }*/
                break;

            case PICK_UP:
                intake.lower();
                intake.run();
                if (intake.hasPixel()) {
                    intake.raise();
                    currentState = RobotState.IDLE;
                }
                break;

            case EMPTY_FLOWER:
                flower.open();
                flower.shake();
                flower.close();
                currentState = RobotState.IDLE;
                break;

            case MOVE:
                // TODO: follower.followPath(path)
                if (!follower.isBusy()) {
                    currentState = RobotState.IDLE;
                }
                break;

            case PARK:
                // TODO: follower.followPath(parkPath)
                if (!follower.isBusy()) {
                    currentState = RobotState.IDLE;
                }
                break;
        }

        // ---------------------------------------------------------
        // Telemetry
        // ---------------------------------------------------------
        telemetry.addData("State", currentState);
        telemetry.addData("Pose", follower.pose());

        if (ll != null && ll.isValid()) {
            telemetry.addData("Tag", ll.getFiducialResults().get(0).getFiducialId()); //.getID());
            telemetry.addData("tx", ll.getTx());
            telemetry.addData("ty", ll.getTy());
        }

        telemetry.update();
    }
}
