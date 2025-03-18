package frc.robot.subsystems.vision;

import java.util.ArrayList;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.networktables.BooleanArrayPublisher;
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
    
    private ArrayList<BooleanArraySubscriber> coralLevelSubscribers;
    private ArrayList<BooleanArraySubscriber> algeaLevelSubscribers;
    private ArrayList<BooleanArrayPublisher> algaeLevelPublishers;
    private final StructSubscriber<Pose3d> robotFrontPoseSubscriber;
    private final StructSubscriber<Pose3d> robotBackPoseSubscriber;
    private final DoubleSubscriber robotFrontTimestampSubscriber;
    private final DoubleSubscriber robotBackTimestampSubscriber;    



    public realVision() {
        // Default values just in case no values are grabbed
        boolean[] reefDefaultList = {false, false, false, false, false, false, false, false, false, false, false, false};
        boolean[] algeaDefaultList = {false, false, false, false, false, false};
        double timestampDefault = 0.0;
        Pose3d robotDefaultPose = new Pose3d();

        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable visionTable = inst.getTable("Vision");
        NetworkTable coralPositionTable = visionTable.getSubTable("CoralPositions");
        NetworkTable algaecoralPositionTable = visionTable.getSubTable("AlgaePositions");
        NetworkTable robotPositionTable = visionTable.getSubTable("RobotPosition");

        coralLevelSubscribers = new ArrayList<BooleanArraySubscriber>();
        algeaLevelSubscribers = new ArrayList<BooleanArraySubscriber>();

        algaeLevelPublishers = new ArrayList<BooleanArrayPublisher>();

        // Loops through and add the coral subscriber to the list
        for (int i = 1; i <= 4; i++) {
            BooleanArrayTopic coralLevel = reefPositionTable.getBooleanArrayTopic("CoralL" + Integer.toString(i));
            BooleanArraySubscriber reefSubscriber = coralLevel.subscribe(reefDefaultList, PubSubOption.keepDuplicates(true));
            coralLevelSubscribers.add(reefSubscriber);
        }

        // Same thing as the coral one, except does this with publishers as well
        for (int i = 1; i <= 2; i++) {
            BooleanArrayTopic algeaLevel = reefPositionTable.getBooleanArrayTopic("Algae" + Integer.toString(i));
            BooleanArrayPublisher algaePublisher = algeaLevel.publish(PubSubOption.keepDuplicates(true));
            BooleanArraySubscriber algeaSubscriber = algeaLevel.subscribe(algeaDefaultList, PubSubOption.keepDuplicates(true));
            algaeLevelPublishers.add(algaePublisher);
            algeaLevelSubscribers.add(algeaSubscriber);
        }

        // Gets the robot's position's subscriber
        StructTopic<Pose3d> robotFrontPoseTopic = visionTable.getStructTopic("FrontRobotPose", Pose3d.struct);
        robotFrontPoseSubscriber = robotFrontPoseTopic.subscribe(robotDefaultPose, PubSubOption.keepDuplicates(true));
        StructTopic<Pose3d> robotBackPoseTopic = visionTable.getStructTopic("BackRobotPose", Pose3d.struct);
        robotBackPoseSubscriber = robotBackPoseTopic.subscribe(robotDefaultPose, PubSubOption.keepDuplicates(true));

        // Gets the subscriber for the timestamp each position was published at
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
        boolean[][] reefArray = {coralLevelSubscribers.get(0).get(), coralLevelSubscribers.get(1).get(), coralLevelSubscribers.get(2).get(), coralLevelSubscribers.get(3).get()};
        return reefArray;

    }

    @Override
    public boolean[][] getAlgeaPosits() {
        boolean[][] algeaArray = {algeaLevelSubscribers.get(0).get(), algeaLevelSubscribers.get(1).get()};
        return algeaArray;
    }



    @Override
    public void freeAlgea(int row, int level) {
        BooleanArrayPublisher freeAlgaePublisher = algaeLevelPublishers.get(level);
        BooleanArraySubscriber freeAlgaeSubscriber = algeaLevelSubscribers.get(level);
        boolean[] algaeRowValues = freeAlgaeSubscriber.get();
        
        algaeRowValues[row] = true;
        freeAlgaePublisher.set(algaeRowValues);

    }
}
