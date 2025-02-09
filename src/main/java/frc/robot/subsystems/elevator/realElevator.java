package frc.robot.subsystems.elevator;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.SystemManager;
import frc.robot.Constants.elevatorConstants;
import frc.robot.Utils.warningManager;
import frc.robot.subsystems.wristElevatorControllManager;

public class realElevator  extends SubsystemBase implements elevatorInterface{
    
    public double setpoint=0;
    public double position=0;
    protected double goal=0;
    
    
    protected TalonFX leftMotor = new TalonFX(Constants.elevatorConstants.leftMotorID);
    protected TalonFX rightMotor = new TalonFX(Constants.elevatorConstants.rightMotorID);

     // create a Motion Magic request, voltage output
     final MotionMagicVoltage motionVoltage = new MotionMagicVoltage(0);



    @Deprecated
    public realElevator(){
        
        leftMotor.getConfigurator().apply(Constants.elevatorConstants.slot0Configs);
        rightMotor.getConfigurator().apply(Constants.elevatorConstants.slot0Configs);
        
        leftMotor.setControl(new Follower(Constants.elevatorConstants.rightMotorID, true));
    }

    @Override
    public void setSetpointRaw(double setpoint) {
        this.setpoint=setpoint;
    }

    @Override
    public boolean isAtSetpoint() {
        return Math.abs(setpoint-getEncoderVal())<Constants.elevatorConstants.tolerence;
    }

    @Override
    public Translation3d getTranslation() {
        return new Translation3d(getHeight()*Math.cos(Constants.elevatorConstants.angle.getRadians()), 0, getHeight()*Math.sin(Constants.elevatorConstants.angle.getRadians())).plus(Constants.elevatorConstants.fromRobotCenter);
    }

    @Deprecated
    @Override
    public double getEncoderVal() {
        return rightMotor.getPosition().getValueAsDouble();
    }

    @Override
    public void reset() {
        setpoint=0;
    }

    @Override
    public void setSetpoint(double setpoint) {
       setSetpointRaw(setpoint*Constants.elevatorConstants.encoderToMeters);
    }

    @Override
    public double getHeight() {
        return getEncoderVal()/Constants.elevatorConstants.encoderToMeters;
    }


    
    @Override
    public void periodic(){



        if (wristElevatorControllManager.getState()==wristElevatorControllManager.wristElevatorControllState.elevator||
            wristElevatorControllManager.getState()==wristElevatorControllManager.wristElevatorControllState.resting){
            goal=setpoint;
        }
        else{
            goal=Constants.elevatorConstants.maxHeight;
        }


       
        // set target position to 100 rotations
        rightMotor.setControl(motionVoltage.withPosition(goal));


    }

    @Override
    public boolean atLegalNonControlState(){
        return Math.abs(getHeight()-Constants.elevatorConstants.maxHeight)<Constants.elevatorConstants.tolerence;
    }



}