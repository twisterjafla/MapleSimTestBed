package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.FieldPosits;
import frc.robot.SystemManager;
import frc.robot.FieldPosits.reefLevel;
import frc.robot.FieldPosits.reefPole;
import frc.robot.Utils.scoringPosit;
import frc.robot.Utils.warningManager;
import frc.robot.subsystems.autoManager;
import frc.robot.subsystems.generalManager.generalState;

public class ScorePiece extends Command{
    scoringPosit posit;
    boolean mechIsFinished=false;
    Command driveCommand;
    Command mechCommand;
    public ScorePiece(scoringPosit posit){
        this.posit=posit;
        addRequirements(SystemManager.mechManager, SystemManager.swerve);
    }

    @Override
    public void initialize(){
        SystemManager.mechManager.scoreAt(posit.level.getasInt());
        driveCommand=SystemManager.swerve.driveToPose(posit.pole.getScorePosit());
        mechCommand=SystemManager.mechManager.getStateCommand();
        SystemManager.mechManager.setExternalEndCallback(this::mechIsFinishedCall);
        mechIsFinished=false;
    }


    @Override
    public void execute() {
        if (mechIsFinished==mechCommand.isScheduled()){
            SystemManager.autoManager.resetAutoAction();
            
        }
    }
   

    public void mechIsFinishedCall(boolean wasInterupted){
        if (wasInterupted){
            SystemManager.autoManager.resetAutoAction();
            warningManager.throwAlert(warningManager.autoInternalCancled);
        }
        mechIsFinished=true;
    }


    public boolean isFinished(){
        return !driveCommand.isScheduled()||mechIsFinished;
    }
    
}
