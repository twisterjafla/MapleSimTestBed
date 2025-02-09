package frc.robot.subsystems.wrist;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.wristElevatorControllManager;

public interface wristInterface extends Subsystem{
   
    public void setSetpoint(Rotation2d setpoint);
    public boolean isAtSetpoint();
    
    public double getCurrentLocation();
    public Rotation2d getCurrentLocationR2D();
    public double getSetpoint();
    public void reset();
    
    public void setManager(wristElevatorControllManager manager);
    public boolean atLegalNonControlState();
}
