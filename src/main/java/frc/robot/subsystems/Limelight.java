package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight extends SubsystemBase{
boolean isBlue;
NetworkTable table;

//post to smart dashboard periodically
    public Limelight(){
        
        isBlue=(DriverStation.getAlliance().get()==DriverStation.Alliance.Blue);
        
        table = NetworkTableInstance.getDefault().getTable("limelight");
    }

   public Pose2d getCoords(){


    if (table.getEntry("tid").getDouble(-1)==-1){
        return null;
    }
    else if(isBlue){
            
        double[] coordsList = table.getEntry("botpose_wpiblue").getDoubleArray(new double[6]);
        return new Pose2d(coordsList[0], coordsList[1], new Rotation2d(coordsList[2])); 
        
    }
    else{
        double[] coordsList = table.getEntry("botpose_wpired").getDoubleArray(new double[6]);
        return new Pose2d(coordsList[0], coordsList[1], new Rotation2d(coordsList[2])); 
    }
    
   }

   public double getDelayInMs(){
    return table.getEntry("botpose").getDoubleArray(new double[7])[6];
   }
}


