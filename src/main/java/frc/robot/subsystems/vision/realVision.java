package frc.robot.subsystems.vision;

import java.util.ArrayList;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.networktables.BooleanArraySubscriber;
import edu.wpi.first.networktables.BooleanArrayTopic;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.DoubleTopic;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.PubSubOption;
import edu.wpi.first.networktables.StructSubscriber;
import edu.wpi.first.networktables.StructTopic;


public class realVision extends reefIndexerIO implements aprilTagInterface{
    
    private ArrayList<BooleanArraySubscriber> reefLevelSubscribers;
    private ArrayList<BooleanArraySubscriber> algeaLevelSubscribers;
    private final StructSubscriber<Pose3d> robotFrontPoseSubscriber;
    private final StructSubscriber<Pose3d> robotBackPoseSubscriber;
    private final DoubleSubscriber robotFrontTimestampSubscriber;
    private final DoubleSubscriber robotBackTimestampSubscriber;    



    public realVision() {
        // Default values just in case no values are grabbed
        boolean[] reefDefaultList = {false, false, false, false};
        boolean[] algeaDefaultList = {false, false};
        double timestampDefault = 0.0;
        Pose3d robotDefaultPose = new Pose3d();

        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable visionTable = inst.getTable("Vision");
        NetworkTable reefPositionTable = visionTable.getSubTable("ReefPositions");
        NetworkTable robotPositionTable = visionTable.getSubTable("RobotPosition");

        reefLevelSubscribers = new ArrayList<BooleanArraySubscriber>();
        algeaLevelSubscribers = new ArrayList<BooleanArraySubscriber>();

        // Loops through and add the reef subscriber to the list
        for (int i = 1; i <= 4; i++) {
            BooleanArrayTopic reefLevel = reefPositionTable.getBooleanArrayTopic("CoralL" + Integer.toString(i));
            BooleanArraySubscriber reefSubscriber = reefLevel.subscribe(reefDefaultList, PubSubOption.keepDuplicates(true));
            reefLevelSubscribers.add(reefSubscriber);
        }

        // Same thing as the reef one
        for (int i = 1; i <= 2; i++) {
            BooleanArrayTopic algeaLevel = reefPositionTable.getBooleanArrayTopic("Algae" + Integer.toString(i));
            BooleanArraySubscriber algeaSubscriber = algeaLevel.subscribe(algeaDefaultList, PubSubOption.keepDuplicates(true));
            algeaLevelSubscribers.add(algeaSubscriber);
        }

        // Gets the robot's position's subscriber
        StructTopic<Pose3d> robotFrontPoseTopic = visionTable.getStructTopic("FrontRobotPose", Pose3d.struct);
        robotFrontPoseSubscriber = robotFrontPoseTopic.subscribe(robotDefaultPose, PubSubOption.keepDuplicates(true));
        StructTopic<Pose3d> robotBackPoseTopic = visionTable.getStructTopic("BackRobotPose", Pose3d.struct);
        robotBackPoseSubscriber = robotBackPoseTopic.subscribe(robotDefaultPose, PubSubOption.keepDuplicates(true));

        // Gets the timestamp each position was published at
        DoubleTopic robotFrontTimestampTopic = robotPositionTable.getDoubleTopic("FrontPoseTimestamp");
        robotFrontTimestampSubscriber = robotFrontTimestampTopic.subscribe(timestampDefault, PubSubOption.keepDuplicates(true));
        DoubleTopic robotBackTimestampTopic = robotPositionTable.getDoubleTopic("BackPoseTimestamp");
        robotBackTimestampSubscriber = robotBackTimestampTopic.subscribe(timestampDefault, PubSubOption.keepDuplicates(true));



    }
    
    @Override
    public Pose3d getFrontPose() {
        Pose3d robotFrontPose = robotFrontPoseSubscriber.get();
        if (robotFrontPose == new Pose3d()){
            return null;
        }

        return robotFrontPose;
    }

    @Override
    public Double getFrontTimestamp() {
        return robotFrontTimestampSubscriber.get();
    }

    @Override
    public Double getBackTimestamp() {
        return robotBackTimestampSubscriber.get();
    }

    @Override
    public Pose3d getBackPose() {
        Pose3d robotBackPose = robotBackPoseSubscriber.get();
        if (robotBackPose == new Pose3d()){
            return null;
        }

        return robotBackPose;
    }

    @Override
    public boolean[][] getFullReefState() {
        boolean[][] reefArray = {reefLevelSubscribers.get(0).get(), reefLevelSubscribers.get(1).get(), reefLevelSubscribers.get(2).get(), reefLevelSubscribers.get(3).get()};
        return reefArray;

    }

    @Override
    public boolean[][] getAlgeaPosits() {
        boolean[][] algeaArray = {algeaLevelSubscribers.get(0).get(), algeaLevelSubscribers.get(1).get()};
        return algeaArray;
    }



    @Override
    public void freeAlgea(int row, int level) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'freeAlgea'");
    }



}
