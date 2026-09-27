package org.firstinspires.ftc.teamcode.tutorials;

public class RobotLocationPractice {
    double angle;
    double x;
    double y;

    // constructor method
    public RobotLocationPractice(double angle){
        this.angle = angle;
    }

    public double getHeading(){
        //normalize angle of turn
        // -180 to 180
        // this method normalizes robot heading between -180 and 180
        // this is useful for calculating turn angle, especially when crossing the 0,360 boundary

        double angle = this.angle; // copy the angle of imu
        while (angle > 180){
            angle -= 360; // subtract until in target range
        }
        while (angle <= -180){
            angle += 360; // add until in target range
        }
        return angle; // return normalize range
    }

    public void turnRobot (double angleChange){
        angle += angleChange;
    }

    public void setAngle(double angle){
        this.angle = angle;
    }

    public double getAngle(){
        return this.angle;
    }


    public double getX(){
        return this.x;
    }

    public void setX(double newX){
        this.x = newX;
    }
    public void changeX(double changeAmount){
        x += changeAmount;
    }


    public double getY(){
        return this.y;
    }

    public void setY(double newY){
        this.y = newY;
    }
    public void changeY(double changeAmount){
        y += changeAmount;
    }
}
