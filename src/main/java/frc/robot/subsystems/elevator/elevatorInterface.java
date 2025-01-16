package frc.robot.subsystems.elevator;

import edu.wpi.first.math.geometry.Translation2d;

public interface elevatorInterface {
    public void setSetpoint(double setpoint);
    public void setSetpointInMeters(double setpoint);
    public boolean isAtSetpoint();
    public Translation2d getPosit();
    public double getEncoderVal();
    public double getHeight();
    public void reset();
}
