package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimbingArm extends SubsystemBase {
	public final CANSparkMax climbingArm = new CANSparkMax(Constants.climbingArm.motorPort, MotorType.kBrushless);

	public final limitSwitch switchRight = new limitSwitch(Constants.climbingArm.limitSwitchRight);
	public final limitSwitch switchLeft = new limitSwitch(Constants.climbingArm.limitSwitchLeft);
	


	public void armDown(){
    	climbingArm.set(Constants.climbingArm.armDownSpeed);
  	}
}


