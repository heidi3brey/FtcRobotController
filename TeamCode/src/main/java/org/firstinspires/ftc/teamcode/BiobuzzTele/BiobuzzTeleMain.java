package org.firstinspires.ftc.teamcode.BiobuzzTele;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pedro.Constants;
@TeleOp(name="BiobuzzTeleMain")
public class BiobuzzTeleMain extends OpMode{
    private DcMotor lb;
    private DcMotor lf;
    private DcMotor rf;
    private DcMotor rb;
    private PinpointLocalizer localizer;
    private Follower follower;
    private Limelight3A limelight;
    private String alliance;
    @Override
    public void init(){
        //    Motors
        lb = hardwareMap.get(DcMotor.class,"leftBack");
        rb = hardwareMap.get(DcMotor.class,"rightBack");
        lf = hardwareMap.get(DcMotor.class,"leftFront");
        rf = hardwareMap.get(DcMotor.class,"rightFront");
        // Localizer
        localizer = new PinpointLocalizer(hardwareMap, Constants.localizerConfig);
        // Follower
        follower = Constants.create(hardwareMap);
        //  Limelight
        limelight = hardwareMap.get(Limelight3A.class,"limeLight");
        limelight.pipelineSwitch(0);
        alliance = "red";
        telemetry.addLine("biobuzz telyop ready");
        telemetry.addData("Allince",alliance);

        double fl = 0;

        telemetry.update();
    }
    @Override
    public void start(){
        limelight.start();

    }

    @Override
    public void loop(){
        localizer.update();
        follower.update();
        Pose pose = follower.pose();

        double forward = -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double rotate = gamepad1.right_stick_x;
        double rotateAuto = 0;

        telemetry.addData("forward",forward);
        telemetry.addData("rotate",forward);
        telemetry.addData("strafe",forward);
    // -------------------
        if (alliance.equals("Red")) {
             forward = -gamepad1.left_stick_y;
             strafe = gamepad1.left_stick_x;
        }
        else if (alliance.equals("Blue")){
             forward = gamepad1.left_stick_y;
             strafe = -gamepad1.left_stick_x;
        }
        else {
            telemetry.addLine("not on red or blue team... pick one");
        }
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
        //user driving
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
        if(gamepad1.b){
            //auto rotate
            fl =
                    fieldForward +
                            fieldStrafe +
                            rotateAuto;

            fr =
                    fieldForward -
                            fieldStrafe -
                            rotateAuto;

            bl =
                    fieldForward -
                            fieldStrafe +
                            rotateAuto;

            br =
                    fieldForward +
                            fieldStrafe -
                            rotateAuto;
        }


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
        lf.setPower(fl);
        rf.setPower(fr);
        lb.setPower(bl);
        rb.setPower(br);
        //-------------------------------------------------
        //Limelight
        //-------------------------------------------------
        LLResult result = limelight.getLatestResult();


        // ------------------------------------------------
        // Telemetry
        // ------------------------------------------------
        telemetry.addLine("=== BioBuzz TeleOp ===");

        telemetry.addData("Alliance", alliance);

        telemetry.addData("X", pose.x()); //.getX());
        telemetry.addData("Y", pose.y()); //getY());
        telemetry.addData("Heading", pose.heading()); //getHeading());
        if (result != null && result.isValid()){
            StringBuilder tagsSeen = new StringBuilder();
            for (LLResultTypes.FiducialResult tag: result.getFiducialResults()){
                if (tagsSeen.length()>0){
                    tagsSeen.append(", ");
                }
                tagsSeen.append(tag.getFiducialId());
            }
            telemetry.addData("Tags Seen",tagsSeen.toString());
            telemetry.addData("Tag Count",result.getFiducialResults().size());
            telemetry.addData("Tx",result.getTx());
            telemetry.addData("Ty",result.getTy());
            telemetry.addData("Ta",result.getTa());
        }
        else {
            telemetry.addLine("No Tags Seen :(");
        }

        // Auto Aim
        double basketY = 48;
        double basketX = 48;
        double kP = .5;
        if (gamepad1.b){
           // Pose pose = follower.pose();
            double desiredHeading = Math.atan2(basketY - pose.y(), basketX - pose.x());
            telemetry.addData("desiredHeading",desiredHeading);
            double error = desiredHeading - pose.heading();
            rotateAuto = error * kP;
            //follower.setHeading(desiredHeading);

        }
        follower.manual(forward,strafe,rotate);

        telemetry.update();
    }
}


