package frc.robot.subsystems.vision;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj2.command.Subsystem;

public interface aprilTagInterface extends Subsystem{
    Pose3d getPose();
}
