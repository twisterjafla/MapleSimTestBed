package frc.robot.subsystems.elevator;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.wristElevatorControlManager;

public class realElevator  extends elevatorIO {
    

    public double elevatorHeartbeat=0;
    public double goal=0;

    protected TalonFX leftMotor = new TalonFX(Constants.elevatorConstants.leftMotorID);
    protected TalonFX rightMotor = new TalonFX(Constants.elevatorConstants.rightMotorID);
    protected PIDController elevatorPid = new PIDController(Constants.elevatorConstants.elevatorPID.kP, Constants.elevatorConstants.elevatorPID.kI, Constants.elevatorConstants.elevatorPID.kD);

     // create a Motion Magic request, voltage output
     final MotionMagicVoltage motionVoltage = new MotionMagicVoltage(0);



 
    public realElevator(){
        
        leftMotor.getConfigurator().apply(Constants.elevatorConstants.slot0Configs);
        rightMotor.getConfigurator().apply(Constants.elevatorConstants.slot0Configs);
        
        rightMotor.setControl(new Follower(Constants.elevatorConstants.leftMotorID, true));
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

        SmartDashboard.putNumber("elevatorHeartbeat", elevatorHeartbeat++);



       
        // set target position to 100 rotations

        elevatorPid.setSetpoint(goal);
        SmartDashboard.putNumber("elevator goal", goal);
        SmartDashboard.putNumber("elevatorPose", getHeight());
        double speed = elevatorPid.calculate(getHeight());
        SmartDashboard.putNumber("elevator speed", speed);
        leftMotor.set(speed);

        //rightMotor.setControl(motionVoltage.withPosition(goal));
        
        updateRender();


    }

    @Override
    public double getEncoderVal() {
        SmartDashboard.putNumber("elevator test", 110);
        return leftMotor.getPosition().getValueAsDouble();
    }
}