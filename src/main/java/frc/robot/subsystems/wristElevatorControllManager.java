package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.SystemManager;
import frc.robot.subsystems.elevator.elevatorInterface;
import frc.robot.subsystems.wrist.wristInterface;
/**class to manage the interactions between the elevator and the wrist */
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

    /**@return the current state in terms of a wristElevatorControllState*/
    public wristElevatorControllState getState(){
        return state;
    }

    /**adds the wrist and elevator to be used by the manager */
    public void addSystems(wristInterface wrist, elevatorInterface elevator){
        this.elevator=elevator;
        this.wrist=wrist;
    }


    /**Updates the wrist elevevator manager. should be called every rio cycle */
    public void periodic(){
        if (wrist==null||elevator==null){
            return;
        }

        //elevator state
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

        //wrist state
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


        //fix elevator state
        else if(state==wristElevatorControllState.fixElevator){
            if (elevator.atLegalNonControlState()){
                state=wristElevatorControllState.resting;
            }
        }

        //fix wrist state
        else if(state==wristElevatorControllState.fixWrist){
            if(wrist.atLegalNonControlState()){
                state=wristElevatorControllState.resting;
            }
        }

        //resting state
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



