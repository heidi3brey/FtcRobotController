package org.firstinspires.ftc.teamcode;
import com.pedropathing.follower.Follower;
//import com.pedropathing.localization.Pose;
import com.pedropathing.math.Pose;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.pedro.Constants;
//import org.firstinspires.ftc.teamcode.util.AllianceManager;


@TeleOp(name = "HeidiBioBuzz")
public class HeidiBioBuzz extends OpMode {

    // ----------------------------------------------------
    // Drive Motors
    // ----------------------------------------------------
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    // ----------------------------------------------------
    // Pedro / Pinpoint
    // ----------------------------------------------------
    private PinpointLocalizer localizer;
    private Follower follower;

    // ----------------------------------------------------
    // Vision
    // ----------------------------------------------------
    private Limelight3A limelight;

    // ----------------------------------------------------
    // Match Settings
    // ----------------------------------------------------
    private String alliance;

    @Override
    public void init() {

        // ------------------------------------------------
        // Drive Motors
        // ------------------------------------------------
        frontLeft =
                hardwareMap.get(DcMotor.class, "leftFront");

        frontRight =
                hardwareMap.get(DcMotor.class, "rightFront");

        backLeft =
                hardwareMap.get(DcMotor.class, "leftBack");

        backRight =
                hardwareMap.get(DcMotor.class, "rightBack");

        // Reverse whichever motors are needed
        // frontLeft.setDirection(DcMotor.Direction.REVERSE);
        // backLeft.setDirection(DcMotor.Direction.REVERSE);

        // ------------------------------------------------
        // Pedro Localization
        // ------------------------------------------------
        localizer =
                new PinpointLocalizer(
                        hardwareMap,
                        Constants.localizerConfig);

        follower =
                Constants.create(hardwareMap);

        // ------------------------------------------------
        // Limelight
        // ------------------------------------------------
        limelight =
                hardwareMap.get(
                        Limelight3A.class,
                        "limeLight");

        limelight.pipelineSwitch(0);

        // ------------------------------------------------
        // Alliance From Auto
        // ------------------------------------------------
        alliance = "unknown";
                // AllianceManager.loadAlliance();

        telemetry.addLine("BioBuzz TeleOp Ready");
        telemetry.addData("Alliance", alliance);
        telemetry.update();
    }

    @Override
    public void start() {
        limelight.start();
    }

    @Override
    public void loop() {

        // ------------------------------------------------
        // Update Localization
        // ------------------------------------------------
        localizer.update();
        follower.update();

        Pose pose = follower.pose();

        // ------------------------------------------------
        // Driver Input
        // ------------------------------------------------
        double forward = gamepad1.left_stick_y;
        double strafe  = -gamepad1.left_stick_x;
        double rotate  = -gamepad1.right_stick_x;

        // ------------------------------------------------
        // Slow Mode
        // ------------------------------------------------
        double speedScale =
                gamepad1.right_bumper ? 0.4 : 1.0;

        forward *= speedScale;
        strafe *= speedScale;
        rotate *= speedScale;

        // ------------------------------------------------
        // Field Centric Using Pedro Heading
        // ------------------------------------------------
        double heading = pose.heading();//.getHeading();

        double cosA = Math.cos(heading);
        double sinA = Math.sin(heading);

        double fieldForward =
                forward * cosA -
                        strafe * sinA;

        double fieldStrafe =
                forward * sinA +
                        strafe * cosA;

        // ------------------------------------------------
        // Mecanum Wheel Math
        // ------------------------------------------------
        double fl =
                fieldForward +
                        fieldStrafe +
                        rotate;

        double fr =
                fieldForward -
                        fieldStrafe -
                        rotate;

        double bl =
                fieldForward -
                        fieldStrafe +
                        rotate;

        double br =
                fieldForward +
                        fieldStrafe -
                        rotate;

        double max =
                Math.max(
                        1.0,
                        Math.max(
                                Math.abs(fl),
                                Math.max(
                                        Math.abs(fr),
                                        Math.max(
                                                Math.abs(bl),
                                                Math.abs(br)))));

        fl /= max;
        fr /= max;
        bl /= max;
        br /= max;

        // ------------------------------------------------
        // Send Motor Powers
        // ------------------------------------------------
        frontLeft.setPower(fl);
        frontRight.setPower(fr);
        backLeft.setPower(bl);
        backRight.setPower(br);

        // ------------------------------------------------
        // Limelight
        // ------------------------------------------------
        LLResult result =
                limelight.getLatestResult();

        // ------------------------------------------------
        // Telemetry
        // ------------------------------------------------
        telemetry.addLine("=== BioBuzz TeleOp ===");

        telemetry.addData("Alliance", alliance);

        telemetry.addData("X", pose.x()); //.getX());
        telemetry.addData("Y", pose.y()); //getY());
        telemetry.addData("Heading", pose.heading()); //getHeading());

        if (result != null && result.isValid()) {

            StringBuilder tagsSeen =
                    new StringBuilder();

            for (LLResultTypes.FiducialResult tag
                    : result.getFiducialResults()) {

                if (tagsSeen.length() > 0) {
                    tagsSeen.append(", ");
                }

                tagsSeen.append(
                        tag.getFiducialId());
            }

            telemetry.addData(
                    "Tags Seen",
                    tagsSeen.toString());

            telemetry.addData("Tag Count", result.getFiducialResults().size());

            telemetry.addData(
                    "Tx",
                    result.getTx());

            telemetry.addData(
                    "Ty",
                    result.getTy());

            telemetry.addData(
                    "Ta",
                    result.getTa());
        }
        else {

            telemetry.addData(
                    "Tags Seen",
                    "None");
        }

        telemetry.update();
    }
}
