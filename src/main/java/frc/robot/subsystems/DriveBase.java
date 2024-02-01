package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class DriveBase extends SubsystemBase {
 // new CANSparkMax(Constats.drive.lt, MotorType.kBrushless);

// left side
 public final CANSparkMax sparkMaxlr = new CANSparkMax(Constants.drive.lr, MotorType.kBrushless);
 public final CANSparkMax sparkMaxlf = new CANSparkMax(Constants.drive.lf, MotorType.kBrushless);

//right side
public final CANSparkMax sparkMaxrr = new CANSparkMax(Constants.drive.rr, MotorType.kBrushless);
public final CANSparkMax sparkMaxrf = new CANSparkMax(Constants.drive.rf, MotorType.kBrushless);
public final RelativeEncoder encoderR;
public final RelativeEncoder encoderL;




  final MotorControllerGroup leftMotors = new MotorControllerGroup(
    sparkMaxlf
      );

  final MotorControllerGroup rightMotors = new MotorControllerGroup(
    
    
    sparkMaxrf
      );

  final DifferentialDrive m_RobotDrive;

  public DriveBase() {


    //left voltage ramping
    encoderR=sparkMaxrf.getEncoder();    
    encoderL= sparkMaxlf.getEncoder();



    sparkMaxlr.setOpenLoopRampRate(Constants.drive.rampspeed);
    sparkMaxlf.setOpenLoopRampRate(Constants.drive.rampspeed);

    //right voltage ramping
    sparkMaxrr.setOpenLoopRampRate(Constants.drive.rampspeed);
    sparkMaxrf.setOpenLoopRampRate(Constants.drive.rampspeed);


    //leftMotors.setInverted(true);
    //m_RobotDrive = new DifferentialDrive(rightMotors, leftMotors)
    m_RobotDrive = new DifferentialDrive(leftMotors, rightMotors);
    resetEncoder();
    

    addChild("Drive", m_RobotDrive);
    SmartDashboard.putNumber("encoder", (getEncoderAvrg()));

  }
  //returns average of encoder values.

  public double getEncoderAvrg(){
    // SmartDashboard.putNumber("encoder", (encoderL.getPosition()+encoderR.getPosition())/2);
    return encoderToMeters((getLeftEncoder()+getRightEncoder())/2);
  }

  public double getLeftEncoder(){
    return encoderL.getPosition();
  }

  public double getRightEncoder(){
    return encoderR.getPosition();
  }

  public void resetEncoder(){
    encoderL.setPosition(0);
    encoderR.setPosition(0);
  }


  public void drive(final double ySpeed, final double rotateValue) {
    SmartDashboard.putNumber("encoder", getEncoderAvrg());
    m_RobotDrive.arcadeDrive(ySpeed, rotateValue);

  }

  public double encoderToMeters(double encoderValue){
    return (encoderValue/Constants.robotStats.gearRatio)*(Constants.robotStats.gearRatio*Math.PI*2);
  }

  public double getRightEncoderInMeters(){
    return encoderToMeters(getRightEncoder());
  }

  public double getLeftEncoderInMeters(){
    return encoderToMeters(getLeftEncoder());
  }

  public void tankDriveVolts(double leftVolts, double rightVolts) {
    leftMotors.setVoltage(leftVolts);
    rightMotors.setVoltage(rightVolts);
    m_RobotDrive.feed();
  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(encoderL.getVelocity()*60, encoderR.getVelocity()*60);
  }

}
