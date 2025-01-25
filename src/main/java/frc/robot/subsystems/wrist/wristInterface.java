package frc.robot.subsystems.wrist;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj2.command.Subsystem;

public interface wristInterface extends Subsystem{
    public void setSetpoint(double setpoint);
    public void setSetpointRaw(double setpoint);
    public boolean isAtSetpoint();
    
    public double getCurrentLocationEncoder();
    public Rotation2d getcurrentLocation();
    public double getSetpoint();
    public void reset();
    
}
