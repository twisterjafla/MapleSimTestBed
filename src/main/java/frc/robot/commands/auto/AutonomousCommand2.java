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
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class AutonomousCommand2 extends SequentialCommandGroup {
  /** Creates a new AutonomousCommand. */
  DriveSubsystem drive;
  IntakeSubsystem intake;
  Bucket bucket;

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

  public AutonomousCommand2(DriveSubsystem drive, IntakeSubsystem intake,Bucket bucket) {
    this.drive = drive;
    this.intake = intake;
    this.bucket = bucket;

    // Use addRequirements() here to declare subsystem dependencies.
    SmartDashboard.getNumber("Auto Selector", 0);

    addCommands(
    //   new WaitCommand(2),
    //    new InstantCommand(
    //      ()->{this.bucket.set(DoubleSolenoid.Value.kForward);},
    //      this.bucket
    //    ),
    //    new WaitCommand(1),
    //    new InstantCommand(
    //      ()->{this.bucket.set(DoubleSolenoid.Value.kReverse);},
    //      this.bucket
    //    ),
      new DriveStraight(drive, 2.5,Constants.auto.fwdSpeed),
      new DriveStraight(drive, 2.7,Constants.auto.revSpeed)  
    );
  }
}
