package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.SystemManager;
import frc.robot.Utils.utillFunctions;
import frc.robot.commands.auto.IntakePeiceCommand;
import frc.robot.commands.auto.ScorePiece;

public class autoManager {

    boolean hasControl=false;
    Command currentRoutine=null;
    public autoManager(){

    }


 


    public Command getAutoAction(){
        if (SystemManager.intake.hasPeice()){
            return new ScorePiece(utillFunctions.getBestScorePosit());
        }
        else{
            return new IntakePeiceCommand(utillFunctions.getBestIntakePosit());
        }

    }

    public void resetAutoAction(){
        
    }
}
