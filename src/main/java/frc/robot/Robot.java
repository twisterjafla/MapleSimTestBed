// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//import edu.wpi.first.wpilibj.motorcontrol.MotorController;
//import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
//import edu.wpi.first.wpilibj.XboxController.Axis;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


/**
 * This is a demo program showing the use of the DifferentialDrive class, specifically it contains
 * the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  private DifferentialDrive m_myRobot;
  private XboxController controler1 = new XboxController(0);

  private CANSparkMax m_leftMotor = new CANSparkMax(0,MotorType.kBrushless);
  private CANSparkMax m_rightMotor= new CANSparkMax(1,MotorType.kBrushless);

  @Override
  // @overide doesn't define me, I can stop any time I want

  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_rightMotor.setInverted(true);

    m_myRobot = new DifferentialDrive(m_leftMotor, m_rightMotor);
    //m_leftStick = new Joystick(0);
    //m_rightStick = new Joystick(1);

    
  }

  @Override
  public void teleopPeriodic() {

    //its saying that it wants me to use a different button
    m_myRobot.tankDrive(controler1.getLeftTriggerAxis(), controler1.getRightTriggerAxis());
  }
}
