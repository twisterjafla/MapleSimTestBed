package frc.robot;

import java.io.File;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.swervedrive.FakeBot;
//import frc.robot.subsystems.swervedrive.FakeBotSubsystem;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import frc.robot.subsystems.swervedrive.SwerveSubsystemReff;

public class SystemManager{
    public static SwerveSubsystem swerve;
    public static SwerveSubsystemReff swerveReff;
    public static Field2d m_field;
    public static FakeBot fakeBot;
    public static boolean hasNote=false;
    
    
    public static void SystemManagerInit(){
        swerve = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),  "swerve/falcon"));
        //swerveReff = new SwerveSubsystemReff(new File(Filesystem.getDeployDirectory(),  "swerve/falcon"));
        
        m_field = new Field2d();
        SmartDashboard.putData("Field", m_field);
        //if (!RobotBase.isReal()){
            fakeBot=new FakeBot();
        //}
    }

    public static Pose2d getSwervePose(){
        return swerve.getPose();
    }
}