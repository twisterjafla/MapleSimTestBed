package frc.robot;


import java.util.List;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.semiAutoCommands.CancelCurrentRoutine;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Gyro;
import frc.robot.subsystems.Limelight;
import edu.wpi.first.math.trajectory.Trajectory;

public  class semiAutoManager{
    public static DriveBase drive;
    public static Gyro gyro;
    public static Limelight limelight;
    public static Timer timer;
    public static DifferentialDrivePoseEstimator poseEstimator;
    private static Command current;
    public static CancelCurrentRoutine cancel= new CancelCurrentRoutine();
    private static Pose2d startingPose;


    public static void configureSemiAutoManager(DriveBase Drive, Gyro Gyro, Limelight Limelight, Timer Timer){
        drive=Drive;
        gyro=Gyro;
        limelight=Limelight;
        timer=Timer;
        startingPose=Constants.fieldPosits.leftStart;


        poseEstimator = new DifferentialDrivePoseEstimator(
            Constants.drive.kinematics,
            gyro.getYaw(),
            drive.getLeftEncoder(),
            drive.getRightEncoder(), 
            startingPose);

        controlInitalizer.controlInitalizerFromSemiAutoManager(cancel);
        
    }
    


    public static void periodic(){
        poseEstimator.update(gyro.getYaw(), drive.getLeftEncoder(), drive.getRightEncoder());

        Pose2d visionCoords=limelight.getCoords();
        
        if (visionCoords!=null){
            poseEstimator.addVisionMeasurement(visionCoords, limelight.getDelayInMs());
        }
        Pose2d currentPose2d = getCoords();
        SmartDashboard.putNumber("robotPositX", currentPose2d.getX());
        SmartDashboard.putNumber("robotPositY", currentPose2d.getY());
        SmartDashboard.putNumber("RobotRotation", currentPose2d.getRotation().getDegrees());
        SmartDashboard.putNumber("robotRotation radians", currentPose2d.getRotation().getRadians());
        //SmartDashboard.putNumber("gyroValue", gyro.getYaw().getDegrees());
        //SmartDashboard.putNumber("leftEncoder", drive.getLeftEncoder());
        //SmartDashboard.putNumber("rightEncoder", drive.getRightEncoder());

        
    }


    public static Pose2d getCoords(){
        return poseEstimator.getEstimatedPosition();
    }


    public static Command getCurrent(){
        return current;
    }
    public static void setCurrent(Command newCommand){
        current = newCommand;
    }

    public static void resetAudomity(){
        poseEstimator.resetPosition(gyro.getRoll(), drive.getLeftEncoder(), drive.getRightEncoder(), startingPose);
    }
    



}
