package frc.robot.commands.SpeakerShooterCommands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SpeakerShooter;

public class ShootSpeakerBack extends Command {
    final SpeakerShooter shooter;
    int count=0;
    
    public ShootSpeakerBack(SpeakerShooter shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
    }


    @Override
    public void execute() {
        shooter.revving();
        if (shooter.canShoot()){
            shooter.runIndex();
            count++;
        }
        else{
            count=0;
        }
    }

    @Override
    public boolean isFinished() { 
        return count>10;
    }  

    @Override
    public void end(boolean wasInterupted){
        if(!wasInterupted){
            shooter.stop();
        }
    }
    
}
