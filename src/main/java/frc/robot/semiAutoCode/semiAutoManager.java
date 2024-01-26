package frc.robot.semiAutoCode;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Gyro;
import frc.robot.subsystems.Limelight;

public class semiAutoManager{
    DriveBase drive;
    Gyro gyro;
    Limelight limelight;
    Timer timer;
    diffObj accessPoint=null;



    double Xchange=0;
    double Ychange=0;
    double Zchange=0;
    Coords current;


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
        accessPoint=new diffObj(timer.get()*1000, accessPoint);

        Xchange = Math.sin(gyro.getRoll())*(drive.getEncoderAvrg()-Xchange);
        Ychange = Math.cos(gyro.getRoll())*(drive.getEncoderAvrg()-Ychange);
        Zchange = gyro.getRoll()-Zchange;

        accessPoint.passItOn(Xchange, Ychange, Zchange);
        
        current=getNewCoords(current);
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

    private Coords getNewCoords(Coords lastCoords){
        return new Coords(limelight, gyro, drive, timer, this, lastCoords); 
        }



    public Coords getCoords(){
        return current;
    }


    public diffObj getDiffObj(Double delay){
        return accessPoint.findTheDiffObj(timer.get()*1000-delay);
    }


}
