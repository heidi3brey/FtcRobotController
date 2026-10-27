package org.firstinspires.ftc.teamcode.BiobuzzTele;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.BiobuzzAuto.AutoState;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@TeleOp(name="BiobuzzTeleMain")
public class BiobuzzTeleMain extends OpMode{

    // --------------------------
    // Field Coordinates
    // --------------------------
    private static final double RED_BASKET_X = 48.0;
    private static final double RED_BASKET_Y = 48.0;

    private static final double BLUE_BASKET_X = -48.0;
    private static final double BLUE_BASKET_Y = 48.0;
    private Follower follower;
    private Limelight3A limelight;
    private String alliance = "unknown";
    public DcMotorEx bangBang;
    public DcMotorEx spinner;
    public DcMotorEx pickup;
    @Override
    public void init(){
        // Follower
        // creates new field with robot at (0,0,0) by default
        follower = Constants.create(hardwareMap);
        //change to last location from auto
        follower.setPose(AutoState.lastPose);

        //get alliance from auto
        alliance = AutoState.alliance;

        //  Limelight setup based on alliance
        limelight = hardwareMap.get(Limelight3A.class,"limeLight");
        if (alliance.equals("red")){
            limelight.pipelineSwitch(0);
        } else if (alliance.equals("blue")) {
            limelight.pipelineSwitch(1);
        }

        telemetry.addData("Alliance", alliance);

        telemetry.addData(
                "Stored X",
                AutoState.lastPose.x());

        telemetry.addData(
                "Stored Y",
                AutoState.lastPose.y());

        telemetry.addData(
                "Stored Heading",
                Math.toDegrees(
                        AutoState.lastPose.heading()));

        telemetry.addLine("biobuzz telyop ready");

        bangBang = hardwareMap.get(DcMotorEx.class,"shootBang");
        spinner = hardwareMap.get(DcMotorEx.class,"spinner");
        pickup = hardwareMap.get(DcMotorEx.class,"pickup");
        telemetry.update();
    }
    @Override
    public void start(){
        limelight.start();
    }

    @Override
    public void loop() {

        follower.update();

        Pose pose = follower.pose();

        double forward = -gamepad1.left_stick_y;
        double strafe = -gamepad1.left_stick_x;
        double rotate = gamepad1.right_stick_x;

        double targetX;
        double targetY;
        // Fienld-centric trasnsform
        double heading = pose.heading() + Math.PI / 2.0;
  // add 90 degers to change side! :)
        // Opional alliance adjustment
        if (alliance.equalsIgnoreCase("red")) {
            heading += Math.PI;
// add pickup control hub! new bot!
            //add shootBang
            //add sorter
            //add pickup
        }

        double tempForward = forward * Math.cos(-heading) - strafe * Math.sin(-heading);
        double tempStrafe = forward * Math.sin(-heading) + strafe * Math.cos(-heading);
        forward = tempForward;
        strafe = tempStrafe;

        if(alliance.equalsIgnoreCase("red")) {
            targetX = RED_BASKET_X;
            targetY = RED_BASKET_Y;
        } else {
            targetX = BLUE_BASKET_X;
            targetY = BLUE_BASKET_Y;
        }

        if (gamepad1.right_stick_button) {

            double desiredHeading =
                    Math.atan2(
                            targetY - pose.y(),
                            targetX - pose.x());

            double error = desiredHeading - pose.heading();

            while (error > Math.PI) {
                error -= 2.0 * Math.PI;
            }

            while (error < -Math.PI) {
                error += 2.0 * Math.PI;
            }

            if (Math.abs(error) < Math.toRadians(2)) {
                rotate = 0;
            } else {
                rotate = error * 0.5;

                rotate = Math.max(
                        -0.6,
                        Math.min(0.6, rotate)
                );
            }

            telemetry.addData("Target X", targetX);
            telemetry.addData("Target Y", targetY);
            telemetry.addData("Desired Heading", desiredHeading);
            telemetry.addData("Turn Error", Math.toDegrees(error));
            telemetry.addData("Rotate Cmd", rotate);
            telemetry.addData(
                    "Heading Deg",
                    Math.toDegrees(pose.heading())
            );
        }

        follower.manual(forward,strafe,rotate);

        telemetry.addData("X", pose.x());
        telemetry.addData("Y", pose.y());
        telemetry.addData("Heading", pose.heading());
        if(gamepad1.leftTriggerWasReleased()){
            //shootbang shooting moter
            bangBang.setPower(-0.4);
        } else if (gamepad1.rightTriggerWasReleased()){
            bangBang.setPower(0);
        } else if (gamepad1.rightBumperWasReleased()){

            bangBang.setPower(bangBang.getPower() +0.01);
        }else if (gamepad1.leftBumperWasReleased()) {

            bangBang.setPower(bangBang.getPower() -0.01);
        }
        telemetry.addData("Shoot Speed ", bangBang.getPower());
        if(gamepad1.aWasReleased()){
            //spinner moter
            spinner.setPower(-0.3);
            pickup.setPower(0.5);
        } else if (gamepad1.bWasReleased()){
            spinner.setPower(0);
            pickup.setPower(0);
        }
        telemetry.update();
    }
}


