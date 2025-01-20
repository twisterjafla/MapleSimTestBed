package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.SystemManager;

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
    public boolean isFinished(){
        return !SystemManager.intake.hasPeice();
    }

    @Override 
    public void end(boolean wasInterupted){
        SystemManager.intake.stop();
        SystemManager.mechManagerEndCallback(wasInterupted);
        
    }
    
}
