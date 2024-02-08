package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.ClimbingArm;

public class ClimbingDown extends Command {
  // setActive setActive;
  ClimbingArm m_climbArm;

  /**
   * Creates a new ArcadeDrive command.
   *
   * @param left       The control input for the left side of the drive
   * @param right      The control input for the right sight of the drive
   * @param driveSubsystem The driveSubsystem subsystem to drive
   */
  public ClimbingDown(ClimbingArm importedClimbingArm) {
    m_climbArm = importedClimbingArm;
  }


  @Override
  public void execute() {
    m_climbArm.armDown();

  }

  @Override
  public void initialize(){
    if (!m_climbArm.switchLeft.isOk()||!m_climbArm.switchRight.isOk()){
        CommandScheduler.getInstance().cancel(this);
    }

  }

  @Override
  public void end(boolean wasInterupted){
  }

  @Override
  public boolean isFinished() { 
    return m_climbArm.switchLeft.getVal() && m_climbArm.switchRight.getVal();

  } 
      
}