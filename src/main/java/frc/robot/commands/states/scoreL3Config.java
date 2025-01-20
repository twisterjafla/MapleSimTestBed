package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.SystemManager;


public class scoreL3Config extends Command{
    public scoreL3Config(){
        addRequirements(SystemManager.wrist, SystemManager.elevator, SystemManager.intake);
    }

    @Override
    public void initialize(){
        SystemManager.wrist.setSetpoint(Constants.wristConstants.l3EncoderVal);
        SystemManager.elevator.setSetpoint(Constants.elevatorConstants.l3EncoderVal);
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
