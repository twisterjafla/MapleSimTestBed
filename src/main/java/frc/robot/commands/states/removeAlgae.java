

package frc.robot.commands.states;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.FieldPosits.reefLevel.algeaRemoval;
import frc.robot.SystemManager;
import frc.robot.subsystems.generalManager;

public class removeAlgae extends Command {
    algeaRemoval level;
    boolean isPrep=false;
    Timer algeaTimer= new Timer();

    removeAlgae(algeaRemoval level, boolean isPrep){
        this.level=level;
        this.isPrep=isPrep;
        addRequirements(generalManager.subsystems);
    }

    /**initalizes the command */
    @Override
    public void initialize(){

    }


    /**called ever rio cycle while the command is scheduled*/
    @Override
    public void execute(){
    }

    /**@return true once the robot has aquired a peice */
    @Override
    public boolean isFinished(){
        return false;
    }


    /**
     * command called when the command finishes
     * @param wasInterupted wether or not the command was cancled
    */
    @Override
    public void end(boolean wasInterupted){
    }

}
