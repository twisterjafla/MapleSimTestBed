package frc.robot.commands.ElevatorParallelCommands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.stayAtTop;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.limitSwitch;

public class ElevatorToggle extends Command {
  Elevator elevator;
  limitSwitch activeSwitch;
  double speed;

  public ElevatorToggle(Elevator elevator) {
    this.elevator = elevator;
    addRequirements(elevator);
  }

  @Override
  public void execute() {
    elevator.moveElevator(speed);
    SmartDashboard.putNumber("elevator switch", activeSwitch.index);
  }

  @Override
  public void initialize() {
    if (elevator.checkOkLimitSwitches()) {
      CommandScheduler.getInstance().cancel(this);
    }
  }

  @Override
  public void end(boolean wasInterupted) {
    if (!wasInterupted){
      elevator.isUp=!elevator.isUp;
    }

    elevator.stop();
    new stayAtTop(elevator).schedule();
  }

  @Override
  public boolean isFinished() { 
    return !activeSwitch.getVal();
  }
}