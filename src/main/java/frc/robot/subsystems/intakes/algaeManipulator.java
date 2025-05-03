package frc.robot.subsystems.intakes;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Radian;

import java.util.function.BooleanSupplier;

import org.ironmaple.simulation.IntakeSimulation;
import org.ironmaple.simulation.SimulatedArena;
import org.ironmaple.simulation.seasonspecific.reefscape2025.ReefscapeAlgaeOnFly;
import org.ironmaple.simulation.seasonspecific.reefscape2025.ReefscapeCoralOnFly;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.SystemManager;

public class algaeManipulator extends intakeIO{
    IntakeSimulation intakeSim;
    


    
    private Pose3d algaePose = new Pose3d(-1000, -1000, -1000, new Rotation3d());
    StructPublisher<Pose3d> heldAlgaePublisher = NetworkTableInstance.getDefault().getStructTopic("heldAlgae", Pose3d.struct).publish();


   

    public algaeManipulator(){

        intakeSim= IntakeSimulation.InTheFrameIntake("Algae", SystemManager.swerve.getMapleSimDrive().get(), Meters.of(0.7), IntakeSimulation.IntakeSide.BACK, 1);
        
        intakeSim.addGamePieceToIntake();
    }

    @Override 
    public void periodic(){
        

        if (stopTrigger.getAsBoolean()){
            stop();
        }
        if (state==intakeState.intaking){
            intakeSim.startIntake();
        }
        else if (state==intakeState.outtaking){
            outtakeInternal();
        }
        

        if (hasPeice()){
            algaePose = SystemManager.getIntakePosit();
        }
        else{
            algaePose = new Pose3d(-1000, -1000, -1000, new Rotation3d());
        }
        heldAlgaePublisher.set(algaePose);
    }

    // public void intake(){
    //     intakeSim.startIntake();
    //     isIntaking=true;
    //     checkClock=clock;

    // }
    @Override
    public boolean hasPeice(){
        return intakeSim.getGamePiecesAmount()==1;
    }


    public void outtakeInternal(){
        if (hasPeice()){

            intakeSim.obtainGamePieceFromIntake();
            SimulatedArena.getInstance()
            .addGamePieceProjectile(new ReefscapeAlgaeOnFly(
                // Obtain robot position from drive simulation
                SystemManager.getRealPoseMaple().getTranslation(),
                // The scoring mechanism is installed at (0.46, 0) (meters) on the robot
                new Translation2d(0, 0),
                // Obtain robot speed from drive simulation
                SystemManager.swerve.getFieldVelocity(),
                // Obtain robot facing from drive simulation
                SystemManager.getRealPoseMaple().getRotation(),
                // The height at which the coral is ejected
                Meters.of(3),
                // The initial speed of the coral
                MetersPerSecond.of(5),
                // The coral is ejected at a 35-degree slope
                Degrees.of(90)));
            
        }
    }

    

    @Override
    public void stop(){
        state=intakeState.resting;
        stopTrigger=()->true;
    }

}
