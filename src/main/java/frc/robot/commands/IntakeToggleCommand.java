package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeToggleCommand extends InstantCommand {
  public IntakeToggleCommand(IntakeSubsystem intakeSubsystem) {
    super(
      ()->{intakeSubsystem.toggleIntakepiston();},
      intakeSubsystem
      );
  }
}