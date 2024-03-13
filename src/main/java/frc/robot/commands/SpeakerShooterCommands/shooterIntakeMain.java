package frc.robot.commands.SpeakerShooterCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SpeakerShooter;

public class shooterIntakeMain extends Command{
    SpeakerShooter shooter;

    public shooterIntakeMain(SpeakerShooter shooter){
        this.shooter=shooter;

    }

    @Override
    public void initialize(){
        if (!shooter.beamBreak.isOk()||shooter.beamBreak.getVal()){
            cancel();
        }
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
