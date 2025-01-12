package frc.robot.subsystems.vision;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructArrayPublisher;
import frc.robot.Constants;
import java.util.ArrayList;
import java.util.List;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.targeting.PhotonTrackedTarget;
import edu.wpi.first.math.geometry.Pose3d;

public class aprilTagDetection implements aprilTagInterface {

    @Override
    public Pose3d getPose() {

        // Extracts camera and result from PhotonLib
        // PhotonLib pulls this info down from networktables in a certain way
        PhotonCamera camera = new PhotonCamera(Constants.cameraConstants.APRIL_TAG_CAMERA_NAME);
        PhotonPoseEstimator photonPoseEstimator = new PhotonPoseEstimator(Constants.cameraConstants.FIELD_LAYOUT, 
                                                                          Constants.cameraConstants.POSITION_STRATEGY, 
                                                                          camera, 
                                                                          Constants.cameraConstants.frontAprilTagCameraTrans
                                                                          );
        var result = camera.getLatestResult();

        if (result.hasTargets()) {
            List<PhotonTrackedTarget> targets = result.getTargets();
            List<PhotonTrackedTarget> accurateTargets = new ArrayList<>();

            for (PhotonTrackedTarget target : targets) {
                if (target.getPoseAmbiguity() > Constants.cameraConstants.POSE_AMBIGUITY_TOLERANCE) {
                    continue;
                }
                accurateTargets.add(target);
            }
        }

        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        StructArrayPublisher<Pose3d> positionPublisher = inst.getStructArrayTopic("Robot Position", Pose3d.struct).publish();
        
        var poseEstimate = photonPoseEstimator.update();
        if (poseEstimate.isPresent()) {
            EstimatedRobotPose pose = poseEstimate.get();
            positionPublisher.set(new Pose3d[]{pose.estimatedPose});
            return pose.estimatedPose;
        }
        return new Pose3d();
    }
}
