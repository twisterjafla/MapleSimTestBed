package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Intake;

public class IntakeToggle extends InstantCommand {
  public IntakeToggle(Intake intakeSubsystem) {
    super(
      ()->{intakeSubsystem.toggleIntakepiston();},
      intakeSubsystem
      );
  }
}