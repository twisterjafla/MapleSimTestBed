package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.SystemManager;
import frc.robot.Utils.warningManager;
import frc.robot.subsystems.generalManager;

public class outtaking extends Command{

    /**creates a command to manage the outtaking state*/
    public outtaking(){
        addRequirements(generalManager.subsystems);
    }

    /**initalizes the command */
    @Override
    public void initialize(){
        if (!SystemManager.intake.hasPeice()){
            cancel();
        }
        
        SystemManager.intake.outtake();
        SystemManager.algaeManipulator.outtake();
    }


    /**called ever rio cycle while the command is scheduled*/
    @Override
    public void execute(){
        if (generalManager.getStateCommand()!=this){
            warningManager.throwAlert(warningManager.generalRoutineCalledManualy);
            cancel();
        }
    }

    /**
     * returns true once the intake is empty
     */
    @Override 
    public boolean isFinished(){
        return !SystemManager.intake.hasPeice()&&!SystemManager.algaeManipulator.hasPeice();
    }

    /**
     * command called when the command finishes
     * @param wasInterupted wether or not the command was cancled
    */
    @Override 
    public void end(boolean wasInterupted){
        SystemManager.intake.stop();
        generalManager.endCallback(wasInterupted);      
    }
}
