package frc.robot.commands.SpeakerShooterCommands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SpeakerShooter;

public class ShootSpeaker extends Command {
    final SpeakerShooter shooter;
    final BooleanSupplier shootTrigger;
    
    public ShootSpeaker(SpeakerShooter shooter, BooleanSupplier shootTrigger) {
        this.shooter = shooter;
        this.shootTrigger = shootTrigger;
        addRequirements(shooter);
    }


    @Override
    public void execute() {
        shooter.runIndex();
        shooter.revving();
    }

    @Override
    public boolean isFinished() { 
        return shootTrigger.getAsBoolean();
    }    
}
