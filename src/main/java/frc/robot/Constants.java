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
        
        public static final int leftFrontMotor = 5;
        public static final int leftBackMotor = 9;

        // right
        public static final int rightFrontMotor = 1;
        public static final int rightBackMotor = 2;
        public static final double gearRatio=8.5;
        
        
        public static double rotationSpeedRatio= 0.6;
        public static final double driveSpeedRatio= 1;

        public static double rampspeed= .25;

        public static final double encoderToMetersRatio= robotStats.wheelRadius*Math.PI*2/(robotStats.gearRatio);
        public static final double encoderToWheelRatio = 1/(robotStats.gearRatio);

        public static final DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(Constants.robotStats.trackWidth);
        public static final double ksVolts = 1.22;
        public static final double kvVoltSecondsPerMeter = 2.402;

    }


    public static final class elevator {
        public static final int motorPortLeft = 3;
        public static final int motorPortRight = 4;

        public static final int topLimitSwitch = 1;
        public static final int bottomLimitSwitch = 0;

        public static final double elevatorUpSpeed = 0.6;
        public static final double elevatorDownSpeed = -0.3;
        public static final double elevatorStayAtTopSpeed = 0.03;
    }
  
    // Changing Solenoid Values idk 50/50 this'll work
    public static final class pneumatics{
        public static final int hubID = 8;

        public static final int solenoidPortA=8;
        public static final int solenoidPortB=9;
    }                             


    public static final class robotStats{
        public static final double trackWidth=0.3937;
        public static final double wheelRadius=0.0762;
        public static final double gearRatio=8.5;
        public static final double SemiAutoRoutineWaitTimes = 0.25;
    }

    public static final class intake {
        public static final int counterCap = 25; // this number is untested, it should run for 0.5 seconds after note is taken
        public static final int beamBreakPort = 2;

        public static final class intakeSpeeds {
            public static final double intakeSpeed = 0.3;
            public static final double outakeSpeed = -1;
            // public static final int intakeRaiseSpeed = 1;
        }

        public static final class intakeNote {
            public static final int intakeMotorPortLeft = 7;
            public static final int intakeMotorPortRight = 11;
        }
        
        // public static final class raisingIntake {
        //     public static final int raisingMotorPort = 0;

        //     public static final int topLimitSwitchPort = 0;
        //     public static final int bottomLimitSwitchPort = 0;
        // }
    }



    public static final class speakerShooter {
        public static int RevTimeCountInTicks = 0;

        public static final class ports {
            public static final int topMotorPort = 0;
            public static final int bottomMotorPort = 0;
            public static final int beamBreakPort = 0;
        }

        public static final class motorSpeeds {
            public static final int topMotorSpeed = 0;
            public static final int bottomMotorSpeed = 0; // slower than top speed
        }
    }

    public static final class climbingArm{
        public static final int motorPort = 0;

        public static final int limitSwitchRight = 0;
        public static final int limitSwitchLeft = 0;

        public static final int armDownSpeed = 0;
    }

    public static final class auto{
        public static final double fwdSpeed = 0.5;
        public static final double revSpeed = -0.4;
        public static final double kMaxAccelerationMetersPerSecondSquared = 1;
        public static final double kMaxSpeedMetersPerSecond = 2;
       
     

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

    public static final class wrist {
        

        public static final class ports {
            public static final int motorPort = 10;
            public static final int encoderLimitSwitch = 20;
        }

        public static final class positions{
            public static final double up = 90;
            public static final double intake = 0;
        }

        public static final class motorSpeeds {
            public static final double motorUp = 0.3;
            public static final double motorDown = -0.6;
        }

        public static final double resetPosition = 0;

        public static final double tolerance = 1;
        public static final double kp = 0.06;
        public static final double ki = 0;
        public static final double kd = 0.03;
        public static final double gearRatio=40;
    }

    public static final int MOVEMENT_JOYSTICK = 0;
    public static final int MANIPULATOR_JOYSTICK = 1;
    public static int blinkinPort=0;
    public static final class semiAuto{

        public static final double goalRingDistance = 0.18;
        public static final double outerRingDistance = 1.5;

        public static final class turn{
            public static final double finalKp = 1;
            public static final double finalKi = 0.9;
            public static final double finalKd= 0.4;
            
            public static final double driveTurnKp = 0.4;
            public static final double driveTurnKi = 0;
            public static final double driveTurnKd= 0.3;
            

            public static final double finalTolerence = 0.0175;
            public static final double driveTolerence = 0.0524;

        }
        public static final class straight{
            public static final double innerKp = 1;
            public static final double innerKi = 0.05;
            public static final double innerKd= 0.4;

            public static final double outerKp=0.2;
            public static final double outerKi=0;
            public static final double outerKd=0.5;



            
        }
    }


    public static final class fieldPosits{
        public static final Pose2d leftStart = new Pose2d(0, 0.0, new Rotation2d(0.0));
        public static final Pose2d ampScore = null;
        public static final Pose2d testPosit = new Pose2d(0, 0.0, new Rotation2d(Math.toRadians(90)));
    }

}
