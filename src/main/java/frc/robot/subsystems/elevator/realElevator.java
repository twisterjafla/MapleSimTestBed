package frc.robot.subsystems.elevator;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.wristElevatorControlManager;

public class realElevator  extends elevatorIO {
    

    public double position=0;
    protected double goal=0;
    
    
    protected TalonFX leftMotor = new TalonFX(Constants.elevatorConstants.leftMotorID);
    protected TalonFX rightMotor = new TalonFX(Constants.elevatorConstants.rightMotorID);
    protected PIDController elevatorPid = new PIDController(Constants.elevatorConstants.elevatorPID.kP, Constants.elevatorConstants.elevatorPID.kI, Constants.elevatorConstants.elevatorPID.kD);

     // create a Motion Magic request, voltage output
     final MotionMagicVoltage motionVoltage = new MotionMagicVoltage(0);



 
    public realElevator(){
        
        leftMotor.getConfigurator().apply(Constants.elevatorConstants.slot0Configs);
        rightMotor.getConfigurator().apply(Constants.elevatorConstants.slot0Configs);
        
        leftMotor.setControl(new Follower(Constants.elevatorConstants.rightMotorID, true));
    }
    
    @Override
    public void periodic(){
        


        if (wristElevatorControlManager.getState()==wristElevatorControlManager.wristElevatorControllState.elevator||
            wristElevatorControlManager.getState()==wristElevatorControlManager.wristElevatorControllState.resting){
            goal=setpoint;
        }
        // quit if the wrist is having issues
        else if(wristElevatorControlManager.getState()==wristElevatorControlManager.wristElevatorControllState.fixWrist){
            return;
        }
        else{
            goal=Constants.elevatorConstants.maxHeight;
        }


       
        // set target position to 100 rotations

        elevatorPid.setSetpoint(goal);
        rightMotor.set(elevatorPid.calculate(getHeight())+Constants.elevatorConstants.g);

        //rightMotor.setControl(motionVoltage.withPosition(goal));
        
        updateRender();


    }

    @Override
    public double getEncoderVal() {
        return rightMotor.getPosition().getValueAsDouble();
    }
}