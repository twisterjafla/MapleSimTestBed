package frc.robot.commands.auto;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import frc.robot.subsystems.DriveSubsystem;


public class DriveStraight extends WaitCommand {
  private final DriveSubsystem driveSubsystem;

  double speed = 0;

  public DriveStraight(DriveSubsystem driveSubsystem, double time, double speed) {
    super(time);
    this.driveSubsystem = driveSubsystem;
    this.speed = MathUtil.clamp(speed, -1, 1);
    addRequirements(this.driveSubsystem);

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
      super.execute();
      driveSubsystem.drive(- speed, 0);
  }

  @Override
  public void end(boolean interupted){
    super.end(interupted);
    driveSubsystem.drive(0,0);
  }
}
