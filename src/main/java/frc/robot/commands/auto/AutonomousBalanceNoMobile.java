// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Bucket;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.Gyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;


public class AutonomousBalanceNoMobile extends SequentialCommandGroup {
  /** Creates a new AutonomousCommand. */
  DriveSubsystem drive;
  IntakeSubsystem intake;
  Bucket bucket;
  Gyro gyro;

  /*
   * pseudoCode:
   * 
   * humans will position robot
   * 
   * milk crate will tip, releasing cube into lowest zone
   * 
   * robot drives forward, getting more auto points
   * 
   * 
   */

   // Subsystem to Dump Cargo then go forward over charge station
   // and then back up onto charge system to attempt balance

  public AutonomousBalanceNoMobile(DriveSubsystem drive, IntakeSubsystem intake, Bucket bucket, Gyro gyro) {
    this.drive = drive;
    this.intake = intake;
    this.bucket = bucket;
    this.gyro = gyro;

    // Use addRequirements() here to declare subsystem dependencies.
    SmartDashboard.getNumber("Auto Selector", 0);

    addCommands(
      new WaitCommand(2),
       new InstantCommand(
         ()->{this.bucket.set(DoubleSolenoid.Value.kForward);},
         this.bucket
       ),
       new WaitCommand(1),
       new InstantCommand(
         ()->{this.bucket.set(DoubleSolenoid.Value.kReverse);},
         this.bucket
       ),
       new WaitCommand(1),
      new DriveStraight(drive, 2,Constants.auto.fwdSpeed),
      new Balance(drive, gyro)
    );
  }
}
