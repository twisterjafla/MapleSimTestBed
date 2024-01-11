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
import frc.robot.autoRoutines.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;


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

 
  SendableChooser<Command> autoChooser = new SendableChooser<Command>();
  SendableChooser<Integer> controlChooser = new SendableChooser<Integer>();





  final CommandXboxController movementController = new CommandXboxController(Constants.MOVEMENT_JOYSTICK);
  final CommandXboxController manipulatorController = new CommandXboxController(Constants.MANIPULATOR_JOYSTICK);
  final CommandXboxController oneController = new CommandXboxController(0);
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    configureControls();
    
    // starts the auto selector
    autoChooser.setDefaultOption("Auto Balance Mobile", new AutonomousBalanceMobile(m_driveSubsystem, m_intakeSubsystem, m_bucketSubsystem, gyro));
    autoChooser.addOption("Auto Grab", new AutonomousGrab(m_driveSubsystem, m_intakeSubsystem, m_bucketSubsystem));
    autoChooser.addOption("Auto No Mobile", new AutonomousBalanceNoMobile(m_driveSubsystem, m_intakeSubsystem, m_bucketSubsystem, gyro));
    autoChooser.addOption("doNothing", new InstantCommand());
    autoChooser.addOption("Dump Do Nothing", new AutonomousDumpDoNothing(m_driveSubsystem, m_intakeSubsystem, m_bucketSubsystem));
  
    SmartDashboard.putData("autos: ", autoChooser);



    //starts the control type chooser
    controlChooser.setDefaultOption("Two Controler", 0);
    controlChooser.addOption("One controler", 1);

    SmartDashboard.putData("control type", controlChooser);


    //start cameraServer
    CameraServer.startAutomaticCapture();
    CameraServer.startAutomaticCapture();
    

    configureControls();

    limeLight.setDefaultCommand(limelightCommand);

    gyro.log();

    m_driveSubsystem.resetEncoder();
  }

  private void configureControls() {
    if (controlChooser.getSelected()==null){}
    else if (controlChooser.getSelected()==0){
      m_driveSubsystem.setDefaultCommand(
        new ArcadeDrive(
              m_driveSubsystem,
              () -> ((-movementController.getLeftTriggerAxis() + movementController.getRightTriggerAxis())),
              () -> (-movementController.getLeftX() )
        ));




      manipulatorController.leftBumper() //intake
      .whileTrue(runIntake);

      manipulatorController.rightBumper()//outake
      .whileTrue(runIntakeBackward);

      manipulatorController.x()
      .onTrue(toggleBucket);

      manipulatorController.a()
      .onTrue(toggleIntake);

      manipulatorController.y()
      .onTrue(toggleCompressor);
    }
    else if (controlChooser.getSelected()==1){
      m_driveSubsystem.setDefaultCommand(
        new ArcadeDrive(
              m_driveSubsystem,
              () -> ((-oneController.getLeftTriggerAxis() + oneController.getRightTriggerAxis())),
              () -> (-oneController.getLeftX() )
        ));




        oneController.leftBumper() //intake
      .whileTrue(runIntake);

      oneController.rightBumper()//outake
      .whileTrue(runIntakeBackward);

      oneController.x()
      .onTrue(toggleBucket);

      oneController.a()
      .onTrue(toggleIntake);

      oneController.y()
      .onTrue(toggleCompressor);
    }
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
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void disabledPeriodic() {
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = autoChooser.getSelected();

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
      CommandScheduler.getInstance().cancelAll();
    }
    configureControls();
    
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
