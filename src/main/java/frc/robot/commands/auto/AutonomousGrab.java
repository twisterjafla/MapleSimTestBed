// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Bucket;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class AutonomousGrab extends SequentialCommandGroup {

  /**
   * pseudoCode:
   * humans will position robot
   * milk crate will tip, releasing cube into lowest zone
   * robot drives forward, getting more auto points
   */
  public AutonomousGrab(DriveSubsystem drive, IntakeSubsystem intake, Bucket bucket) {
    super(
      // dump milk crate
      new InstantCommand(
        () ->bucket.set(DoubleSolenoid.Value.kReverse),
        bucket
      ),
      new WaitCommand(.5),
      //bring milk crate back up
      new InstantCommand(
        () -> bucket.set(DoubleSolenoid.Value.kForward),
        bucket
      ),
      new WaitCommand(.5),
      //go forward
      new DriveStraight(drive, 2.7, Constants.auto.fwdSpeed),
      //drop intake
      new InstantCommand(
        () -> intake.set(DoubleSolenoid.Value.kReverse),
        intake
      ),
      // start intake spinning
      new InstantCommand(
        () -> intake.intakeCargo(Constants.intake.fwdSpeed),
        intake
      ),
      // drive forward into game piece
      new DriveStraight(drive, 2.8, Constants.auto.fwdSpeed),
      //reverse back into community
      new DriveStraight(drive, 2.7, Constants.auto.revSpeed));
  }
}
