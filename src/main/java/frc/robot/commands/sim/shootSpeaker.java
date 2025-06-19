package frc.robot.commands.sim;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.SystemManager;

public class shootSpeaker extends InstantCommand {
    public shootSpeaker(){
        addRequirements(SystemManager.noteManipulator);
    }

    @Override
    public void execute(){
        SystemManager.noteManipulator.shootSpeaker();
    }
}
