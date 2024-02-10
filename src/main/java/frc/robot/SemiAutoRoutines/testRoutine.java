package frc.robot.SemiAutoRoutines;

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
      null,
                       Constants.fieldPosits.testPosit, Constants.TrajectoryGeneratorObjects.trajectoryConfigurer))
        );
    }
}
