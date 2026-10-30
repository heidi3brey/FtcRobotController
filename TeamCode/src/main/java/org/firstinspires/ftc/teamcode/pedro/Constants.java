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
                new Mecanum(h, Constants.drivetrainConfig),null
               // new Foresight(Constants.foresightConfig)
        );
    }
    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        //hardware config
        c.frontLeftName.set("leftFront");
        c.frontRightName.set("rightFront");
        c.backLeftName.set("leftBack");
        c.backRightName.set("rightBack");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.manualBrakeMode.set(true);
    });

    // BP: public static PinpointConstants localizerConstants = PinPointConstatns()

    /*public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(-3.3991229440283592); //TODO Heidi may need to measure inch
        c.yPodOffset.set(-1.4737969049318569); //TODO Heidi may need to measure inch

        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });*/

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
    /*
    public static ForesightConfig foresightConfig = new ForesightConfig(
            c ->{
                Controller primaryTranslationalForward = Controller.proportional(0.21667);
                Controller secondaryTranslationalForward = Controller.proportional(0.0880);
            }
    );
   public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.21667466823086484);
                Controller secondaryTranslationalForward = Controller.proportional(0.08005550025753985);
                Controller primaryTranslationalLateral = Controller.proportional(0.2389170506961669);
                Controller secondaryTranslationalLateral = Controller.proportional(0.08827346625106361);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.017175621348692046));
                c.brake.set(Controller.proportionalFeedforward(0.014599278146388239));

                c.headingFeedback.set(Controller.proportional(1.1984353226879165));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.0407767445147731, 0.004023985876263169));

                c.linearBrakeCoefficients.set(Matrix.diag(0.08488211509005676, 0.04056023845129483));
                c.quadraticBrakeCoefficients.set(Matrix.diag(1.2619796753143074E-4, 0.0016991433989965218));

                c.maxAchievableForwardVelocity.set(66.84287589106012);
                c.maxAchievableStrafeVelocity.set(52.73641500678751);
                c.naturalForwardDeceleration.set(489.2265286197284);
                c.naturalStrafeDeceleration.set(55.97575671461449);
            }
    );*/
    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(-6.5);
        c.yPodOffset.set(-3.5);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.27277753843595487);
                Controller secondaryTranslationalForward = Controller.proportional(0.10078401170201948);
                Controller primaryTranslationalLateral = Controller.proportional(0.39476847235996143);
                Controller secondaryTranslationalLateral = Controller.proportional(0.14585640212915146);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.018841179360138797));
                c.brake.set(Controller.proportionalFeedforward(0.016015002456117977));

                c.headingFeedback.set(Controller.proportional(48.555473477370384));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.031235536370563734, 0.05137548624520718));

                c.linearBrakeCoefficients.set(Matrix.diag(0.016377330167571348, 0.025212415184059006));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.002052718116550962, 0.0017291527432734665));

                c.maxAchievableForwardVelocity.set(56.356463399802124);
                c.maxAchievableStrafeVelocity.set(45.315456210814986);
                c.naturalForwardDeceleration.set(43.24417323676764);
                c.naturalStrafeDeceleration.set(69.29856082122693);
            }
    );
}