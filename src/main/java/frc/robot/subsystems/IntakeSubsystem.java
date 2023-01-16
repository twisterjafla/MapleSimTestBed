/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
  
  private final CANSparkMax intakeMotor = new CANSparkMax(Constants.INTAKE_MOTOR);

  public IntakeSubsystem() {
    super();
    addChild("Intake", intakeMotor);
    addChild("Indexer", indexerMotor);
    intakeMotor.setInverted(true);
  }

  public void intakeCargo(double speed) {
    intakeMotor.set(speed);
  }

  public void indexCargo(double speed) {
    indexerMotor.set(speed);
  }

}
