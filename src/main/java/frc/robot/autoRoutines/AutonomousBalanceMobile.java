// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.autoRoutines;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Balance;
import frc.robot.commands.DriveStraight;
import frc.robot.Constants;
import frc.robot.commands.*;
import frc.robot.subsystems.Bucket;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Gyro;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class AutonomousBalanceMobile extends SequentialCommandGroup {
  /*
   * pseudoCode:
   * humans will position robot
   * milk crate will tip, releasing cube into lowest zone
   * robot drives forward, getting more auto points
   */

   // Subsystem to Dump Cargo then go forward over charge station
   // and then back up onto charge system to attempt balance

  public AutonomousBalanceMobile(DriveBase drive, Intake intake, Bucket bucket, Gyro gyro) {
    super(
      new WaitCommand(2),
      //dump game piece
      new InstantCommand(
        ()->bucket.set(DoubleSolenoid.Value.kForward),
        bucket
      ),
      new WaitCommand(1),
      //pick up milk crate
      new InstantCommand(
        ()->bucket.set(DoubleSolenoid.Value.kReverse),
        bucket
      ),
      new WaitCommand(1),
      //go forward
      new DriveStraight(drive, 2.7),
      new WaitCommand(1),
      //go back
      new DriveStraight(drive, 2.7),
      // balance
      new Balance(drive, gyro)
    );
  }
}
