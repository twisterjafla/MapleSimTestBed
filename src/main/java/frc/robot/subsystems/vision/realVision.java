package frc.robot.subsystems.vision;

import java.lang.reflect.Array;
import java.util.ArrayList;

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
    private ArrayList<BooleanArraySubscriber> reefLevelSubscribers;
    private ArrayList<BooleanArraySubscriber> algeaLevelSubscribers;
    



        public realVision() {
            // Default values just in case no values are grabbed
            boolean[][] reefDefaultList = {false, false, false, false};
            boolean[][] algeaDefaultList = {false, false};
            Pose3d robotDefaultPose = new Pose3d();

            NetworkTableInstance inst = NetworkTableInstance.getDefault();
            this.reefLevelSubscribers = new ArrayList<>();
            this.algeaLevelSubscribers = new ArrayList<>();

            // Loops through and add the reef subscriber to the list
            for (int i = 2; i < 5; i++) {
                BooleanArrayTopic reefLevel = inst.getBooleanArrayTopic("ReefLevel" + Integer.toString(i));
                BooleanArraySubscriber reefSubscriber = reefLevel.subscribe(reefDefaultList, PubSubOption.keepDuplicates(true));
                this.reefLevelSubscribers.add(reefSubscriber);
            }

            // Same thing as the reef one
            for (int i = 2; i < 4; i++) {
                BooleanArrayTopic algeaLevel = inst.getBooleanArrayTopic("ReefLevel" + Integer.toString(i));
                BooleanArraySubscriber algeaSubscriber = reefLevel.subscribe();
                this.algeaLevelSubscribers.add(algeaSubscriber);
            }
    
            // Gets the robot's position's subscriber
            StructTopic<Pose3d> robotPoseTopic = inst.getStructTopic("RobotValues", Pose3d.struct);
            this.robotPoseSubscriber = robotPoseTopic.subscribe(robotDefaultPose, PubSubOption.keepDuplicates(true));

        }
        
        @Override
        public Pose3d getPose() {
            return this.robotPoseSubscriber.get();
        }

        @Override
        public boolean[][] getFullReefState() {
            ArrayList<ArrayList<Boolean>> reefArray = {this.reefLevelSubscribers.get(0), this.reefLevelSubscribers.get(1), this.reefLevelSubscribers.get(2)};
            return reefArray;
    
        }

        @Override
        public boolean[][] getAlgeaPosits() {
            ArrayList<ArrayList<Boolean>> algeaArray = {this.algeaLevelSubscribers.get(0), this.algeaLevelSubscribers.get(1), this.algeaLevelSubscribers.get(2)};
            return algeaArray;
        }

        @Override
        public boolean getIsClosed(int row, int level) {
            boolean[][] reefList = getFullReefState();
            return !reefList[row][level];
            
        }

        @Override
        public boolean hasAlgea(int row, int level) {
            boolean[][] algeaList = this.getAlgeaPosits();
            return !algeaList[level][row];

            
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

}
