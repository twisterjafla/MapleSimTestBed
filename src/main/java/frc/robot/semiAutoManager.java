package frc.robot;


import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Gyro;
import frc.robot.subsystems.Limelight;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

public  class semiAutoManager{
    public static DriveBase drive;
    public static Gyro gyro;
    public static Limelight limelight;
    public static Timer timer;
    public static DifferentialDrivePoseEstimator poseEstimator;



    public static void semiAutoManagerCompiler(DriveBase Drive, Gyro Gyro, Limelight Limelight, Timer Timer){
        drive=Drive;
        gyro=Gyro;
        limelight=Limelight;
        timer=Timer;


        poseEstimator = new DifferentialDrivePoseEstimator(
            Constants.drive.kinematics,
            gyro.getRoll(),
            0,
            0, 
            Constants.fieldPosits.leftStart);
        
    }


    public static void periodic(){
        poseEstimator.update(gyro.getRoll(), drive.getLeftEncoderInMeters(), drive.getRightEncoderInMeters());

        Pose2d visionCoords=limelight.getCoords();
        if (visionCoords!=null){
            poseEstimator.addVisionMeasurement(limelight.getCoords(), limelight.getDelayInMs());
        }
    }


    public static Pose2d getCoords(){
        return poseEstimator.getEstimatedPosition();
    }



}
