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
Coords coordBox;
//post to smart dashboard periodically
    public Limelight(Coords coordBox){
        this.coordBox=coordBox;
        isBlue=(DriverStation.getAlliance() == DriverStation.Alliance.Blue);
        table = NetworkTableInstance.getDefault().getTable("limelight");
    }
   @Override
   public void periodic(){
    
    
//read values periodically
    
    UpdateCoords(coordBox);
    
    SmartDashboard.putNumber("LimelightX", coordBox.getX());
    SmartDashboard.putNumber("LimelightY", coordBox.getY());
    SmartDashboard.putNumber("LimelightArea", coordBox.getZ());

    // SmartDashboard.putNumber("blueX", table.getEntry("botpose_wpilib."));
   }
   public void UpdateCoords(Coords coords){

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
        
   }
}


