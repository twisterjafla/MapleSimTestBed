package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.SpeakerShooter;

public class RevSpeaker extends Command {
    // setActive setActive;
    final SpeakerShooter shooter;
    final BooleanSupplier endTrigger;
    int count = 0;

    public RevSpeaker(SpeakerShooter shooter, BooleanSupplier endTrigger) {
        this.shooter = shooter;
        this.endTrigger = endTrigger;
        addRequirements(shooter);
    }

    @Override
    public void execute() {
        shooter.revving();
    }

    @Override
    public boolean isFinished() { 
        count++;
        return (endTrigger.getAsBoolean() && count>Constants.speakerShooter.RevTimeCountInTicks);
    }      
}
