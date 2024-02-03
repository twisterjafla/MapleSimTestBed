package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.limitSwitch;

public class RaiseIntake extends Command {
  // setActive setActive;
  Intake m_intake;
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
  public RaiseIntake(final Intake elevator) {
    this.m_intake = elevator;
  }


  @Override
  public void execute() {
    m_intake.raiseIntake();
    

  }

  @Override
  public void initialize(){
    if (m_intake.isUp) {
      activeSwitch=m_intake.bottomSwitch;
    }
    else {
      activeSwitch=m_intake.topSwitch;
    }

  }

  @Override
  public void end(boolean wasInterupted){
    m_intake.isUp=!m_intake.isUp;
  }

  @Override
  public boolean isFinished() { 
    return activeSwitch.getVal(); 

  } 
      
}