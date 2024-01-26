package frc.robot.semiAutoCode;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.drive;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Gyro;
import frc.robot.subsystems.Limelight;

public class semiAutoManager{
    DriveBase drive;
    Gyro gyro;
    Limelight limelight;
    boolean isGoodToRun;
    Timer timer;
    diffObj accessPoint=null;


    public semiAutoManager(DriveBase drive, Gyro gyro, Limelight limelight, Timer timer){
        this.drive=drive;
        this.gyro=gyro;
        this.limelight=limelight;
        this.timer=timer;
        
    }

    public Command getBoardControls(){
        //Code that returns an in integer value that relates to the routine to be run
        return null;
    }
    public void periodic(){
        if (accessPoint!=null){
            accessPoint.makeNewDiffObj((int)timer.get()*1000);
        }
        else{
            accessPoint=new diffObj((int)timer.get()*1000, null);
        }

        double Xchange = Math.sin(gyro.getRoll())*drive.getEncoderAvrg();
        double Ychange = math.cos(gyro.getRoll())*

        accessPoint.passItOn(0, 0);





        // private final DifferentialDrivePoseEstimator m_poseEstimator =
        // new DifferentialDrivePoseEstimator(
        //     m_kinematics,
        //     m_gyro.getRotation2d(),
        //     m_leftEncoder.getDistance(),
        //     m_rightEncoder.getDistance(),
        //     new Pose2d(),
        //     VecBuilder.fill(0.05, 0.05, Units.degreesToRadians(5)),
        //     VecBuilder.fill(0.5, 0.5, Units.degreesToRadians(30)))
        
    }

    public Coords getCoords(Coords lastCoords){
        Coords working = limelight.updateCoords(lastCoords);

        working.setEncoders(drive);
        return working; 
        }


}
