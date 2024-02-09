package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class IntakeNote extends Command {
  // setActive setActive;
  Intake intake;

  /**
   * Creates a new ArcadeDrive command.
   *
   * @param left       The control input for the left side of the drive
   * @param right      The control input for the right sight of the drive
   * @param driveSubsystem The driveSubsystem subsystem to drive
   */
  public IntakeNote(Intake intake) {
    this.intake = intake;
    addRequirements(intake);
  }


  @Override
  public void execute() {
    intake.intake();
      
  }
}