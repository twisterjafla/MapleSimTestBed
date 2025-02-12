package frc.robot.subsystems.wrist;


import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.wristElevatorControlManager;

public interface wristInterface extends Subsystem{
   
    /**
     * sets the setpoint the wrist should try and acheave 
     * @param setpoint the setpoint the wrist will try and acheave
    */
    public void setSetpoint(Rotation2d setpoint);
    
    /**@return wether or not the wrist is within tolerence from its current setpoint */
    public boolean isAtSetpoint();
    
    /**@return the current lcoation in degrees*/
    public double getCurrentLocation();

    /**@return the current location as a roation2d */
    public Rotation2d getCurrentLocationR2D();

    /**@return the current setpoint as a rotation2d */
    public Rotation2d getSetpoint();

    /**resets the wrist to starting configs */
    public void reset();
    

    /**@return wether or not the wrist is at a position at which the elevator can safely move */
    public boolean atLegalNonControlState();
}
