package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.states.intaking;
import frc.robot.commands.states.scoreL1;
import frc.robot.commands.states.scoreL2;
import frc.robot.commands.states.scoreL3;
import frc.robot.commands.states.scoreL4;
import frc.robot.commands.states.starting;

public class FieldPosits {
    public static class StaringGamePeices{
        public static final Pose2d leftStack= new Pose2d(1.2, 5.8, new Rotation2d());
        public static final Pose2d midStack = new Pose2d(1.2, 4, new Rotation2d());
        public static final Pose2d rightStack = new Pose2d(1.2, 2.2, new Rotation2d());
    }

    /**
     * THESE ARE NOT GRAB POINTS FOR THE ROBOT. THESE POINTS TELL MAPLE SIM WHERE TO SPAWN CORAL AND ARE NOT ACCESABLE BY ROBOT
     */
    public static class coralSpawnPoints{
        public static final Pose3d leftLeft= new Pose3d(1.3, 7.7, 0.95, new Rotation3d());
        public static final Pose3d leftMid= new Pose3d(0.8, 7.3, 0.95, new Rotation3d());
        public static final Pose3d leftRight= new Pose3d(0.4, 7.1, 0.95, new Rotation3d());
        public static final Pose3d rightLeft= new Pose3d(0.4, 1, 0.95, new Rotation3d());
        public static final Pose3d rightMid= new Pose3d(0.8, 0.7, 0.95, new Rotation3d());
        public static final Pose3d rightRight= new Pose3d(1.3, 0.3, 0.95, new Rotation3d());

    }

    // enum poles {
    //     1,
    //     2,
    //     3,
    //     4,
    //     5,
    //     6,
    //     7,
    //     8,
    //     9,
    //     10,
    //     11
    
    // }

    public static enum reefPole{
        A,
        B,
        C,
        D,
        E,
        F,
        G,
        H,
        I,
        J,
        K,
        L
      }
      
    public static enum reefLevel{
        L1,
        L2,
        L3,
        L4
    }


    
}
