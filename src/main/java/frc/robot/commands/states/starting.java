package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.SystemManager;
import frc.robot.Utils.warningManager;
import frc.robot.subsystems.generalManager;

public class starting extends Command{
    public starting(){
        addRequirements(generalManager.subsystems);

    }

    /**initalizes the command */
    @Override
    public void initialize(){
        SystemManager.wrist.setSetpoint(Constants.wristConstants.intakePosit);
        SystemManager.elevator.setSetpoint(Constants.elevatorConstants.intakePosit);
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

    @Override 
    public boolean isFinished(){
        return SystemManager.wrist.isAtSetpoint()&&SystemManager.elevator.isAtSetpoint();
    }
}
