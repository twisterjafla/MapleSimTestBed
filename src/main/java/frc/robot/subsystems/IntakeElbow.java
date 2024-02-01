package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeElbow extends SubsystemBase {
	public final CANSparkMax intakeMotorLeft = new CANSparkMax(Constants.intakeElbow.intakeCherrio.intakeMotorPortLeft, MotorType.kBrushless);
	public final CANSparkMax intakeMotorRight = new CANSparkMax(Constants.intakeElbow.intakeCherrio.intakeMotorPortRight, MotorType.kBrushless);
	public final MotorControllerGroup intakeMotors = new MotorControllerGroup(intakeMotorLeft, intakeMotorRight);

    public final CANSparkMax raiseMotor = new CANSparkMax(Constants.intakeElbow.raisingIntake.raisingMotorPort, MotorType.kBrushless);

	public final limitSwitch topSwitch = new limitSwitch(Constants.elevator.topLimitSwitch);
	public final limitSwitch bottomSwitch = new limitSwitch(Constants.elevator.bottomLimitSwitch);
	


	public void intake(double speed){
    	intakeMotors.set(speed);
  	}

    public void raiseIntake(double speed){

    }

}


