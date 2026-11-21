package org.firstinspires.ftc.teamcode.BiobuzzTele;

import static java.lang.Thread.sleep;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

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
    public static final Pose start =
            new Pose(0,0,90);
    public DcMotorEx bangBang;
    public DcMotorEx spinner;
    public DcMotorEx pickup;
    public CRServo flipperUpper;
    public Servo blocker;
    public String fieldOrBot="field";
    public double defaultShootPower = -0.85;
    public double defaultShootVelocity = -950;
    private ElapsedTime timer = new ElapsedTime();
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
        //limelight = hardwareMap.get(Limelight3A.class,"limeLight");
        /*if (alliance.equals("red")){
            limelight.pipelineSwitch(0);
        } else if (alliance.equals("blue")) {
            limelight.pipelineSwitch(1);
        }*/

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
        try {
            bangBang = hardwareMap.get(DcMotorEx.class,"shootBang");

        } catch (Exception e){
            telemetry.addLine ("ShootBang No Work");
        }
        try {
            spinner = hardwareMap.get(DcMotorEx.class,"spinner");

        } catch (Exception e){
            telemetry.addLine ("Spinner No Work");
        }
        try {
            pickup = hardwareMap.get(DcMotorEx.class,"pickup");

        } catch (Exception e){
            telemetry.addLine("Pickup No Work");
        }
        try {
            flipperUpper = hardwareMap.get(CRServo.class, "flipperUpper");

        } catch (Exception e){
            telemetry.addLine("FlipperUpper No Work");
        }
        try {
            blocker = hardwareMap.get(Servo.class,"blocker");

        } catch (Exception e){
            telemetry.addLine("Blocker No Work");
        }
        try {
            bangBang.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        } catch (Exception e){
            telemetry.addLine("BangBang No Work");
        }
        telemetry.update();
    }
    @Override
    public void start(){
        //limelight.start();
        bangBang.setVelocity(defaultShootVelocity);
    }

    @Override
    public void loop() {

        follower.update();

        Pose pose = follower.pose();

        double forward = -gamepad1.left_stick_y *1;
        double strafe = -gamepad1.left_stick_x *1;
        double rotate = gamepad1.right_stick_x*-1;

        double targetX;
        double targetY;
        // Fienld-centric trasnsform
        double heading = pose.heading(); //+ Math.PI / 2.0;
  // add 90 degers to change side! :)
        // Opional alliance adjustment
        if (alliance.equalsIgnoreCase("red")) {
            heading += Math.PI;
// add pickup control hub! new bot!
            //add shootBang
            //add sorter
            //add pickup
        }

      //  double tempForward = forward * Math.cos(-heading) - strafe * Math.sin(-heading);
        //double tempStrafe = forward * Math.sin(-heading) + strafe * Math.cos(-heading);
        //forward = tempForward;
        //strafe = tempStrafe;

        if(alliance.equalsIgnoreCase("red")) {
            targetX = RED_BASKET_X;
            targetY = RED_BASKET_Y;
        } else {
            targetX = BLUE_BASKET_X;
            targetY = BLUE_BASKET_Y;
        }

        //autoaim
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
        heading = follower.pose().heading();
        double fieldCentricForward = forward * Math.cos(-heading) - strafe * Math.sin(-heading);
        double fieldCentricStrafe = forward * Math.sin(-heading) + strafe * Math.cos(-heading);
        if (gamepad1.backWasReleased()){
            if (fieldOrBot.equals("field")) {
                fieldOrBot = "bot";
            } else {

                 fieldOrBot = "field";

                }
        }
        if (fieldOrBot.equals("field")){
            follower.manual(fieldCentricForward,fieldCentricStrafe,rotate);

        } else {
            follower.manual (forward,strafe,rotate);

        }
        telemetry.addData("field",fieldOrBot);



        telemetry.addData("X", pose.x());
        telemetry.addData("Y", pose.y());
        telemetry.addData("Heading", pose.heading());
        if (bangBang!= null) {
            if (gamepad1.leftTriggerWasReleased() || gamepad2.leftTriggerWasReleased()) {
                //shootbang shooting moter
                bangBang.setVelocity(defaultShootVelocity);
            } else if (gamepad1.rightTriggerWasReleased() || gamepad2.rightTriggerWasReleased()) {
                bangBang.setVelocity(0);
            } else if (gamepad1.rightBumperWasReleased() || gamepad2.rightBumperWasReleased()) {
                //decrease shoot speed
                bangBang.setVelocity(bangBang.getVelocity() + 5);
            } else if (gamepad1.leftBumperWasReleased() || gamepad2.leftBumperWasReleased()) {
                //increase shoot speed
                bangBang.setVelocity(bangBang.getVelocity() - 5);
            }
        }
        telemetry.addLine("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        telemetry.addLine("   left bumper: +speed-----right bumper: -speed");
        telemetry.addLine("left trigger: shoot ON---right trigger: shoot OFF");
        telemetry.addLine("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        if (bangBang!= null) {
            telemetry.addData("Shoot Speed ", -bangBang.getPower());
            telemetry.addData("Shoot Velocity", -bangBang.getVelocity());

            //data for controls
            telemetry.addLine("------------------------------------");
            telemetry.addLine("------------Intake----------------- ");
            telemetry.addLine("      [Y]  ");
            telemetry.addLine(" [X] OFF                [B] REVERSE");
            telemetry.addLine("      [A] ON ");
            telemetry.addLine("------------------------------------");
            telemetry.addData("Pick up motor power: ",pickup.getPower());
            telemetry.addData("Spinner motor power: ",-spinner.getPower());
            if(gamepad1.aWasReleased() || gamepad2.aWasReleased()){
                //intake ON
                pickup.setPower(0.5);
                spinner.setPower(-1);
                //flipperUpper.setPower(-0.5);
            } else if (gamepad1.xWasReleased() || gamepad2.xWasReleased()){
                //intake OFF
                spinner.setPower(0);
                pickup.setPower(0);
                flipperUpper.setPower(0);
            }else if (gamepad1.bWasPressed() || gamepad2.bWasPressed()){
                // reverse
                pickup.setPower(-0.5);
                spinner.setPower(1);
            }else if (gamepad1.bWasReleased() || gamepad2.bWasReleased()){
                //back to normal
                pickup.setPower(0.5);
                spinner.setPower(-1);
            }
        }




        //blocking from shooting
        //driver 2
        // up d pad block up
        // down d pad block down
        if (blocker!= null) {
            if (gamepad2.dpad_up && bangBang.getVelocity()>=(defaultShootVelocity - 30)) {
                blocker.setPosition(.25);
                //timer.reset();
                //if (timer.milliseconds() > 250) {
                  //  blocker.setPosition(.25);
             //   }
            } else if (gamepad2.dpad_down && bangBang.getVelocity()>=(defaultShootVelocity - 30))  {
                blocker.setPosition(0.9);
                follower.setPose(start);
                follower.hold(start);
            } else {
                blocker.setPosition(.75);
            }

            telemetry.addLine("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            telemetry.addLine("------Shot Blocker------------");
            telemetry.addLine("         UP - Blocker up    ");
            telemetry.addLine("  left                  right   ");
            telemetry.addLine("         Down - Blocker down");
            telemetry.addData("Block position", blocker.getPosition());
        }
        telemetry.update();
    }
}


