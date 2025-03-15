package frc.robot.subsystems.wrist;

import com.ctre.phoenix6.hardware.core.CoreCANcoder;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.trajectory.TrapezoidProfile.State;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.subsystems.wristElevatorControlManager;
import frc.robot.subsystems.CoralGUI.coralGUI;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;


public class realWrist extends wristIO{


    
    private double goal;

    protected CoreCANcoder wristEncoder = new CoreCANcoder(Constants.wristConstants.CANCoderID);
    protected SparkFlex wristMotor = new SparkFlex(Constants.wristConstants.motorID, MotorType.kBrushless);
 
    TrapezoidProfile.Constraints constraints = new TrapezoidProfile.Constraints(Constants.wristConstants.maxVel, Constants.wristConstants.maxAccel);

    TrapezoidProfile.State previousProfiledReference = new TrapezoidProfile.State(getCurrentLocation(), 0.0);
    
    TrapezoidProfile profile = new TrapezoidProfile(constraints);



    public realWrist(){
       
            

        
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

        previousProfiledReference = profile.calculate(0.02, previousProfiledReference, new State(goal, 0));

        double speed = previousProfiledReference.velocity;
        
        SmartDashboard.putNumber("Wrist goal", goal);
        SmartDashboard.putNumber("Wrist location", getCurrentLocation());
        
        SmartDashboard.putNumber("wristSpeed", speed);
        SmartDashboard.putNumber("wristError", Math.abs(goal-getCurrentLocation()));
        wristMotor.set(speed);
        
    }

    @Override
    public double getCurrentLocation() {
        return (-wristEncoder.getAbsolutePosition().getValue().in(edu.wpi.first.units.Units.Degrees)-Constants.wristConstants.CANCoderOffset)%360;
    }

}
