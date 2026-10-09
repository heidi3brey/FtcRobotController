package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
//import com.qualcomm.hardware.gobilda.GoBildaPinpointDriverParameters;

@Autonomous
public class ApriltagLimeline extends OpMode {

    private Limelight3A limelight;
    private GoBildaPinpointDriver imu;
    @Override
    public void init() {
        limelight=hardwareMap.get(Limelight3A.class, "limeLight");
        limelight.pipelineSwitch( 0); //change piplineSwitch. April tag 34,35,36,37
        imu = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        RevHubOrientationOnRobot revHubOrientationOnRobot = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection. FORWARD);
        //imu.initialize(new GoBildaPinpointDriver.Parameters(revHubOrientationOnRobot);
    }

    @Override
    public void start() {
        limelight.start();
    }

    @Override
    public void loop() {
        imu.update();
        //YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        //limelight.updateRobotOrientation(orientation.getYaw());
        LLResult llResult = limelight.getLatestResult();
        if (llResult != null && llResult.isValid()) {
            Pose3D botPose = llResult.getBotpose_MT2();
            telemetry.addData("Tx", llResult.getTx());
            telemetry.addData("Ty", llResult.getTy());
            telemetry.addData("Ta", llResult.getTa());
            //telemetry.addData("ID Tag", llResult.getID);
        }
        else{
            telemetry.addLine("None Found");
        }

    }
}