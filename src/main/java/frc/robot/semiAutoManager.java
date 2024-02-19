package frc.robot;


import java.util.List;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
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
            0,
            0, 
            startingPose);

        controlInitalizer.controlInitalizerFromSemiAutoManager(cancel);
        
    }


    public static void periodic(){
        poseEstimator.update(gyro.getYaw(), drive.getLeftEncoder(), drive.getRightEncoder());

        Pose2d visionCoords=limelight.getCoords();
        if (visionCoords!=null){
            poseEstimator.addVisionMeasurement(limelight.getCoords(), limelight.getDelayInMs());
        }
        Pose2d currentPose2d = getCoords();
        SmartDashboard.putNumber("robotPositX", currentPose2d.getX());
        SmartDashboard.putNumber("robotPositY", currentPose2d.getY());
        SmartDashboard.putNumber("RobotRotation", currentPose2d.getRotation().getDegrees());
        SmartDashboard.putNumber("gyroValue", gyro.getYaw().getDegrees());

        
    }


    public static Pose2d getCoords(){
        return poseEstimator.getEstimatedPosition();
    }



    public static RamseteCommand getRamseteCommand(Trajectory trajectory) {
        
        return new RamseteCommand(
            trajectory,
            semiAutoManager :: getCoords,
            new RamseteController(),
            new SimpleMotorFeedforward(
                Constants.drive.ksVolts,
                Constants.drive.kvVoltSecondsPerMeter),
            Constants.drive.kinematics,
            drive::getWheelSpeeds,
            new PIDController(0.1, 0, 0),
            new PIDController(0.1, 0, 0),
            // RamseteCommand passes volts to the callback
            drive::tankDriveVolts,
            drive);
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
