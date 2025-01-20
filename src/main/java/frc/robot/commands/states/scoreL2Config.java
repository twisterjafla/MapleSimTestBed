package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.SystemManager;


public class scoreL2Config extends Command{
    public scoreL2Config(){
        addRequirements(SystemManager.wrist, SystemManager.elevator, SystemManager.intake);
    }

    @Override
    public void initialize(){
        SystemManager.wrist.setSetpoint(Constants.wristConstants.l2EncoderVal);
        SystemManager.elevator.setSetpoint(Constants.elevatorConstants.l2EncoderVal);
        SystemManager.intake.stop();
    }

    @Override 
    public void execute(){
        
    }

    @Override
    public boolean isFinished(){
        return SystemManager.wrist.isAtSetpoint() && SystemManager.wrist.isAtSetpoint();
    }

    @Override
    public void end(boolean wasInterupted){
        SystemManager.mechManagerEndCallback(wasInterupted);
    }
}
