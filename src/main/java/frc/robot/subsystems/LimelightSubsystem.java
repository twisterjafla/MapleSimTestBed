package frc.robot.subsystems;
//Code credit bakedpotatoelord

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LimelightSubsystem extends SubsystemBase {
    public NetworkTable table =NetworkTableInstance.getDefault().getTable("limelight");
	public NetworkTableEntry txN = table.getEntry("tx");
	public NetworkTableEntry tyN = table.getEntry("ty");
	public NetworkTableEntry taN = table.getEntry("ta");
    
    public LimelightSubsystem(){}

    public double tx = txN.getDouble(0);
    public double ty = tyN.getDouble(0);
    public double ta = taN.getDouble(0);
}
