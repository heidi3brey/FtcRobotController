package org.firstinspires.ftc.teamcode.tutorials;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@TeleOp
public class GamePadPractice extends OpMode {
    @Override
    public void init() {

    }

    @Override
    public void loop() {
        // runs 50x a second
        double speedForward = -gamepad1.left_stick_y / 2.0;
        double difference = gamepad1.left_stick_x-gamepad1.right_stick_x;
        double sumTriggers = gamepad1.left_trigger + gamepad1.right_trigger;

        telemetry.addData("xleft",gamepad1.left_stick_x);
        telemetry.addData("yleft",gamepad1.left_stick_y);
        telemetry.addData("a button",gamepad1.a);
        telemetry.addData("speedForward",speedForward);
        telemetry.addData("xright",gamepad1.right_stick_x);
        telemetry.addData("yright",gamepad1.right_stick_y);
        telemetry.addData("b button",gamepad1.b);
        telemetry.addData("xright and xleft", difference);
        telemetry.addData("sum triggers", sumTriggers);
    }
}
