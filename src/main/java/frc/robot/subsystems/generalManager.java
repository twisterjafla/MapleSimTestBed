package frc.robot.subsystems;

import java.util.Optional;
import java.util.function.Supplier;
import frc.robot.commands.states.*;
import com.ctre.phoenix.sensors.PigeonIMU.GeneralStatus;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class generalManager extends SubsystemBase{
    public enum generalState{
        intake(new intaking()),
        start(new starting()),
        L1(new scoreL1()),
        L2(new scoreL2()),
        L3(new scoreL3()),
        L4(new scoreL4());

        Command state;
        private generalState(Command command){
            state=command;
        }

        private generalState(){
            throw new Error("Attempted to start a state that has not been implemented");
        }
    }


    generalState state;

    public generalManager(){
      start();
    }


    public void scoreAt(int level){
        switch (level) {
            case 1:
                scoreL1();
                break;
            case 2:
                scoreL2();
                break;
            case 3:
                scoreL3();
                break;
            case 4:
                scoreL4();
                break;
            default:
                throw new Error("Attempted to score at an invalid level");
        }
    }

    public void scoreL1(){
    
    }
    public void scoreL2(){

    }
    
    public void scoreL3(){

    }

    public void scoreL4(){

    }


    public void intake(){

    }

    public void start(){

    }

    public void startState(generalState state){
        this.state=state;
        CommandScheduler.getInstance().schedule(state.state);
    }

    public Command getStateCommand(){
        return state.state;
    }

    public generalState getState(){
        return state;
    }

    public void endCallback(){

    }
}
