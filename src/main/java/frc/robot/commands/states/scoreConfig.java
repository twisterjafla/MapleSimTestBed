package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.SystemManager;
import frc.robot.FieldPosits.reefLevel;
import frc.robot.Utils.warningManager;
import frc.robot.subsystems.generalManager;


public class scoreConfig extends Command{
    protected reefLevel scoreLevel;

    /**
     * Creates a command to configure mechs to specified level. DOES NOT ACTUALY OUTTAKE 
     * @param level the level to configure
     */
    public scoreConfig(reefLevel level){
        scoreLevel=level;
        addRequirements(generalManager.subsystems);
    }

    /**initalizes the command */
    @Override
    public void initialize(){
        SystemManager.wrist.setSetpoint(scoreLevel.getWristVal());
        SystemManager.elevator.setSetpoint(scoreLevel.getElevatorValue());
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

    /**returns true when the mec is configured to the requested level */
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
