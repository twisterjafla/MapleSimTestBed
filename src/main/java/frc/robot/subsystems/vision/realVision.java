package frc.robot.subsystems.vision;

import java.util.ArrayList;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.networktables.BooleanArraySubscriber;
import edu.wpi.first.networktables.BooleanArrayTopic;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.PubSubOption;
import edu.wpi.first.networktables.StructSubscriber;
import edu.wpi.first.networktables.StructTopic;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.SystemManager;


public class realVision extends reefIndexerIO implements aprilTagInterface{
    private final StructSubscriber<Pose3d> robotFrontPoseSubscriber;
    private final StructSubscriber<Pose3d> robotBackPoseSubscriber;
    private ArrayList<BooleanArraySubscriber> reefLevelSubscribers;
    private ArrayList<BooleanArraySubscriber> algeaLevelSubscribers;
    



    public realVision() {
        // Default values just in case no values are grabbed
        boolean[] reefDefaultList = {false, false, false, false};
        boolean[] algeaDefaultList = {false, false};
        Pose3d robotDefaultPose = new Pose3d();

        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable visionTable = inst.getTable("Vision");
        this.reefLevelSubscribers = new ArrayList<BooleanArraySubscriber>();
        this.algeaLevelSubscribers = new ArrayList<BooleanArraySubscriber>();

        // Loops through and add the reef subscriber to the list
        for (int i = 2; i < 5; i++) {
            BooleanArrayTopic reefLevel = visionTable.getBooleanArrayTopic("ReefLevel" + Integer.toString(i));
            BooleanArraySubscriber reefSubscriber = reefLevel.subscribe(reefDefaultList, PubSubOption.keepDuplicates(true));
            this.reefLevelSubscribers.add(reefSubscriber);
        }

        // Same thing as the reef one
        for (int i = 2; i < 4; i++) {
            BooleanArrayTopic algeaLevel = visionTable.getBooleanArrayTopic("ReefLevel" + Integer.toString(i));
            BooleanArraySubscriber algeaSubscriber = algeaLevel.subscribe(algeaDefaultList, PubSubOption.keepDuplicates(true));
            this.algeaLevelSubscribers.add(algeaSubscriber);
        }

        // Gets the robot's position's subscriber
        StructTopic<Pose3d> robotFrontPoseTopic = visionTable.getStructTopic("FrontRobotPose", Pose3d.struct);
        this.robotFrontPoseSubscriber = robotFrontPoseTopic.subscribe(robotDefaultPose, PubSubOption.keepDuplicates(true));
        StructTopic<Pose3d> robotBackPoseTopic = visionTable.getStructTopic("BackRobotPose", Pose3d.struct);
        this.robotBackPoseSubscriber = robotBackPoseTopic.subscribe(robotDefaultPose, PubSubOption.keepDuplicates(true));


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
    public Pose3d getBackPose() {
        Pose3d robotBackPose = robotBackPoseSubscriber.get();
        if (robotBackPose == new Pose3d()){
            return null;
        }

        return robotBackPose;
    }

    @Override
    public boolean[][] getFullReefState() {
        boolean[][] reefArray = {this.reefLevelSubscribers.get(0).get(), this.reefLevelSubscribers.get(1).get(), this.reefLevelSubscribers.get(2).get()};
        return reefArray;

    }

    @Override
    public boolean[][] getAlgeaPosits() {
        boolean[][] algeaArray = {this.algeaLevelSubscribers.get(0).get(), this.algeaLevelSubscribers.get(1).get(), this.algeaLevelSubscribers.get(2).get()};
        return algeaArray;
    }



    @Override
    public void freeAlgea(int row, int level) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'freeAlgea'");
    }



}
