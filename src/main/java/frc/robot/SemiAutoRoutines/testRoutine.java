package frc.robot.SemiAutoRoutines;

import java.util.List;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.semiAutoManager;
import frc.robot.commands.testDriveStraight;
import frc.robot.subsystems.DriveBase;

public class testRoutine extends SequentialCommandGroup{
    public testRoutine(DriveBase drive){
        super(
            semiAutoManager.getRamseteCommand(
                TrajectoryGenerator.generateTrajectory(
                    // Start at the origin facing the +X direction
                    new Pose2d(0, 0, new Rotation2d(0)),
                    // Pass through these two interior waypoints, making an 's' curve path
                    List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
                    // End 3 meters straight ahead of where we started, facing forward
                    new Pose2d(3, 0, new Rotation2d(0)),
                    // Pass config
                    Constants.TrajectoryGeneratorObjects.trajectoryConfigurer))
        );
    }
}
