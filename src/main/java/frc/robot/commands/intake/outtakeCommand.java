package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.SystemManager;
import frc.robot.subsystems.intake.intakeInterface;

public class outtakeCommand extends Command {
    intakeInterface intake;
    public outtakeCommand(intakeInterface intake){
        this.intake=intake;
    }

    public outtakeCommand(){
        this.intake=SystemManager.intake;
    }


    @Override
    public void execute(){
        intake.outtake();
    }

    @Override
    public boolean isFinished(){
        return !intake.hasPeice();
    }
}