package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.SystemManager;
import frc.robot.Utils.warningManager;
import frc.robot.subsystems.generalManager;

public class outtaking extends Command{
    public outtaking(){
        addRequirements(SystemManager.elevator, SystemManager.wrist, SystemManager.intake);
    }

    @Override
    public void initialize(){
        if (!SystemManager.intake.hasPeice()){
            cancel();
        }
        
        SystemManager.intake.outtake();
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
        return !SystemManager.intake.hasPeice();
    }

    @Override 
    public void end(boolean wasInterupted){
        SystemManager.intake.stop();
        generalManager.endCallback(wasInterupted);
        
    }
    
}
