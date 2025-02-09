package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.SystemManager;
import frc.robot.Utils.warningManager;
import frc.robot.subsystems.generalManager;

public class scoreL1Config extends Command{
    
    /**Creates a command to configure mechs to l1. DOES NOT ACTUALY OUTTAKE */
    public scoreL1Config(){
        addRequirements(generalManager.subsystems);
    }

    /**initalizes the command */
    @Override
    public void initialize(){
        SystemManager.wrist.setSetpoint(Constants.wristConstants.l1EncoderVal);
        SystemManager.elevator.setSetpoint(Constants.elevatorConstants.l1EncoderVal);
        SystemManager.intake.stop();
    }

    /**called ever rio cycle while the command is scheduled*/
    @Override 
    public void execute(){
        if (generalManager.getStateCommand()!=this){
            warningManager.throwAlert(warningManager.generalRoutineCalledManualy);
            cancel();
        }
    }

    /**@return true once the mechs are at the proper position */
    @Override
    public boolean isFinished(){
        return SystemManager.wrist.isAtSetpoint() && SystemManager.elevator.isAtSetpoint();
    }


    /**
     * command called when the command finishes
     * @param wasInterupted wether or not the command was cancled
    */
    @Override
    public void end(boolean wasInterupted){
        generalManager.endCallback(wasInterupted);
    }
}
