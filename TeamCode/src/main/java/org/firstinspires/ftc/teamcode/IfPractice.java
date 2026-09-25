package org.firstinspires.ftc.teamcode;

import androidx.annotation.VisibleForTesting;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class IfPractice extends OpMode {
    @Override
    public void init() {

    }

    @Override
    public void loop() {
        boolean aButton = gamepad1.a; // press true, depress false
        //true or false
        if(aButton) {
            telemetry.addData("A BUtton State", "Pressed");
        }
        else{
            telemetry.addData("A Button", "Not Pressed");
            }

        telemetry.addData("A Button State", aButton);

        // stick
        double leftY = gamepad1.left_stick_y;;
        if (leftY < 0){
            telemetry.addData("Left Stick","is Negtive");
        }
        else {
            telemetry.addData("left stick","is Positive");
        }
        telemetry.addData("left stick value", leftY);

        if(leftY < 0){
            telemetry.addData("left stick", "NEG");
        }
        else if (leftY > 0){
            telemetry.addData("left stick","POSITVE");
        } else {
            telemetry.addData("left stick","Zero");
        }

        // common combined comparision && || NOT!
        if(leftY < 0.1 && leftY > -0.1){
            telemetry.addData("left stick", "in dead zone");
        }

        double motorSpeed = gamepad1.left_stick_y;
        if(!aButton){
            motorSpeed = motorSpeed *0.5;
        }

        telemetry.addData("Motor Speed",motorSpeed);
    }
}
