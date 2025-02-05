// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import com.pathplanner.lib.config.PIDConstants;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.PubSubOption;
import edu.wpi.first.networktables.PubSubOptions;
import edu.wpi.first.wpilibj.RobotBase;
import swervelib.math.Matter;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean constants. This
 * class should not be used for any other purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants
{

    private static double inchesToMeters(double inches){
        return inches*0.0254;
    }


  public static final double LOOP_TIME  = 0.02; //s, 20ms + 110ms sprk max velocity lag


  public static final class driveConstants {
    public static final double driveFrictionVoltage = 0.25;
    public static final double steerFrictionVoltage = 0.25;
    public static final double totalWidth=inchesToMeters(27);
    public static final double totalHeight=inchesToMeters(27);
    public static final double chassisWidth=inchesToMeters(27);
    public static final double chassisHeight=inchesToMeters(27);
    public static final double steerInertia = 0.025;
    public static final double wheelRadusInMeters = inchesToMeters(2);
    public static final double robotMass = 50;//(33) * 0.453592; // 32lbs * kg per pound
    public static final double maxSpeed  = 4.6;
    public static final Matter chassis    = new Matter(new Translation3d(0, 0, Units.inchesToMeters(3)), robotMass);
    public static final double MOI = 6.884;
 

    public static enum driveType {
        GENERIC,
        CTRE_ON_RIO,
        CTRE_ON_CANIVORE
    }

  }
  
      // Maximum speed of the robot in meters per second, used to limit acceleration.

  public static final class AutonConstants
  {

    // public static final PIDConstants TRANSLATION_PID = new PIDConstants(10, 0, 0.5);
    public static final PIDConstants TRANSLATION_PID = new PIDConstants(10, 0, 0);

    public static final PIDConstants ANGLE_PID       = new PIDConstants(5, 0, 0);
    public static final double colisionSpeed = 2;

    public static final double widthOfMapMeters=8;
    public static final double heightOfMapMeters=8.9;
    public static final double bonusScore=1;

    public static final double autoDriveTolerence  = 0.1;
    public static final double distanceWithinPathplannerDontWork = 0.5;

    public static final double humanPlayerBeingBad = 0.5;
    public static final Transform2d intakeCoralOffset = new Transform2d(-0.5, 0, new Rotation2d());
  
  }

  public static final class DrivebaseConstants
  {

    // Hold time on motor brakes when disabled
    public static final double WHEEL_LOCK_TIME = 10; // seconds
  }

  public static class OperatorConstants
  {

    // Joystick Deadband
    public static final double LEFT_X_DEADBAND  = 0.1;
    public static final double LEFT_Y_DEADBAND  = 0.1;
    public static final double RIGHT_X_DEADBAND = 0.1;
    public static final double TURN_CONSTANT    = 6;
    public static final int[] supportedPOV={0,90,180,270};
  }
  public static class sim{
    public static double targetTolerence =0.2;
    public static double l4CoralDropCheatX=0.08;
    public static double l4CoralDropCheatY=0.1;
    //DriveTrainSimulationConfig simConfig= new DriveTrainSimulationConfig(55, 1, 0.05, 1, 0.05, null, null);
  }

  public static class simConfigs{
    public static final boolean driveShouldBeSim=false||!RobotBase.isReal();
    public static final boolean intakeShouldBeSim=true||!RobotBase.isReal();
    public static final boolean aprilTagShouldBeSim=true||!RobotBase.isReal();
    public static final boolean wristShouldBeSim=true||!RobotBase.isReal();
    public static final boolean elevatorShouldBeSim=true||!RobotBase.isReal();
    public static final boolean reefIndexerShouldBeSim=true||!RobotBase.isReal();
    public static final boolean lidarShouldBeSim=true||!RobotBase.isReal();
    public static final boolean robotCanBeSimOnReal=true;

  }
  public static class controllerIDs{
    public static final int commandXboxController1ID=0;
    public static final int commandXboxController2ID=1;
  }

  public static class intakeConstants{
    public static final double intakeSpeed=0.3;
    public static final double outtakeSpeed=-0.3;
    public static final double intakeLength=0.245745;
    public static final int RightIntake = 50;
    public static final int LeftIntake = 50;
    public static final double coralFromWristLen = 0.0715772;
    public static final double coralLenght = 0.3;
    public static final double coralWidth = 0.11;
  }

  public static class elevatorConstants{
    public static final double l4EncoderVal = 1.42;
    public static final double l3EncoderVal = 0.702381;
    public static final double l2EncoderVal = 0.414528;
    public static final double l1EncoderVal = 0;
    public static final double encoderToMeters =1;
    public static final double maxHeight = l4EncoderVal;
    public static final Rotation2d angle = Rotation2d.fromDegrees(70);
    public static final Translation3d fromRobotCenter = new Translation3d(0.0584454, 0, 0.583565 );
    public static final double intakePosit = 0;
    public static final double tolerence = 0.05;
    public static final double speedForSim =0.03;
    public static final double compressedLen = 0.889;
    
  }

  public static class wristConstants{
    public static final double l4EncoderVal = -15;
    public static final double l3EncoderVal = 0;
    public static final double l2EncoderVal = 0;
    public static final double l1EncoderVal = 0;
    public static final double restingPosit = 0;
    public static final double degreesPerEncoderTick=360;
    public static final Rotation2d minDegrees = new Rotation2d();
    public static final Rotation2d maxDegrees = new Rotation2d();
    public static final double intakePosit = 0;
    public static final double tolerence = 5;
    public static final double speedForSim=5;
  
  }


  public static class cameraConstants{
    public static Transform3d frontAprilTagCameraTrans = new Transform3d();

  }

// Since AutoBuilder is configured, we can use it to build pathfinding commands

}
