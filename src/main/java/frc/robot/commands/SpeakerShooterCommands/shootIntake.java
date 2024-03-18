package frc.robot.commands.SpeakerShooterCommands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Constants.speakerShooter;
import frc.robot.subsystems.SpeakerShooter;

public class shootIntake extends ParallelCommandGroup{
    public shootIntake(SpeakerShooter shooter){
        super(new shooterIntakeBack(shooter), new shooterIntakeMain(shooter));
    }
}
