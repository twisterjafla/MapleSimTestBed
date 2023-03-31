/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.RunCommand;
//import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class RunIntake extends RunCommand {

  private final Intake intake;

  public RunIntake(Intake intake, double speed) {
    super(
      ()->{
        intake.intakeCargo(speed);
      },
     intake
     );
     this.intake = intake;
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    intake.intakeCargo(0);
  }
}
