package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "DriveMain", group = "Iterative Opmode")
public class DriveMain extends OpMode {
    private Foundation foundation;
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftRear = null;
    private DcMotor rightRear = null;
    private DcMotor leftFront = null;
    private DcMotor rightFront = null;
    public void init(){
        foundation = new Foundation(hardwareMap);
        leftRear = hardwareMap.get(DcMotor.class, "backLeft");
        rightRear = hardwareMap.get(DcMotor.class, "backRight");
        leftFront = hardwareMap.get(DcMotor.class, "frontLeft");
        rightFront = hardwareMap.get(DcMotor.class, "frontRight");


        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        //STUFF MIGHT GO BACKWARDS! JUST CHANGE EVERYTHIGN TO THE OPPOSITE IF IT DOES
        leftRear.setDirection(DcMotor.Direction.REVERSE);
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        rightRear.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData("Status","Initialized");

    }
    public void loop(){
        //double theta = Math.atan2(-gamepad1.left_stick_y, -gamepad1.left_stick_x);
        //double magnitude = Math.sqrt(Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2));
        //double turn = -Range.clip(gamepad1.right_stick_x, -1, 1);
        double theta = Math.atan2(-gamepad1.right_stick_y, -gamepad1.right_stick_x);
        double magnitude = Math.sqrt(Math.pow(gamepad1.right_stick_x, 2) + Math.pow(gamepad1.right_stick_y, 2));
        double turn = Range.clip(gamepad1.left_stick_x, -1, 1);

        //IF STUFF TURNS BACKWARD ADJUST THE PLUSES AND MINUSES
        double rf = Math.sin(theta + (Math.PI/4)) * magnitude;
        double lf = Math.sin(theta - (Math.PI/4)) * magnitude;
        double rb = Math.sin(theta - (Math.PI/4)) * magnitude;
        double lb = Math.sin(theta + (Math.PI/4)) * magnitude;

        leftRear.setPower(lb + turn);
        rightRear.setPower(rb - turn);
        leftFront.setPower(lf + turn);
        rightFront.setPower(rf - turn);

        if(gamepad1.right_bumper){
            foundation.moveUp();
        }
        else if(gamepad1.left_bumper){
            foundation.moveDown();
        }
        telemetry.update();
    }
}