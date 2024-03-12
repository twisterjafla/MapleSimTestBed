package frc.robot.commands.SpeakerShooterCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SpeakerShooter;

public class shooterIntake extends Command{
    SpeakerShooter shooter;

    public shooterIntake(SpeakerShooter shooter){
        this.shooter=shooter;

    }


    public void execute(){
        shooter.intake();
    }

    public boolean isFinished(){
        return shooter.beamBreak.getVal();
    }

    public void end(){
        shooter.stop();
    }
}
