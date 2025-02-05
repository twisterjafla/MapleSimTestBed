package frc.robot.subsystems.intake;

import java.util.function.BooleanSupplier;

import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj2.command.Subsystem;


public interface intakeInterface extends Subsystem{
    // public void intake();
    // public void outtake();
    public boolean hasPeice();
    public void intake();
    public void intakeUntil(BooleanSupplier trigger);
    public void outtake();
    public void outtakeUntil(BooleanSupplier trigger);
    public void stop();
    public void reset();
    public intakeState getState();
    public Translation3d getTranslation();
    //public void setPoseSimOnly(scoringPosit posit);
} 
