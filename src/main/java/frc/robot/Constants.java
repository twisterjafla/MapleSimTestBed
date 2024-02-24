// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

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
        public static final int leftFrontMotor = 5;
        public static final int leftBackMotor = 9;

        // right
        public static final int rightFrontMotor = 1;
        public static final int rightBackMotor = 2;
        public static final double gearRatio=8.5;
        
        
        public static double rotationSpeedRatio= 0.6;
        public static final double driveSpeedRatio= 1;

        public static double rampspeed= .25;



    }

    public static final class elevator {
        public static final int motorPortLeft = 3;
        public static final int motorPortRight = 4;

        public static final int topLimitSwitch = 0;
        public static final int bottomLimitSwitch = 1;

        public static final double elevatorUpSpeed = 0.1;
        public static final double elevatorDownSpeed = -0.3;
        public static final double elevatorStayAtTopSpeed = 0.03;
    }
  
    // Changing Solenoid Values idk 50/50 this'll work
    public static final class pneumatics{
        public static final int hubID = 8;

        public static final int solenoidPortA=8;
        public static final int solenoidPortB=9
        ;
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
        public static final double wheelRadius=3;
        public static final double TicksPerRotation= 4096;

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

    public static final class intake {
        public static final int counterCap = 25; // this number is untested, it should run for 0.5 seconds after note is taken
        public static final int beamBreakPort = 2;

        public static final class intakeSpeeds {
            public static final double intakeSpeed = 0.6;
            public static final double outakeSpeed = -0.6;
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

    public static final class wrist {

        public static final class ports {
            public static final int motorPort = 10;
            public static final int encoderLimitSwitch = 20;
        }

        public static final class posits{
            public static final double scorePosit=8.16;
            public static final double intakePosit=10.43;
        }

        public static final class motorSpeeds {
            public static final double motorUp = 0.5;
            public static final double motorDown = -0.5;
        }

        public static final double resetPosition = 0;
        public static final double tolerance = 1;
        public static final double kp = 0.06;
        public static final double ki = 0;
        public static final double kd = 0.05;
    }

    public static final int MOVEMENT_JOYSTICK = 0;
    public static final int MANIPULATOR_JOYSTICK = 1;
    public static final int OneJoystick=2;
    public static double driveSpeedRatio;
}
