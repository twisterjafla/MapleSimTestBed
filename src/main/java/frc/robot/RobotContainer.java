// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.AutonomousCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.ReverseIntakeCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveSubsystem m_driveSubsystem = new DriveSubsystem();
  private final IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem();

  private final CommandBase m_autoCommand = new AutonomousCommand(m_driveSubsystem, m_intakeSubsystem, m_shooterSubsystem);
  private NetworkTableEntry cameraSelection;

  XboxController movementJoystick = new XboxController(Constants.MOVEMENT_JOYSTICK);
  XboxController manipulatorJoystick = new XboxController(Constants.MANIPULATOR_JOYSTICK);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    
    m_driveSubsystem.setDefaultCommand(
      new ArcadeDrive(
            m_driveSubsystem,
            () -> -((-movementJoystick.getRawAxis(Axis.kLeftTrigger.value) + movementJoystick.getRawAxis(Axis.kRightTrigger.value))),
            () -> (-movementJoystick.getRawAxis(Axis.kLeftX.value) * 0.75),
      ));

    m_driveSubsystem.log();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    final JoystickButton manipulator_l1 = new JoystickButton(manipulatorJoystick, Button.kLeftBumper.value);
    final JoystickButton manipulator_l2 = new JoystickButton(manipulatorJoystick, Axis.kLeftTrigger.value);
    final JoystickButton manipulator_r1 = new JoystickButton(manipulatorJoystick, Button.kRightBumper.value);
    final JoystickButton manipulator_r2 = new JoystickButton(manipulatorJoystick, Axis.kRightTrigger.value);
    final JoystickButton manipulator_y = new JoystickButton(manipulatorJoystick, Button.kY.value);
    final JoystickButton manipulator_a = new JoystickButton(manipulatorJoystick, Button.kA.value);
    final JoystickButton manipulator_b = new JoystickButton(manipulatorJoystick, Button.kB.value);
    final JoystickButton manipulator_x = new JoystickButton(manipulatorJoystick, Button.kX.value);
    final POVButton manipulator_dpad_up = new POVButton(manipulatorJoystick, Constants.DPAD_UP);
    final POVButton manipulator_dpad_down = new POVButton(manipulatorJoystick, Constants.DPAD_DOWN);

    final JoystickButton movement_a = new JoystickButton(movementJoystick, Button.kA.value);
    final JoystickButton movement_b = new JoystickButton(movementJoystick, Button.kB.value);
    final JoystickButton movement_y = new JoystickButton(movementJoystick, Button.kY.value);
    
    // movement_a.toggleWhenPressed(new IntakeCommand(m_intakeSubsystem, Constants.INTAKE_SPEED));
    // movement_b.whileHeld(new ReverseIntake(m_intakeSubsystem, Constants.OUTTAKE_SPEED));
    
    // manipulator_b.whileHeld(new ShootCargo(m_shooterSubsystem, Constants.SHOOTER_SPEED));
    // manipulator_x.toggleWhenPressed(new IntakeCommand(m_intakeSubsystem, Constants.INTAKE_SPEED));
    // manipulator_y.whileHeld(new OuttakeCargoToFloor(m_intakeSubsystem, m_shooterSubsystem));
    // manipulator_a.whileHeld(new IntakeCargoCommandGroup(m_intakeSubsystem));
    // manipulator_dpad_up.whileHeld(new RunWinch(m_climberSubsystem, Constants.WINCH_IN_SPEED));
    // manipulator_dpad_down.whileHeld(new RunWinch(m_climberSubsystem, Constants.WINCH_OUT_SPEED));

    // manipulator_l2.whileHeld(new IntakeCommand(m_intakeSubsystem, Constants.INTAKE_SPEED));
    // manipulator_r2.whileHeld(new ReverseIntake(m_intakeSubsystem, Constants.OUTTAKE_SPEED));
    // movement_y.toggleWhenPressed(new SwitchCamera(cameraSelection, camera1.getName(), camera2.getName()));
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
