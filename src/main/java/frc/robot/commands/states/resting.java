package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Utils.warningManager;
import frc.robot.subsystems.generalManager;

public class resting extends Command{
    public resting(){
        addRequirements(generalManager.subsystems);
    }


    @Override
    public void execute(){
        if (generalManager.getStateCommand()!=this){
            warningManager.throwAlert(warningManager.generalRoutineCalledManualy);
            cancel();
        }
    }

    @Override
    public void end(boolean wasInterupted){
        generalManager.endCallback(wasInterupted);
    }
}
