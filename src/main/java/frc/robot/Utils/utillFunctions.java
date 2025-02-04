package frc.robot.Utils;



public class utillFunctions {
    public static double mpsToMph(double mps) {
        // 1 mile = 1609.344 meters
        // 1 hour = 3600 seconds
        return mps * 1609.344 / 3600;
    }

    public static double pythagorean(double x, double y){
        return Math.sqrt(Math.pow(x, 2)+ Math.pow(y, 2));
    }
    public static double pythagorean(double x1, double x2, double y1, double y2){
        return pythagorean(x1-x2, y1-y2);
    }
}
