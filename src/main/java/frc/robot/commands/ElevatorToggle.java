package frc.robot.commands;

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
  }

    @Override
    public void initialize() {
      if (elevator.isUp) {
        activeSwitch=elevator.bottomSwitch;
        speed = Constants.elevator.elevatorDown;
      }
      else {
        activeSwitch = elevator.topSwitch;
        speed = Constants.elevator.elevatorUp;
      }
    }

    @Override
    public void end(boolean wasInterupted) {
      elevator.isUp=!elevator.isUp;
    }

    @Override
    public boolean isFinished() { 
      return activeSwitch.getVal();
    } 
  }