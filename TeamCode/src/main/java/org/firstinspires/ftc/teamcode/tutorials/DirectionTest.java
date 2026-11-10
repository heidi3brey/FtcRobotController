package org.firstinspires.ftc.teamcode.tutorials;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@TeleOp(name="DirectionTest")
public class DirectionTest extends OpMode {

    private Follower follower;

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        follower.setPose(new Pose(0, 0, 0));
    }

    @Override
    public void loop() {
        follower.update();

        telemetry.addData("X", follower.pose().x());
        telemetry.addData("Y", follower.pose().y());
        telemetry.addData(
                "Heading",
                Math.toDegrees(follower.pose().heading())
        );

        telemetry.update();
    }
}