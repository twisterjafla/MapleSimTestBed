package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.math.MathUtil;
import frc.robot.subsystems.DriveSubsystem;

public class ArcadeDrive extends RunCommand {
  /**
   * Creates a new ArcadeDrive command.
   *
   * @param left       The control input for the left side of the drive
   * @param right      The control input for the right sight of the drive
   * @param driveSubsystem The driveSubsystem subsystem to drive
   */
  public ArcadeDrive(DriveSubsystem driveSubsystem, double speed, double rotation) {
    super(
      ()->{
        double driveMotorSpeed = MathUtil.applyDeadband(speed, 0.1);
        double rotationMotorSpeed = MathUtil.applyDeadband(rotation, 0.1);
        driveSubsystem.drive(driveMotorSpeed, rotationMotorSpeed);
      },
      driveSubsystem
    );
    SmartDashboard.putString("On", "true");
  }

  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    SmartDashboard.putString("On", "false");
  }
}
