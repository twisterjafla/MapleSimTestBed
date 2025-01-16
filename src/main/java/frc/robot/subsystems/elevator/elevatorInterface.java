package frc.robot.subsystems.elevator;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;

public interface elevatorInterface {
    public void setSetpoint(double setpoint);
    public void setSetpointInMeters(double setpoint);
    public boolean isAtSetpoint();
    public Translation3d getTranslation();
    public double getEncoderVal();
    public double getHeight();
    public void reset();
}
