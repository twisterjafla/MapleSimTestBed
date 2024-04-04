package frc.robot.commands.IntakeCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class ShootNoteRaw extends Command {
  Intake intake;

  public ShootNoteRaw(Intake intake) {
    this.intake = intake;
    addRequirements(intake);
  }

  @Override
  public void execute() {
    intake.outake();
  } 
  
  @Override
  public void end(boolean interrupted) {
    intake.stop();
  }
}