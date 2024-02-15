package frc.robot;


import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.semiAutoCommands.CancelCurrentRoutine;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Gyro;
import frc.robot.subsystems.Limelight;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.trajectory.Trajectory;

public  class semiAutoManager{
    public static DriveBase drive;
    public static Gyro gyro;
    public static Limelight limelight;
    public static Timer timer;
    public static DifferentialDrivePoseEstimator poseEstimator;
    private static Command current;
    public static CancelCurrentRoutine cancel= new CancelCurrentRoutine();


    public static void configureSemiAutoManager(DriveBase Drive, Gyro Gyro, Limelight Limelight, Timer Timer){
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
            
        controlInitalizer.controlInitalizerFromSemiAutoManager(cancel);
        
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



    public static RamseteCommand getRamseteCommand(Trajectory trajectory) {
        
        return new RamseteCommand(
            trajectory,
            semiAutoManager :: getCoords,
            new RamseteController(),
            new SimpleMotorFeedforward(
                Constants.drive.ksVolts,
                Constants.drive.kvVoltSecondsPerMeter,
                Constants.drive.kaVoltSecondsSquaredPerMeter),
            Constants.drive.kinematics,
            drive::getWheelSpeeds,
            new PIDController(Constants.drive.kPDriveVel, 0, 0),
            new PIDController(Constants.drive.kPDriveVel, 0, 0),
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




}
