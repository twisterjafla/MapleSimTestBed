package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class DriveBase extends SubsystemBase {

// left side
 public final CANSparkMax sparkMaxLeftBack = new CANSparkMax(Constants.drive.leftBackMotor, MotorType.kBrushless);
 public final CANSparkMax sparkMaxLeftFront = new CANSparkMax(Constants.drive.leftFrontMotor, MotorType.kBrushless);

//right side
public final CANSparkMax sparkMaxRightBack = new CANSparkMax(Constants.drive.rightBackMotor, MotorType.kBrushless);
public final CANSparkMax sparkMaxRightFront = new CANSparkMax(Constants.drive.rightFrontMotor, MotorType.kBrushless);
public final RelativeEncoder encoderR;
public final RelativeEncoder encoderL;



  final MotorControllerGroup leftMotors = new MotorControllerGroup(
    sparkMaxLeftFront
      );

  final MotorControllerGroup rightMotors = new MotorControllerGroup(
    
    
    sparkMaxRightFront
      );

  final DifferentialDrive m_RobotDrive;

  public DriveBase() {


    //left voltage ramping
    encoderR=sparkMaxRightFront.getEncoder();    
    encoderL= sparkMaxLeftFront.getEncoder();



    sparkMaxLeftBack.setOpenLoopRampRate(Constants.drive.rampspeed);
    sparkMaxLeftFront.setOpenLoopRampRate(Constants.drive.rampspeed);

    //right voltage ramping
    sparkMaxRightBack.setOpenLoopRampRate(Constants.drive.rampspeed);
    sparkMaxRightFront.setOpenLoopRampRate(Constants.drive.rampspeed);


    //leftMotors.setInverted(true);
    //m_RobotDrive = new DifferentialDrive(rightMotors, leftMotors)
    m_RobotDrive = new DifferentialDrive(leftMotors, rightMotors);
    resetEncoder();
    

    addChild("Drive", m_RobotDrive);
    SmartDashboard.putNumber("encoder", (getEncoder()));

  }
  //returns average of encoder values. 
  public double getEncoder(){
    // SmartDashboard.putNumber("encoder", (encoderL.getPosition()+encoderR.getPosition())/2);
    return (encoderL.getPosition()+encoderR.getPosition())/2;
  }

  public void resetEncoder(){
    encoderL.setPosition(0);
    encoderR.setPosition(0);
  }


  public void drive(final double ySpeed, final double rotateValue) {
    SmartDashboard.putNumber("encoder", getEncoder());
    m_RobotDrive.arcadeDrive(ySpeed, rotateValue);

  }

}
