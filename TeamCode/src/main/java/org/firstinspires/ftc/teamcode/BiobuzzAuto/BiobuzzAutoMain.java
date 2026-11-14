package org.firstinspires.ftc.teamcode.BiobuzzAuto;

import static com.pedropathing.api.Paths.line;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(name = "BiobuzzAutoMain", preselectTeleOp = "BiobuzzTeleMain")
public class BiobuzzAutoMain extends LinearOpMode {

    // set alliance value based on position and can override with B
    public String alliance = "red";
    public String robotPath = "none";
    private final PoseFactory poseFactory = PoseFactory.degrees();

    private static final Pose start =
            new Pose(56, 0, Math.toRadians(90));
    private final Pose point1 = poseFactory.of(60, 115, 90);
    private final Pose point2 = poseFactory.of(12, 116, 90);
    private final Pose greenStart = poseFactory.of(8.49, 133.44, 90);
    private final Pose greenEnd = poseFactory.of(10.81, 150, 90);
    private  final  Pose offwallstart = poseFactory.of(0,0,90);

    private  final  Pose offwallend = poseFactory.of(5,5,90);


    private static final Pose BLUE_START_POSE =
            new Pose(132, 60, Math.toRadians(90));

    private Follower follower;

    public void runOpMode(){
        follower = new com.pedropathing.follower.Follower(
                new PinpointLocalizer(hardwareMap, Constants.localizerConfig),
                new Mecanum(hardwareMap, Constants.drivetrainConfig),
                new com.pedropathing.algorithm.Foresight(Constants.foresightConfig)
        );
        telemetry.addData("Status","Initializing...");
        telemetry.update();
        // follower = Constants.create(hardwareMap);
        // follower = Tuning.tests().getFollower(hardwareMap);
        follower.setPose(start);
        follower.update();

        while (!isStarted() && !isStopRequested()) {
            //TODO add in alliance based on limelight apriltags

            //TODO add in IF statement to help with alliance toggle
            if (gamepad1.x) {
                alliance = "blue";
            }

            if (gamepad1.b) {
                alliance = "red";
            }
            if (gamepad1.yWasReleased()){
                robotPath = "yellow";
            }
            if (gamepad1.aWasReleased()){
                robotPath = "green";
            }

            AutoState.alliance = alliance;

            telemetry.addData("Alliance", alliance);
            telemetry.addData( "Robot Path", robotPath);
            //telemetry.addData("X",pose.x());
            //telemetry.addData("Y",pose.y());
            telemetry.update();
        }

        waitForStart();

        if(isStopRequested()) return;
        // TO DO shoot starting pollen, turn on intake

        // Execute the first path and wait for it to finish
        if (robotPath.equals("yellow")) {
            follower.setPose(start);
            follower.follow(path1());
            while (opModeIsActive() && follower.isBusy()) {
                follower.update();
                telemetryDebug(); // adds real-time debugging updates here
            }
            // Execute the second path and wait for it to finish
            follower.follow(path2());
            while (opModeIsActive() && follower.isBusy()) {
                follower.update();
                telemetryDebug(); // adds real-time debugging updates here
            }
            while (opModeIsActive()) {
                follower.update();
                telemetryDebug(); // adds real-time debugging updates here

            }
        } else if (robotPath.equals("green")) {
            follower.setPose(greenStart);
            follower.follow(greenPath());
            while (opModeIsActive() && follower.isBusy()) {
                follower.update();
                telemetryDebug(); // adds real-time debugging updates here
            }
            while (opModeIsActive()) {
                follower.update();
                telemetryDebug(); // adds real-time debugging updates here

            }
        } else {
            follower.setPose(offwallstart);

            follower.follow(offwallpath());
            while (opModeIsActive() && follower.isBusy()) {
                follower.update();
                telemetryDebug(); // adds real-time debugging updates here
            }
            while (opModeIsActive()) {
                follower.update();
                telemetryDebug(); // adds real-time debugging updates here

            }
        }


        if (alliance.equals("red")) {
            follower.setPose(start);
        }
        else {
            follower.setPose(BLUE_START_POSE);
        }
        follower.update();
        //todo not working
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
    public Path path1() {
        return line(start, point1).constant(point1);//.facingPoint(point1);
    }
    public Path path2() {
        return line(point1, point2).constant(point2);
    }
    public Path greenPath() {
        return line(greenStart, greenEnd).constant(greenEnd);
    }
    public Path offwallpath() {
        return line(offwallstart, offwallend).constant(offwallend);
    }
    public void telemetryDebug(){
        telemetry.addData("x", follower.pose().x());
        telemetry.addData("y", follower.pose().y());
        telemetry.addData("heading", follower.pose().heading());

        if (follower.currentPath() != null) {
            telemetry.addData("Current path distance remaining", follower.distanceToEndpoint());
            telemetry.addData("Path number", follower.pathIndex());
        }

        telemetry.update();
    }
}
