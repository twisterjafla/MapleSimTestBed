package frc.robot.commands.auto;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.SystemManager;
import frc.robot.Constants.AutonConstants;
import frc.robot.Utils.utillFunctions;
import swervelib.math.SwerveMath;

public class smallAutoDrive extends Command{
    Pose2d pose;
    int correctCount=0;
    protected static StructPublisher<Pose2d> goalPosePublisher = NetworkTableInstance.getDefault().getStructTopic("goalPose", Pose2d.struct).publish();
    PIDController pid = new PIDController(Constants.AutonConstants.smallAutoPID.kP, Constants.AutonConstants.smallAutoPID.kI, Constants.AutonConstants.smallAutoPID.kD);
    public smallAutoDrive(Pose2d pose){
        this.pose=pose;
        pid.setSetpoint(0);
        pid.setTolerance(Constants.AutonConstants.autoDriveScoreTolerence);
        
        addRequirements(SystemManager.swerve);
    }

    @Override
    public void execute(){
        SmartDashboard.putBoolean("smallDriveRunning", true);
        Rotation2d angleRad = new Rotation2d(-(pose.getX()-SystemManager.getSwervePose().getX()), pose.getY()-SystemManager.getSwervePose().getY());
        
        double speed = -pid.calculate(utillFunctions.pythagorean(pose.getX(), SystemManager.getSwervePose().getX(), pose.getY(), SystemManager.getSwervePose().getY()));
        SmartDashboard.putNumber("smallDriveSpeed", speed);
        SmartDashboard.putNumber("smallDriveError", utillFunctions.pythagorean(pose.getX(), SystemManager.getSwervePose().getX(), pose.getY(), SystemManager.getSwervePose().getY()));
        goalPosePublisher.set(pose);

        SystemManager.swerve.drive(speed, angleRad, pose.getRotation());
        
    }

    @Override
    public boolean isFinished(){
        if (pid.atSetpoint()){
            correctCount++;
        }
        else{
            correctCount=0;
        }
        return correctCount>Constants.AutonConstants.autoDriveCorrectCount;
    }


    @Override
    public void end(boolean wasCancled){
        SmartDashboard.putBoolean("smallDriveRunning", false);
    }
}
