package frc.robot.SemiAutoRoutines;

import java.util.List;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.semiAutoManager;
import frc.robot.subsystems.DriveBase;

public class testRoutine extends SequentialCommandGroup{
    public testRoutine(DriveBase drive){
        super(
            semiAutoManager.getRamseteCommand(
                    TrajectoryGenerator.generateTrajectory(
                        semiAutoManager.getCoords(),
                        List.of(),
                       Constants.fieldPosits.testPosit, Constants.TrajectoryGeneratorObjects.trajectoryConfigurer))
        );
    }
}
