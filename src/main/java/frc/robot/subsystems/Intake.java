package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Intake extends SubsystemBase {
  

  CANSparkMax intakeMotor1 = new CANSparkMax(Constants.intake.motor1,MotorType.kBrushless);
  CANSparkMax intakeMotor2 = new CANSparkMax(Constants.intake.motor2, MotorType.kBrushless);
  DoubleSolenoid intakeSolenoid;


  MotorControllerGroup intakeMotors;
  
  public Intake(Pneumatics pneumatics) {

    intakeSolenoid = pneumatics.makeDoubleSolenoid(
      Constants.intake.solenoid.fwdPort, 
      Constants.intake.solenoid.revPort
    );

    intakeMotor1.setInverted(true);
    intakeMotor1.setSmartCurrentLimit(40, 25);
    intakeMotor2.setSmartCurrentLimit(40, 25);
    intakeMotors = new MotorControllerGroup(intakeMotor1, intakeMotor2);
    
    intakeSolenoid.set(DoubleSolenoid.Value.kForward);
  }

  public void toggleIntakepiston(){
    intakeSolenoid.toggle();
  }

  public void intakeCargo(double speed) {
    intakeMotor1.set(speed);
    intakeMotor2.set(-speed);

  }

  public void stopMotors(){
    intakeMotors.stopMotor();
  }

  public void set(DoubleSolenoid.Value val){
    intakeSolenoid.set(val);
  }

}
