package org.firstinspires.ftc.teamcode.BiobuzzAuto;

import static com.pedropathing.api.Paths.line;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(name = "straightDebug")
public class straightDebug extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    // GREEN TEST PATH
    private final Pose startPose = poseFactory.of(8.49, 133.44, 90);
    private final Pose endPose = poseFactory.of(8.50, 133.50, 90);

    @Override
    public void runOpMode() {

        follower = new Follower(
                new PinpointLocalizer(hardwareMap, Constants.localizerConfig),
                new Mecanum(hardwareMap, Constants.drivetrainConfig),
                new com.pedropathing.algorithm.Foresight(Constants.foresightConfig)
        );

        follower.setPose(startPose);

        telemetry.addLine("Straight Debug Ready");
        telemetry.update();

        waitForStart();

        if (isStopRequested()) return;

        follower.follow(straightPath());

        while (opModeIsActive()) {

            follower.update();

            telemetry.addData("X", follower.pose().x());
            telemetry.addData("Y", follower.pose().y());
            telemetry.addData("Heading", Math.toDegrees(follower.pose().heading()));

            if (follower.currentPath() != null) {
                telemetry.addData("Distance Remaining",
                        follower.distanceToEndpoint());
            }

            telemetry.update();
        }
    }

    private Path straightPath() {
        return line(startPose, endPose).constant(endPose);
    }
}