package frc.robot.subsystems.wrist;

public interface wristInterface {
    public void setSetpoint(double setpoint);
    public void setSetpointInDegrees(double setPoint);
    public boolean isAtSetpoint();
    
    public double getCurrentLocation();
    public double getcurrentLocationDeg();
    public double getSetpoint();
    public void reset();
}
