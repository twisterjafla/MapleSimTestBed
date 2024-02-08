package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class RepetitiveOutake extends Command {
  // setActive setActive;
  Intake intake;
  double speed;
  int counter;
  boolean noteInIntake;

  /**
   * Creates a new ArcadeDrive command.
   *
   * @param left       The control input for the left side of the drive
   * @param right      The control input for the right sight of the drive
   * @param driveSubsystem The driveSubsystem subsystem to drive
   */
  public RepetitiveOutake(Intake intake) {
    this.intake = intake;
  }

  @Override
  public void initialize(){
    if (!this.intake.bottomSwitch.isOk()||!this.intake.topSwitch.isOk()){
      CommandScheduler.getInstance().cancel(this);
    }
    counter = 0;
    noteInIntake = false;
  }

  @Override
  public void execute() {
    noteInIntake = this.intake.beamBreak.getVal();

    if (noteInIntake){
      this.intake.outake();
    }

    else if (counter <= Constants.intake.counterCap) {
      this.intake.outake();
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
        this.intake.outake();
    }
  
  }
   
}
