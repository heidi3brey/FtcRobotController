package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//import java.util.Timer;
import com.pedropathing.utils.Timer;

@TeleOp
public class SampleAutoPathing extends OpMode {
    private Follower follower;
    private Timer pathTimer, opModeTimer;

    public  enum PathState{
        // start position to end position
        // drive - movmeent state
        // shoot - attempt to score pollin/nector
        Drive_STARTPOS_SHOOT_POS,
        SHOOT_PRELOAD
    }
    PathState pathState;

    private final Pose startPose = Pose[];// x, y, heading in radians
    private final Pose shootPose = Pose[];

    /*
    video how to write ftc auto programs for pedropathing

    private PathChain driveStartPosShootPose;
    public void buildPaths[]{
            //put in coordinate for starting pose > ending pose
        driveStartPosShootPose = follower.pathBuilder()
                .addPath(BezierLine(startPose,shootPose))

    }



    public void statePathUpdate(){
        switch (pathState){
            case Drive_STARTPOS_SHOOT_POS:
                follower.follwPath(drive...)
        }
    }
        */
    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }
}
