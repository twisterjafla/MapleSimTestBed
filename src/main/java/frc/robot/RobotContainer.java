// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
//import edu.wpi.first.wpilibj.PS4Controller.Button;
//import edu.wpi.first.wpilibj.PS4Controller.Button;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.AutonomousCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
//import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj.XboxController.Axis;
//import frc.robot.commands.ToggleBucketCommand;
import frc.robot.subsystems.BucketSubsystem;
import frc.robot.commands.IntakeToggleCommand;
//import frc.robot.subsystems.IntakeSubsystem;




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
  private final BucketSubsystem m_bucketSubsystem = new BucketSubsystem();

  private final CommandBase m_autoCommand = new AutonomousCommand(m_driveSubsystem, m_intakeSubsystem);

  CommandXboxController movementJoystick = new CommandXboxController(Constants.MOVEMENT_JOYSTICK);
  CommandXboxController manipulatorJoystick = new CommandXboxController(Constants.MANIPULATOR_JOYSTICK);

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

    m_driveSubsystem.log();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    /*
    
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
     */
    final Trigger manipulator_l1 = manipulatorJoystick.leftBumper();//intake
    final Trigger manipulator_r1 = manipulatorJoystick.rightBumper();//outake

    final Trigger manipulator_x = manipulatorJoystick.x();
    final Trigger manipulator_a = manipulatorJoystick.a();

    //final POVButton manipulator_dpad_up = new POVButton(manipulatorJoystick, Constants.DPAD_UP);
    //final POVButton manipulator_dpad_down = new POVButton(manipulatorJoystick, Constants.DPAD_DOWN);
    // movement_a.toggleWhenPressed(new IntakeCommand(m_intakeSubsystem, Constants.INTAKE_SPEED));
    // movement_b.whileHeld(new ReverseIntake(m_intakeSubsystem, Constants.OUTTAKE_SPEED));
    
    // manipulator_b.whileHeld(new ShootCargo(m_shooterSubsystem, Constants.SHOOTER_SPEED));
    // manipulator_x.toggleWhenPressed(new IntakeCommand(m_intakeSubsystem, Constants.INTAKE_SPEED));
    // manipulator_y.whileHeld(new OuttakeCargoToFloor(m_intakeSubsystem, m_shooterSubsystem));
    // manipulator_a.whileHeld(new IntakeCargoCommandGroup(m_intakeSubsystem));
    //manipulator_dpad_up.whileHeld(new RunWinch(m_climberSubsystem, Constants.WINCH_IN_SPEED));
    //manipulator_dpad_down.whileHeld(new RunWinch(m_climberSubsystem, Constants.WINCH_OUT_SPEED));

    manipulator_l1.whileTrue(new IntakeCommand(m_intakeSubsystem, Constants.INTAKE_SPEED));
    manipulator_r1.whileTrue(new IntakeCommand(m_intakeSubsystem, Constants.OUTTAKE_SPEED));

    manipulator_x.onTrue(new ToggleBucketCommand(m_bucketSubsystem));
    manipulator_a.onTrue(new IntakeToggleCommand(m_intakeSubsystem));
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
