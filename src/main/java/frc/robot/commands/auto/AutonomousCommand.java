// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.ToggleBucketCommand;
import frc.robot.subsystems.BucketSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class AutonomousCommand extends SequentialCommandGroup {
  /** Creates a new AutonomousCommand. */
  DriveSubsystem drive;
  IntakeSubsystem intake;
  BucketSubsystem bucket;

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

  public AutonomousCommand(DriveSubsystem drive, IntakeSubsystem intake,BucketSubsystem bucket) {
    this.drive = drive;
    this.intake = intake;
    this.bucket = bucket;

    // Use addRequirements() here to declare subsystem dependencies.
    SmartDashboard.getNumber("Auto Selector", 0);

    addCommands(
      new InstantCommand(
        ()->{this.bucket.set(DoubleSolenoid.Value.kForward);},
        this.bucket
      ),
      new DriveStraightCommand(drive, 2.5),
      new ToggleBucketCommand(this.bucket),
      new WaitCommand(1)
    );
  }
}
