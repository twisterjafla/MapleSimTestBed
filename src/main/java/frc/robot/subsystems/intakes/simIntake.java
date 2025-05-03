package frc.robot.subsystems.intakes;
     
import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Radian;

import java.util.function.BooleanSupplier;

import org.ironmaple.simulation.IntakeSimulation;
import org.ironmaple.simulation.SimulatedArena;
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

public class simIntake extends intakeIO{
    IntakeSimulation intakeSim;
    


    
    private Pose3d coralPose = new Pose3d(-1000, -1000, -1000, new Rotation3d());
    StructPublisher<Pose3d> heldCoralPublisher = NetworkTableInstance.getDefault().getStructTopic("heldCoral", Pose3d.struct).publish();


   

    public simIntake(){
        if (RobotBase.isReal()){
            intakeSim= IntakeSimulation.InTheFrameIntake("Coral", SystemManager.simButRealTrain, Meters.of(0.7), IntakeSimulation.IntakeSide.BACK, 1);
        }
        else{
            intakeSim= IntakeSimulation.InTheFrameIntake("Coral", SystemManager.swerve.getMapleSimDrive().get(), Meters.of(0.7), IntakeSimulation.IntakeSide.BACK, 1);
        }
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
        

        SmartDashboard.putBoolean("hasCoral", hasPeice());
        SmartDashboard.putBoolean("intakeIsRunning", state==intakeState.intaking);

        if (hasPeice()){
            coralPose = SystemManager.getIntakePosit();
        }
        else{
            coralPose = new Pose3d(-1000, -1000, -1000, new Rotation3d());
        }
        heldCoralPublisher.set(coralPose);
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
            if (SystemManager.elevator.atLegalNonControlState()){
                SimulatedArena.getInstance()
                .addGamePieceProjectile(new ReefscapeCoralOnFly(
                    // Obtain robot position from drive simulation
                    SystemManager.getRealPoseMaple().getTranslation(),
                    // The scoring mechanism is installed at (0.46, 0) (meters) on the robot
                    new Translation2d(getTranslation().getX()+Constants.sim.l4CoralDropCheatX, getTranslation().getY()),
                    // Obtain robot speed from drive simulation
                    SystemManager.swerve.getFieldVelocity(),
                    // Obtain robot facing from drive simulation
                    SystemManager.getRealPoseMaple().getRotation(),
                    // The height at which the coral is ejected
                    Meters.of(SystemManager.getIntakePosit().getZ()),
                    // The initial speed of the coral
                    MetersPerSecond.of(0),
                    // The coral is ejected at a 35-degree slope
                    Radian.of(-Math.PI/2)));
            }
            else{
                SimulatedArena.getInstance()
                .addGamePieceProjectile(new ReefscapeCoralOnFly(
                    // Obtain robot position from drive simulation
                    SystemManager.getRealPoseMaple().getTranslation(),
                    // The scoring mechanism is installed at (0.46, 0) (meters) on the robot
                    new Translation2d(getTranslation().getX(), getTranslation().getY()),
                    // Obtain robot speed from drive simulation
                    SystemManager.swerve.getFieldVelocity(),
                    // Obtain robot facing from drive simulation
                    SystemManager.getRealPoseMaple().getRotation(),
                    // The height at which the coral is ejected
                    Meters.of(SystemManager.getIntakePosit().getZ()),
                    // The initial speed of the coral
                    MetersPerSecond.of(2),
                    // The coral is ejected at a 35-degree slope
                    Degrees.of(-35)));
            }
        }
    }

   

    @Override
    public void stop(){
        state=intakeState.resting;
        stopTrigger=()->true;
    }

}
