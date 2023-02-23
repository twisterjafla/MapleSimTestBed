// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


//import edu.wpi.first.wpilibj.PS4Controller.Button;
//import edu.wpi.first.wpilibj.PS4Controller.Button;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.AutonomousCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

import frc.robot.subsystems.LimelightSubsystem;

import frc.robot.subsystems.PneumaticsSubsytem;

import frc.robot.commands.*;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.BucketSubsystem;
import frc.robot.commands.IntakeToggleCommand;

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
  final BucketSubsystem m_bucketSubsystem = new BucketSubsystem(pneumatics);
  final ToggleCompressor toggleCompressor = new ToggleCompressor(pneumatics);

  final LimelightCommand limelightCommand = new LimelightCommand(limeLight);
  final IntakeCommand runIntake = new IntakeCommand(m_intakeSubsystem, Constants.intake.fwdSpeed);
  final IntakeCommand runIntakeBackward = new IntakeCommand(m_intakeSubsystem, Constants.intake.revSpeed);
  final ToggleBucketCommand toggleBucket = new ToggleBucketCommand(m_bucketSubsystem);
  final IntakeToggleCommand toggleIntake = new IntakeToggleCommand(m_intakeSubsystem);

  final AutonomousCommand m_autoCommand = new AutonomousCommand(m_driveSubsystem, m_intakeSubsystem);

  final CommandXboxController movementJoystick = new CommandXboxController(Constants.MOVEMENT_JOYSTICK);
  final CommandXboxController manipulatorJoystick = new CommandXboxController(Constants.MANIPULATOR_JOYSTICK);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    
    m_driveSubsystem.setDefaultCommand(
      new ArcadeDrive(
            m_driveSubsystem,
            () -> -((-movementJoystick.getRawAxis(Axis.kLeftTrigger.value) + movementJoystick.getRawAxis(Axis.kRightTrigger.value))),
            () -> (-movementJoystick.getRawAxis(Axis.kLeftX.value) * 0.75)
      ));


    limeLight.setDefaultCommand(limelightCommand);

    m_driveSubsystem.log();
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
