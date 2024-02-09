package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.limitSwitch;

public class RaiseIntake extends Command {
  // setActive setActive;
  Intake intake;
  Boolean hasHitTop = false;
  limitSwitch activeSwitch;
  double speed;

  /**
   * Creates a new ArcadeDrive command.
   *
   * @param left       The control input for the left side of the drive
   * @param right      The control input for the right sight of the drive
   * @param driveSubsystem The driveSubsystem subsystem to drive
   */
  public RaiseIntake(Intake intake) {
    this.intake = intake;
    addRequirements(intake);
  }


  @Override
  public void execute() {
    intake.raiseIntake();
  }

  @Override
  public void initialize(){
    if (intake.isUp) {
      activeSwitch=intake.bottomSwitch;
    }
    else {
      activeSwitch=intake.topSwitch;
    }

  }

  @Override
  public void end(boolean wasInterupted){
    intake.isUp=!intake.isUp;
  }

  @Override
  public boolean isFinished() { 
    return activeSwitch.getVal(); 

  } 
      
}