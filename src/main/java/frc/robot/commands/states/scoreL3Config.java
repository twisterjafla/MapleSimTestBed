package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.SystemManager;
import frc.robot.Utils.warningManager;
import frc.robot.subsystems.generalManager;


public class scoreL3Config extends Command{
    public scoreL3Config(){
        addRequirements(generalManager.subsystems);
    }

    @Override
    public void initialize(){
        SystemManager.wrist.setSetpoint(Constants.wristConstants.l3EncoderVal);
        SystemManager.elevator.setSetpoint(Constants.elevatorConstants.l3EncoderVal);
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
