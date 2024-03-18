package frc.robot.commands.ElevatorCommands;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Elevator;

public class ElevatorToggleBack extends WaitCommand {
  private final Elevator elevator;
  private double speed;

  public ElevatorToggleBack(Elevator elevator, double time) {
    super(time);
    this.elevator = elevator;
  }

  @Override
  public void initialize() {
    if (elevator.isUp) {
      this.speed = Constants.elevator.elevatorBackupDownSpeed;
    }
    else {
      this.speed = Constants.elevator.elevatorBackupUpSpeed;
    }

    if (elevator.isUp){
      if (elevator.topSwitch.isOk()){
        cancel();
      }
    }
    else{
      if(elevator.bottomSwitch.isOk()){
        cancel();
      }
    }
  }

  @Override
  public void execute() {
    super.execute();
    elevator.moveElevator(speed);
  }



  @Override
  public void end(boolean interupted) {
    if( !interupted){
      elevator.isUp=!elevator.isUp;
      elevator.moveElevator(0);
    }
  }
}