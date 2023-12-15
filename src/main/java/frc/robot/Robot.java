/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.IntakeToggle;
import frc.robot.commands.LimelightCommand;
import frc.robot.commands.RunIntake;
import frc.robot.commands.ToggleBucket;
import frc.robot.commands.ToggleCompressor;
import frc.robot.commands.auto.AutoRoutines.AutonomousBalanceMobile;
import frc.robot.commands.auto.AutoRoutines.AutonomousBalanceNoMobile;
import frc.robot.commands.auto.AutoRoutines.AutonomousDumpDoNothing;
import frc.robot.commands.auto.AutoRoutines.AutonomousGrab;
import frc.robot.subsystems.Bucket;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Gyro;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Pneumatics;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;


  final Limelight limeLight = new Limelight();
  final Pneumatics pneumatics = new Pneumatics();
  final DriveBase m_driveSubsystem = new DriveBase();
  final Intake m_intakeSubsystem = new Intake(pneumatics);
  final Bucket m_bucketSubsystem = new Bucket(pneumatics);
  final ToggleCompressor toggleCompressor = new ToggleCompressor(pneumatics);
  final Gyro gyro = new Gyro();

  final LimelightCommand limelightCommand = new LimelightCommand(limeLight);
  final RunIntake runIntake = new RunIntake(m_intakeSubsystem, Constants.intake.fwdSpeed);
  final RunIntake runIntakeBackward = new RunIntake(m_intakeSubsystem, Constants.intake.revSpeed);
  final ToggleBucket toggleBucket = new ToggleBucket(m_bucketSubsystem);
  final IntakeToggle toggleIntake = new IntakeToggle(m_intakeSubsystem);

 
  SendableChooser<Command> m_chooser = new SendableChooser<Command>();





  final CommandXboxController movementJoystick = new CommandXboxController(Constants.MOVEMENT_JOYSTICK);
  final CommandXboxController manipulatorJoystick = new CommandXboxController(Constants.MANIPULATOR_JOYSTICK);

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    configureButtonBindings();
    
    // starts the auto selector
    m_chooser.setDefaultOption("Auto Balance Mobile", new AutonomousBalanceMobile(m_driveSubsystem, m_intakeSubsystem, m_bucketSubsystem, gyro));
    m_chooser.addOption("Auto Grab", new AutonomousGrab(m_driveSubsystem, m_intakeSubsystem, m_bucketSubsystem));
    m_chooser.addOption("Auto No Mobile", new AutonomousBalanceNoMobile(m_driveSubsystem, m_intakeSubsystem, m_bucketSubsystem, gyro));
    m_chooser.addOption("doNothing", new InstantCommand());
    m_chooser.addOption("Dump Do Nothing", new AutonomousDumpDoNothing(m_driveSubsystem, m_intakeSubsystem, m_bucketSubsystem));
  
    SmartDashboard.putData("autos: ", m_chooser);




    //start cameraServer
    CameraServer.startAutomaticCapture();
    CameraServer.startAutomaticCapture();
    
    m_driveSubsystem.setDefaultCommand(
      new ArcadeDrive(
            m_driveSubsystem,
            () -> ((-movementJoystick.getLeftTriggerAxis() + movementJoystick.getRightTriggerAxis())),
            () -> (-movementJoystick.getLeftX() )
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
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   * 
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  
}
