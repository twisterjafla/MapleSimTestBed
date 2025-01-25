package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.SystemManager;
import frc.robot.Utils.warningManager;
import frc.robot.subsystems.generalManager;
import frc.robot.subsystems.wrist.wristInterface;

public class scoreL1Config extends Command{
    
    public scoreL1Config(){
        addRequirements(SystemManager.wrist, SystemManager.elevator, SystemManager.intake);
    }

    @Override
    public void initialize(){
        SystemManager.wrist.setSetpoint(Constants.wristConstants.l1EncoderVal);
        SystemManager.elevator.setSetpoint(Constants.elevatorConstants.l1EncoderVal);
        SystemManager.intake.stop();
    }

    @Override 
    public void execute(){
        if (generalManager.getStateCommand()!=this){
            warningManager.throwAlert(warningManager.generalRoutineCalledManualy);
            cancel();
        }
    }

    @Override
    public boolean isFinished(){
        return SystemManager.wrist.isAtSetpoint() && SystemManager.wrist.isAtSetpoint();
    }

    @Override
    public void end(boolean wasInterupted){
        generalManager.endCallback(wasInterupted);
    }
}
