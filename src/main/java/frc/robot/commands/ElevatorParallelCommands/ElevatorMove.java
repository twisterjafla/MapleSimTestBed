package frc.robot.commands.ElevatorParallelCommands;

import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.stayAtTop;
import frc.robot.subsystems.Elevator;

public class ElevatorMove extends Command {
  private final Elevator elevator;
  private double speed;

  public ElevatorMove(Elevator elevator, double speed) {
    this.speed = speed;
    this.elevator = elevator;
  }

  // @Override
  // public void initialize(){
  //    if (true) {
  //    CommandScheduler.getInstance().cancel(this);
  //    }
  // }

  @Override
  public void execute() {
    elevator.moveElevator(speed);
  }

  @Override
  public void end(boolean wasInterupted) {
    if (!wasInterupted){
      elevator.isUp=!elevator.isUp;
    }

    elevator.stop();
    new stayAtTop(elevator).schedule();
  }

}
