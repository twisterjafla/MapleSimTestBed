package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
	public final CANSparkMax intakeMotorLeft = new CANSparkMax(Constants.intake.intakeNote.intakeMotorPortLeft, MotorType.kBrushless);
	public final CANSparkMax intakeMotorRight = new CANSparkMax(Constants.intake.intakeNote.intakeMotorPortRight, MotorType.kBrushless);
	public final MotorControllerGroup intakeMotors = new MotorControllerGroup(intakeMotorLeft, intakeMotorRight);

    public final CANSparkMax raiseMotor = new CANSparkMax(Constants.intake.raisingIntake.raisingMotorPort, MotorType.kBrushless);

	public final limitSwitch topSwitch = new limitSwitch(Constants.intake.raisingIntake.topLimitSwitch);
	public final limitSwitch bottomSwitch = new limitSwitch(Constants.intake.raisingIntake.bottomLimitSwitch);
	


	public void intake(double speed){
    	intakeMotors.set(speed);
  	}

    public void raiseIntake(double speed){
		raiseMotor.set(speed);
    }

}


