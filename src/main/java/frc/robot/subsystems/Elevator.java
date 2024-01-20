package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import frc.robot.Constants;

public class Elevator {
	private final CANSparkMax elevatorMoterLeft = new CANSparkMax(Constants.elevator.motorLeft, MotorType.kBrushless);
	private final CANSparkMax elevatorMoterRight = new CANSparkMax(Constants.elevator.motorRight, MotorType.kBrushless);

	private final limitSwitch switchTop = new limitSwitch(Constants.elevator.topLimitSwitch);
	private final limitSwitch switchBottom = new limitSwitch(Constants.elevator.bottomLimitSwitch);

	private final MotorControllerGroup armMotors = new MotorControllerGroup(elevatorMoterLeft, elevatorMoterRight);

	public boolean isActive = false;
	public boolean isUp = false;
	public boolean isDown = false;

	public void move() {
		if (isUp && isActive) {
			if (switchTop.getVal()) {
				// We are going up and top limit is tripped so stop
				isDown = true;
				isUp = false;
				armMotors.set(0);
				
			}
			else {
				armMotors.set(Constants.elevator.elevatorUp);
			}
		}
		else if (switchBottom.getVal() && isDown){
			// We are going down and bottom limit is tripped so stop
		  
			armMotors.set(0);
			isGoingDown = false;
			isActive = false;
			
		}
		  else{
			armMotors.set(Constants.arm.ArmDown);
		}
		  }
		}
	}


