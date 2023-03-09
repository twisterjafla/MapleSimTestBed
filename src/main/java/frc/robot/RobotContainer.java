// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.commands.auto.AutonomousCommand;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...


  final LimelightSubsystem limeLight = new LimelightSubsystem();
  final PneumaticsSubsytem pneumatics = new PneumaticsSubsytem();
  final DriveSubsystem m_driveSubsystem = new DriveSubsystem();
  final IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem(pneumatics);
  final Bucket m_bucketSubsystem = new Bucket(pneumatics);
  final ToggleCompressor toggleCompressor = new ToggleCompressor(pneumatics);
  final Gyro gyro = new Gyro();

  final LimelightCommand limelightCommand = new LimelightCommand(limeLight);
  final IntakeCommand runIntake = new IntakeCommand(m_intakeSubsystem, Constants.intake.fwdSpeed);
  final IntakeCommand runIntakeBackward = new IntakeCommand(m_intakeSubsystem, Constants.intake.revSpeed);
  final ToggleBucketCommand toggleBucket = new ToggleBucketCommand(m_bucketSubsystem);
  final IntakeToggleCommand toggleIntake = new IntakeToggleCommand(m_intakeSubsystem);

  final AutonomousCommand m_autoCommand = new AutonomousCommand(m_driveSubsystem, m_intakeSubsystem,m_bucketSubsystem);

  final CommandXboxController movementJoystick = new CommandXboxController(Constants.MOVEMENT_JOYSTICK);
  final CommandXboxController manipulatorJoystick = new CommandXboxController(Constants.MANIPULATOR_JOYSTICK);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    
    m_driveSubsystem.setDefaultCommand(
      new ArcadeDrive(
            m_driveSubsystem,
            () -> ((-movementJoystick.getLeftTriggerAxis() + movementJoystick.getRightTriggerAxis())),
            () -> (movementJoystick.getLeftX() )
      ));


    limeLight.setDefaultCommand(limelightCommand);

    gyro.log();
  }

  private void configureButtonBindings() {
    manipulatorJoystick.leftBumper() //intake
    .whileTrue(runIntake);

    manipulatorJoystick.rightBumper()//outake
    .whileTrue(runIntakeBackward);

    manipulatorJoystick.x()
    .onTrue(toggleBucket);

    manipulatorJoystick.a()
    .onTrue(toggleIntake);

    manipulatorJoystick.y()
    .onTrue(toggleCompressor);
  }
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
