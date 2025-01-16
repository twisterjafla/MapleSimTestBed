package frc.robot.subsystems.elevator;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import frc.robot.Constants;

public class simElevator implements elevatorInterface{

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
    public Translation3d getTranslation() {
        return new Translation3d(getHeight()*Math.cos(Constants.elevatorConstants.angle.getRadians()), 0, getHeight()*Math.sin(Constants.elevatorConstants.angle.getRadians()));
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
