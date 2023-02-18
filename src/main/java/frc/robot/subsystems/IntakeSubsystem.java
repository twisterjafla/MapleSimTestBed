/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

//import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
//import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
//import frc.robot.Constants;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


//Spark Max's
//import com.revrobotics.CANSparkMax;
//import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class IntakeSubsystem extends SubsystemBase {
  
  private final CANSparkMax intakeMotor1 = new CANSparkMax(Constants.INTAKE_MOTOR1,MotorType.kBrushless);
  private final CANSparkMax intakeMotor2 = new CANSparkMax(Constants.INTAKE_MOTOR2, MotorType.kBrushless);

  DoubleSolenoid intakeSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.INTAKE_SOLENOID_1, Constants.INTAKE_SOLENOID_2);
  //MotorControllerGroup m_LeftMotorGroup = new MotorControllerGroup(intakeMotor1, intakeMotor2);
  
  public IntakeSubsystem() {
 
    //super();
    //addChild("Intake", (Sendable) intakeMotor);
    //addChild("Indexer", (Sendable) indexerMotor);
    //intakeMotor.setInverted(true);
    intakeSolenoid.set(kReverse);
  }

  public void toggleIntakepiston(){
    intakeSolenoid.toggle();

  }

  public void intakeCargo(double speed) {
    //intakeMotor1.setInverted(true);

    intakeMotor1.set(speed);
    intakeMotor2.set(speed);

  }

  public void indexCargo(double speed) {
    //indexerMotor.set(speed);
  }

}
