package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.IntakeSubsystem;


public class ReverseIntakeCommand extends RunCommand {

  private final IntakeSubsystem intake;

  public ReverseIntakeCommand(IntakeSubsystem intake, double speed) {
    super(
      ()->{
        intake.intakeCargo(speed);
      },
     intake
     );
     this.intake = intake;
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    intake.intakeCargo(0);
  }
}
