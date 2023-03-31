package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.Limelight;

public class LimelightCommand extends RunCommand {
  public LimelightCommand(Limelight limelight) {
    super(
      () -> {
        SmartDashboard.putNumber("x", limelight.getX());
        SmartDashboard.putNumber("y", limelight.getY());
        SmartDashboard.putNumber("a", limelight.getA());
      });
      addRequirements(limelight);
  }
}
