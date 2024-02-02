// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.List;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    // drive Constants
    public static final class drive {

        // f: front
        // t: top
        // r: rear

        // left
        public static final int lf = 3;
        public static final int lt = 4;
        public static final int lr = 9;

        // right
        public static final int rf = 7;
        public static final int rt = 5;
        public static final int rr = 8;
        
        
        
        public static double rotationSpeedRatio= 0.6;
        public static final double driveSpeedRatio= 1;

        public static double rampspeed= .25;

        public static final DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(Constants.robotStats.trackWidth);
        public static final double ksVolts = 0;
        public static final double kvVoltSecondsPerMeter = 0;
        public static final double kaVoltSecondsSquaredPerMeter = 0;
        public static final double kPDriveVel = 0;

    }

    // Changing Solenoid Values idk 50/50 this'll work
    public static final class bucket {
        public static final class solenoid {
            public static final int fwdPort = 6;
            public static final int revPort = 7;

        }
    }

    public static final class robotStats{
        public static final double trackWidth=0.3937;
        public static final double wheelRadius=0.0762;
        public static final double gearRatio=8.5;
    }

    public static final class intake{
        public static final class solenoid {
            public static final int fwdPort = 4;
            public static final int revPort = 5;

        }
        public static final int motor1 = 6;
        public static final int motor2 = 11;
        
        //reversed
        public static final double fwdSpeed = .5;
        public static final double revSpeed = -.2;
    }

    public static final class auto{
        public static final double fwdSpeed = 0.5;
        public static final double revSpeed = -0.4;
        public static final double kMaxAccelerationMetersPerSecondSquared = 0;
        public static final double kMaxSpeedMetersPerSecond = 0;
       
     

        public static final class balancePID{
            public static final double kP = 0.06;
            public static final double kI = 0;
            public static final double kD = 0.05;
            public static final double outputMax = 1;
            public static final double outputMin = -1;

            public static final double positionTolerance = 2;
            public static final double velocityTolerance = 2;
        }
        public static final class straightPID{
            public static final double kp = 0.09;
            public static final double ki = 0;
            public static final double kd = 0.1;

            public static final double positionTolerance = 1;
        }
    }
    public static final class Midi{
        public static final String[] buttonNames={
            "bankButton1", 
            "bankButton2", 
            "slider1", 
            "slider2", 
            "slider3", 
            "slider4", 
            "slider5", 
            "slider6", 
            "slider7", 
            "slider8", 
            "slider9", 
            "dail1", 
            "dail2", 
            "dail3", 
            "dail4", 
            "dail5", 
            "dail6", 
            "dail7", 
            "dail8", 
            "dail9", 
            "button1",
            "button2",
            "button3",
            "button4",
            "button5",
            "button6",
            "button7",
            "button8",
            "button9",
            "record",
            "pause",
            "play",
            "rewindLeft",
            "rewindRight",
            "replay",
            "sliderAB",
            "rightSilverDial",
            "leftSilverDial"};
    }

    public static final int MOVEMENT_JOYSTICK = 0;
    public static final int MANIPULATOR_JOYSTICK = 1;
    public static final int OneJoystick=2;
    public static int blinkinPort=0;

    public static final class fieldPosits{
        public static final Pose2d leftStart = new Pose2d(7.1, 0.0, new Rotation2d(0.0));
    }


    private static final class TrajectoryGeneratorObjects{
        public static final DifferentialDriveVoltageConstraint TrajectoryVoltageConstraint =
        new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(
                Constants.drive.ksVolts,
                Constants.drive.kvVoltSecondsPerMeter,
                Constants.drive.kaVoltSecondsSquaredPerMeter),
            Constants.drive.kinematics,
            10);


        public static TrajectoryConfig trajectoryConfigurer =
        new TrajectoryConfig(
                Constants.auto.kMaxSpeedMetersPerSecond,
                Constants.auto.kMaxAccelerationMetersPerSecondSquared)
            // Add kinematics to ensure max speed is actually obeyed
            .setKinematics(Constants.drive.kinematics)
            // Apply the voltage constraint
            .addConstraint(TrajectoryVoltageConstraint);

    }

    public static final class Trajectorys{

        public static Trajectory exampleTrajectory =
        TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(0, 0, new Rotation2d(0)),
            // Pass through these two interior waypoints, making an 's' curve path
            List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
            // End 3 meters straight ahead of where we started, facing forward
            new Pose2d(3, 0, new Rotation2d(0)),
            // Pass config
            TrajectoryGeneratorObjects.trajectoryConfigurer);

            
    }

}
