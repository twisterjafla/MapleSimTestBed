package frc.robot.subsystems.intake;

import java.util.function.BooleanSupplier;

import frc.robot.Utils.scoringPosit;

public interface intakeInterface {
    // public void intake();
    // public void outtake();
    public boolean hasPeice();
    public void intake();
    public void intakeUntil(BooleanSupplier trigger);
    public void outtake();
    public void outtakeUntil(BooleanSupplier trigger);
    public void stop();
    //public void setPoseSimOnly(scoringPosit posit);
} 
