package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Flower {

    private Servo flowerServo;

    public Flower(HardwareMap hardwareMap) {
        flowerServo = hardwareMap.get(Servo.class, "flower");
    }

    public void open() {
        flowerServo.setPosition(1.0);
    }

    public void close() {
        flowerServo.setPosition(0.0);
    }

    public void shake() {
        flowerServo.setPosition(0.5);
    }

    public void stop() {
        // nothing needed
    }
}
