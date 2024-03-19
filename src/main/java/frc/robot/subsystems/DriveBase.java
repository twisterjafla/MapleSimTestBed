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
import com.revrobotics.CANSparkLowLevel.MotorType;

public class DriveBase extends SubsystemBase {

  public final CANSparkMax sparkMaxLeftBack = new CANSparkMax(Constants.drive.leftBackMotor, MotorType.kBrushless);
  public final CANSparkMax sparkMaxLeftFront = new CANSparkMax(Constants.drive.leftFrontMotor, MotorType.kBrushless);
  public final CANSparkMax sparkMaxRightBack = new CANSparkMax(Constants.drive.rightBackMotor, MotorType.kBrushless);
  public final CANSparkMax sparkMaxRightFront = new CANSparkMax(Constants.drive.rightFrontMotor, MotorType.kBrushless);

  public final RelativeEncoder encoderR;
  public final RelativeEncoder encoderL;

  final MotorControllerGroup leftMotors = new MotorControllerGroup(sparkMaxLeftFront, sparkMaxLeftBack);
  final MotorControllerGroup rightMotors = new MotorControllerGroup(sparkMaxRightFront, sparkMaxRightBack);
  final DifferentialDrive m_RobotDrive;

  public DriveBase() {
   
    encoderL=sparkMaxLeftBack.getEncoder();
    encoderR=sparkMaxRightBack.getEncoder();

    sparkMaxLeftFront.setOpenLoopRampRate(Constants.drive.rampspeed);
    sparkMaxRightFront.setOpenLoopRampRate(Constants.drive.rampspeed);
    sparkMaxRightBack.setOpenLoopRampRate(Constants.drive.rampspeed);
    sparkMaxRightFront.setOpenLoopRampRate(Constants.drive.rampspeed);

    encoderL.setPositionConversionFactor(Constants.drive.encoderToMetersRatio);
    encoderR.setPositionConversionFactor(Constants.drive.encoderToMetersRatio);

    sparkMaxLeftBack.setInverted(true);
    sparkMaxLeftFront.setInverted(true);

    m_RobotDrive = new DifferentialDrive(leftMotors, rightMotors);
    resetEncoder();
  }

  public void resetEncoder(){
    encoderL.setPosition(0);
    encoderR.setPosition(0);
  }


  public void drive(final double ySpeed, final double rotateValue) {
    SmartDashboard.putNumber("encoder", getEncoderAvrg());
    SmartDashboard.putNumber("speed", ySpeed);
    SmartDashboard.putNumber("rotate", -rotateValue);
    m_RobotDrive.arcadeDrive(ySpeed, -rotateValue);
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


  public void shift(boolean isHigh){
  //   encoderL.shift(isHigh);
  //   encoderR.shift(isHigh);
  }

}
