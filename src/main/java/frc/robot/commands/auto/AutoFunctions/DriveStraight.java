package frc.robot.commands.auto.AutoFunctions;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.DriveBase;


public class DriveStraight extends RunCommand {
  // Encoder leftEncoder = new Encoder(4,5); 
  // Encoder rightEncoder = new Encoder(5);


  private final DriveBase driveSubsystem;

  double speed = 0;

  public DriveStraight(DriveBase driveSubsystem, double feet, double speed) {
    double distance= feet/Constants.auto.wheelRadius*Constants.auto.wheelRadius*3.14;
    while (driveSubsystem.getEncoder()< distance){
      execute();
    }
    end(false);

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
