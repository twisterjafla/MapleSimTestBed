package frc.robot.commands.SpeakerShooterCommands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.SpeakerShooter;

public class shooterIntakeBack extends WaitCommand {
    final SpeakerShooter shooter;
    
    public shooterIntakeBack(SpeakerShooter shooter) {
        super(Constants.speakerShooter.intakeTime);
        this.shooter = shooter;
        addRequirements(shooter);
        
    }


    @Override
    public void initialize(){
        if (shooter.beamBreak.isOk()){
            cancel();
        }
    }


    @Override
    public void execute() {
        shooter.intake();
    }


    @Override
    public void end(boolean wasInterupted){
        if(!wasInterupted){
            shooter.stop();
        }
    }
    
}
