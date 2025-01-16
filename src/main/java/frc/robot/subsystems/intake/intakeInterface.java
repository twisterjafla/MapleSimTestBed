package frc.robot.subsystems.intake;

import java.util.function.BooleanSupplier;

import edu.wpi.first.math.geometry.Translation3d;
import frc.robot.Utils.scoringPosit;
import frc.robot.subsystems.intake.simIntake.intakeState;

public interface intakeInterface {
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
