package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SpeakerShooter extends SubsystemBase {
	public final CANSparkMax speakerMotorTop = new CANSparkMax(Constants.speakerShooter.ports.topMotorPort, MotorType.kBrushless);
	public final CANSparkMax speakerMotorBottom = new CANSparkMax(Constants.speakerShooter.ports.bottomMotorPort, MotorType.kBrushless);
	public final MotorControllerGroup intakeMotors = new MotorControllerGroup(speakerMotorTop, speakerMotorBottom);

	public final limitSwitch beamBreak = new limitSwitch(Constants.speakerShooter.ports.beamBreakPort);
	


	public void revving(){
    	speakerMotorTop.set(Constants.speakerShooter.motorSpeeds.topMotorSpeed);
  	}

	public void runIndex(){
        speakerMotorBottom.set(Constants.speakerShooter.motorSpeeds.bottomMotorSpeed);
  	}

    public boolean checkNote(){
        return beamBreak.getVal();
    }

    public void SafteyFunction(double indexSpeed, double flySpeed){
        speakerMotorBottom.set(indexSpeed);
        speakerMotorTop.set(flySpeed);
    }

}