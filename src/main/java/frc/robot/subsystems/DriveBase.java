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

    encoderL.setPositionConversionFactor(Constants.drive.encoderToMetersRatio);
    encoderR.setPositionConversionFactor(Constants.drive.encoderToMetersRatio);

    encoderL.setVelocityConversionFactor(Constants.drive.encoderToWheelRatio);
    encoderR.setVelocityConversionFactor(Constants.drive.encoderToWheelRatio);


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

  public void resetEncoder(){
    encoderL.setPosition(0);
    encoderR.setPosition(0);
  }


  public void drive(final double ySpeed, final double rotateValue) {
    SmartDashboard.putNumber("encoder", getEncoderAvrg());
    SmartDashboard.putNumber("speed", ySpeed);
    SmartDashboard.putNumber("rotate", rotateValue);
    m_RobotDrive.arcadeDrive(ySpeed, rotateValue);

  }

  public double getEncoderAvrg(){
    return (getRightEncoder()+getLeftEncoder())/2;
  }

  public double getRightEncoder(){
    return encoderR.getPosition();
  }

  public double getLeftEncoder(){
    return encoderL.getPosition();
  }

  public void tankDriveVolts(double leftVolts, double rightVolts) {
    SmartDashboard.putNumber("leftVolt", leftVolts);
    sparkMaxlf.setVoltage(leftVolts);
    SmartDashboard.putNumber("rightVolts", rightVolts);
    sparkMaxrf.setVoltage(rightVolts);
    SmartDashboard.putNumber("voltageDiff", leftVolts-rightVolts);
    m_RobotDrive.feed();
  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(encoderL.getVelocity()*60, encoderR.getVelocity()*60);
  }

}
