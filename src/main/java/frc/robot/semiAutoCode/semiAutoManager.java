package frc.robot.semiAutoCode;


import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Gyro;
import frc.robot.subsystems.Limelight;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

public class semiAutoManager{
    DriveBase drive;
    Gyro gyro;
    Limelight limelight;
    Timer timer;
    DifferentialDrivePoseEstimator poseEstimator;



    double Xchange=0;
    double Ychange=0;
    double Zchange=0;


    public semiAutoManager(DriveBase drive, Gyro gyro, Limelight limelight, Timer timer){
        this.drive=drive;
        this.gyro=gyro;
        this.limelight=limelight;
        this.timer=timer;


        poseEstimator = new DifferentialDrivePoseEstimator(
            new DifferentialDriveKinematics(Constants.robotStats.trackWidth),
            gyro.getRoll(),
            0,
            0, 
            Constants.fieldPosits.leftStart);
        
    }


    public void periodic(){
        poseEstimator.update(gyro.getRoll(), drive.getLeftEncoderInMeters(), drive.getRightEncoderInMeters());

        Pose2d visionCoords=limelight.getCoords();
        if (visionCoords!=null){
            poseEstimator.addVisionMeasurement(limelight.getCoords(), Xchange);
        }
    }


    public Pose2d getCoords(){
        return poseEstimator.getEstimatedPosition();
    }



}
