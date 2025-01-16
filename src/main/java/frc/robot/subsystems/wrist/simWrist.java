package frc.robot.subsystems.wrist;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation3d;
import frc.robot.Constants;

public class simWrist implements wristInterface{

    public double setpoint;

    @Override
    public void setSetpoint(double setpoint) {
        this.setpoint=setpoint;
    }

    @Override
    public boolean isAtSetpoint() {
        return true;
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
    public void setSetpointInDegrees(Rotation2d setPoint) {
        setSetpoint(setPoint.getDegrees()*Constants.wristConstants.degreesPerEncoderTick);
    }

    @Override
    public Rotation2d getcurrentLocation() {
        return Rotation2d.fromDegrees(getSetpoint()/Constants.wristConstants.degreesPerEncoderTick);
    }


}
