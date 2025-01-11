package frc.robot.Utils;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.Constants;

public class scoringPosit {
    public Pose2d neededRobotPose2d;
    public double elevatorEncoderValue;
    public double wristEncoderValue;

    public scoringPosit(Pose2d neededRobotPose2d, double elevatorEncoderValue, double wristEncoderValue){
        this.neededRobotPose2d=neededRobotPose2d;
        this.elevatorEncoderValue=elevatorEncoderValue;
        this.wristEncoderValue=wristEncoderValue;
    }

    public scoringPosit(Pose2d neededRobotPose2d, int level){
        this.neededRobotPose2d=neededRobotPose2d;
        switch (level){
            case (1):
                elevatorEncoderValue=Constants.elevatorConstants.l1EncoderVal;
                wristEncoderValue=Constants.wristConstants.l1EncoderVal;
                break;
            case (2):
                elevatorEncoderValue=Constants.elevatorConstants.l2EncoderVal;
                wristEncoderValue=Constants.wristConstants.l2EncoderVal;      
                break;
            case (3):
                elevatorEncoderValue=Constants.elevatorConstants.l3EncoderVal;
                wristEncoderValue=Constants.wristConstants.l3EncoderVal;      
                break;
            case (4):
                elevatorEncoderValue=Constants.elevatorConstants.l4EncoderVal;
                wristEncoderValue=Constants.wristConstants.l4EncoderVal;      
                break;
            default:
                throw new Error("attemped to create a scoringPosit with a level that does not exist");            

        }
    }
}
