package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.follower.Follower;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {

    // BP: public static FollowerConstants followerConstants = new FollowerConstants()
    public static Follower create(HardwareMap h) {
        // return new Follower(Drivetrain, Localizer, Foresight);
        return null;
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
        c.name.set("imu2");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(-3.3991229440283592);
        c.yPodOffset.set(-0.0737969049318569);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static final com.pedropathing.algorithm.ForesightConfig forsightConfig = new com.pedropathing.algorithm.ForesightConfig(
            config-> {

            config.forwardTranslational.set(new com.pedropathing.utils.Control());

            /*set(new com.qualcomm.robotcore.hardware.PIDFCoefficients(0.1,0,0,0)); // x position PID
            new com.qualcomm.robotcore.hardware.PIDFCoefficients(0.1,0,0,0), // y position PID
            new com.qualcomm.robotcore.hardware.PIDFCoefficients(0.1,0,0,0), // heading / turn PID
            5.3,                                        // Mass of robot (KG)
            0.1                                         // Inertia
*/
            }

    );

}