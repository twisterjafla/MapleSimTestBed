/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

/**
 * Have the robot drive tank style.
 */
public class ArcadeDrive extends CommandBase {
  private final DriveSubsystem m_driveSubsystem;
  private final DoubleSupplier m_speed;
  private final DoubleSupplier m_rotation;

  /**
   * Creates a new ArcadeDrive command.
   *
   * @param left       The control input for the left side of the drive
   * @param right      The control input for the right sight of the drive
   * @param driveSubsystem The driveSubsystem subsystem to drive
   */
  public ArcadeDrive(DriveSubsystem driveSubsystem, DoubleSupplier speed, DoubleSupplier rotation) {
    m_driveSubsystem = driveSubsystem;
    m_speed = speed;
    m_rotation = rotation;
    addRequirements(m_driveSubsystem);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    SmartDashboard.putString("On", "true");
    Double driveMotorSpeed = Math.abs(m_speed.getAsDouble()) > 0.1 ? m_speed.getAsDouble() : 0;
    Double rotationMotorSpeed = Math.abs(m_rotation.getAsDouble()) > 0.1 ? m_rotation.getAsDouble() : 0;
     m_driveSubsystem.drive(driveMotorSpeed, rotationMotorSpeed);
    //m_driveSubsystem.drive(0.4, rotationMotorSpeed); // delete me
  }
  

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return false; // Runs until interrupted
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    SmartDashboard.putString("On", "false");
    m_driveSubsystem.drive(0, 0);
  }
}
