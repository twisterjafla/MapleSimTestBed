package frc.robot.commands.auto.AutoFunctions;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.DriveBase;


public class DriveStraight extends CommandBase {
    // Encoder leftEncoder = new Encoder(4,5); 
    // Encoder rightEncoder = new Encoder(5);


    private final DriveBase driveBase;

    double encoderValue;

    public DriveStraight(DriveBase driveSubsystem, double feet) {
      driveBase = driveSubsystem;
      double inches= feet*12;
      double wheeldistance=Constants.auto.wheelRadius*3.14*2;
      double wheelRotations=inches/wheeldistance;
      double motorRotations=wheelRotations*Constants.drive.gearRatio;
    //   /double encoderTicks = motorRotations*Constants.auto.TicksPerRotation;
      encoderValue=motorRotations;
      SmartDashboard.putNumber("ticks", encoderValue);

    //   encoderValue = ((((3*12)//converts feet to inches
    //   /((Constants.auto.wheelRadius*3.14*2))//converts inches to wheel rotations
    //   *Constants.drive.gearRatio)// converts wheel rotations to motor rotations
    //   *Constants.auto.TicksPerRotation)//converts motor rotations to encoder ticks
    //   );
    

    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        driveBase.resetEncoder();
    }

    // Called every time the scheduler runs while the command is scheduled.S
    @Override
    public void execute() {
        
        while(driveBase.getEncoder() < encoderValue ) {
            driveBase.drive(Constants.auto.fwdSpeed, 0);
        }
        end(false);

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
