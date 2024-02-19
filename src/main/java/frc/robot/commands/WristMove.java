/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.WristIntake;

/**
 * Have the robot drive tank style.
 */
public class WristMove extends Command {

  WristIntake wrist;
  double setpoint;

  private final PIDController pid = new PIDController(
        Constants.auto.straightPID.kp,
        Constants.auto.straightPID.ki,
        Constants.auto.straightPID.kd
  );

  public WristMove(WristIntake wrist, double setpoint) {
    this.wrist = wrist;
    this.setpoint=setpoint;
    
    addRequirements(wrist);

  }

  @Override
  public void initialize() {
    wrist.resetEncoder();
    pid.setSetpoint(setpoint);
  }


  @Override
  public void execute() {
    wrist.move(pid.calculate(wrist.getEncoder()));
  }

  @Override
  public boolean isFinished() { 
    return pid.atSetpoint();

  } 

}
