package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class RepetitiveIntake extends Command {
  // setActive setActive;
  Intake intake;
  int counter;
  boolean noteInIntake;

  /**
   * Creates a new ArcadeDrive command.
   *
   * @param left       The control input for the left side of the drive
   * @param right      The control input for the right sight of the drive
   * @param driveSubsystem The driveSubsystem subsystem to drive
   */ 
  
  public RepetitiveIntake(Intake intake) {
    this.intake = intake;
    addRequirements(intake);
  }

  @Override
  public void initialize(){
    if (!intake.topSwitch.isOk()||!intake.bottomSwitch.isOk()){
      CommandScheduler.getInstance().cancel(this);
    }

    counter = 0;
  }

  @Override
  public void execute() {
    intake.intake();
  }

  @Override
  public boolean isFinished() { 
    if (intake.beamBreak.getVal()){
      counter++;
    }

    else{
      counter=0;
    }

    return counter > Constants.intake.counterCap;
  } 

  @Override
  public void end(boolean wasInterupted){
  
  }
   
}
