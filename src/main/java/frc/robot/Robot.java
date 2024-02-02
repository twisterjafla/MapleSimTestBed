/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
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


  final Pneumatics pneumatics = new Pneumatics();
  final DriveBase m_driveSubsystem = new DriveBase();
  final Intake m_intakeSubsystem = new Intake(pneumatics);
  final Bucket m_bucketSubsystem = new Bucket(pneumatics);
  final ToggleCompressor toggleCompressor = new ToggleCompressor(pneumatics);
  final Gyro gyro = new Gyro();
  final Timer timer = new Timer();
  final Limelight lime = new Limelight();


 

  final RunIntake runIntake = new RunIntake(m_intakeSubsystem, Constants.intake.fwdSpeed);
  final RunIntake runIntakeBackward = new RunIntake(m_intakeSubsystem, Constants.intake.revSpeed);
  final ToggleBucket toggleBucket = new ToggleBucket(m_bucketSubsystem);
  final IntakeToggle toggleIntake = new IntakeToggle(m_intakeSubsystem);
 
  SendableChooser<Command> autoChooser = new SendableChooser<Command>();
  SendableChooser<Integer> controlChooser = new SendableChooser<Integer>();


  

  final Midi midi = new Midi();

  final CommandXboxController controller1 = new CommandXboxController(Constants.MOVEMENT_JOYSTICK);
  final CommandXboxController controller2 = new CommandXboxController(Constants.MANIPULATOR_JOYSTICK);
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    configureControls();
    midi.InitButtons();
    
    // starts the auto selector

    autoChooser.setDefaultOption("Auto Grab", new AutonomousGrab(m_driveSubsystem, m_intakeSubsystem, m_bucketSubsystem));

    autoChooser.addOption("doNothing", new InstantCommand());
    autoChooser.addOption("Dump Do Nothing", new AutonomousDumpDoNothing(m_driveSubsystem, m_intakeSubsystem, m_bucketSubsystem));
  
    SmartDashboard.putData("autos: ", autoChooser);



    //starts the control type chooser
    controlChooser.setDefaultOption("Two Controler", 0);
    controlChooser.addOption("One controler", 1);
    controlChooser.addOption("jace control", 2);
    controlChooser.addOption("MidiControl alone", 3);


    SmartDashboard.putData("control type", controlChooser);


    //start cameraServer
    CameraServer.startAutomaticCapture();
    CameraServer.startAutomaticCapture();
    

    configureControls();


    gyro.log();

    m_driveSubsystem.resetEncoder();
  }

  private void configureControls() {
    if (controlChooser.getSelected()==null){}

    else if (controlChooser.getSelected()==0){
      controlInitalizer.configureTwoControllersBasic(controller1, controller2);
    }

    else if (controlChooser.getSelected()==1){
      controlInitalizer.configureOneControllersBasic(controller1);
    }


    else if (controlChooser.getSelected()==2){
      controlInitalizer.initalizeJaceControllWithSecondController(controller1, controller2);
    }
    else if (controlChooser.getSelected()==3){
      controlInitalizer.initalizeMIDIAloneControl(midi);
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
    semiAutoManager.periodic();

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
