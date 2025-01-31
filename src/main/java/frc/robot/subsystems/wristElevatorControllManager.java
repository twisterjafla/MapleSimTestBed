package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.SystemManager;
import frc.robot.subsystems.elevator.elevatorInterface;
import frc.robot.subsystems.wrist.wristInterface;

public class wristElevatorControllManager{


    public enum wristElevatorControllState{
        wrist,
        elevator,
        fixWrist,
        fixElevator,
        resting
    }


    protected wristElevatorControllState state;
    protected wristInterface wrist;
    protected elevatorInterface elevator;

    public wristElevatorControllManager(){
        this(wristElevatorControllState.resting);
    }

    public wristElevatorControllManager(wristElevatorControllState startingState){
        state=startingState;
    }

    // public void giveElevatorControl(){
    //     state=wristElevatorControllState.elevator;
    //     // SystemManager.wrist.elevatorGotControl();
    //     // SystemManager.elevator.elevatorGotControl();
    // }

    // public void giveWristControl(){
    //     state=wristElevatorControllState.wrist;
    //     // SystemManager.wrist.elevatorGotControl();
    //     // SystemManager.elevator.wristGotControl();
    // }
    // public void giveNetherControl(){
    //     state=wristElevatorControllState.resin;
    //     // SystemManager.wrist.elevatorGotControl();
    //     // SystemManager.elevator.wristGotControl();
    // }
    
    public wristElevatorControllState getState(){
        return state;
    }

    public void addSystems(wristInterface wrist, elevatorInterface elevator){
        this.elevator=elevator;
        this.wrist=wrist;
    }

    public void periodic(){
        


        if (state == wristElevatorControllState.elevator){
            if (!wrist.atLegalNonControlState()){
                state=wristElevatorControllState.fixWrist;
            }
            if (elevator.isAtSetpoint()){
                if (wrist.isAtSetpoint()){
                    state=wristElevatorControllState.resting;
                }
                else{
                    state=wristElevatorControllState.wrist;
                }
            }
        }

        else if(state==wristElevatorControllState.wrist){
            if(!elevator.atLegalNonControlState()){
                state=wristElevatorControllState.fixElevator;
            }
            if(!elevator.isAtSetpoint()){
                if (wrist.atLegalNonControlState()){
                    state=wristElevatorControllState.fixWrist;
                }
                else{
                    state=wristElevatorControllState.elevator;
                }
            }

            if (wrist.isAtSetpoint()){
                state=wristElevatorControllState.resting;
            }
        }



        else if(state==wristElevatorControllState.fixElevator){
            if (elevator.atLegalNonControlState()){
                state=wristElevatorControllState.resting;
            }
        }

        else if(state==wristElevatorControllState.fixWrist){
            if(wrist.atLegalNonControlState()){
                state=wristElevatorControllState.resting;
            }
        }

        else if (state==wristElevatorControllState.resting){
            if (!elevator.isAtSetpoint()){
                if (!wrist.atLegalNonControlState()){
                    state=wristElevatorControllState.fixWrist;
                }
                else{
                    state=wristElevatorControllState.elevator;
                }
            }
            else if(!wrist.isAtSetpoint()){
                state=wristElevatorControllState.wrist;
            }
        }

        SmartDashboard.putString("wristElevator state", state.name());

    }


}



