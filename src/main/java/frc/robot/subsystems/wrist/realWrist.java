package frc.robot.subsystems.wrist;

import static edu.wpi.first.units.Units.Degrees;

import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix6.hardware.core.CoreCANcoder;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Utils.warningManager;
import frc.robot.subsystems.wristElevatorControllManager;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;


public class realWrist implements wristInterface{


    private double setpoint;
    private double goal;
    protected wristElevatorControllManager manager;
    protected CoreCANcoder wristEncoder = new CoreCANcoder(Constants.wristConstants.CANCoderID);
    protected SparkFlex wristMotor = new SparkFlex(Constants.wristConstants.motorID, MotorType.kBrushless);


    @Deprecated
    public realWrist(){
    }


    @Override
    public void setSetpointRaw(double setpoint) {
        this.setpoint=setpoint;
    }


    @Deprecated
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


    }


    @Override
    public boolean isAtSetpoint() {
        return Math.abs(setpoint-getCurrentLocation().getDegrees())<Constants.wristConstants.tolerence;
    }


    @Override
    public double getCurrentLocationEncoder() {
        return wristEncoder.getAbsolutePosition().getValue().in(edu.wpi.first.units.Units.Degrees);
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
    public Rotation2d getCurrentLocation() {
        return Rotation2d.fromDegrees(getCurrentLocationEncoder()*Constants.wristConstants.degreesPerEncoderTick);
    }


    @Override
    public void setManager(wristElevatorControllManager manager){
        this.manager=manager;
    }

    @Override
    public boolean atLegalNonControlState(){
        return Math.abs(getCurrentLocation().getDegrees())<Constants.wristConstants.tolerence;
    }
}
