/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Gyro;

/**
 * Have the robot drive tank style.
 */
public class ArcadeDrive extends Command {
  /**
   * Creates a new ArcadeDrive command.
   *
   * @param left       The control input for the left side of the drive
   * @param right      The control input for the right sight of the drive
   * @param driveSubsystem The driveSubsystem subsystem to drive
   */

  public DriveBase drive;
  public DoubleSupplier speed;
  public DoubleSupplier rotation;
  public double StraightAngle=0;
  public boolean isCurrentlyStraight=true;
  public Gyro gyro;

  private final PIDController driftPID = new PIDController(
    Constants.drive.driftSolve.Kp,
    Constants.drive.driftSolve.Ki,
    Constants.drive.driftSolve.Kd
);

  public ArcadeDrive(DriveBase drive, DoubleSupplier speed, DoubleSupplier rotation, Gyro gyro) {
    
    this.drive=drive;
    this.speed=speed;
    this.rotation=rotation;
    this.gyro=gyro;
    StraightAngle=gyro.getYaw();

    driftPID.setTolerance(Constants.drive.driftSolve.tolerence);
    addRequirements(drive);
  }


    @Override
    public void execute(){
      double rotation=MathUtil.applyDeadband(this.rotation.getAsDouble(), 0.1);
      double speed=MathUtil.applyDeadband(this.speed.getAsDouble(), 0.1);
      SmartDashboard.putBoolean("isStraight", isCurrentlyStraight);
      SmartDashboard.putNumber("rotateRaw", rotation);
      if (rotation==0 && speed!=0){
        driveStraight(speed);
      }
      else{
        driveCurved(speed, rotation);
      }

    }

    private void driveStraight(double speed){
      if (!isCurrentlyStraight){
        isCurrentlyStraight=true;
        
        SmartDashboard.putNumber("goal", gyro.getYaw());
        driftPID.setSetpoint(gyro.getYaw());

      }

      if (!driftPID.atSetpoint()){
        SmartDashboard.putBoolean("isGood", false);
        drive.drive(speed, driftPID.calculate(gyro.getYaw())/7);
                SmartDashboard.putNumber("driftinput", driftPID.calculate(gyro.getYaw()) );


      }
      else{
        drive.drive(speed, 0);
        SmartDashboard.putBoolean("isGood", true);
        driftPID.calculate(gyro.getYaw());
      }
      
      SmartDashboard.putNumber("pidSetpoint", driftPID.getSetpoint());
    }

    private void driveCurved(double speed, double rotation){
      isCurrentlyStraight=false;
      drive.drive(speed, rotation);
    }

  
}
