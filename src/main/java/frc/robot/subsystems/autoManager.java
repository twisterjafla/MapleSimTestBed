package frc.robot.subsystems;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.pathfinding.LocalADStar;
import com.pathplanner.lib.trajectory.PathPlannerTrajectory;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.FieldPosits;
import frc.robot.SystemManager;
import frc.robot.FieldPosits.reefLevel;
import frc.robot.FieldPosits.reefPole;
import frc.robot.Utils.scoringPosit;
import frc.robot.Utils.utillFunctions;
import frc.robot.commands.auto.IntakePeiceCommand;
import frc.robot.commands.auto.ScorePiece;

public class autoManager{

    public static boolean hasControl=false;
    public static Command currentRoutine=null;
    public static LocalADStar pathBuilder = new LocalADStar();

    public static void autoManagerInit(){
    }

    
 
    public static void periodic(){

    }

    public static Command getAutoAction(){
        if (SystemManager.intake.hasPeice()){
            return new ScorePiece(getBestScorePosit());
        }
        else{
            return new IntakePeiceCommand(getBestIntakePosit());
        }

    }

    public static void resetAutoAction(){
        getAutoAction().schedule();
    }

    public static scoringPosit getBestScorePosit(){
        reefPole winningPose=null;
        double winningScore=180;
        for (reefPole posit: FieldPosits.scoringPosits.scoringPoles){
            pathBuilder.setGoalPosition(posit.getScorePosit().getTranslation());
            PathPlannerTrajectory newPath = pathBuilder.getCurrentPath(SystemManager.swerve.constraints, new GoalEndState(0, posit.getScorePosit().getRotation())).generateTrajectory(
                SystemManager.swerve.getRobotVelocity(),
                SystemManager.swerve.getPitch(),
                SystemManager.swerve.config);
            if (scoringPosit.getPointValForItem(SystemManager.reefIndexer.getHighestLevelForRow(posit.getRowAsIndex()))/newPath.getTotalTimeSeconds()>winningScore){
                winningScore=newPath.getTotalTimeSeconds();
                winningPose = posit;
            }
        }


        return new scoringPosit(reefLevel.CreateFromLevel(SystemManager.reefIndexer.getHighestLevelForRow(winningPose.getRowAsIndex())),winningPose);
    }
    public static Pose2d getBestIntakePosit(){
        return null;
    }
    
}
