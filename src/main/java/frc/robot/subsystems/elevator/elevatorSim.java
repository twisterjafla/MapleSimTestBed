package frc.robot.subsystems.elevator;

import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.Constants;

public class elevatorSim implements elevatorInterface{

    public double setpoint=0;

    @Override
    public void setSetpoint(double setpoint) {
        this.setpoint=setpoint;
    }

    @Override
    public boolean isAtSetpoint() {
        return true;
    }

    @Override
    public Translation2d getPosit() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPosit'");
    }

    @Override
    public double getEncoderVal() {
        return setpoint;
    }

    @Override
    public void reset() {
        setpoint=0;
    }

    @Override
    public void setSetpointInMeters(double setpoint) {
       setSetpoint(setpoint*Constants.elevatorConstants.encoderToMeters);
    }

    @Override
    public double getHeight() {
        return getEncoderVal()/Constants.elevatorConstants.encoderToMeters;
    }

    
    
}
