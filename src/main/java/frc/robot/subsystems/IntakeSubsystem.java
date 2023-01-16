/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

//Spark Max's
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class IntakeSubsystem extends SubsystemBase {
  
  private final CANSparkMax intakeMotor = new CANSparkMax(Constants.INTAKE_MOTOR,MotorType.kBrushless);
  private final CANSparkMax indexerMotor = new CANSparkMax(Constants.INTAKE_MOTOR,MotorType.kBrushless);

  public IntakeSubsystem() {
    super();
    addChild("Intake", (Sendable) intakeMotor);
    addChild("Indexer", (Sendable) indexerMotor);
    intakeMotor.setInverted(true);
  }

  public void intakeCargo(double speed) {
    intakeMotor.set(speed);
  }

  public void indexCargo(double speed) {
    indexerMotor.set(speed);
  }

}
