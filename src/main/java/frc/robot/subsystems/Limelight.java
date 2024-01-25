package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.subsystems.Coords;

public class Limelight extends SubsystemBase{
boolean isBlue;
NetworkTable table;
Coords limeCoords;
//post to smart dashboard periodically
    public Limelight(Coords coordBox){
        this.limeCoords=coordBox;
        isBlue=(DriverStation.getAlliance() == DriverStation.Alliance.Blue);
        table = NetworkTableInstance.getDefault().getTable("limelight");
    }
   @Override
   public void periodic(){
    
    
//read values periodically
    
    limeCoords = getCoords();
    
    SmartDashboard.putNumber("LimelightX", limeCoords.getX());
    SmartDashboard.putNumber("LimelightY", limeCoords.getY());
    SmartDashboard.putNumber("LimelightArea", limeCoords.getZ());

    // SmartDashboard.putNumber("blueX", table.getEntry("botpose_wpilib."));
   }
   public Coords getCoords(){
    Coords coords = new Coords();

    if (table.getEntry("tid").getDouble(-1)==-1){
    }
    else if(isBlue){
            
        double[] coordsList = table.getEntry("botpose_wpiblue").getDoubleArray(new double[6]);;
        coords.setCoords(coordsList[0], coordsList[1], coordsList[2]); 
        
    }
    else{
        double[] coordsList = table.getEntry("botpose_wpired").getDoubleArray(new double[6]);;
        coords.setCoords(coordsList[0], coordsList[1], coordsList[2]); 
    }
    return coords;
        
   }
   public Coords updateCoords(Coords coordToUpdate){
    Coords newCoords=getCoords();
    coordToUpdate.setCoords(newCoords.getX(), newCoords.getY(), newCoords.getX());
    return coordToUpdate;
   }
}


