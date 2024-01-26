package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.semiAutoCode.Coords;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight extends SubsystemBase{
boolean isBlue;
NetworkTable table;

//post to smart dashboard periodically
    public Limelight(){

        isBlue=(DriverStation.getAlliance() == DriverStation.Alliance.Blue);
        table = NetworkTableInstance.getDefault().getTable("limelight");
    }

   public Coords updateCoords(Coords coordToUpdate){


    if (table.getEntry("tid").getDouble(-1)==-1){
    }
    else if(isBlue){
            
        double[] coordsList = table.getEntry("botpose_wpiblue").getDoubleArray(new double[6]);
        coordToUpdate.setCoords(coordsList[0], coordsList[1], coordsList[2]); 
        
    }
    else{
        double[] coordsList = table.getEntry("botpose_wpired").getDoubleArray(new double[6]);
        coordToUpdate.setCoords(coordsList[0], coordsList[1], coordsList[2]);
    }
    return coordToUpdate;
    
   }

   public double getDelayInMs(){
    return table.getEntry("botpose").getDoubleArray(new double[7])[6];
   }
}


