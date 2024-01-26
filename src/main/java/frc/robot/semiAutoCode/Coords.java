package frc.robot.semiAutoCode;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Gyro;
import frc.robot.subsystems.Limelight;

public class Coords {

    private double X;
    private double Y;
    private double Z;

    private diffObj diff;


    public Coords(Limelight limelight, Gyro gyro, DriveBase drive, Timer timer, semiAutoManager manager, Coords lastCoords){
        
        if (!limelight.updateCoords(this)){
            this.X=lastCoords.X+manager.Xchange;
            this.Y=lastCoords.Y+manager.Ychange;
            this.Z=lastCoords.Z+manager.Zchange;
        }
        
        diff=manager.getDiffObj(limelight.getDelayInMs());
        if (diff!=null){
            X+=diff.XChange;
            Y+=diff.YChange;
            Z+=diff.Zchange;
        }
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
