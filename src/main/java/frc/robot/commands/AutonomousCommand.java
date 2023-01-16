// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class AutonomousCommand extends SequentialCommandGroup {
  /** Creates a new AutonomousCommand. */
  DriveSubsystem autoDriveSubsystem;
  IntakeSubsystem autoIntakeSubsystem;
  Timer timer;

  public AutonomousCommand(DriveSubsystem driveSubsystem, IntakeSubsystem intakeSubsystem) {
    autoDriveSubsystem = driveSubsystem;

    // Use addRequirements() here to declare subsystem dependencies.
    SmartDashboard.getNumber("Auto Selector", 0);

    addCommands(
      new DriveStraightCommand(autoDriveSubsystem, 2.5)
    );
  }
}
