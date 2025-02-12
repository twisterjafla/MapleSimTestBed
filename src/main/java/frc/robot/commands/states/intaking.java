package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.SystemManager;
import frc.robot.subsystems.generalManager;


public class intaking extends Command{
    public boolean isInIntakingFase;

    /**
     * Command to manage the intaking state
     */
    public intaking(){
        addRequirements(generalManager.subsystems);
    }

    /**initalizes the command */
    @Override
    public void initialize(){
        if (SystemManager.intake.hasPeice()){
            cancel();
        }
        isInIntakingFase=false;

        SystemManager.elevator.setSetpoint(Constants.elevatorConstants.intakePosit);
        SystemManager.wrist.setSetpoint(Constants.wristConstants.intakePosit);
    }


    /**called ever rio cycle while the command is scheduled*/
    @Override
    public void execute(){
        if (!isInIntakingFase && SystemManager.elevator.isAtSetpoint() && SystemManager.wrist.isAtSetpoint()){
            SystemManager.intake.intake();
            isInIntakingFase=true;
        }    
    }

    /**@return true once the robot has aquired a peice */
    @Override
    public boolean isFinished(){
        return SystemManager.intake.hasPeice();
    }


    /**
     * command called when the command finishes
     * @param wasInterupted wether or not the command was cancled
    */
    @Override
    public void end(boolean wasInterupted){
        generalManager.endCallback(wasInterupted);
        SystemManager.intake.stop();
    }
}
