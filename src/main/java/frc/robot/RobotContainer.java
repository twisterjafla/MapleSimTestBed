// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.commands.auto.AutonomousBalanceMobile;
import frc.robot.commands.auto.AutonomousBalanceNoMobile;
import frc.robot.commands.auto.AutonomousGrab;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.MecanumControllerCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.Constants.drive;
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

  final AutonomousBalanceMobile m_autoCommand = new AutonomousBalanceMobile(m_driveSubsystem, m_intakeSubsystem,m_bucketSubsystem, gyro);

  final CommandXboxController movementJoystick = new CommandXboxController(Constants.MOVEMENT_JOYSTICK);
  final CommandXboxController manipulatorJoystick = new CommandXboxController(Constants.MANIPULATOR_JOYSTICK);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    
    //start cameraServer
    CameraServer.startAutomaticCapture();
    CameraServer.startAutomaticCapture();
    
    m_driveSubsystem.setDefaultCommand(
      new ArcadeDrive(
            m_driveSubsystem,
            () -> ((-movementJoystick.getLeftTriggerAxis() + movementJoystick.getRightTriggerAxis())),
            () -> (-movementJoystick.getLeftX() ),
            () -> (movementJoystick.x().getAsBoolean())
      ));


    limeLight.setDefaultCommand(limelightCommand);

    gyro.log();

    m_chooser.setDefaultOption("Simple Auto", m_autoBalancemobile);
    m_chooser.addOption("Complex Auto", m_complexAuto);
    m_chooser.addOption("Auto No Mobile", m_autoNoMobile);

    SmartDashboard.putData("autos: ", m_chooser);
  }

  private void configureButtonBindings() {
    manipulatorJoystick.leftBumper() //intake
    .whileTrue(runIntake);

    manipulatorJoystick.rightBumper() //outake
    .whileTrue(runIntakeBackward);

    manipulatorJoystick.x()
    .onTrue(toggleBucket);

    manipulatorJoystick.a()
    .onTrue(toggleIntake);

    manipulatorJoystick.y()
    .onTrue(toggleCompressor);
  }

  // Auto Stuff
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  private final Command m_autoBalancemobile =
  new AutonomousBalanceMobile(m_driveSubsystem, m_intakeSubsystem, m_bucketSubsystem, gyro);

// A complex auto routine that drives forward, drops a hatch, and then drives backward.
private final Command m_complexAuto =
 new AutonomousGrab(m_driveSubsystem, m_intakeSubsystem, m_bucketSubsystem);

 private final Command m_autoNoMobile= 
 new AutonomousBalanceNoMobile(m_driveSubsystem, m_intakeSubsystem, m_bucketSubsystem, gyro);

// A simple auto routine that drives forward a specified distance, and then stops.
SendableChooser<Command> m_chooser = new SendableChooser<>();



  // public Command getAutonomousCommand() {

  //   // An ExampleCommand will run in autonomous
  //   //return m_autoCommand;
  //   return m_chooser.getSelected();
  // }


  public Command getAutonomousCommand() {

    var table = NetworkTableInstance.getDefault().getTable("troubleshooting");
    var leftReference = table.getEntry("left_reference");
    var leftMeasurement = table.getEntry("left_measurement");
    var rightReference = table.getEntry("right_reference");
    var rightMeasurement = table.getEntry("right_measurement");

    var leftController = new PIDController(DriveConstants.Drive_Kp, 0, 0);
    var rightController = new PIDController(DriveConstants.Drive_Kp, 0, 0);

    Trajectory trajectoryOne = new Trajectory();
    Trajectory trajectoryTwo = new Trajectory();
    Trajectory trajectoryThree = new Trajectory();
    Trajectory trajectoryFour= new Trajectory();

    String trajectoryFileOne = "pathplanner/generatedJSON/2cargoreal.wpilib.json";
    //String trajectoryFileOne = "pathplanner/paths/3 carg.wpilib.json";
    String trajectoryFileTwo = "pathplanner/generatedJSON/4cargoreal.wpilib.json";
    String trajectoryFileThree = "pathplanner/generatedJSON/4cargoreverse.wpilib.json";
    String trajectoryFileFour = "pathplanner/generatedJSON/5cargoreal.wpilib.json";
   

    
    try{
      Path trajectoryPathOne = Filesystem.getDeployDirectory().toPath().resolve(trajectoryFileOne);
      trajectoryOne = TrajectoryUtil.fromPathweaverJson(trajectoryPathOne);
    } catch(IOException ex) {
        DriverStation.reportError("Unable to open trajectory:" + trajectoryFileOne, ex.getStackTrace());
    }

    try{
      Path trajectoryPathTwo = Filesystem.getDeployDirectory().toPath().resolve(trajectoryFileTwo);
      trajectoryTwo = TrajectoryUtil.fromPathweaverJson(trajectoryPathTwo);
    } catch(IOException ex) {
        DriverStation.reportError("Unable to open trajectory:" + trajectoryFileTwo, ex.getStackTrace());
    }

    try{
      Path trajectoryPathThree = Filesystem.getDeployDirectory().toPath().resolve(trajectoryFileThree);
      trajectoryThree = TrajectoryUtil.fromPathweaverJson(trajectoryPathThree);
    } catch(IOException ex) {
        DriverStation.reportError("Unable to open trajectory:" + trajectoryFileThree, ex.getStackTrace());
    }

    try{
      Path trajectoryPathFour = Filesystem.getDeployDirectory().toPath().resolve(trajectoryFileFour);
      trajectoryFour = TrajectoryUtil.fromPathweaverJson(trajectoryPathFour);
    } catch(IOException ex) {
        DriverStation.reportError("Unable to open trajectory:" + trajectoryFileFour, ex.getStackTrace());
    }
    
 
    
 

    //Call concatTraj in Ramsete to run both trajectories back to back as one
    //var concatTraj = trajectoryTwo.concatenate(trajectoryThree);


            RamseteCommand ramseteCommandOne =
            new RamseteCommand(
                trajectoryOne,
                DriveSubsystem::getPose,
                new RamseteController(DriveConstants.kRamseteB, DriveConstants.kRamseteZeta),
                new SimpleMotorFeedforward(
                    DriveConstants.Drive_Ks,
                    DriveConstants.Drive_Kv,
                    DriveConstants.Drive_Ka),
                DriveConstants.kDriveKinematics,
                driveSub::getWheelSpeeds,
                leftController,
                rightController,
                // RamseteCommand passes volts to the callback
                driveSub::tankDriveVolts,
                driveSub);

              RamseteCommand ramseteCommandTwo =
                new RamseteCommand(
                    trajectoryTwo,
                    driveSub::getPose,
                    new RamseteController(DriveConstants.kRamseteB, DriveConstants.kRamseteZeta),
                    new SimpleMotorFeedforward(
                        DriveConstants.Drive_Ks,
                        DriveConstants.Drive_Kv,
                        DriveConstants.Drive_Ka),
                    DriveConstants.kDriveKinematics,
                    driveSub::getWheelSpeeds,
                    leftController,
                    rightController,
                    // RamseteCommand passes volts to the callback
                    driveSub::tankDriveVolts,
                    driveSub);

                    RamseteCommand ramseteCommandTwoTwo =
                    new RamseteCommand(
                        trajectoryThree,
                        driveSub::getPose,
                        new RamseteController(DriveConstants.kRamseteB, DriveConstants.kRamseteZeta),
                        new SimpleMotorFeedforward(
                            DriveConstants.Drive_Ks,
                            DriveConstants.Drive_Kv,
                            DriveConstants.Drive_Ka),
                        DriveConstants.kDriveKinematics,
                        driveSub::getWheelSpeeds,
                        leftController,
                        rightController,
                        // RamseteCommand passes volts to the callback
                        driveSub::tankDriveVolts,
                        driveSub);      
                    
              RamseteCommand ramseteCommandThree =
              new RamseteCommand(
                  trajectoryFour,
                  driveSub::getPose,
                  new RamseteController(DriveConstants.kRamseteB, DriveConstants.kRamseteZeta),
                  new SimpleMotorFeedforward(
                      DriveConstants.Drive_Ks,
                      DriveConstants.Drive_Kv,
                      DriveConstants.Drive_Ka),
                  DriveConstants.kDriveKinematics,
                  driveSub::getWheelSpeeds,
                  leftController,
                  rightController,
                  // RamseteCommand passes volts to the callback
                  driveSub::tankDriveVolts,
                  driveSub);


    leftMeasurement.setNumber(driveSub.getWheelSpeeds().leftMetersPerSecond);
    leftReference.setNumber(leftController.getSetpoint());

    rightMeasurement.setNumber(driveSub.getWheelSpeeds().rightMetersPerSecond);
    rightReference.setNumber(rightController.getSetpoint());
    
    // Reset odometry to the starting pose of the trajectory.
    driveSub.resetOdometry(trajectoryOne.getInitialPose());

    // Run path following command, then stop at the end.
    //return ramseteCommand.andThen(() -> driveSub.tankDriveVolts(0, 0));
    return new ParallelCommandGroup(new SequentialCommandGroup(/*autoShoot1.withTimeout(.5),*/ flywheelVelocityRunTwoBall,
     ramseteCommandOne.andThen(() -> driveSub.tankDriveVolts(0, 0)), nullCommand2.withTimeout(.1), autoShoot.withTimeout(.9), flywheelVelocityRunFourBall,
     ramseteCommandTwo.andThen(() -> driveSub.tankDriveVolts(0, 0)), nullCommand.withTimeout(.1), ramseteCommandTwoTwo.andThen(() -> driveSub.tankDriveVolts(0, 0)), autoShoot1.withTimeout(1), 
     flywheelVelocityRunFiveBall, ramseteCommandThree.andThen(() -> driveSub.tankDriveVolts(0, 0)), autoShoot2), autoTurretAutoAlign, autoDrop.withTimeout(.5));
   

    //return new ParallelCommandGroup(new SequentialCommandGroup(flyWheelVelocityRunTwoBall, ramseteCommandOne.andThen(() -> driveSub.tankDriveVolts(0, 0)), autoShoot), autoTurretAutoAlign, autoDrop.withTimeout(.5)); 
    }   



}
