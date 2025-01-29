package frc.robot.subsystems.intake;
import java.util.function.BooleanSupplier;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants;
import frc.robot.SystemManager;
import frc.robot.Utils.scoringPosit;
import frc.robot.subsystems.intake.intakeInterface;
import frc.robot.subsystems.intake.simIntake.intakeState;

public class Intake extends SubsystemBase implements intakeInterface{ 


	intakeState state;
	CANSparkMax IntakeLeader = new CANSparkMax(Constants.intakeConstants.LeftIntake, MotorType.kBrushless);
	CANSparkMax IntakeFollow = new CANSparkMax(Constants.intakeConstants.RightIntake, MotorType.kBrushless);

	BooleanSupplier stopTrigger=()->{return false;};

	public void InitIntake() {
		IntakeFollow.follow(IntakeLeader);
		IntakeFollow.setInverted(true);
		IntakeLeader.setInverted(false);
	}

	@Override
	public void intake() {
		intakeUntil(()->hasPeice());
	}
	
	@Deprecated
	@Override
	public boolean hasPeice() {
		return true;
	}
	
	@Override
	public void intakeUntil(BooleanSupplier trigger) {
		state=intakeState.intaking;
		stopTrigger=trigger;
	}


	@Override
	public void outtake() {
		outtakeUntil(()->!hasPeice());
	}

	@Override
	public void outtakeUntil(BooleanSupplier trigger) {
		state=intakeState.outtaking;
        stopTrigger=trigger;
	}

	@Override
	public void periodic(){
		if (stopTrigger.getAsBoolean()){
		    stop();					   // Stop Intake Motor
		}
		if (state == intakeState.intaking){
		    IntakeLeader.set(Constants.intakeConstants.intakeSpeed); // Start Intake Motor
		}
		else if (state == intakeState.outtaking){
		    IntakeLeader.set(Constants.intakeConstants.outtakeSpeed); // Start OutTake Motor
			outtake();
		}
	}
	
	@Override
	public void stop() {
		IntakeLeader.stopMotor();
	}

	@Override
	public void reset() {
		stop();
		state = intakeState.resting;
	}

	@Override
	public intakeState getState() {
		return state;
	}

	@Override
	public Translation3d getTranslation() {
		Rotation2d rotation = SystemManager.wrist.getcurrentLocation();
        return new Translation3d(Math.cos(rotation.getRadians())*Constants.intakeConstants.intakeLength, 0 ,Math.sin(rotation.getRadians())*Constants.intakeConstants.intakeLength).plus(SystemManager.elevator.getTranslation());
	}
}