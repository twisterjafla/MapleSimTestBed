package frc.robot.subsystems;

public class Coords {

    double X;
    double Y;
    double Z;
    double leftEncoder;
    double rightEncoder;

    public Coords(){
    }

    public void setCoords(double X, double Y, double Z){
        this.X=X;
        this.Y=Y;
        this.Z=Z;
    }

    

    public void setEncoders(DriveBase drive){
        leftEncoder=drive.getLeftEncoder();
        rightEncoder=drive.getRightEncoder();

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
