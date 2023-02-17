package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
//Most of this is coppied from IntakeCommand
import frc.robot.subsystems.IntakeSubsystem;


public class IntakeToggleCommand extends CommandBase {

    private final IntakeSubsystem m_intakeSubsystem;
  
    public IntakeToggleCommand(IntakeSubsystem intakeSubsystem) {
      //m_intakeSubsystem = bucketSubsystem;
      m_intakeSubsystem = intakeSubsystem;
      // Use addRequirements() here to declare subsystem dependencies.
      //addRequirements(m_bucketSubsystem);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
      m_intakeSubsystem.toggleIntakepiston();
    }
  
    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
      return true; // Runs until interrupted
    }
  
    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {

    }

}