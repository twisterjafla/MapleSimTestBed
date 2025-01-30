package frc.robot.Utils;



public class utillFunctions {
    public static double mpsToMph(double mps) {
        // 1 mile = 1609.344 meters
        // 1 hour = 3600 seconds
        return mps * 1609.344 / 3600;
    }
}
