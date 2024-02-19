package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class WristIntake extends SubsystemBase {
	public final CANSparkMax wristMotor = new CANSparkMax(Constants.wrist.ports.motorPort, MotorType.kBrushless);

    public final limitSwitch wristLimitSwitch = new limitSwitch(Constants.wrist.ports.encoderLimitSwitch);

    public final RelativeEncoder wristEncoder = wristMotor.getEncoder();

	public void move(double speed){
    	wristMotor.set(speed);
  	}

    public void resetEncoder(){
        wristEncoder.setPosition(0);
    }

    public double getEncoder(){
        return wristEncoder.getPosition();
    }

    @Override
    public void periodic() {
        if (wristLimitSwitch.getVal()){
            resetEncoder();
        }
    }
}


