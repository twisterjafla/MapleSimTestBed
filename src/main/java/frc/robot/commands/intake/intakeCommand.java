package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.SystemManager;
import frc.robot.subsystems.intake.intakeInterface;

public class intakeCommand extends Command {
    intakeInterface intake;
    public intakeCommand(intakeInterface intake){
        this.intake=intake;
    }

    public intakeCommand(){
        this.intake=SystemManager.intake;
    }


    @Override
    public void execute(){
        intake.intake();
    }

    @Override
    public boolean isFinished(){
        return intake.hasPeice();
    }
}
