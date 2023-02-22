package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class IntakeSubsystem extends SubsystemBase {
  

  CANSparkMax intakeMotor1 = new CANSparkMax(Constants.intake.motor1,MotorType.kBrushless);
  CANSparkMax intakeMotor2 = new CANSparkMax(Constants.intake.motor2, MotorType.kBrushless);
  DoubleSolenoid intakeSolenoid = new DoubleSolenoid(
    PneumaticsModuleType.REVPH, 
    Constants.intake.solenoid.fwdPort, 
    Constants.intake.solenoid.revPort
  );

  MotorControllerGroup intakeMotors;
  
  public IntakeSubsystem(Pneumatics pneumatics) {

    intakeSolenoid = pneumatics.makeDoubleSolenoid(
      Constants.intake.solenoid.fwdPort, 
      Constants.intake.solenoid.revPort
    );

    intakeMotor1.setInverted(true);
    intakeMotors = new MotorControllerGroup(intakeMotor1, intakeMotor2);

    intakeSolenoid.set(DoubleSolenoid.Value.kReverse);
  }

  public void toggleIntakepiston(){
    intakeSolenoid.toggle();
  }

  public void intakeCargo(double speed) {
    intakeMotors.set(speed);
  }

  public void stopMotors(){
    intakeMotors.stopMotor();
  }

}
