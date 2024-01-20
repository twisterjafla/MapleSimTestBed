package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.PrintCommand;

public class Limelight extends SubsystemBase{
boolean isBlue;
NetworkTable table;
//post to smart dashboard periodically
    public Limelight(){
        isBlue=(DriverStation.getAlliance() == DriverStation.Alliance.Blue);
        table = NetworkTableInstance.getDefault().getTable("limelight");
    }
   @Override
   public void periodic(){
    
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry ta = table.getEntry("ta");
    
//read values periodically
    

    double x = tx.getDouble(0.0);
    double y = ty.getDouble(0.0);
    double area = ta.getDouble(0.0);
    SmartDashboard.putNumber("LimelightX", x);
    SmartDashboard.putNumber("LimelightY", y);
    SmartDashboard.putNumber("LimelightArea", area);

    // SmartDashboard.putNumber("blueX", table.getEntry("botpose_wpilib."));
   }
   public void UpdateCoords(Coords coords){
    DriverStation.Alliance color;
	color = DriverStation.getAlliance();
        if (table.getEntry("tid").getDouble(-1)==-1){
           
        }
        else if(isBlue);
            //coords.setCoords(table.getEntry)
            
        }
        
   }


