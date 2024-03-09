package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.ElevatorParallelCommands.ElevatorMove;
import frc.robot.subsystems.Elevator;

public class ElevatorParallel extends ParallelCommandGroup {
    
    final Elevator elevator;
    double speed;
    

    public ElevatorParallel(Elevator elevator, double speed) {
        this.elevator = elevator;
        this.speed = speed;

        addCommands(new ElevatorMove(elevator, speed), new ElevatorToggle(elevator));
    }
}
