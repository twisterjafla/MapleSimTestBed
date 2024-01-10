package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.DriveBase;
import java.lang.Math;


public class DriveStraight extends CommandBase {
    // Encoder leftEncoder = new Encoder(4,5); 
    // Encoder rightEncoder = new Encoder(5);


    private final DriveBase driveBase;
    private final PIDController pid = new PIDController(0.50, 0, 0.10);
    double setpoint;
    int count=0;

    public DriveStraight(DriveBase driveSubsystem, double feet) {
      driveBase = driveSubsystem;
      double inches= 3*12;
      double wheelRotations=inches*(Constants.auto.wheelRadius*3.14*2);
      double motorRotations=wheelRotations*Constants.drive.gearRatio;
      setpoint=motorRotations*Constants.auto.TicksPerRotation;


    
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
        pid.setSetpoint(setpoint);
        SmartDashboard.putBoolean("has ended", false);


    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        SmartDashboard.putNumber("goal", setpoint);
        SmartDashboard.putNumber("count", count);
        count++;

        SmartDashboard.putNumber("pid output", pid.calculate(driveBase.getEncoder()));
        driveBase.drive(1, 0);
        
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        driveBase.drive(0, 0);
        SmartDashboard.putBoolean("has ended", true);

    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        SmartDashboard.putNumber("setpoint-encoder", setpoint-driveBase.getEncoder());
        return Math.abs(setpoint-driveBase.getEncoder())<=0.5;
    }

      // Called repeatedly when this Command is scheduled to run

}
