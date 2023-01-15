/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ReverseIntakeCommand extends CommandBase {

  private final IntakeSubsystem m_intakeSubsystem;
  private final double m_outtakeSpeed;

  public ReverseIntakeCommand(IntakeSubsystem outtakeSubsystem, double speed) {
    m_intakeSubsystem = outtakeSubsystem;
    m_outtakeSpeed = speed;
    // Use addRequirements() here to declare subsystem dependencies.
    // addRequirements(m_intakeSubsystem);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    m_intakeSubsystem.intakeCargo(m_outtakeSpeed);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return false; // Runs until interrupted
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    m_intakeSubsystem.intakeCargo(0);
  }
}
