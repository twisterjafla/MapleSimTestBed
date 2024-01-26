package frc.robot.semiAutoCode;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Gyro;
import frc.robot.subsystems.Limelight;

public class Coords {

    private double X;
    private double Y;
    private double Z;
    private double leftEncoder;
    private double rightEncoder;
    private double timeAtAsign; 

    public Coords(Limelight limelight, Gyro gyro, DriveBase drive, Timer timer){
        limelight.updateCoords(this);

        leftEncoder=drive.getLeftEncoder();
        rightEncoder=drive.getRightEncoder();

        timeAtAsign=timer.get();
        
    }

    public void setCoords(double X, double Y, double Z){
        this.X=X;
        this.Y=Y;
        this.Z=Z;
    }

    public double getX(){
        return X;
    }
    public double getY(){
        return Y;
    }
    public double getZ(){
        return Z;
    }
    
}
