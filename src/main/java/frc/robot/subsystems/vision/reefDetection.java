package frc.robot.subsystems.vision;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.networktables.BooleanArraySubscriber;
import edu.wpi.first.networktables.BooleanArrayTopic;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.PubSubOption;
import edu.wpi.first.networktables.StructSubscriber;
import edu.wpi.first.networktables.StructTopic;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class reefDetection extends SubsystemBase implements aprilTagInterface {
    private final StructSubscriber<Pose3d> robotPoseSubscriber;
    private final BooleanArraySubscriber reefSubscriber;
    
        public reefDetection() {
            boolean[] reefDefaultList = {false};
            Pose3d robotDefaultPose = new Pose3d();
    
            NetworkTableInstance inst = NetworkTableInstance.getDefault();
            StructTopic<Pose3d> robotPose3d = inst.getStructTopic("RobotPose", Pose3d.struct);
            this.robotPoseSubscriber = robotPose3d.subscribe(robotDefaultPose, PubSubOption.keepDuplicates(true));
            BooleanArrayTopic reefList = inst.getBooleanArrayTopic("ReefValues");
            this.reefSubscriber = reefList.subscribe(reefDefaultList, PubSubOption.keepDuplicates(true));

        }
    
        public Pose3d getPose() {
            return this.robotPoseSubscriber.get();
        }

        public boolean[] getReef() {
            return this.reefSubscriber.get();
        }
}
