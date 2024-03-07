package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;

public class ElevatorMove extends Command {
  private final Elevator elevator;
  private double speed;

  public ElevatorMove(Elevator elevator, double speed) {
    this.speed = speed;
    this.elevator = elevator;
  }


  @Override
  public void execute() {
    elevator.moveElevator(speed);
  }

}