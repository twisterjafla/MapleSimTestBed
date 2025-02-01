package frc.robot.subsystems;

import java.util.HashSet;
import java.util.Set;

import frc.robot.SystemManager;
import frc.robot.Utils.warningManager;
import frc.robot.commands.states.*;

import edu.wpi.first.util.function.BooleanConsumer;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class generalManager{
    public enum generalState{
        intake(new intaking()),
        start(new starting()),
        L1(new scoreL1Config()),
        L2(new scoreL2Config()),
        L3(new scoreL3Config()),
        L4(new scoreL4Config()),
        outtake(new outtaking()),
        resting(new resting());

        Command state;
        private generalState(Command command){
            state=command;
        }

        private generalState(){
            throw new Error("Attempted to start a state that has not been implemented");
        }
    }


    public static generalState state;
    
    public static BooleanConsumer externalCallback=null;
    
    
        
    public static Set<Subsystem> subsystems = new HashSet<>();
    
    
    

    public static void generalManagerInit(){
      start();
      subsystems.add(SystemManager.wrist);
      subsystems.add(SystemManager.intake);
      subsystems.add(SystemManager.elevator);
    }


    
    public static void periodic(){
        if (state!=null&&!CommandScheduler.getInstance().isScheduled(state.state)){
            warningManager.throwAlert(warningManager.badGeneralRoutine);
            state=null;
        }

        if (state==null){
            resting();
        }
        
        SmartDashboard.putString("general state", state.state.getName());
        

    }

    public static void scoreAt(int level){
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

    public static void scoreL1(){
        startState(generalState.L1);
    }
    public static void scoreL2(){
        startState(generalState.L2);
    }
    
    public static void scoreL3(){
        startState(generalState.L3);
    }

    public static void scoreL4(){
        startState(generalState.L4);
    }


    public static void intake(){
        startState(generalState.intake);
    }

    public static void outtake(){
        startState(generalState.outtake);
    }

    public static void resting(){
        startState(generalState.resting);
    }

    public static void start(){
        startState(generalState.start);
    }

    public static void startState(generalState state){
        generalManager.state=state;
        CommandScheduler.getInstance().schedule(state.state);
    }

    public static Command getStateCommand(){
        return state.state;
    }

    public static generalState getState(){
        return state;
    }

    public static void endCallback(boolean wasInterupted){
        
        if (externalCallback!=null){
            externalCallback.accept(wasInterupted);
            externalCallback=null;
        }
    }


    public static boolean isScoringState() {
        return state==generalState.L1 || state==generalState.L2 || state==generalState.L3 || state==generalState.L4;
    }


    public static void setExternalEndCallback(BooleanConsumer callback){
        externalCallback=callback;
    }
}
