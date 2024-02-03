package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.SpeakerShooter;

public class SpeakerSaftey extends RunCommand {
  // setActive setActive;

  /**
   * Creates a new ArcadeDrive command.
   *
   * @param left       The control input for the left side of the drive
   * @param right      The control input for the right sight of the drive
   * @param driveSubsystem The driveSubsystem subsystem to drive
   */
  public SpeakerSaftey(SpeakerShooter importedShooter, DoubleSupplier flySpeed, DoubleSupplier indexSpeed) {
    super(
        ()->{
            importedShooter.SafteyFunction(
                MathUtil.applyDeadband(indexSpeed.getAsDouble(),0.1),
                MathUtil.applyDeadband(flySpeed.getAsDouble(), 0.1));
        },
        importedShooter
    );
    addRequirements(importedShooter);
  }      
}