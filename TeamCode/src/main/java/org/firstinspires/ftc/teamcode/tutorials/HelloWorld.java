package org.firstinspires.ftc.teamcode.tutorials;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Disabled
@Autonomous
//@TeleOp
public class HelloWorld extends OpMode {
    @Override
    public void init() {
        telemetry.addData("Hello", "heidi");
    }

    @Override
    public void loop() {

    }
}
