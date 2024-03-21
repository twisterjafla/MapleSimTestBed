package frc.robot.commands.ElevatorCommands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.limitSwitch;

public class ElevatorToggleMain extends Command {
  Elevator elevator;
  limitSwitch activeSwitch;
  double speed;

  public ElevatorToggleMain(Elevator elevator) {
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

    if(!activeSwitch.isOk()){
      cancel();
    }
  }

  @Override
  public void end(boolean wasInterupted) {

    if (!wasInterupted){
      elevator.stop();
      elevator.isUp=!elevator.isUp;
      if (elevator.isUp){
        new stayAtTopMain(elevator).schedule();
      }
    }



  }

  @Override
  public boolean isFinished() { 
    return !activeSwitch.getVal();
  }
}