package frc.robot.subsystems.wrist;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.wristElevatorControllManager;

public interface wristInterface extends Subsystem{
    public void setSetpoint(double setpoint);
    public void setSetpointRaw(double setpoint);
    public boolean isAtSetpoint();
    
    public double getCurrentLocationEncoder();
    public Rotation2d getCurrentLocation();
    public double getSetpoint();
    public void reset();
    
    public void setManager(wristElevatorControllManager manager);
    public boolean atLegalNonControlState();
}
