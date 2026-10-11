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
        // TODO programming team- to have telemetry updated faster than 100ms default suggest adding this line
        //      telemetry.setMsTransmissionInterval(11);
        //  this updates data every 11ms
        // TODO end programming team
        limelight.pipelineSwitch( 0); //change piplineSwitch. April tag 34,35,36,37
        // ------------------------------------------------------------
        // DO NOT set up the IMU manually.
        // PedroPathing already defines the Pinpoint IMU in Constants.java
        // ------------------------------------------------------------
        //TODO programming team use localcizer from pedropathing, don't redefine here
        //      add this line:
        //          localizer = new PinpointLocalizer(hardwareMap, Constants.localizerConfig);
        //      this defines a nw localizer and then don't need to define imu or robot orentation in following 5 lines
        imu = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        RevHubOrientationOnRobot revHubOrientationOnRobot = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection. FORWARD);
        //imu.initialize(new GoBildaPinpointDriver.Parameters(revHubOrientationOnRobot);
        //TODO end programming team

        //TODO programming team
        // what data do we want to show during init to make sure we are up and running for the robot?
        // thoughts: april tags seen, robot pose, limelight health, anything else?
        /*
        LLResult result = limeLight.getLatestResult();
        if (result != null && result.isValid()) {

            // AprilTags seen
            for (FiducialResult tag : result.getFiducialResults()) {
                telemetry.addData("Tag Seen", tag.getID());
            }

            // Robot pose
            Pose3D pose = result.getBotPose_MT2();
            telemetry.addData("X", pose.getPosition().x);
            telemetry.addData("Y", pose.getPosition().y);
            telemetry.addData("Heading", pose.getOrientation().getYaw());

            // Limelight health
            LLStatus status = limeLight.getStatus();
            telemetry.addData("FPS", status.getFps());
            telemetry.addData("CPU", status.getCpu());
            telemetry.addData("Temp", status.getTemp());
        } else {
            telemetry.addLine("No tags detected");
        }


        telemetry.update();

         */
        //TODO end programming team

    }

    @Override
    public void start() {
        limelight.start();
    }

    @Override
    public void loop() {
        // ------------------------------------------------------------
        // Use PedroPathing localizer instead of IMU update
        // ------------------------------------------------------------
        //TODO programming team using localizer get updates of location
        //      add these lines:
        //          localizer.update();
        //          Pose2D pose = localizer.getPose();
        //          telemetry.addData("Pose", pose);
        //      then can remove the following 3 lines
        imu.update();
        //YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        //limelight.updateRobotOrientation(orientation.getYaw());
        //TODO end programming team

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