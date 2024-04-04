package frc.robot.commands.ElevatorCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.limitSwitch;

public class stayAtTopMain extends Command {
    limitSwitch topSwitch;
    Elevator elevator;

    public stayAtTopMain(Elevator elevator){
        this.elevator=elevator;
        this.topSwitch=elevator.topSwitch;
    }

    @Override
    public void initialize(){
        if (!elevator.isUp||!topSwitch.isOk()){
            cancel();
        }
    }

    @Override
    public void execute(){
        if (topSwitch.getVal()){
            elevator.moveElevator(Constants.elevator.elevatorStayAtTopSpeed);
        }
        else{
            elevator.moveElevator(0);
        }
    }

    @Override
    public void end(boolean wasInterupted){
        elevator.moveElevator(0);
    }
}
