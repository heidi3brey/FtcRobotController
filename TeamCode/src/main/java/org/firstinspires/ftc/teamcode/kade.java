package org.firstinspires.ftc.teamcode;

import static com.pedropathing.api.Paths.*;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
// import com.pedropathing.ivy.Command;
// import com.pedropathing.ivy.Scheduler;
// import static com.pedropathing.ivy.Scheduler.schedule;
// import static com.pedropathing.ivy.commands.Commands.*;
// import static com.pedropathing.ivy.groups.Groups.sequential;
// import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.robocol.Command;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.pedro.Tuning;

@Autonomous(name = "kade", group = "Autonomous")
public class kade extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(56,8, 90);
    //private final Pose point1 = poseFactory.of(59.9685, 114.9741, -90.6732);
    private final Pose point1 = poseFactory.of(33.43886, 19.9205, 180);
    // private final Pose point2 = poseFactory.of(12.5019, 116.8574, -2.2721);
    private final Pose point2 = poseFactory.of(35.5256, 106.6278, 91.3457);
    private final Pose point3 = poseFactory.of(11.5455, 106.7173, -0.2138);

    // Autonomous routine

    @Override
    public void runOpMode() {
        //initialize your follower using constants file
        follower = new com.pedropathing.follower.Follower(
                new PinpointLocalizer(hardwareMap, Constants.localizerConfig),
                new Mecanum(hardwareMap, Constants.drivetrainConfig),
                new com.pedropathing.algorithm.Foresight(Constants.foresightConfig)
        );
        // follower = org.firstinspires.ftc.teamcode.pedro.Constants.create(hardwareMap);
        telemetry.addData("Status","Initializing...");
        telemetry.update();
        // follower = Constants.create(hardwareMap);
        // follower = Tuning.tests().getFollower(hardwareMap);
        follower.setPose(start);
        follower.update();

        waitForStart();

        if(isStopRequested()) return;

        // Execute the first path and wait for it to finish
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
    }

    public Path path1() {
        return Paths.line(start, point1).linear(start, point1);
    }

    public Path path2() {
        return Paths.line(point1, point2).reverseTangent();
    }

    public Path path3() {
        return Paths.line(point2, point3).reverseTangent();
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
