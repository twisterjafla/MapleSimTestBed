// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.util.PIDConstants;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
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


  public static final double LOOP_TIME  = 0.03; //s, 20ms + 110ms sprk max velocity lag

  public static final class driveConstants {
    public static final double driveFrictionVoltage = 0.25;
    public static final double steerFrictionVoltage = 0.25;
    public static final double totalWidth=inchesToMeters(27);
    public static final double totalHeight=inchesToMeters(27);
    public static final double chassisWidth=inchesToMeters(27);
    public static final double chassisHeight=inchesToMeters(27);
    public static final double steerInertia = 0.025;
    public static final double wheelRadusInMeters = inchesToMeters(3.75);
    public static final double robotMass = (148 - 20.3) * 0.453592; // 32lbs * kg per pound
    public static final double maxSpeed  = Units.feetToMeters(14.5);
    public static final Matter chassis    = new Matter(new Translation3d(0, 0, Units.inchesToMeters(8)), robotMass);

    public static enum driveType {
        GENERIC,
        CTRE_ON_RIO,
        CTRE_ON_CANIVORE
    }

  }
  
      // Maximum speed of the robot in meters per second, used to limit acceleration.

  public static final class AutonConstants
  {

    public static final PIDConstants TRANSLATION_PID = new PIDConstants(0.7, 0, 0);
    public static final PIDConstants ANGLE_PID       = new PIDConstants(0.4, 0, 0.01);
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
    public static final int mainControllerPort =0;
    public static final int[] supportedPOV={0,90,180,270};
  }
  public static class sim{
    public static final double maxSpeed=4;
    public static final double maxExel=2;
  }

// Since AutoBuilder is configured, we can use it to build pathfinding commands

}
