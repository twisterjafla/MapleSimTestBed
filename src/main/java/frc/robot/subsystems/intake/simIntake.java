package frc.robot.subsystems.intake;

import static edu.wpi.first.units.Units.Meters;

import java.util.function.BooleanSupplier;

import org.ironmaple.simulation.IntakeSimulation;


import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.SystemManager;

public class simIntake extends SubsystemBase implements intakeInterface{
    IntakeSimulation intakeSim;
    

    public static enum intakeState{
        intaking,
        outtaking,
        resting;
    }
    
    intakeState state;
    BooleanSupplier stopTrigger=()->{return false;};

    public simIntake(){
        intakeSim= IntakeSimulation.InTheFrameIntake("Coral", SystemManager.swerve.getMapleSimDrive().get(), Meters.of(0.7), IntakeSimulation.IntakeSide.BACK, 1);
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
        }
    }

    @Override
    public void intake(){
        intakeUntil(()->hasPeice());
        
    }
    
    @Override
    public void intakeUntil(BooleanSupplier trigger){
        state=intakeState.intaking;
        stopTrigger=trigger;
    }


    @Override
    public void outtake(){
        outtakeUntil(()->!hasPeice());

    }

    @Override
    public void outtakeUntil(BooleanSupplier trigger) {
        state=intakeState.outtaking;
        stopTrigger=trigger;
    }

    @Override
    public void stop(){
        state=intakeState.resting;
        stopTrigger=()->true;
    }

    @Override 
    public intakeState getState(){
        return state;
    }

    @Override 
    public void reset(){
        stop();
    }

    @Override
    public Translation3d getTranslation(){
        Rotation2d rotation = SystemManager.wrist.getcurrentLocation();
        return new Translation3d(Math.cos(rotation.getRadians())*Constants.intakeConstants.intakeLength, 0 ,Math.sin(rotation.getRadians())*Constants.intakeConstants.intakeLength).plus(SystemManager.elevator.getTranslation());
    }

}
