package frc.robot;

import java.io.File;
import org.ironmaple.simulation.SimulatedArena;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.FieldPosits.reefLevel.algeaRemoval;
import frc.robot.subsystems.autoManager;
import frc.robot.subsystems.generalManager;
import frc.robot.subsystems.wristElevatorControlManager;
import frc.robot.subsystems.AlgaeRemover.algaeRemoverInterface;
import frc.robot.subsystems.AlgaeRemover.realAlgaeRemover;
import frc.robot.subsystems.AlgaeRemover.simAlgaeRemover;
import frc.robot.subsystems.blinkin.blinkinInterface;
import frc.robot.subsystems.blinkin.realBlinkin;
import frc.robot.subsystems.blinkin.simBlinkin;
import frc.robot.subsystems.elevator.elevatorInterface;
import frc.robot.subsystems.elevator.realElevator;
import frc.robot.subsystems.elevator.simElevator;
import frc.robot.subsystems.intake.intakeInterface;
import frc.robot.subsystems.intake.realIntake;
import frc.robot.subsystems.intake.simIntake;
import frc.robot.subsystems.lidar.lidarInterface;
import frc.robot.subsystems.lidar.simLidar;
import frc.robot.subsystems.swervedrive.AIRobotInSimulation;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import frc.robot.subsystems.swervedrive.realSimulatedDriveTrain;
import frc.robot.subsystems.vision.aprilTagInterface;
import frc.robot.subsystems.vision.photonSim;
import frc.robot.subsystems.vision.realVision;

import frc.robot.subsystems.vision.reefIndexerInterface;
import frc.robot.subsystems.vision.simReefIndexer;
import frc.robot.subsystems.wrist.realWrist;
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
    public static lidarInterface lidar;
    public static realSimulatedDriveTrain simButRealTrain=null;
    public static realVision realVisTemp=null;
    public static blinkinInterface blinkin;
    public static algaeRemoverInterface algaeRemover;
    
    /**inializes the system manager along with all the systems on the robot */
    public static void SystemManagerInit(){

        //creates the swerve drive. Do to the complexity of the swerve system it handles simulation differently and so does not need a if else block
        swerve = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),  "swerve"));
        swerve.resetOdometry(Constants.driveConstants.startingPosit);
       
        
        feild = new Field2d();
        SmartDashboard.putData("Field", feild);


        SimulatedArena.ALLOW_CREATION_ON_REAL_ROBOT=Constants.simConfigs.robotCanBeSimOnReal;


        //Initalizes all the systems
        //Each block should inialize one system as ether real of imaginary based on the constants value 
        
        //Intake
        if (Constants.simConfigs.intakeShouldBeSim){
            if (RobotBase.isReal()){
                simButRealTrain = new realSimulatedDriveTrain();
            }
            intake=new simIntake();
        }
        else{
            intake = new realIntake();
        }

        //April tags
        if (Constants.simConfigs.aprilTagShouldBeSim){
            aprilTag= new photonSim();
        }
        else{
            realVisTemp = new realVision();
            aprilTag=realVisTemp;
        }

        //Elevator
        if (Constants.simConfigs.elevatorShouldBeSim){
            elevator= new simElevator();
        }
        else{
            elevator = new realElevator();
        }

        //Wrist
        if (Constants.simConfigs.wristShouldBeSim){
            wrist= new simWrist();
        }
        else{
            wrist = new realWrist();
        }

        //Reef indexer
        if (Constants.simConfigs.reefIndexerShouldBeSim){
            reefIndexer= new simReefIndexer();
        }
        else{
            if (realVisTemp != null){
                reefIndexer=realVisTemp;
            }
            else{
                reefIndexer = new realVision();
            }
        }

        //Lidar
        if (Constants.simConfigs.lidarShouldBeSim){
            lidar=new simLidar();
        }
        else{
            
        }

        //Blinkin
        if(Constants.simConfigs.blinkinShouldBeSim){
            blinkin = new simBlinkin();
        }
        else{
            blinkin = new realBlinkin();
        }

        
        //creates an imaginary robot
        if (!RobotBase.isReal()){
            AIRobotInSimulation.startOpponentRobotSimulations();
            fakeBot=AIRobotInSimulation.getRobotAtIndex(0);
            // Overrides the default simulation

        }

        if (Constants.simConfigs.algaeRemoverShouldBeSim){
            algaeRemover= new simAlgaeRemover();
        }
        else{
            algaeRemover = new realAlgaeRemover();
        }

        //inializes and distributes the managers
        wristElevatorControlManager.addSystems(wrist, elevator);
        generalManager.generalManagerInit();
        autoManager.autoManagerInit();
    }


    /**Calls periodic on all the systems that do not inherit subsytstem base. this function should be called in robot periodic*/
    public static void periodic(){
        wristElevatorControlManager.periodic();
        generalManager.periodic();
        autoManager.periodic();
    }

    /**@return the current pose of the robot*/
    public static Pose2d getSwervePose(){
        return swerve.getPose();
    }

    /**@return the pose of the simulated maplesim drive. If the drivetain is real than the function will just return the pose estimators pose */
    public static Pose2d getRealPoseMaple(){
        if (RobotBase.isReal()){
            return getSwervePose();
        }
        return swerve.getMapleSimPose();
    }


    /**returns the pose3 of a coral in the intake */
    public static Pose3d getIntakePosit(){
        return new Pose3d(getSwervePose()).plus(new Transform3d(intake.getTranslation(), new Rotation3d( 0, SystemManager.wrist.getCurrentLocationR2D().getRadians()+Constants.elevatorConstants.angle.getRadians()+Math.PI/2, Math.PI)));
    }





    

}