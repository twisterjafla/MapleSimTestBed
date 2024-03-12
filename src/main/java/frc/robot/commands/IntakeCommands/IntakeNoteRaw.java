package frc.robot.commands.IntakeCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class IntakeNoteRaw extends Command {
  Intake intake;

  public IntakeNoteRaw(Intake intake) {
    this.intake = intake;
    addRequirements(intake);
  }
  
  @Override
  public void execute() {
    intake.intake();
  }
  
  @Override
  public void end(boolean interrupted) {
    intake.stop();
  }
}