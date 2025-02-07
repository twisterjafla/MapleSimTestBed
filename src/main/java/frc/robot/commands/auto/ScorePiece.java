package frc.robot.commands.auto;



import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.SystemManager;
import frc.robot.Utils.scoringPosit;
import frc.robot.Utils.utillFunctions;
import frc.robot.Utils.warningManager;
import frc.robot.subsystems.autoManager;
import frc.robot.subsystems.generalManager;


public class ScorePiece extends Command{
    scoringPosit posit;
    boolean mechIsFinished=false;
    Command driveCommand;
    Command mechCommand;
    boolean hasSpit;
    boolean driveIsFinished;


    public ScorePiece(scoringPosit posit){
        this.posit=posit;
        
    }

    @Override
    public void initialize(){
        generalManager.scoreAt(posit.level.getasInt());


        driveCommand=SystemManager.swerve.driveToPose(posit.getScorePose());
        driveCommand.schedule();

        mechCommand=generalManager.getStateCommand();
        generalManager.setExternalEndCallback(this::mechIsFinishedCall);
        
        mechIsFinished=false;
        hasSpit=false;
        driveIsFinished=false;
    }


    @Override
    public void execute() {
        if (!driveCommand.isScheduled()){
            if (utillFunctions.pythagorean(SystemManager.getSwervePose().getX(), posit.getScorePose().getX(), SystemManager.getSwervePose().getY(), posit.getScorePose().getY())
                >=Constants.AutonConstants.autoDriveScoreTolerence){
                if (
                    utillFunctions.pythagorean(SystemManager.getSwervePose().getX(), posit.getScorePose().getX(),
                    SystemManager.getSwervePose().getY(), posit.getScorePose().getY())
                    <=Constants.AutonConstants.distanceWithinPathplannerDontWork){

                    driveCommand= new smallAutoDrive(posit.getScorePose());
                }
                driveCommand.schedule();
            }
            else{
                driveIsFinished=true;
            }
        }

        if (mechIsFinished&&driveIsFinished){
            generalManager.outtake();
            generalManager.setExternalEndCallback(this::intakeIsFinishedCall);
        }
        SmartDashboard.putNumber("reef pole", posit.pole.getRowAsIndex());
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
        else{
            autoManager.cycleCount++;
            autoManager.score+=posit.getPointValForItem();
        }
        
    }   
}
