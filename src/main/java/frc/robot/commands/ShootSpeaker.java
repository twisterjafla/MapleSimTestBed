package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SpeakerShooter;

public class ShootSpeaker extends Command {
    // setActive setActive;
    final SpeakerShooter m_shooter;
    final BooleanSupplier shootTrigger;
    
    public ShootSpeaker(SpeakerShooter importedShooter, BooleanSupplier shootTrigger) {
        m_shooter = importedShooter;
        this.shootTrigger=shootTrigger;
    }


    @Override
    public void execute() {
        m_shooter.runIndex();
        m_shooter.revving();
    }

    @Override
    public boolean isFinished() { 
        
        return shootTrigger.getAsBoolean();

    } 
        
}
