package frc.robot.subsystems;

import java.util.Optional;
import java.util.function.Supplier;
import frc.robot.commands.states.*;
import com.ctre.phoenix.sensors.PigeonIMU.GeneralStatus;

import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class generalManager extends SubsystemBase{
    public enum generalState{
        intake(new intaking()),
        start(new starting()),
        L1(new scoreL1Config()),
        L2(new scoreL2Config()),
        L3(new scoreL3Config()),
        L4(new scoreL4Config()),
        outtake(new outtaking());

        Command state;
        private generalState(Command command){
            state=command;
        }

        private generalState(){
            throw new Error("Attempted to start a state that has not been implemented");
        }
    }


    generalState state;
    Alert weirdStatAlert = new Alert("Warning, a state was sheduled in generalManager that did not use the correct callback. generalManager caught this issue and resolved it but strange bugs may still occur", AlertType.kError);
    public generalManager(){
      start();
    }


    @Override
    public void periodic(){
        if (!CommandScheduler.getInstance().isScheduled(state.state)){
            weirdStatAlert.set(true);
            start();
        }
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
        startState(generalState.L1);
    }
    public void scoreL2(){
        startState(generalState.L2);
    }
    
    public void scoreL3(){
        startState(generalState.L3);
    }

    public void scoreL4(){
        startState(generalState.L4);
    }


    public void intake(){
        startState(generalState.intake);
    }

    public void start(){
        startState(generalState.start);
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

    public void endCallback(boolean wasInterupted){
        if (!wasInterupted){
            start();
        }
    }


    public boolean isScoringState() {
        return state==generalState.L1 || state==generalState.L2 || state==generalState.L3 || state==generalState.L4;
    }
}
