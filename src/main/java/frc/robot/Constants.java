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
        public static final int lf = 3;
        public static final int lt = 4;
        public static final int lr = 9;

        // right
        public static final int rf = 7;
        public static final int rt = 5;
        public static final int rr = 8;
    }

    public static final class bucket {
        public static final class solenoid {
            public static final int fwdPort = 0;
            public static final int revPort = 1;

        }
    }

    public static final class intake{
        public static final class solenoid {
            public static final int fwdPort = 2;
            public static final int revPort = 3;

        }
        public static final int motor1 = 2;
        public static final int motor2 = 6;

        public static final double fwdSpeed = 0.4;
        public static final double revSpeed = -0.4;
    }

    public static final class auto{
        public static final double fwdSpeed = 0.6;
        public static final double revSpeed = -0.4;
    }

    public static final int MOVEMENT_JOYSTICK = 0;
    public static final int MANIPULATOR_JOYSTICK = 1;

}
