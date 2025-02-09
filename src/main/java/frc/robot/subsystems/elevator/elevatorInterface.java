package frc.robot.subsystems.elevator;

import java.util.function.BooleanSupplier;

import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.wristElevatorControllManager;

public interface elevatorInterface extends Subsystem {

    /**
     * sets the setpoint of the elevator in raw encoder units. Use setSetpoint instead
     * @param setpoint the setpoint to set in raw encoder units.
     */
    public void setSetpointRaw(double setpoint);

    /**
     * sets the setpoint of the elevator in meters
     * @param setpoint the height of the elevator in meters
     */
    public void setSetpoint(double setpoint);

    /**@return wether or not the elevator is within tolerence of its setpoint*/
    public boolean isAtSetpoint();

    /**@return the 3d translation from the botom of the elevator to the current point. all mesurments use the rotation point of the wrist for consistency*/
    public Translation3d getTranslation();

    /**@return returns the internal encoder value of the elevator encoder. use getHeight instead*/
    public double getEncoderVal();

    /**@return the height of the elevator in meters. all measurments use the rotation point of the wrist for consistency*/
    public double getHeight();

    /**resets the elevator to its starting config */
    public void reset();

    /**
     * sets the wrist elevator contoll manager to use.
     * @param manager
     */
    public void setManager(wristElevatorControllManager manager);

    /**@return true if the elevator is at a point in which the wrist can move without breaking anything */
    public boolean atLegalNonControlState();
}
