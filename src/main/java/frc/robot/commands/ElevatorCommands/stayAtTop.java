package frc.robot.commands.ElevatorCommands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Elevator;

public class stayAtTop extends ParallelCommandGroup { 
    public stayAtTop(Elevator elevator) {
        addCommands(new stayAtTopMain(elevator), new stayAtTopBack(elevator));
    }
}
