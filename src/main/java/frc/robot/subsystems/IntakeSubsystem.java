package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class IntakeSubsystem extends SubsystemBase {
  
  CANSparkMax intakeMotor1 = new CANSparkMax(Constants.INTAKE_MOTOR1,MotorType.kBrushless);
  CANSparkMax intakeMotor2 = new CANSparkMax(Constants.INTAKE_MOTOR2, MotorType.kBrushless);
  DoubleSolenoid intakeSolenoid;
  MotorControllerGroup intakeMotors;
  
  public IntakeSubsystem(Pneumatics pneumatics) {

    intakeSolenoid = pneumatics.makeDoubleSolenoid(
      Constants.INTAKE_SOLENOID_1, 
      Constants.INTAKE_SOLENOID_2
    );

    intakeMotor1.setInverted(true);
    intakeMotors = new MotorControllerGroup(intakeMotor1, intakeMotor2);

    intakeSolenoid.set(kReverse);
  }

  public void toggleIntakepiston(){
    intakeSolenoid.toggle();
  }

  public void intakeCargo(double speed) {
    intakeMotors.set(speed);
  }

  public void indexCargo(double speed) {
    //indexerMotor.set(speed);
  }
}
