package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;

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
}
