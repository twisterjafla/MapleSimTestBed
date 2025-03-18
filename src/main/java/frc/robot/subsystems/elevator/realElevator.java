package frc.robot.subsystems.elevator;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.elevatorConstants;
import frc.robot.subsystems.wristElevatorControlManager;

public class realElevator  extends elevatorIO {
    

    public double elevatorHeartbeat=0;
    public double goal=0;

    protected TalonFX mainMotor = new TalonFX(Constants.elevatorConstants.rightMotorID);
    protected TalonFX altMotor = new TalonFX(Constants.elevatorConstants.leftMotorID);
    protected PIDController elevatorPid = new PIDController(Constants.elevatorConstants.elevatorPID.kP, Constants.elevatorConstants.elevatorPID.kI, Constants.elevatorConstants.elevatorPID.kD);

     // create a Motion Magic request, voltage output
     final MotionMagicVoltage motionVoltage = new MotionMagicVoltage(0);



 
    public realElevator(){
        
        mainMotor.getConfigurator().apply(Constants.elevatorConstants.slot0Configs);
        altMotor.getConfigurator().apply(Constants.elevatorConstants.slot0Configs);
        
        altMotor.setControl(new Follower(mainMotor.getDeviceID(), true));
        SmartDashboard.putBoolean("elevatorHasReset", false);
    }
    
    @Override
    public void periodic(){
        boolean speedOverride = false;

        SmartDashboard.putNumber("elevator stall torque", mainMotor.getTorqueCurrent().getValueAsDouble());
        if (elevatorConstants.shouldUseCurrentEncoderReset&&Math.abs(mainMotor.getVelocity().getValueAsDouble())<0.5){
            if (Math.abs(mainMotor.getTorqueCurrent().getValueAsDouble())>Constants.elevatorConstants.currentResetThreashold){
                SmartDashboard.putBoolean("elevatorHasReset", true);

                if (this.getHeight()<Constants.elevatorConstants.elevatorResetTolerence){
                    mainMotor.setPosition(0);

                }
                else if (Math.abs(this.getHeight()-Constants.elevatorConstants.l4EncoderVal)<Constants.elevatorConstants.elevatorResetTolerence){
                    mainMotor.setPosition(Constants.elevatorConstants.l4EncoderVal*Constants.elevatorConstants.encoderToMeters);
                }
                else{
                    return;
                }

            }
        }

        if (wristElevatorControlManager.getState()==wristElevatorControlManager.wristElevatorControllState.elevator||
            wristElevatorControlManager.getState()==wristElevatorControlManager.wristElevatorControllState.resting){
            goal=setpoint;
        }
        // quit if the wrist is having issues
        else if(wristElevatorControlManager.getState()==wristElevatorControlManager.wristElevatorControllState.fixWrist||wristElevatorControlManager.getState()==wristElevatorControlManager.wristElevatorControllState.wrist){
            speedOverride=true;
        }
        else{
            goal=Constants.elevatorConstants.maxHeight;
        }

        SmartDashboard.putNumber("elevatorHeartbeat", elevatorHeartbeat++);



       
        // set target position to 100 rotations

        elevatorPid.setSetpoint(goal);
        SmartDashboard.putNumber("elevator goal", goal);
        SmartDashboard.putNumber("elevatorPose", getHeight());
        double speed = speedOverride? 0: elevatorPid.calculate(getHeight());
    
        SmartDashboard.putNumber("Pre g speed", speed);
        if (getHeight()>0.2||!speedOverride){
            speed += Constants.elevatorConstants.g;
        }
        SmartDashboard.putNumber("elevator speed", speed);

        SmartDashboard.putBoolean("isAtSetpoint", isAtSetpoint());
        mainMotor.set(speed);

        //rightMotor.setControl(motionVoltage.withPosition(goal));
        
        updateRender();


    }

    @Override
    public double getEncoderVal() {
        SmartDashboard.putNumber("elevator test", 110);
        return mainMotor.getPosition().getValueAsDouble();
    }
}