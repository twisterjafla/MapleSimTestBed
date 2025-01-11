package frc.robot;

import java.io.File;

import org.ironmaple.simulation.SimulatedArena;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.intake.intakeInterface;
import frc.robot.subsystems.intake.simIntake;
import frc.robot.subsystems.swervedrive.AIRobotInSimulation;
//import frc.robot.subsystems.swervedrive.FakeBotSubsystem;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import frc.robot.subsystems.vision.aprilTagInterface;
import frc.robot.subsystems.vision.photonSim;



public class SystemManager{
    public static SwerveSubsystem swerve;

    public static Field2d feild;
    public static SimulatedArena simFeild;
    public static AIRobotInSimulation fakeBot;
    public static boolean hasNote=false;
    public static intakeInterface intake;
    public static aprilTagInterface aprilTag;
    
    
    public static void SystemManagerInit(){
        swerve = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),  "swerve"));
        swerve.resetOdometry(new Pose2d(3.,3, new Rotation2d()));
        //swerveReff = new SwerveSubsystemReff(new File(Filesystem.getDeployDirectory(),  "swerve/falcon"));
        
        feild = new Field2d();
        SmartDashboard.putData("Field", feild);


        if (Constants.simConfigs.intakeShouldBeSim){
            intake=new simIntake();
        }
        else{
        }

        if (Constants.simConfigs.aprilTagShouldBeSim){
            aprilTag= new photonSim();
        }
        else{
        }

        if (!RobotBase.isReal()){
            //AIRobotInSimulation.startOpponentRobotSimulations();
            simFeild = SimulatedArena.getInstance();
            // Overrides the default simulation
            SimulatedArena.overrideInstance(simFeild); 

        }

    }

    public static Pose2d getSwervePose(){
        return swerve.getPose();
    }

    public static Pose2d getRealPoseMaple(){
        return swerve.getMapleSimPose();
    }
}