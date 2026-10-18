package org.firstinspires.ftc.teamcode.BiobuzzAuto;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(name = "BiobuzzAutoMain", preselectTeleOp = "BiobuzzTeleMain")
public class BiobuzzAutoMain extends LinearOpMode {

    // set alliance value based on position and can override with B
    public String alliance = "red";

    private static final Pose RED_START_POSE =
            new Pose(12, 60, Math.toRadians(90));

    private static final Pose BLUE_START_POSE =
            new Pose(132, 60, Math.toRadians(90));

    private Follower follower;

    public void runOpMode(){
        follower = Constants.create(hardwareMap);

        while (!isStarted() && !isStopRequested()) {
            //TODO add in alliance based on limelight apriltags

            //TODO add in IF statement to help with alliance toggle
            if (gamepad1.x) {
                alliance = "blue";
            }

            if (gamepad1.b) {
                alliance = "red";
            }

            AutoState.alliance = alliance;

            telemetry.addData("Alliance", alliance);
            telemetry.update();
        }

        waitForStart();

        if (alliance.equals("red")) {
            follower.setPose(RED_START_POSE);
        }
        else {
            follower.setPose(BLUE_START_POSE);
        }
        follower.update();

        AutoState.lastPose = follower.pose();


        while(opModeIsActive()) {

            follower.update();

            Pose pose = follower.pose();

            telemetry.addData("X", pose.x());
            telemetry.addData("Y", pose.y());
            telemetry.addData("Heading", pose.heading());

            // TODO stuff we want to do in Auto

            AutoState.lastPose = follower.pose();

            telemetry.update();
        }

        //at very end of runOpMode
        //save alliance selection to use in teleop
        AutoState.alliance = alliance;
        AutoState.lastPose = follower.pose();

        telemetry.addLine("Pose saved for TeleOp");
        telemetry.update();
    }
}
