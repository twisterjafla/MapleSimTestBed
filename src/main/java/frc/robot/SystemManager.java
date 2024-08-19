package frc.robot;

import java.io.File;

import edu.wpi.first.wpilibj.Filesystem;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;

public class SystemManager{
    SwerveSubsystem swerve = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),  "swerve/falcon"));
    
}