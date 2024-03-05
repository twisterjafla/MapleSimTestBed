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


  public final gearBoxEncoder encoderR;
  public final gearBoxEncoder encoderL;



  final MotorControllerGroup leftMotors = new MotorControllerGroup(
    sparkMaxLeftFront, sparkMaxLeftBack
      );

  final MotorControllerGroup rightMotors = new MotorControllerGroup(
    sparkMaxRightFront, sparkMaxRightBack
      );

  final DifferentialDrive m_RobotDrive;

  public DriveBase() {


    //left voltage ramping
    encoderR=new gearBoxEncoder(sparkMaxRightBack, Constants.drive.lowGearRatio, Constants.drive.highGearRatio, Constants.drive.Wheelcircumference);    
    encoderL= new gearBoxEncoder(sparkMaxLeftBack, Constants.drive.lowGearRatio, Constants.drive.highGearRatio, Constants.drive.Wheelcircumference);
    sparkMaxLeftBack.setInverted(true);
    sparkMaxLeftFront.setInverted(true);

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
    encoderL.resetEncoderPosition();;
    encoderR.resetEncoderPosition();;
  }


  public void drive(final double ySpeed, final double rotateValue) {
    SmartDashboard.putNumber("encoder", getEncoder());
    m_RobotDrive.arcadeDrive(ySpeed, rotateValue);

  }

  public void shift(boolean isHigh){
    encoderL.shift(isHigh);
    encoderR.shift(isHigh);
  }

}
