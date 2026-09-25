package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@Disabled
@TeleOp
public class UseRobotLocationOpMode extends OpMode {
    RobotLocationPractice robotLocationPractice = new RobotLocationPractice(0);
    @Override
    public void init() {
        robotLocationPractice.setAngle(0);
        robotLocationPractice.setX(0);
    }

    @Override
    public void loop() {
        if (gamepad1.a){
            robotLocationPractice.turnRobot(0.1);

        }
        else if (gamepad1.b){
            robotLocationPractice.turnRobot(-0.1);
        }

        telemetry.addData("Heading", robotLocationPractice.getHeading());
        telemetry.addData("Angle",robotLocationPractice.getAngle());

        if (gamepad1.dpad_left){
            //add 0.1 to x
            robotLocationPractice.changeX(0.1);
        }
        else if (gamepad1.dpad_right){
            // -0.1 to x
            robotLocationPractice.changeX(-0.1);
        }

        telemetry.addData("X value", robotLocationPractice.getX());

        if (gamepad1.dpad_down){
            //add 0.1 to y
            robotLocationPractice.changeY(0.1);
        }
        else if (gamepad1.dpad_up){
            // -0.1 to y
            robotLocationPractice.changeY(-0.1);
        }

        telemetry.addData("Y value", robotLocationPractice.getY());
    }
}
