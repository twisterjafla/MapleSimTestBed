// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructArrayPublisher;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.networktables.StructTopic;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.swervedrive.AIRobotInSimulation;

import java.io.File;
import java.io.IOException;

import org.ironmaple.simulation.SimulatedArena;
import org.ironmaple.simulation.seasonspecific.reefscape2025.ReefscapeAlgaeOnField;
import org.ironmaple.simulation.seasonspecific.reefscape2025.ReefscapeCoralAlgaeStack;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;

import com.pathplanner.lib.commands.FollowPathCommand;

import swervelib.parser.SwerveParser;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to each mode, as
 * described in the TimedRobot documentation. If you change the name of this class or the package after creating this
 * project, you must also update the build.gradle file in the project.
 */
public class Robot extends TimedRobot
{

  private static Robot   instance;
  private        Command m_autonomousCommand;
  ControlChooser controlChooser;
  SendableChooser<Integer> controlChooserTest = new SendableChooser<Integer>();
  

  //private RobotContainer m_robotContainer;

  private Timer disabledTimer;

  public Robot()
  {
    instance = this;
    SystemManager.SystemManagerInit();
    SmartDashboard.putString("testBox", "test");
  
  }

  public static Robot getInstance()
  {
    return instance;
  }

  /**
   * This function is run when the robot is first started up and should be used for any initialization code.
   */
  @Override
  public void robotInit()
  {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.

    // Create a timer to disable motor brake a few seconds after disable.  This will let the robot stop
    // immediately when disabled, but then also let it be pushed more 
    disabledTimer = new Timer();
    FollowPathCommand.warmupCommand().schedule();
    this.controlChooser=new ControlChooser();
    
    //ControlManager.testControl();

  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics that you want ran
   * during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic()
  {
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
  public void disabledInit()
  {
    //m_robotContainer.setMotorBrake(true);
    disabledTimer.reset();
    disabledTimer.start();
  }



  @Override
  public void disabledPeriodic()
  {
    if (disabledTimer.hasElapsed(Constants.DrivebaseConstants.WHEEL_LOCK_TIME))
    {
      //m_robotContainer.setMotorBrake(false);
      disabledTimer.stop();
    }
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit()
  {

    controlChooser.cancel();
    //m_robotContainer.setMotorBrake(true);
    //m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null)
    {
      m_autonomousCommand.schedule();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic()
  {
  }

  @Override
  public void teleopInit()
  {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null)
    {
      m_autonomousCommand.cancel();
    }
    controlChooser.restart();
    // m_robotContainer.setDriveMode();
    // m_robotContainer.setMotorBrake(true);
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic()
  {
  }

  @Override
  public void testInit()
  {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
    try
    {
      new SwerveParser(new File(Filesystem.getDeployDirectory(), "swerve"));
    } catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic()
  {
  }




  StructArrayPublisher<Pose3d> algeaPublisher = NetworkTableInstance.getDefault()
  .getStructArrayTopic("algea", Pose3d.struct).publish();

  // StructArrayPublisher<Pose2d> opponentRobotsPublisher = NetworkTableInstance.getDefault()
  // .getStructArrayTopic("opponentRobots", Pose2d.struct).publish();
  // StructArrayPublisher<Pose2d> teamRobotsPublisher = NetworkTableInstance.getDefault()
  // .getStructArrayTopic("teamRobots", Pose2d.struct).publish();
  StructPublisher<Pose2d> opponentPublisher = NetworkTableInstance.getDefault().getStructTopic("René DesCoded", Pose2d.struct).publish();

StructArrayPublisher<Pose3d> coralPublisher = NetworkTableInstance.getDefault()
  .getStructArrayTopic("coral ", Pose3d.struct).publish();

StructPublisher<Pose2d> robotPublisher = NetworkTableInstance.getDefault().getStructTopic("robot", Pose2d.struct).publish();




  // StructArrayPublisher<Pose3d> algaePoses = NetworkTableInstance.getDefault()
  // .getStructArrayTopic("AlgaePoses", Pose3d.struct)
  // .publish();

  /**
   * This function is called once when the robot is first started up.
   */
  @Override
  public void simulationInit(){

    SimulatedArena.getInstance().resetFieldForAuto();
    
    // SimulatedArena.getInstance().addGamePiece(new ReefscapeCoralAlgaeStack(FieldPosits.StaringGamePeices.leftStack.getTranslation()));
    // SimulatedArena.getInstance().addGamePiece(new ReefscapeCoralAlgaeStack(FieldPosits.StaringGamePeices.midStack.getTranslation()));
    // SimulatedArena.getInstance().addGamePiece(new ReefscapeCoralAlgaeStack(FieldPosits.StaringGamePeices.rightStack.getTranslation()));
    SmartDashboard.putBoolean("isSim", true);
    Logger.addDataReceiver(new NT4Publisher());


  }

  /**
   * This function is called periodically whilst in simulation.
   */

@Override
public void simulationPeriodic() {
  SimulatedArena.getInstance().simulationPeriodic();

  algeaPublisher.set(SimulatedArena.getInstance().getGamePiecesArrayByType("Algae"));
  coralPublisher.set(SimulatedArena.getInstance().getGamePiecesArrayByType("Coral"));
  robotPublisher.set(SystemManager.getSwervePose());
  // opponentRobotsPublisher.set(AIRobotInSimulation.getOpponentRobotPoses());
  // teamRobotsPublisher.set(AIRobotInSimulation.getAlliancePartnerRobotPoses());
  if (SystemManager.fakeBot!=null){
    opponentPublisher.set(SystemManager.fakeBot.driveSimulation.getActualPoseInSimulationWorld());
  }
  // Get the positions of the notes (both on the field and in the air)
  // Pose3d[] AlgaePoses = SimulatedArena.getInstance()
  //       .getGamePiecesArrayByType("Note");
  // algaePoses.accept(SimulatedArena.getInstance()
  //       .getGamePiecesByType("Note")
  //       .toArray(Pose3d::new));

  ///SystemManager.feild;

  Logger.recordOutput("FieldSimulation/Algae", 
      SimulatedArena.getInstance().getGamePiecesArrayByType("Algae"));
  Logger.recordOutput("FieldSimulation/Coral", 
      SimulatedArena.getInstance().getGamePiecesArrayByType("Coral"));
    //SimulatedArena.getInstance().simulationPeriodic();
  }

}
