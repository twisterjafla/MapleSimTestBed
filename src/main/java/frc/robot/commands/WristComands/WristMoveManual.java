/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.WristComands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.WristIntake;

public class WristMoveManual extends Command {
  WristIntake wrist;
  double speed;

  public WristMoveManual(WristIntake wrist, double speed) {
    this.wrist = wrist;
    this.speed=speed;

  }

  @Override 
  public void execute() {
    wrist.move(speed);
  }

  @Override
  public void end(boolean interrupted){
    wrist.stop();
  }

}
