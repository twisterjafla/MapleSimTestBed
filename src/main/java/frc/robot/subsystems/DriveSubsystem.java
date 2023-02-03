// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
//import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class DriveSubsystem extends SubsystemBase {
  //right motors
  private final CANSparkMax lefttop = new CANSparkMax(Constants.LEFT_TOP,MotorType.kBrushless);
  private final CANSparkMax leftbottomright = new CANSparkMax(Constants.LEFT_BACK,MotorType.kBrushless);
  private final CANSparkMax leftbottomleft = new CANSparkMax(Constants.LEFT_FRONT, MotorType.kBrushless);

  //left motors
  private final CANSparkMax righttop = new CANSparkMax(Constants.RIGHT_TOP, MotorType.kBrushless);
  private final CANSparkMax rightbottomright = new CANSparkMax(Constants.RIGHT_BACK,MotorType.kBrushless);
  private final CANSparkMax rightbottomleft = new CANSparkMax(Constants.RIGHT_FRONT, MotorType.kBrushless);

  MotorControllerGroup m_LeftMotorGroup = new MotorControllerGroup(lefttop, leftbottomright,leftbottomleft);
  MotorControllerGroup m_RightMotorGroup = new MotorControllerGroup(righttop, rightbottomright,rightbottomleft);
  //MotorControllerGroup m_hDriveMotorGroup = new MotorControllerGroup(hDriveMotor1, hDriveMotor2);
  
  private final DifferentialDrive m_RobotDrive = new DifferentialDrive(m_LeftMotorGroup, m_RightMotorGroup);

  // TODO: Uncomment when we get the encoders put on
  // private final Encoder m_leftEncoder = new Encoder(Constants.ENCODER_PORT_A, Constants.ENCODER_PORT_B);
  // private final Encoder m_rightEncoder = new Encoder(Constants.ENCODER_PORT_C, Constants.ENCODER_PORT_D);
  private final ADXRS450_Gyro m_gyro = new ADXRS450_Gyro();
  
  public DriveSubsystem() {
    super();

    addChild("Drive", m_RobotDrive);
    // addChild("Left Encoder", m_leftEncoder);
    // addChild("Right Encoder", m_rightEncoder);
    addChild("Gyro", m_gyro);
    m_gyro.calibrate();
    // m_leftEncoder.setDistancePerPulse((Math.PI * 6) / 360.0);
    // m_rightEncoder.setDistancePerPulse((Math.PI * 6) / 360.0);

    m_LeftMotorGroup.setInverted(true);

    reset();
  }
  
  public void drive(final double ySpeed, final double rotateValue) {
    m_RobotDrive.arcadeDrive(ySpeed, rotateValue);
  }

  public void drive(final double ySpeed, final double rotateValue, final double hDriveMotorSpeed) {
    m_RobotDrive.arcadeDrive(ySpeed, rotateValue);
  }

  public void reset() {
    m_gyro.reset();
    // m_leftEncoder.reset();
    // m_rightEncoder.reset();
  }
  
  public void log() {
    // SmartDashboard.putNumber("Left Distance", m_leftEncoder.getDistance());
    // SmartDashboard.putNumber("Right Distance", m_rightEncoder.getDistance());
    // SmartDashboard.putNumber("Left Speed", m_leftEncoder.getRate());
    // SmartDashboard.putNumber("Right Speed", m_rightEncoder.getRate());
    SmartDashboard.putNumber("Gyro", m_gyro.getAngle());
  }

  /**
   * Get the robot's heading.
   *
   * @return The robots heading in degrees.
   */
  public double getHeading() {
    return m_gyro.getAngle();
  }

}
