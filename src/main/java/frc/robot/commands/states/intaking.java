package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.SystemManager;
import frc.robot.subsystems.generalManager;
import frc.robot.subsystems.intake.simIntake.intakeState;


public class intaking extends Command{
    public boolean isInIntakingFase;

    public intaking(){
        addRequirements(SystemManager.elevator, SystemManager.wrist, SystemManager.intake);

    }

    @Override
    public void initialize(){
        if (SystemManager.intake.hasPeice()){
            cancel();
        }
        isInIntakingFase=false;

        SystemManager.elevator.setSetpoint(Constants.elevatorConstants.intakePosit);
        SystemManager.wrist.setSetpoint(Constants.wristConstants.intakePosit);
    }

    @Override
    public void execute(){
        if (!isInIntakingFase && SystemManager.elevator.isAtSetpoint() && SystemManager.wrist.isAtSetpoint()){
            SystemManager.intake.intake();
            isInIntakingFase=true;
        }
    }

    @Override
    public boolean isFinished(){
        return SystemManager.intake.hasPeice();
    }

    @Override
    public void end(boolean wasInterupted){
        generalManager.endCallback(wasInterupted);
        SystemManager.intake.stop();
    }
}
