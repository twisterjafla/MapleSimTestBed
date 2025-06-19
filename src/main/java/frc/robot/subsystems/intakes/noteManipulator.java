package frc.robot.subsystems.intakes;

import static edu.wpi.first.units.Units.Degree;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Radian;

import java.util.function.BooleanSupplier;

import org.ironmaple.simulation.IntakeSimulation;
import org.ironmaple.simulation.SimulatedArena;
import org.ironmaple.simulation.seasonspecific.crescendo2024.CrescendoHumanPlayerSimulation;
import org.ironmaple.simulation.seasonspecific.crescendo2024.NoteOnFly;
import org.ironmaple.simulation.seasonspecific.reefscape2025.ReefscapeAlgaeOnFly;
import org.ironmaple.simulation.seasonspecific.reefscape2025.ReefscapeCoralOnFly;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.SystemManager;
import frc.robot.subsystems.intakes.intakeIO.intakeState;

public class noteManipulator extends SubsystemBase {
    IntakeSimulation intakeSim;
    


    protected intakeState state;
    BooleanSupplier stopTrigger=()->{return false;};



    /**sets the intake state to intaking untill a peice is intaked */
    public void intake(){
        intakeUntil(()->this.hasPeice());
    }

    /**
     * sets the intake state to intake untill trigger returns true 
     * @param trigger the supplier that will stop the intake when it returns true
    */
    public void intakeUntil(BooleanSupplier trigger){
        state=intakeState.intaking;
		stopTrigger=trigger;
    }

    /**sets the intake state to outtake until the peice is outtaked*/
    public void outtake(){
        outtakeUntil(()->!this.hasPeice());
    };

    /**
     * outtakes untill the trigger returns true
     * @param trigger the supplier that will stop the intake when it returns true
     */
    public void outtakeUntil(BooleanSupplier trigger){
        state=intakeState.outtaking;
        stopTrigger=trigger;
    }

    /**resets the intake */
    public void reset(){
        stop();
		state = intakeState.resting;
    }

    /**@return the state of the intake in the form of a intakeState */
    public intakeState getState(){
        return state;
    }

    /**gets a translation that represents the change from the 0,0 of the intake the point a coral would be */
    public Translation3d getTranslation(){
        
        Rotation2d rotation = SystemManager.wrist.getCurrentLocationR2D();
        return new Translation3d(
            Math.sin(-rotation.getRadians()+Math.toRadians(20))*Constants.intakeConstants.coralFromWristLen+Constants.intakeConstants.coralLenght/2,
            0,
            Math.cos(-rotation.getRadians()+Math.toRadians(20))*Constants.intakeConstants.coralFromWristLen)
            
            .plus(SystemManager.elevator.getTranslation());    
    }
   

    
    private Pose3d notePose = new Pose3d(-1000, -1000, -1000, new Rotation3d());
    StructPublisher<Pose3d> heldNotePublisher = NetworkTableInstance.getDefault().getStructTopic("heldNote", Pose3d.struct).publish();


   

    public noteManipulator(){

        intakeSim= IntakeSimulation.InTheFrameIntake("Note", SystemManager.swerve.getMapleSimDrive().get(), Meters.of(2), IntakeSimulation.IntakeSide.BACK, 1);
        
        //intakeSim.addGamePieceToIntake();
    }


    public void addPeice(){
        intakeSim.addGamePieceToIntake();
    }

    @Override 
    public void periodic(){
        SmartDashboard.putBoolean("isNoteIntaking", intakeSim.isRunning());

        if (stopTrigger.getAsBoolean()){
            stop();
        }
        if (state==intakeState.intaking){
            intakeSim.startIntake();
        }
   
        

        if (hasPeice()){
            notePose = new Pose3d(SystemManager.getRealPoseMaple()).plus(new Transform3d(0.4, 0, 0.6, new Rotation3d(0, Math.PI/3, 0)));
        }
        else{
            notePose = new Pose3d(-1000, -1000, -1000, new Rotation3d());
        }
        heldNotePublisher.set(notePose);
    }


    public boolean hasPeice(){
        return intakeSim.getGamePiecesAmount()==1;
    }


    public void shootAmp(){

    }

    public void shootSpeaker(){
            if (hasPeice()){
            intakeSim.obtainGamePieceFromIntake();
            
            SimulatedArena.getInstance()
            .addGamePieceProjectile(new NoteOnFly(
                // Obtain robot position from drive simulation
                SystemManager.getRealPoseMaple().getTranslation(),
                // The scoring mechanism is installed at (0.46, 0) (meters) on the robot
                new Translation2d(0.4, 0),
                // Obtain robot speed from drive simulation
                SystemManager.swerve.getFieldVelocity(),
                // Obtain robot facing from drive simulation
                SystemManager.getRealPoseMaple().getRotation(),
                // The height at which the coral is ejected
                Meters.of(0.4),
                // The initial speed of the coral
                MetersPerSecond.of(20),
                // The coral is ejected at a 35-degree slope
                Degree.of(60)).enableBecomeNoteOnFieldAfterTouchGround());
            }
    }

    


    public void stop(){
        state=intakeState.resting;
        stopTrigger=()->true;
    }
}
