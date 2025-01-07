package frc.robot;

import java.io.File;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.intake.intakeInterface;
import frc.robot.subsystems.intake.simIntake;
import frc.robot.subsystems.swervedrive.AIRobotInSimulation;
//import frc.robot.subsystems.swervedrive.FakeBotSubsystem;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;



public class SystemManager{
    public static SwerveSubsystem swerve;

    public static Field2d m_field;
    public static AIRobotInSimulation fakeBot;
    public static boolean hasNote=false;
    public static intakeInterface intake;
    
    
    public static void SystemManagerInit(){
        swerve = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),  "swerve/falcon"));
        //swerveReff = new SwerveSubsystemReff(new File(Filesystem.getDeployDirectory(),  "swerve/falcon"));
        
        m_field = new Field2d();
        SmartDashboard.putData("Field", m_field);


        if (Constants.simConfigs.intakeShouldBeReal){
            intake=new simIntake();
        }
        // if (!RobotBase.isReal()){
        //     AIRobotInSimulation.startOpponentRobotSimulations();
        // }
    }

    public static Pose2d getSwervePose(){
        return swerve.getPose();
    }
}