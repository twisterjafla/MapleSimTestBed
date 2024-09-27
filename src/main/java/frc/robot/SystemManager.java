package frc.robot;

import java.io.File;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import frc.robot.subsystems.swervedrive.SwerveSubsystemReff;

public class SystemManager{
    public static SwerveSubsystem swerve;
    public static SwerveSubsystemReff swerveReff;
    public static Field2d m_field;
    public static void SystemManagerInit(){
        swerve = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),  "swerve/falcon"));
        //swerveReff = new SwerveSubsystemReff(new File(Filesystem.getDeployDirectory(),  "swerve/falcon"));
        m_field = new Field2d();
    }
}