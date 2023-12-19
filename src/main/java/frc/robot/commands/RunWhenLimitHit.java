package frc.robot.commands;



import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RunCommand;
//Most of this is coppied from IntakeCommand
import frc.robot.subsystems.Bucket;
import frc.robot.subsystems.limitSwitch;

public class RunWhenLimitHit extends CommandBase {
    Command runner;
    limitSwitch limit;
    public RunWhenLimitHit(limitSwitch Limit, Command Runner) {
        limit=Limit;
        runner=Runner;
    }
    @Override
    public void execute(){
        if (limit.getVal()){
            runner.schedule();
        }
    }

    @Override
    public boolean isFinished() {
        return limit.isOk();
    }

    @Override
    public void end(boolean interupted) {
        runner.cancel();
    }

}