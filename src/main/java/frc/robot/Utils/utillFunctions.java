package frc.robot.Utils;

import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.math.geometry.Pose2d;

public class utillFunctions {
    public static double mpsToMph(double mps) {
        // 1 mile = 1609.344 meters
        // 1 hour = 3600 seconds
        return mps * 1609.344 / 3600;
    }
}
