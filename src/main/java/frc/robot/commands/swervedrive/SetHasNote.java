package frc.robot.commands.swervedrive;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.SystemManager;

public class SetHasNote extends InstantCommand{
    boolean set;
    public SetHasNote(boolean set){this.set=set;}

    @Override
    public void execute(){
        SystemManager.hasNote=set;
    }

}
