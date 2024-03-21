package frc.robot.commands.SpeakerShooterCommands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SpeakerShooter;

public class ShootSpeakerMain extends Command {
    final SpeakerShooter shooter;
    
    public ShootSpeakerMain(SpeakerShooter shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void initialize(){
        if(!shooter.beamBreak.isOk()||!shooter.beamBreak.getVal()){
            cancel();
        }
    }

    @Override
    public void execute() {
        shooter.revving();
        if (shooter.canShoot()){
            shooter.runIndex();
        }
    }

    @Override
    public boolean isFinished() { 
        return (!shooter.beamBreak.getVal());
    }  

    @Override
    public void end(boolean wasInterupted){
        if(!wasInterupted){
            shooter.stop();
        }
    }
    
}
