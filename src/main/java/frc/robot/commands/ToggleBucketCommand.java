package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.InstantCommand;
//Most of this is coppied from IntakeCommand
import frc.robot.subsystems.Bucket;

public class ToggleBucketCommand extends InstantCommand {
  public ToggleBucketCommand(Bucket bucketSubsystem) {
    super(
      ()->{bucketSubsystem.bucketToggle();},
      bucketSubsystem
    );
  }
}