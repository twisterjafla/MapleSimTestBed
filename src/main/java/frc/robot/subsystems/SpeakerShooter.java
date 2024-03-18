package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SpeakerShooter extends SubsystemBase {
	public final CANSparkFlex flyWheel = new CANSparkFlex(Constants.speakerShooter.ports.topMotorPort, MotorType.kBrushless);
	public final CANSparkFlex indexer = new CANSparkFlex(Constants.speakerShooter.ports.bottomMotorPort, MotorType.kBrushless);
	public final RelativeEncoder topEncoder = flyWheel.getEncoder();
	public final RelativeEncoder bottomEncoder = indexer.getEncoder();


	public final limitSwitch beamBreak = new limitSwitch(Constants.speakerShooter.ports.beamBreakPort);
	

	public void revving(){
    	flyWheel.set(Constants.speakerShooter.motorSpeeds.topMotorSpeed);
  	}

	public void runIndex(){
        indexer.set(Constants.speakerShooter.motorSpeeds.bottomMotorSpeed);
  	}

    public boolean checkNote(){
        return beamBreak.getVal();
    }

    public void SafetyFunction(double indexSpeed, double flySpeed){
        indexer.set(indexSpeed);
        flyWheel.set(flySpeed);
    }


	public void intake(){
		flyWheel.set(Constants.speakerShooter.motorSpeeds.intakeSpeed);
		indexer.set(Constants.speakerShooter.motorSpeeds.intakeSpeed);
	}

	public void stop(){
		flyWheel.set(0);
		indexer.set(0);
	}

	public boolean canShoot(){
		return (topEncoder.getVelocity()>Constants.speakerShooter.minimumSpeed);

	}

	@Override
	public void periodic(){
		SmartDashboard.putBoolean("shooterBeamBreak", beamBreak.getVal());
	}




}