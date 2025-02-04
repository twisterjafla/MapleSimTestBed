package frc.robot;

import java.io.File;

import org.ironmaple.simulation.SimulatedArena;
import org.ironmaple.simulation.drivesims.AbstractDriveTrainSimulation;
import org.ironmaple.simulation.drivesims.configs.DriveTrainSimulationConfig;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.autoManager;
import frc.robot.subsystems.generalManager;
import frc.robot.subsystems.wristElevatorControllManager;
import frc.robot.subsystems.elevator.elevatorInterface;
import frc.robot.subsystems.elevator.simElevator;
import frc.robot.subsystems.intake.intakeInterface;
import frc.robot.subsystems.intake.realIntake;
import frc.robot.subsystems.intake.simIntake;
import frc.robot.subsystems.lidar.lidarInterface;
import frc.robot.subsystems.lidar.simLidar;
import frc.robot.subsystems.swervedrive.AIRobotInSimulation;
//import frc.robot.subsystems.swervedrive.FakeBotSubsystem;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import frc.robot.subsystems.swervedrive.realSimulatedDriveTrain;
import frc.robot.subsystems.vision.aprilTagInterface;
import frc.robot.subsystems.vision.photonSim;
//import frc.robot.subsystems.vision.realVision;
import frc.robot.subsystems.vision.reefIndexerInterface;
import frc.robot.subsystems.vision.simReefIndexer;
import frc.robot.subsystems.wrist.simWrist;
import frc.robot.subsystems.wrist.wristInterface;



public class SystemManager{
    public static SwerveSubsystem swerve;

    public static Field2d feild;
    public static SimulatedArena simFeild;
    public static AIRobotInSimulation fakeBot;
    public static boolean hasNote=false;
    public static intakeInterface intake;
    public static aprilTagInterface aprilTag;
    public static wristInterface wrist;
    public static elevatorInterface elevator;
    public static reefIndexerInterface reefIndexer;
    public static wristElevatorControllManager wristManager;
    public static lidarInterface lidar;
    public static realSimulatedDriveTrain simButRealTrain=null;
    //public static realVision realVisTemp;
    
    public static void SystemManagerInit(){
        swerve = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),  "swerve"));
        swerve.resetOdometry(new Pose2d(3.,3, new Rotation2d()));
        //swerveReff = new SwerveSubsystemReff(new File(Filesystem.getDeployDirectory(),  "swerve/falcon"));
        
        feild = new Field2d();
        SmartDashboard.putData("Field", feild);


        if (Constants.simConfigs.intakeShouldBeSim){
            if (RobotBase.isReal()){
                simButRealTrain = new realSimulatedDriveTrain(DriveTrainSimulationConfig.Default(), getSwervePose());
            }
            intake=new simIntake();
            
        }
        else{
            intake = new realIntake();
        }

        if (Constants.simConfigs.aprilTagShouldBeSim){
            aprilTag= new photonSim();
        }
        else{
            // realVisTemp = new realVision();
            // aprilTag=realVisTemp;
        }

        if (Constants.simConfigs.elevatorShouldBeSim){
            elevator= new simElevator();
        }

        if (Constants.simConfigs.wristShouldBeSim){
            wrist= new simWrist();
        }
        else{

        }

        if (Constants.simConfigs.reefIndexerShouldBeSim){
            reefIndexer= new simReefIndexer();
        }
        else{
            // if (realVisTemp != null){
            //     reefIndexer=realVisTemp;
            // }
            // else{
            //     reefIndexer = new realVision();
            // }
        }

        if (Constants.simConfigs.lidarShouldBeSim){
            lidar=new simLidar();
        }
        else{
            
        }

        

        if (!RobotBase.isReal()){
            AIRobotInSimulation.startOpponentRobotSimulations();
            fakeBot=AIRobotInSimulation.getRobotAtIndex(0);
            // Overrides the default simulation

        }

        wristManager = new wristElevatorControllManager();
        elevator.setManager(wristManager);
        wrist.setManager(wristManager);
        wristManager.addSystems(wrist, elevator);

        generalManager.generalManagerInit();
        autoManager.autoManagerInit();
        System.out.println("sytems started up");
    }


    /**Calls periodic on all the systems that do not inherit subststem base. this function should be called in robot periodic*/
    public static void periodic(){
        wristManager.periodic();
        generalManager.periodic();
        autoManager.periodic();
        reefIndexer.periodic();
        if (simButRealTrain!=null){
            simButRealTrain.periodic();
        }
    }



    public static Pose2d getSwervePose(){
        return swerve.getPose();
    }

    public static Pose2d getRealPoseMaple(){
        return swerve.getMapleSimPose();
    }

    public static Pose3d getIntakePosit(){
        return new Pose3d(getSwervePose()).plus(new Transform3d(intake.getTranslation(), new Rotation3d( 0, SystemManager.wrist.getcurrentLocation().getRadians()+Constants.elevatorConstants.angle.getRadians()+Math.PI/2, Math.PI)));
    }

    public static boolean hasPeice(){
        return intake.hasPeice();
    }

    public static Command driveToPose(Pose2d pose){
        return swerve.driveToPose(pose);
    }

}