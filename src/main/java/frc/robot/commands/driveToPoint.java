package frc.robot.commands;

import java.util.List;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants;
import frc.robot.semiAutoManager;
import frc.robot.subsystems.DriveBase;

public class driveToPoint {
    // Create a voltage constraint to ensure we don't accelerate too fast
    public driveToPoint(DriveBase drive){
        var autoVoltageConstraint =
        new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(
                Constants.drive.ksVolts,
                Constants.drive.kvVoltSecondsPerMeter,
                Constants.drive.kaVoltSecondsSquaredPerMeter),
            Constants.drive.kinematics,
            10);

    // Create config for trajectory
    TrajectoryConfig config =
        new TrajectoryConfig(
                Constants.auto.kMaxSpeedMetersPerSecond,
                Constants.auto.kMaxAccelerationMetersPerSecondSquared)
            // Add kinematics to ensure max speed is actually obeyed
            .setKinematics(Constants.drive.kinematics)
            // Apply the voltage constraint
            .addConstraint(autoVoltageConstraint);

    // An example trajectory to follow. All units in meters.
    Trajectory exampleTrajectory =
        TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(0, 0, new Rotation2d(0)),
            // Pass through these two interior waypoints, making an 's' curve path
            List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
            // End 3 meters straight ahead of where we started, facing forward
            new Pose2d(3, 0, new Rotation2d(0)),
            // Pass config
            config);

    RamseteCommand ramseteCommand =
        new RamseteCommand(
            exampleTrajectory,
            semiAutoManager::getPose2D,
            new RamseteController(),
            new SimpleMotorFeedforward(
                Constants.drive.ksVolts,
                Constants.drive.kvVoltSecondsPerMeter,
                Constants.drive.kaVoltSecondsSquaredPerMeter),
            Constants.drive.kinematics,
            drive::getWheelSpeeds,
            new PIDController(Constants.drive.kPDriveVel, 0, 0),
            new PIDController(Constants.drive.kPDriveVel, 0, 0),
            // RamseteCommand passes volts to the callback
            drive::tankDriveVolts,
            drive);

    // Reset odometry to the initial pose of the trajectory, run path following
    // command, then stop at the end.
    // return Commands.runOnce(() -> m_robotDrive.resetOdometry(exampleTrajectory.getInitialPose()))
    //     .andThen(ramseteCommand)
    //     .andThen(Commands.runOnce(() -> m_robotDrive.tankDriveVolts(0, 0)));}
    }
}


