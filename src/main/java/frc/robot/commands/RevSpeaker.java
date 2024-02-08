package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.SpeakerShooter;

public class RevSpeaker extends Command {
    // setActive setActive;
    final SpeakerShooter m_shooter;
    final BooleanSupplier endTrigger;
    int count=0;


    public RevSpeaker(SpeakerShooter importedShooter, BooleanSupplier endTrigger) {
        m_shooter = importedShooter;
        this.endTrigger=endTrigger;
        addRequirements(m_shooter);
    }


    @Override
    public void execute() {
        m_shooter.revving();
    }

    @Override
    public void initialize(){

    }

    @Override
    public void end(boolean wasInterupted){
    }

    @Override
    public boolean isFinished() { 
        count++;
        return (endTrigger.getAsBoolean() && count>Constants.speakerShooter.RevTimeCountInTicks);

    } 
        
}
