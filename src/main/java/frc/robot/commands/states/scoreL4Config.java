package frc.robot.commands.states;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.SystemManager;
import frc.robot.Utils.warningManager;
import frc.robot.subsystems.generalManager;



public class scoreL4Config extends Command{

    /**Creates a command to configure mechs to l4. DOES NOT ACTUALY OUTTAKE */
    public scoreL4Config(){
        addRequirements(generalManager.subsystems);
    }

    /**initalizes the command */
    @Override
    public void initialize(){
        SystemManager.wrist.setSetpoint(Constants.wristConstants.l4EncoderVal);
        SystemManager.elevator.setSetpoint(Constants.elevatorConstants.l4EncoderVal);
        SystemManager.intake.stop();
        SmartDashboard.putString("l4 started", "true");
    }

    /**called ever rio cycle while the command is scheduled*/
    @Override 
    public void execute(){
        if (generalManager.getStateCommand()!=this){
            warningManager.throwAlert(warningManager.generalRoutineCalledManualy);
            cancel();
        }
    }

    @Override
    public boolean isFinished(){
        return SystemManager.elevator.isAtSetpoint() && SystemManager.wrist.isAtSetpoint();
    }


    /**
     * command called when the command finishes
     * @param wasInterupted wether or not the command was cancled
    */
    @Override
    public void end(boolean wasInterupted){
        generalManager.endCallback(wasInterupted);
    }
}
