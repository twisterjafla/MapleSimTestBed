package frc.robot.commands.IntakeCommands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class IntakeMain extends Command {
  Intake intake;
  int counter;

  boolean noteInIntake;
  
  public IntakeMain(Intake intake) {
    this.intake = intake;
    addRequirements(intake);

  }

  @Override
  public void initialize() {
    if (!intake.beamBreak.isOk()||intake.beamBreak.getVal()) {
      cancel();
    }
    counter = 0;

  }

  @Override
  public void execute() {
    intake.intake();

  }

  @Override
  public void end(boolean wasInterupted){
    intake.stop();
  }

  @Override
  public boolean isFinished() { 
    if (intake.beamBreak.getVal()) {
      counter++;
    }

    else {
      counter=0;
    }
    return counter > Constants.intake.counterCap;
  } 
}
