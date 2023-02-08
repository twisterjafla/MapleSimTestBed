package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
//Most of this is coppied from IntakeCommand
import frc.robot.subsystems.BucketSubsystem;

public class ToggleBucketCommand extends CommandBase {

    private final BucketSubsystem m_bucketSubsystem;
  
    public ToggleBucketCommand(BucketSubsystem bucketSubsystem) {
      m_bucketSubsystem = bucketSubsystem;
      // Use addRequirements() here to declare subsystem dependencies.
      //addRequirements(m_bucketSubsystem);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
      m_bucketSubsystem.bucketToggle();
    }
  
    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
      return true; // Runs until interrupted
    }
  
    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
      m_bucketSubsystem.bucketToggle();
    }

}