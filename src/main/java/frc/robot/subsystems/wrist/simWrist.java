package frc.robot.subsystems.wrist;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import frc.robot.Utils.warningManager;
import frc.robot.subsystems.wristElevatorControllManager;

public class simWrist extends SubsystemBase implements wristInterface{

    private double setpoint;
    private double position;
    private double goal;
    protected wristElevatorControllManager manager;



    public simWrist(){
    }


    @Override
    public void setSetpointRaw(double setpoint) {
        this.setpoint=setpoint;
    }

    @Override
    public void periodic(){
        if (manager==null){
            warningManager.throwAlert(warningManager.noWristElevatorManagerSet);
            return;
        }

        if (manager.getState()==wristElevatorControllManager.wristElevatorControllState.wrist||manager.getState()==wristElevatorControllManager.wristElevatorControllState.resting){
            goal=setpoint;
        }
        else{
            goal=Constants.wristConstants.restingPosit;
        }

        if (Math.abs(goal-position)<Constants.wristConstants.speedForSim/Constants.wristConstants.degreesPerEncoderTick){
            position=goal;
        }
        else if (goal>position){
            position+=Constants.wristConstants.speedForSim/Constants.wristConstants.degreesPerEncoderTick;
        }
        else{
            position-=Constants.wristConstants.speedForSim/Constants.wristConstants.degreesPerEncoderTick;
        }


        SmartDashboard.putNumber("wristEncoder", position);
    }


    @Override
    public boolean isAtSetpoint() {
        return Math.abs(setpoint-position)<Constants.wristConstants.tolerence;
    }

    @Override
    public double getCurrentLocationEncoder() {
        return setpoint;
    }

    @Override
    public double getSetpoint() {
        return setpoint;
    }

    @Override
    public void reset(){
        setpoint=0;
    }

    @Override
    public void setSetpoint(double setPoint) {
        setSetpointRaw(setPoint/Constants.wristConstants.degreesPerEncoderTick);
    }

    @Override
    public Rotation2d getcurrentLocation() {
        return Rotation2d.fromDegrees(position*Constants.wristConstants.degreesPerEncoderTick);
    }


    @Override
    public void setManager(wristElevatorControllManager manager){
        this.manager=manager;
    }

    @Override
    public boolean atLegalNonControlState(){
        return Math.abs(getcurrentLocation().getDegrees())<Constants.wristConstants.tolerence;
    }


}
