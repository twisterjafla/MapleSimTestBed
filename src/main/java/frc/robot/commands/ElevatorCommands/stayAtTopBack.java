package frc.robot.commands.ElevatorCommands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.limitSwitch;

public class stayAtTopBack extends WaitCommand {

    Elevator elevator;

    public stayAtTopBack(Elevator elevator){
        super(20);
        this.elevator=elevator;

    }


    @Override
    public void initialize(){
        if (!elevator.isUp||elevator.topSwitch.isOk()){
            cancel();
        }
    }


    @Override
    public void execute(){
        elevator.moveElevator(Constants.elevator.elevatorStayAtTopSpeed);
    }

    @Override public void end(boolean wasInterupted){
        elevator.moveElevator(0);
    }

}
