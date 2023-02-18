// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    // Motor Constants

    //right motors
    public static final int LEFT_FRONT= 2;
    public static final int LEFT_TOP = 3;
    public static final int LEFT_BACK = 4;

    //left motors
    public static final int RIGHT_FRONT=5;
    public static final int RIGHT_TOP = 6;
    public static final int RIGHT_BACK=7;

    //TODO:Change solenoits to port on PCH
    public static final int BUCKET_SOLENOID_1=0;
    public static final int BUCKET_SOLENOID_2=1;
    
    public static final int INTAKE_SOLENOID_1=2;
    public static final int INTAKE_SOLENOID_2=3;


    //TODO: Add motor contants from tuner
    public static final int INTAKE_MOTOR1=8;
    public static final int INTAKE_MOTOR2=9;




    // Motor speed constants
    public static final double INTAKE_SPEED = 0.4;
    public static final double OUTTAKE_SPEED = -0.4;
    public static final double INDEXER_SPEED = 1;
    public static final double REVERSE_INDEXER_SPEED = -1;
    public static final double SHOOTER_SPEED = 0.75;
    public static final double REVERSE_SHOOTER_SPEED = -0.5;

    public static final double WINCH_IN_SPEED = -0.5;
    public static final double WINCH_OUT_SPEED = 1;
    
    public static final double AUTONOMOUS_FORWARD_SPEED = 0.6;

    // TODO: Do these start at 0?
    public static final int ENCODER_PORT_A = 1;
    public static final int ENCODER_PORT_B = 2;
    public static final int ENCODER_PORT_C = 3;
    public static final int ENCODER_PORT_D = 4;
    public static final int ENCODER_PORT_E = 5;
    public static final int ENCODER_PORT_F = 6;
  
    public static final int MOVEMENT_JOYSTICK = 0;
    public static final int MANIPULATOR_JOYSTICK = 1;

    public static final int DPAD_UP = 0;
    public static final int DPAD_DOWN = 180;
    public static final int DPAD_LEFT = 270;
    public static final int DPAD_RIGHT = 90;

}
