package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Intake;

public class WaitShootNote extends WaitCommand {
  private final Intake intake;

  public WaitShootNote(Intake intake, double time) {
    super(time);
    this.intake = intake;


  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
      super.execute();
      intake.outake();
  }

  @Override
  public void end(boolean interupted){
    super.end(interupted);
    intake.stop();
  }
}