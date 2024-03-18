package frc.robot.commands.IntakeCommands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Intake;

public class intake extends ParallelCommandGroup{
    public intake(Intake intake){
        super(new IntakeMain(intake), new IntakeBack(intake));
    }
}
