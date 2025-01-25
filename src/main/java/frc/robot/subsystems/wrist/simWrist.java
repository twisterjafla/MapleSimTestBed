package frc.robot.subsystems.wrist;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.SystemManager;

public class simWrist extends SubsystemBase implements wristInterface{

    public double setpoint;
    public double position;




    public simWrist(){
    }


    @Override
    public void setSetpointRaw(double setpoint) {
        this.setpoint=setpoint;
    }

    @Override
    public void periodic(){
        if (Math.abs(setpoint-position)<Constants.wristConstants.speedForSim){
            position=setpoint;
        }
        else if (setpoint>position){
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


}
