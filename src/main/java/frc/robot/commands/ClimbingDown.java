package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.ClimbingArm;

public class ClimbingDown extends Command {
  ClimbingArm climbArm;

  public ClimbingDown(ClimbingArm climbArm) {
    this.climbArm = climbArm;
    addRequirements(climbArm);

  }


  @Override
  public void execute() {
    climbArm.armDown();

  }

  @Override
  public void initialize() {
    if (!climbArm.switchLeft.isOk()||!climbArm.switchRight.isOk()) {
      CommandScheduler.getInstance().cancel(this);

    }
  }

  @Override
  public boolean isFinished() { 
    return climbArm.switchLeft.getVal() && climbArm.switchRight.getVal();

  }
      
}