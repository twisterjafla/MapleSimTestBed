package frc.robot.commands.IntakeCommands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import frc.robot.subsystems.Intake;

public class outtake extends ParallelCommandGroup{
    public outtake(Intake intake){
        super(new OuttakeBack(intake), new OuttakeMain(intake));
    }
}
