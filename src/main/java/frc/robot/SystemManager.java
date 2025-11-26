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
import frc.robot.subsystems.generalManager;
import frc.robot.subsystems.wristElevatorControlManager;
import frc.robot.subsystems.elevator.elevatorIO;
import frc.robot.subsystems.elevator.realElevator;
import frc.robot.subsystems.elevator.simElevator;
import frc.robot.subsystems.intakes.algaeManipulator;
import frc.robot.subsystems.intakes.intakeIO;
import frc.robot.subsystems.intakes.noteManipulator;
import frc.robot.subsystems.intakes.realIntake;
import frc.robot.subsystems.intakes.simIntake;
import frc.robot.subsystems.swervedrive.AIRobotInSimulation;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import frc.robot.subsystems.swervedrive.realSimulatedDriveTrain;
import frc.robot.subsystems.vision.aprilTagInterface;
import frc.robot.subsystems.vision.photonSim;
import frc.robot.subsystems.wrist.realWrist;
import frc.robot.subsystems.wrist.simWrist;
import frc.robot.subsystems.wrist.wristIO;

public class SystemManager{
    public static SwerveSubsystem swerve;

    public static Field2d feild;
    //public static SimulatedArena simFeild;
    public static AIRobotInSimulation fakeBot;
    public static boolean hasNote = false;
    public static intakeIO intake;
    public static wristIO wrist;
    public static aprilTagInterface aprilTag;
    public static elevatorIO elevator;
    public static realSimulatedDriveTrain simButRealTrain = null;
    public static algaeManipulator algaeManipulator;
    public static noteManipulator noteManipulator;
         
    // Add a Coral Array object for tracking

    /** Initializes the system manager along with all the systems on the robot */
    public static void SystemManagerInit(){
        // creates the swerve drive. Due to the complexity of the swerve system, it handles simulation differently and does not need an if-else block
        swerve = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),  "swerve"));
        swerve.resetOdometry(Constants.driveConstants.startingPosit);
        
        feild = new Field2d();
        SmartDashboard.putData("Field", feild);
        aprilTag = new photonSim();

        SimulatedArena.ALLOW_CREATION_ON_REAL_ROBOT = Constants.simConfigs.robotCanBeSimOnReal;

        // Initializes all the systems
        // Each block should initialize one system as either real or imaginary based on the constants value 
        
        // Intake
        if (Constants.simConfigs.intakeShouldBeSim){
            if (RobotBase.isReal()){
                simButRealTrain = new realSimulatedDriveTrain();
            }
            intake = new simIntake();
        } else {
            intake = new realIntake();
        }

        // April tags

        // Elevator
        if (Constants.simConfigs.elevatorShouldBeSim){
            elevator = new simElevator();
        } else {
            elevator = new realElevator();
        }

        // Wrist
        if (Constants.simConfigs.wristShouldBeSim){
            wrist = new simWrist();
        } else {
            wrist = new realWrist();
        }







        // Create an imaginary robot
        if (!RobotBase.isReal()){
            //AIRobotInSimulation.startOpponentRobotSimulations();
            //fakeBot = AIRobotInSimulation.getRobotAtIndex(0);
            // Overrides the default simulation
        }

        // Initialize and distribute the managers
        wristElevatorControlManager.addSystems(wrist, elevator);
        generalManager.generalManagerInit();
        algaeManipulator = new algaeManipulator();
        noteManipulator = new noteManipulator();


        // Initialize Coral Array

    }

    /** Calls periodic on all the systems that do not inherit subsystem base. This function should be called in robot periodic */
    public static void periodic(){
        wristElevatorControlManager.periodic();
        generalManager.periodic();

    }

    /** @return the current pose of the robot */
    public static Pose2d getSwervePose(){
        return swerve.getPose();
    }

    /** @return the pose of the simulated maplesim drive. If the drivetrain is real, then the function will just return the pose estimator's pose */
    public static Pose2d getRealPoseMaple(){
        if (RobotBase.isReal()){
            return getSwervePose();
        }
        return swerve.getMapleSimPose();
    }

    /** Returns the pose3 of a coral in the intake */
    public static Pose3d getIntakePosit(){
        return new Pose3d(getSwervePose()).plus(new Transform3d(intake.getTranslation(), new Rotation3d(0, SystemManager.wrist.getCurrentLocationR2D().getRadians() + Constants.elevatorConstants.angle.getRadians() + Math.PI / 2, Math.PI)));
    }
}
