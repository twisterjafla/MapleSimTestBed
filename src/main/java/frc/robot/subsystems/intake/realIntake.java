package frc.robot.subsystems.intake;
import java.util.function.BooleanSupplier;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.SystemManager;
import frc.robot.subsystems.intake.simIntake.intakeState;

public class realIntake extends SubsystemBase implements intakeInterface{ 

	intakeState state;
	SparkMax intakeTop = new SparkMax(Constants.intakeConstants.LeftIntake, MotorType.kBrushless);
	SparkMax intakeBottom = new SparkMax(Constants.intakeConstants.RightIntake, MotorType.kBrushless);
	DigitalInput frontBeambrake = new DigitalInput(Constants.intakeConstants.frontBeamBrakePort);
	hasPeiceState peiceState=hasPeiceState.full;

	private enum hasPeiceState{
		intaking,
		full,
		empty,
		starting
	}

	BooleanSupplier stopTrigger=()->{return false;};

	public void InitIntake() {
		intakeBottom.setInverted(true);
		intakeTop.setInverted(false);
	}

	@Override
	public void intake() {
		intakeUntil(()->hasPeice());
	}
	
	@Deprecated
	@Override
	public boolean hasPeice() {
		return peiceState==hasPeiceState.full;
	}
	
	public void checkForPiece(){

		//starting
		if (peiceState==hasPeiceState.starting){
			if(frontBeambrake.get()){
				peiceState=hasPeiceState.full;
			}
			else{
				peiceState=hasPeiceState.empty;
			}
		}

		//empty
		else if (peiceState==hasPeiceState.empty){
			if (frontBeambrake.get()){
				peiceState=hasPeiceState.full;
			}
		}

		//intakeing
		//else if(peiceState==hasPeiceState.intaking){
		// 	if (!backBeambrake.get()){
		// 		if (frontBeambrake.get()){
		// 			peiceState=hasPeiceState.full;
		// 		}
		// 		else{
		// 			peiceState=hasPeiceState.empty;
		// 		}
		// 	}
		// }

		//full
		else if(peiceState==hasPeiceState.full){
			if(!frontBeambrake.get()){
				peiceState=hasPeiceState.empty;
			}
		}
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
		checkForPiece();
		if (stopTrigger.getAsBoolean()){
		    stop();					   // Stop Intake Motor
		}
		if (state == intakeState.intaking){
		    intakeTop.set(Constants.intakeConstants.intakeSpeed); // Start Intake Motor
			intakeBottom.set(Constants.intakeConstants.intakeSpeed);
		}
		else if (state == intakeState.outtaking){
			if (SystemManager.elevator.atLegalNonControlState()){
				intakeTop.set(-Constants.intakeConstants.outtakeSpeed); // Start OutTake Motor
				intakeBottom.set(Constants.intakeConstants.outtakeSpeed);
			}
			else{
				intakeTop.set(Constants.intakeConstants.outtakeSpeed); // Start OutTake Motor
				intakeBottom.set(-Constants.intakeConstants.outtakeSpeed);
			}
		}
		else{
			intakeTop.set(0);
			intakeBottom.set(0);
		}
	}
	
	@Override
	public void stop() {
		intakeTop.stopMotor();
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
		Rotation2d rotation = SystemManager.wrist.getCurrentLocationR2D();
        return new Translation3d(Math.cos(rotation.getRadians())*Constants.intakeConstants.intakeLength, 0 ,Math.sin(rotation.getRadians())*Constants.intakeConstants.intakeLength).plus(SystemManager.elevator.getTranslation());
	}
}