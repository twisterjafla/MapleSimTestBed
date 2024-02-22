package frc.robot.semiAutoCommands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.semiAutoManager;


public class attemptToScheduleRoutine extends InstantCommand {

    Command toSchedule;
    public attemptToScheduleRoutine(Command toSchedule){
        this.toSchedule=toSchedule;
    }

    @Override
    public void initialize(){
        if (semiAutoManager.getCurrent()!=null){
            CommandScheduler.getInstance().cancel(semiAutoManager.getCurrent());
        }
        semiAutoManager.setCurrent(toSchedule);
        toSchedule.schedule();
    }
}
