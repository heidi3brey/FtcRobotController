package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

    private DcMotor intakeMotor;
    private Servo liftServo;

    public Intake(HardwareMap hardwareMap) {
        intakeMotor = hardwareMap.get(DcMotor.class, "intake");
        liftServo   = hardwareMap.get(Servo.class, "intakeLift");
    }

    public void lower() {
        liftServo.setPosition(0.2);
    }

    public void raise() {
        liftServo.setPosition(0.8);
    }

    public void run() {
        intakeMotor.setPower(1.0);
    }

    public boolean hasPixel() {
        // Replace with sensor logic if you have one
        return false;
    }

    public void stop() {
        intakeMotor.setPower(0);
    }
}

