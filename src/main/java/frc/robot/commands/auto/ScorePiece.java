package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.SystemManager;
import frc.robot.Utils.scoringPosit;
import frc.robot.Utils.warningManager;
import frc.robot.subsystems.autoManager;
import frc.robot.subsystems.generalManager;


public class ScorePiece extends Command{
    scoringPosit posit;
    boolean mechIsFinished=false;
    Command driveCommand;
    Command mechCommand;
    boolean hasSpit;


    public ScorePiece(scoringPosit posit){
        this.posit=posit;
        
    }

    @Override
    public void initialize(){
        generalManager.scoreAt(posit.level.getasInt());


        driveCommand=SystemManager.driveToPose(posit.getScorePose());
        driveCommand.schedule();

        mechCommand=generalManager.getStateCommand();
        generalManager.setExternalEndCallback(this::mechIsFinishedCall);
        
        mechIsFinished=false;
        hasSpit=false;
    }


    @Override
    public void execute() {


        if (mechIsFinished&&!driveCommand.isScheduled()){
            generalManager.outtake();
            generalManager.setExternalEndCallback(this::intakeIsFinishedCall);
        }
    }
   

    public void mechIsFinishedCall(boolean wasInterupted){

        mechIsFinished=true;
    }

    public void intakeIsFinishedCall(boolean wasInterupted){
        
        hasSpit=true;
    }


    public boolean isFinished(){
        return hasSpit;
    }
    
    @Override
    public void end(boolean wasInterupted){
        if (driveCommand.isScheduled()){
            driveCommand.cancel();
            
        }
        
    }   
}
