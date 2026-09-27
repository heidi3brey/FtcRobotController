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
                Controller primaryTranslationalForward = Controller.proportional(0.19438470567956515);
                Controller secondaryTranslationalForward = Controller.proportional(0.07181995469360329);
                Controller primaryTranslationalLateral = Controller.proportional(0.6386306103215901);
                Controller secondaryTranslationalLateral = Controller.proportional(0.2359569459896379);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.018623217920797304));
                c.brake.set(Controller.proportionalFeedforward(0.015829735232677708));

                c.headingFeedback.set(Controller.proportional(66.8276029250737));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.05606538497332215, 0.002019049776387706));

                c.linearBrakeCoefficients.set(Matrix.diag(3.5555612298138857, 1.194560205691727));
                c.quadraticBrakeCoefficients.set(Matrix.diag(-0.07203956749256023, -0.0023033910254839367));

                c.maxAchievableForwardVelocity.set(60.00385477493633);
                c.maxAchievableStrafeVelocity.set(22.54288331890124);
                c.naturalForwardDeceleration.set(44.08871780892126);
                c.naturalStrafeDeceleration.set(71.67987417020186);
            }
    );
}