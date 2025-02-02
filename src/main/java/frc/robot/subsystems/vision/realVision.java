package frc.robot.subsystems.vision;

import java.lang.reflect.Array;

import com.google.flatbuffers.Struct;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.networktables.BooleanArrayEntry;
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

    private final BooleanArraySubscriber reefSubscriber;
    private final StructSubscriber algeaSubscriber;
    
        public realVision() {
            boolean[][] reefDefaultList = {{false}, {false}};
            boolean[][] algeaDefaultList = {{false}, {false}};
            Pose3d robotDefaultPose = new Pose3d();
    
            NetworkTableInstance inst = NetworkTableInstance.getDefault();
            StructTopic<Pose3d> robotPoseTopic = inst.getStructTopic("RobotValues", Pose3d.struct);
            this.robotPoseSubscriber = robotPoseTopic.subscribe(robotDefaultPose, PubSubOption.keepDuplicates(true));
            //StructTopic<Array> reefTopic = inst.getStructTopic("ReefValues");
            //this.reefSubscriber = reefTopic.subscribe(reefDefaultList, PubSubOption.keepDuplicates(true));
            StructArrayTopic<Array> reefTopic = inst.getStructTopic("ReefValues", StructTopic<Array>);
            this.reefSubscriber = reefTopic.subscribe(reefDefaultList, PubSubOption.keepDuplicates(true));
            StructTopic<Array> algeaTopic = inst.getStructTopic("AlgeaValues", StructTopic<Array>);
            this.algeaSubscriber = algeaTopic.subscribe(algeaDefaultList, PubSubOption.keepDuplicates(true));
            

        }
        
        @Override
        public Pose3d getPose() {
            return this.robotPoseSubscriber.get();
        }

        @Override
        public boolean[][] getFullReefState() {
            return this.reefSubscriber.get();
    
        }

        @Override
        public boolean[][] getAlgeaPosits() {
            return algeaSubscriber.get();
        }

        @Override
        public boolean getIsClosed(int row, int level) {
            boolean[][] reefList = getFullReefState();
            return !reefList[row][level];
            
        }

        @Override
        public int getHighestLevelForRow(int row) {
            if (!getIsClosed(row, 4)){
                return 4;
            }
            if (!getIsClosed(row, 3)){
                return 3;
            }
            if (!getIsClosed(row, 2)){
                return 2;
            }
            return 1;
        }

        @Override
        public boolean hasAlgea(int row, int level) {
            boolean[][] algeaList = this.getAlgeaPosits();
            return !algeaList[level][row];

            
        }


}
