package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.SystemManager;

public class starting extends Command{
    public starting(){
        addRequirements(SystemManager.intake, SystemManager.elevator, SystemManager.wrist);

    }

    @Override
    public void initialize(){
        SystemManager.wrist.setSetpoint(Constants.wristConstants.intakePosit);
        SystemManager.elevator.setSetpoint(Constants.elevatorConstants.intakePosit);
        SystemManager.intake.stop();
    }

    @Override 
    public boolean isFinished(){
        return SystemManager.wrist.isAtSetpoint()&&SystemManager.elevator.isAtSetpoint();
    }
}
