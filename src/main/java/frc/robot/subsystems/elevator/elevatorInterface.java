package frc.robot.subsystems.elevator;

import java.util.function.BooleanSupplier;

import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.wristElevatorControllManager;

public interface elevatorInterface extends Subsystem {
    public void setSetpointRaw(double setpoint);
    public void setSetpoint(double setpoint);
    public boolean isAtSetpoint();
    public Translation3d getTranslation();
    public double getEncoderVal();
    public double getHeight();
    public void reset();
    public void setManager(wristElevatorControllManager manager);
    public boolean atLegalNonControlState();
}
