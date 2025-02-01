package frc.robot.subsystems.vision;

import java.lang.reflect.Array;

import com.google.flatbuffers.Struct;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.networktables.BooleanArraySubscriber;
import edu.wpi.first.networktables.BooleanArrayTopic;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.PubSubOption;
import edu.wpi.first.networktables.StructArrayTopic;
import edu.wpi.first.networktables.StructSubscriber;
import edu.wpi.first.networktables.StructTopic;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class realVision extends SubsystemBase implements aprilTagInterface, reefIndexerInterface {
    private final StructSubscriber<Pose3d> robotPoseSubscriber;
    //private final BooleanArraySubscriber reefSubscriber;
    
        public realVision() {
            boolean[][] reefDefaultList = {{false}, {false}};
            Pose3d robotDefaultPose = new Pose3d();
    
            NetworkTableInstance inst = NetworkTableInstance.getDefault();
            StructTopic<Pose3d> robotPoseTopic = inst.getStructTopic("RobotValues", Pose3d.struct);
            this.robotPoseSubscriber = robotPoseTopic.subscribe(robotDefaultPose, PubSubOption.keepDuplicates(true));
            //StructTopic<Array> reefTopic = inst.getStructTopic("ReefValues");
            //this.reefSubscriber = reefTopic.subscribe(reefDefaultList, PubSubOption.keepDuplicates(true));

        }
    
        public Pose3d getPose() {
            return this.robotPoseSubscriber.get();
        }

        @Override
        public boolean[][] getFullReefState() {
            //return this.reefSubscriber.get();
            return null;
        }

        @Override
        public boolean getIsClosed(int row, int level) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getIsClosed'");
        }

        @Override
        public int getHighestLevelForRow(int row) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getHighestLevelForRow'");
        }

        @Override
        public boolean hasAlgea(int row, int level) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'hasAlgea'");
        }

        @Override
        public boolean[][] getAlgeaPosits() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getAlgeaPosits'");
        }
}
