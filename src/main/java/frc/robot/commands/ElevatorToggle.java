package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
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
    if (elevator.isUp) {
      activeSwitch=elevator.bottomSwitch;
      speed = Constants.elevator.elevatorDownSpeed;
    }
    else {
      activeSwitch = elevator.topSwitch;
      speed = Constants.elevator.elevatorUpSpeed;
    }
  }

  @Override
  public void end(boolean wasInterupted) {
    elevator.stop();
    if (!wasInterupted){
      elevator.isUp=!elevator.isUp;
    }
    if (elevator.isUp){
        new stayAtTop(elevator).schedule();
    }


  }

  @Override
  public boolean isFinished() { 
    return !activeSwitch.getVal();
  }
}