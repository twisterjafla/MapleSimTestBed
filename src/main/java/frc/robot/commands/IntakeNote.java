package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class IntakeNote extends Command {
  Intake intake;

  public IntakeNote(Intake intake) {
    this.intake = intake;
    addRequirements(intake);
  }
  
  @Override
  public void execute() {
    intake.intake();
    intake.stop();
  }
}