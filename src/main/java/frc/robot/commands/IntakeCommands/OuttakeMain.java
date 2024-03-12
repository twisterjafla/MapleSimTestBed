package frc.robot.commands.IntakeCommands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class OuttakeMain extends Command {
  Intake intake;
  double speed;
  int counter;
  boolean noteInIntake;

  public OuttakeMain(Intake intake) {
    this.intake = intake;
    addRequirements(intake);
  }

  @Override
  public void initialize(){
    if (!intake.beamBreak.isOk()||intake.beamBreak.getVal()) {
      cancel();
    }
    counter = 0;
    noteInIntake = false;
  }

  @Override
  public void execute() {
    noteInIntake = intake.beamBreak.getVal();

    if (noteInIntake){
      intake.outake();
    }
    else if (counter <= Constants.intake.counterCap) {
      intake.outake();
      counter++;
    } 
  }

  @Override
  public boolean isFinished() { 
    return counter > Constants.intake.counterCap;
  } 

  @Override
  public void end(boolean wasInterupted){
    while (counter <= Constants.intake.counterCap){
        counter++;
        intake.outake();
    }
    intake.stop();
  }
}
