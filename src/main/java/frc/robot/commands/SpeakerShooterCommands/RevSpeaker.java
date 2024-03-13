package frc.robot.commands.SpeakerShooterCommands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.SpeakerShooter;

public class RevSpeaker extends Command {
    // setActive setActive;
    final SpeakerShooter shooter;

    public RevSpeaker(SpeakerShooter shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void execute() {
        shooter.revving();
    }

}
