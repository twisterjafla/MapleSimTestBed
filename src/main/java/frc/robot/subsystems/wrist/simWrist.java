package frc.robot.subsystems.wrist;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.wristElevatorControlManager;

public class simWrist extends SubsystemBase implements wristInterface{

    private double setpoint;
    private double position;
    private double goal;
    



    public simWrist(){
    }




    @Override
    public void periodic(){


        if (
            wristElevatorControlManager.getState()==wristElevatorControlManager.wristElevatorControllState.wrist||
            wristElevatorControlManager.getState()==wristElevatorControlManager.wristElevatorControllState.resting){
            goal=setpoint;
        }
        else{
            goal=Constants.wristConstants.restingPosit.getDegrees();
        }

        if (Math.abs(goal-position)<Constants.wristConstants.speedForSim){
            position=goal;
        }
        else if (goal>position){
            position+=Constants.wristConstants.speedForSim;
        }
        else{
            position-=Constants.wristConstants.speedForSim;
        }

        SmartDashboard.putNumber("wristEncoder", position);
    }


    @Override
    public boolean isAtSetpoint() {
        return Math.abs(setpoint-position)<Constants.wristConstants.tolerence;
    }

    @Override
    public double getCurrentLocation() {
        return position;
    }

    @Override
    public Rotation2d getSetpoint() {
        return Rotation2d.fromDegrees(setpoint);
    }

    @Override
    public void reset(){
        setpoint=0;
    }

    @Override
    public Rotation2d getCurrentLocationR2D() {
        return Rotation2d.fromDegrees(position);
    }

    @Override
    public boolean atLegalNonControlState(){
        return Math.abs(getCurrentLocationR2D().getDegrees())<Constants.wristConstants.tolerence;
    }

    @Override
    public void setSetpoint(Rotation2d setpoint) {
        this.setpoint=setpoint.getDegrees();
        }


}
