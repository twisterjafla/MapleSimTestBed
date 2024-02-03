package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SpeakerShooter;

public class ShootSpeaker extends Command {
    // setActive setActive;
    final SpeakerShooter m_shooter;
    final BooleanSupplier shootTrigger;



    /**
     * Creates a new ArcadeDrive command.
     *
     * @param left       The control input for the left side of the drive
     * @param right      The control input for the right sight of the drive
     * @param driveSubsystem The driveSubsystem subsystem to drive
     */
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