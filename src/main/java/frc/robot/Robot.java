package frc.robot;

import com.ctre.phoenix.motorcontrol.InvertType; //don't remove this import!
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Robot extends TimedRobot {

    //declare and set CAN ids on our drivetrain controllers
    WPI_TalonSRX rightFront = new WPI_TalonSRX(4);
    WPI_TalonSRX rightBack = new WPI_TalonSRX(5);
    WPI_TalonSRX leftFront = new WPI_TalonSRX(0);
    WPI_TalonSRX leftBack = new WPI_TalonSRX(1);
    
    //set up our drivetrain mechanism
    DifferentialDrive driveTrain = new DifferentialDrive(leftFront, rightFront);

    //tells the program what controller port to use - make sure this matches on driver station software
    Joystick driveController = new Joystick(0);

    @Override
    public void teleopPeriodic() {

      //read inputs from controller
      double forw = -1 * driveController.getRawAxis(1); //positive is up
      double turn = +1 * driveController.getRawAxis(2); //positive is right 

      //set deadband on controller (this makes it so the sticks aren't super sensitive)
      if (Math.abs(forw) < 0.10) {
        forw = 0;
      }
      if (Math.abs(turn) < 0.10) {
        turn = 0;
      }

      //take controller inputs and send them to our subsystem
      driveTrain.arcadeDrive(forw, turn);

    }

    @Override
    public void robotInit() {

        //set neutral mode
        rightFront.setNeutralMode(NeutralMode.Coast);
        rightBack.setNeutralMode(NeutralMode.Coast);
        leftFront.setNeutralMode(NeutralMode.Coast);
        leftBack.setNeutralMode(NeutralMode.Coast);

        //set up the followers and masters
        rightBack.follow(rightFront);
        leftBack.follow(leftFront);

        //flip values so robot moves forward when told to go forward
        //rightFront.setInverted(true);
        //leftFront.setInverted(false); 

        //uncomment below to invert the followers to match the masters, if needed
        //rightBack.setInverted(InvertType.FollowMaster);
        //leftBack.setInverted(InvertType.FollowMaster);

        //wpilib thing - dont delete :)
        driveTrain.setRightSideInverted(false);
    }
}