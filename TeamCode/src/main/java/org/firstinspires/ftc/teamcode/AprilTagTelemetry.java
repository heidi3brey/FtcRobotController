package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.pedro.Constants;
//import com.qualcomm.hardware.gobilda.GoBildaPinpointDriverParameters;
@Disabled
@Autonomous
public class AprilTagTelemetry extends OpMode {

    private Limelight3A limeLight;
    PinpointLocalizer localizer;
    public String redOrBlue = "Red";
    private boolean allianceButtonPressed = false;
    private GoBildaPinpointDriver imu;
    @Override
    public void init() {
        limeLight=hardwareMap.get(Limelight3A.class, "limeLight");
        // TODO programming team- to have telemetry updated faster than 100ms default suggest adding this line
        telemetry.setMsTransmissionInterval(10);
        //  this updates data every 11ms
        // TODO end programming team
        limeLight.pipelineSwitch( 0); //change piplineSwitch. April tag 34,35,36,37
        limeLight.start();
        // ------------------------------------------------------------
        // DO NOT set up the IMU manually.
        // PedroPathing already defines the Pinpoint IMU in Constants.java
        // ------------------------------------------------------------
        //TODO programming team use localcizer from pedropathing, don't redefine here
        //      add this line:
        localizer = new PinpointLocalizer(hardwareMap, Constants.localizerConfig);
        //      this defines a nw localizer and then don't need to define imu or robot orentation in following 5 lines
        //imu.initialize(new GoBildaPinpointDriver.Parameters(revHubOrientationOnRobot);
        //TODO end programming team

        //TODO programming team
        // what data do we want to show during init to make sure we are up and running for the robot?
        // thoughts: april tags seen, robot pose, limelight health, anything else?


        //need to do in init_loop

        //TODO end programming team

    }


    @Override
    public void init_loop() {

        if (gamepad1.b && !allianceButtonPressed) {
            redOrBlue = redOrBlue.equals("Red") ? "Blue" : "Red";
        }

        allianceButtonPressed = gamepad1.b;

        telemetry.addData("Alliance", redOrBlue);
        telemetry.addLine("Press B to toggle");

        if (redOrBlue.equals("Red")) {
            limeLight.pipelineSwitch(0);
        } else if (redOrBlue.equals("Blue")){
            limeLight.pipelineSwitch((1));
        }

        LLResult result = limeLight.getLatestResult();
        if (result != null && result.isValid()) {

            // AprilTags seen
            for (LLResultTypes.FiducialResult tag : result.getFiducialResults()) {
                telemetry.addData("Tag Seen", tag.getFiducialId());
            }

            StringBuilder tagsSeen = new StringBuilder();

            for (LLResultTypes.FiducialResult tag :result.getFiducialResults()) {

                if (tagsSeen.length() > 0) {
                    tagsSeen.append(", ");
                }

                tagsSeen.append(tag.getFiducialId());
            }

            telemetry.addData("Tag Count", result.getFiducialResults().size());
            telemetry.addData("All Tags Seen", tagsSeen.toString());
/*
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
            */
        } else {
            telemetry.addLine("No tags detected");
            telemetry.addLine("Grant, Kade, and Walker are awesome");
            telemetry.addLine("System Failed :(");
            telemetry.addLine("Self Destruct In");
            telemetry.addLine("3");
            telemetry.addLine("2");
            telemetry.addLine("1");
            telemetry.addLine("Error Self Destruct Failed");
        }


        telemetry.update();
    }
    @Override
    public void start() {

        limeLight.start();
    }

    @Override
    public void loop() {
        // ------------------------------------------------------------
        // Use PedroPathing localizer instead of IMU update
        // ------------------------------------------------------------
        //TODO programming team using localizer get updates of location
        //      add these lines:
                  localizer.update();
        //          Pose2D pose = localizer.getPose();
         //         telemetry.addData("Pose", pose);
        //      then can remove the following 3 lines
        //imu.update();
        //YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        //limelight.updateRobotOrientation(orientation.getYaw());
        //TODO end programming team

        LLResult llResult = limeLight.getLatestResult();
        if (llResult != null && llResult.isValid()) {
            Pose3D botPose = llResult.getBotpose_MT2();
            telemetry.addData("Tx", llResult.getTx());
            telemetry.addData("Ty", llResult.getTy());
            telemetry.addData("Ta", llResult.getTa());
            telemetry.addData("ID Tag", llResult.getFiducialResults());
        }
        else{
            telemetry.addLine("None Found");
        }

    }
}