package frc.robot.commands.sim;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.SystemManager;

public class shootAmp extends InstantCommand{
    public shootAmp(){
            addRequirements(SystemManager.noteManipulator);
    }

    @Override
    public void execute(){
        //SHOOY AMP
        SystemManager.noteManipulator.shootAmp();
    }
}

