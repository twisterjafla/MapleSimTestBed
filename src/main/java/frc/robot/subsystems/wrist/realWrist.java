package frc.robot.subsystems.wrist;

import com.ctre.phoenix6.hardware.core.CoreCANcoder;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.subsystems.wristElevatorControlManager;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;


public class realWrist extends wristIO{


    
    private double goal;

    
    protected SparkFlex wristMotor = new SparkFlex(Constants.wristConstants.motorID, MotorType.kBrushless);
    protected SparkAbsoluteEncoder wristEncoder;
    protected PIDController wristPID = new PIDController(Constants.wristConstants.wristPID.kP, Constants.wristConstants.wristPID.kI, Constants.wristConstants.wristPID.kD);


    public realWrist(){
        wristPID.setTolerance(Constants.wristConstants.tolerence);
        SparkMax sparkMax= new SparkMax(Constants.wristConstants.throughBoreID, MotorType.kBrushless);
        wristEncoder = sparkMax.getAbsoluteEncoder();
        sparkMax.close();
        
        
    }

    @Override
    public void periodic(){

        if (wristElevatorControlManager.getState()==wristElevatorControlManager.wristElevatorControllState.wrist||
            wristElevatorControlManager.getState()==wristElevatorControlManager.wristElevatorControllState.resting){     
            goal=setpoint;
        }

        else{
            goal=Constants.wristConstants.restingPosit.getDegrees();
        }

        wristPID.setSetpoint(goal);
        SmartDashboard.putNumber("Wrist goal", goal);
        SmartDashboard.putNumber("Wrist location", getCurrentLocation());
        double speed = wristPID.calculate(getCurrentLocation());
        // if (!isAtSetpoint()){
        speed = speed + Constants.wristConstants.fConstant*Math.signum(speed);
        // }
        SmartDashboard.putNumber("wristSpeed", speed);
        SmartDashboard.putNumber("wristError", Math.abs(goal-getCurrentLocation()));

        SmartDashboard.putNumber("wristEncoderRaw", wristEncoder.getPosition());
        wristMotor.set(speed);
        
    }

    @Override
    public double getCurrentLocation() {
        return (wristEncoder.getPosition() + Constants.wristConstants.CANCoderOffset)%1*360;
    }

}