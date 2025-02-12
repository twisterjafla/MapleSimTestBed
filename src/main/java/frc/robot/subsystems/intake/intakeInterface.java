package frc.robot.subsystems.intake;

import java.util.function.BooleanSupplier;

import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.intake.simIntake.intakeState;

public interface intakeInterface extends Subsystem{

    /**@return wether or not the intake currently contains a peice.*/
    public boolean hasPeice();

    /**sets the intake state to intaking untill a peice is intaked */
    public void intake();

    /**
     * sets the intake state to intake untill trigger returns true 
     * @param trigger the supplier that will stop the intake when it returns true
    */
    public void intakeUntil(BooleanSupplier trigger);

    /**sets the intake state to outtake until the peice is outtaked*/
    public void outtake();

    /**
     * outtakes untill the trigger returns true
     * @param trigger the supplier that will stop the intake when it returns true
     */
    public void outtakeUntil(BooleanSupplier trigger);

    /**stops the intake */
    public void stop();

    /**resets the intake */
    public void reset();

    /**@return the state of the intake in the form of a intakeState */
    public intakeState getState();

    /**gets a translation that represents the change from the 0,0 of the intake the point a coral would be */
    public Translation3d getTranslation();
   
} 
