package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;

public class DriveStraightCommand extends WaitCommand {
  private final DriveSubsystem driveSubsystem;

  public DriveStraightCommand(DriveSubsystem driveSubsystem, double time) {
    super(time);
    this.driveSubsystem = driveSubsystem;
    addRequirements(this.driveSubsystem);

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
      super.execute();
      driveSubsystem.drive(-Constants.auto.fwdSpeed, 0);
  }

  @Override
  public void end(boolean interupted){
    super.end(interupted);
    driveSubsystem.drive(0,0);
  }
}
