package org.firstinspires.ftc.teamcode;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//import java.util.Timer;
import com.pedropathing.utils.Timer;

import org.firstinspires.ftc.teamcode.pedro.Constants;

import java.nio.channels.ClosedByInterruptException;
@Disabled
@Autonomous(name = "Sample Pedro AutoHeidi")
public class SampleAutoPathing extends LinearOpMode {
    private Follower follower;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();
        // follower = Constants.create(hardwareMap);
        //initialize your follower using constants file
        follower = new com.pedropathing.follower.Follower(
                new PinpointLocalizer(hardwareMap, Constants.localizerConfig),
                new Mecanum(hardwareMap, Constants.drivetrainConfig),
                new com.pedropathing.algorithm.Foresight(Constants.foresightConfig)
        );

        PoseFactory p = PoseFactory.degrees();
        Pose start = p.of(24,24,0);
        Pose end = p.of(48, 24,0);

        Path line = Paths.line(start,end);
        line = line.constant(start);

        follower.setPose(start);
        telemetry.addLine("Ready Ready");
        telemetry.update();
        waitForStart();

        follower.follow(line);

        while (opModeIsActive() && follower.isBusy()){
            follower.update();
        }
        requestOpModeStop();
    }
}
