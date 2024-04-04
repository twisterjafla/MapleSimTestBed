package frc.robot.commands.SpeakerShooterCommands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.SpeakerShooter;

public class ShootSpeaker extends ParallelCommandGroup{
    public ShootSpeaker(SpeakerShooter shooter){
        super( new ShootSpeakerBack(shooter), new shooterIntakeMain(shooter));
    }
}
