// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.autoRoutines;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.DriveStraight;
import frc.robot.Constants;
import frc.robot.commands.*;
import frc.robot.subsystems.Bucket;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Intake;

public class AutonomousGrab extends SequentialCommandGroup {

  /**
   * pseudoCode:
   * humans will position robot
   * milk crate will tip, releasing cube into lowest zone
   * robot drives forward, getting more auto points
   */
  public AutonomousGrab(DriveBase drive, Intake intake, Bucket bucket) {
    super(
      // dump milk crate
      new InstantCommand(
        () ->bucket.set(DoubleSolenoid.Value.kReverse),
        bucket
      ),
      new WaitCommand(1),
      //bring milk crate back up
      new InstantCommand(
        () -> bucket.set(DoubleSolenoid.Value.kForward),
        bucket
      ),
      new WaitCommand(.5),
      //drop intake
      new InstantCommand(
        () -> intake.set(DoubleSolenoid.Value.kReverse),
        intake
      ),
      // start intake spinning
      new InstantCommand(
        () -> intake.intakeCargo(Constants.intake.revSpeed),
        intake
      ),
      // drive forward into game piece
      new DriveStraight(drive, 10),//18.66
      
      //drive back into community
      
      new WaitCommand(0.5),

      new InstantCommand(
        () -> intake.intakeCargo(0),
        intake
      )

      //new DriveStraight(drive, 2)
    );
  }
}
