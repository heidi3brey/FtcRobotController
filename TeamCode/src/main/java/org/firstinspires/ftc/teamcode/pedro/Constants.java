package org.firstinspires.ftc.teamcode.pedro;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {

    // BP: public static FollowerConstants followerConstants = new FollowerConstants()
    public static Follower create(HardwareMap h) {
        // return new Follower(Drivetrain, Localizer, Foresight);
        return new Follower(
                new PinpointLocalizer(h, Constants.localizerConfig),
                new Mecanum(h, Constants.drivetrainConfig),
                new Foresight(Constants.foresightConfig)
        );
    }
    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("leftFront");
        c.frontRightName.set("rightFront");
        c.backLeftName.set("leftBack");
        c.backRightName.set("rightBack");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.REVERSE);
        c.manualBrakeMode.set(true);
    });

    // BP: public static PinpointConstants localizerConstants = PinPointConstatns()

    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(-3.3991229440283592); //TODO Heidi may need to measure inch
        c.yPodOffset.set(-0.0737969049318569); //TODO Heidi may need to measure inch
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    /*
    public static final com.pedropathing.algorithm.ForesightConfig forsightConfig = new com.pedropathing.algorithm.ForesightConfig(
            config-> {

            config.forwardTranslational.set(new com.pedropathing.utils.Control());

            /*set(new com.qualcomm.robotcore.hardware.PIDFCoefficients(0.1,0,0,0)); // x position PID
            new com.qualcomm.robotcore.hardware.PIDFCoefficients(0.1,0,0,0), // y position PID
            new com.qualcomm.robotcore.hardware.PIDFCoefficients(0.1,0,0,0), // heading / turn PID
            5.3,                                        // Mass of robot (KG)
            0.1                                         // Inertia
            }

    );
    */
    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.22794766185418);
                Controller secondaryTranslationalForward = Controller.proportional(0.08422057018141754);
                Controller primaryTranslationalLateral = Controller.proportional(0.3428569458483733);
                Controller secondaryTranslationalLateral = Controller.proportional(0.15027034754336288);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(1.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.017313620937034743));
                c.brake.set(Controller.proportionalFeedforward(0.014716577796479531));

                c.headingFeedback.set(Controller.proportional(2.8771279895435554));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.043223640961282964, 0.004711879857920989));

                c.linearBrakeCoefficients.set(Matrix.diag(0.056154457341376994, 0.03702000190286601));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0013975925892548947, 0.002063979927863462));

                c.maxAchievableForwardVelocity.set(62.53967181055147);
                c.maxAchievableStrafeVelocity.set(54.37283242889434);
                c.naturalForwardDeceleration.set(42.04040632592756);
                c.naturalStrafeDeceleration.set(63.50599555615311);
            }
    );

}