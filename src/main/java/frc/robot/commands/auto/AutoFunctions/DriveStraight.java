package frc.robot.commands.auto.AutoFunctions;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.DriveBase;


public class DriveStraight extends CommandBase {
    // Encoder leftEncoder = new Encoder(4,5); 
    // Encoder rightEncoder = new Encoder(5);


    private final DriveBase driveBase;

    double speed = 0;
    double encoderValue;

    public DriveStraight(DriveBase driveSubsystem, double feet, double speed) {
      driveBase = driveSubsystem;
      encoderValue= feet*Constants.auto.wheelRadius*Constants.auto.wheelRadius*3.14;
    

      // lowPass = LinearFilter.movingAverage(3);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        driveBase.resetEncoder();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        if (driveBase.getEncoder() > encoderValue ) {
            driveBase.drive(Constants.auto.fwdSpeed, 0);
        }

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        driveBase.drive(0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

      // Called repeatedly when this Command is scheduled to run

}
