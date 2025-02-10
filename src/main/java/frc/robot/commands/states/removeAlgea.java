package frc.robot.commands.states;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.FieldPosits.reefLevel.algeaRemoval;
import frc.robot.SystemManager;
import frc.robot.subsystems.generalManager;

public class removeAlgea extends Command {
    algeaRemoval level;
    boolean isInAlgeaRemoval=false;
    Timer algeaTimer= new Timer();

    removeAlgea(algeaRemoval level){
        this.level=level;
        
        addRequirements(generalManager.subsystems);
    }

    /**initalizes the command */
    @Override
    public void initialize(){
        if (SystemManager.intake.hasPeice()){
            cancel();
        }

        algeaTimer.reset();
        isInAlgeaRemoval=false;
        
        
        SystemManager.elevator.setSetpoint(level.getElevatorValue());
        SystemManager.wrist.setSetpoint(level.getWristValue());
    }


    /**called ever rio cycle while the command is scheduled*/
    @Override
    public void execute(){
        if (!isInAlgeaRemoval&&SystemManager.elevator.isAtSetpoint() && SystemManager.wrist.isAtSetpoint()){
            SystemManager.intake.intakeUntil(()->!this.isScheduled());
            algeaTimer.start();
            isInAlgeaRemoval=true;
        }    
    }

    /**@return true once the robot has aquired a peice */
    @Override
    public boolean isFinished(){
        return isInAlgeaRemoval&&algeaTimer.get()>Constants.intakeConstants.algeaTimerVal;
    }


    /**
     * command called when the command finishes
     * @param wasInterupted wether or not the command was cancled
    */
    @Override
    public void end(boolean wasInterupted){
        generalManager.endCallback(wasInterupted);
        SystemManager.intake.stop();
    }
}
