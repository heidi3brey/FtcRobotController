package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
@Disabled
public class Shooter {

    private DcMotor shooterMotor;
    private DcMotor feederMotor;

    public Shooter(HardwareMap hardwareMap) {
        shooterMotor = hardwareMap.get(DcMotor.class, "shooter");
        feederMotor  = hardwareMap.get(DcMotor.class, "feeder");

        shooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        feederMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void spinUp() {
        shooterMotor.setPower(1.0);
    }
/*
    public boolean atSpeed() {
        return shooterMotor.getVelocity() > 1500; // adjust for your robot
    }
*/
    public void feed() {
        feederMotor.setPower(1.0);
    }

    public void stop() {
        shooterMotor.setPower(0);
        feederMotor.setPower(0);
    }
}

