package frc.robot.subsystems.wrist;

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
    public double getCurrentLocation() {
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
    public void setSetpointInDegrees(double setPoint) {
        setSetpoint(setPoint*Constants.wristConstants.degreesPerEncoderTick);
    }

    @Override
    public double getcurrentLocationDeg() {
        return getSetpoint()/Constants.wristConstants.degreesPerEncoderTick;
    }
    
}
