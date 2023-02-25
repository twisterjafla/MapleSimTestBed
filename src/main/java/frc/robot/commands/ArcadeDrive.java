/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;

/**
 * Have the robot drive tank style.
 */
public class ArcadeDrive extends RunCommand {
  /**
   * Creates a new ArcadeDrive command.
   *
   * @param left       The control input for the left side of the drive
   * @param right      The control input for the right sight of the drive
   * @param driveSubsystem The driveSubsystem subsystem to drive
   */
  public ArcadeDrive(DriveSubsystem drive, double speed, double rotation) {
    super(
      ()->{
        SmartDashboard.putString("On", "true");

        drive.drive(
          MathUtil.applyDeadband(speed, 0.1)*Constants.drive.driveSpeedRatio,
          MathUtil.applyDeadband(rotation, 0.1)*Constants.drive.rotationSpeedRatio
        );
      },
      drive
    );
  }
}
