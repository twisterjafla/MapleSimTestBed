/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
        Constants.wrist.kp,
        Constants.wrist.ki,
        Constants.wrist.kd
  );

  public WristMove(WristIntake wrist, double setpoint) {
    this.wrist = wrist;
    this.setpoint=setpoint;
    
    addRequirements(wrist);

  }

  @Override
  public void initialize() {
    //SmartDashboard.putNumber("setpoint", setpoint);
    pid.setSetpoint(setpoint);
    pid.setTolerance(Constants.wrist.tolerance);
  }


  @Override
  public void execute() {
    wrist.move(pid.calculate(wrist.getEncoder()));
    SmartDashboard.putNumber("wristPID", pid.calculate(wrist.getEncoder()));
    SmartDashboard.putNumber("Encoder Wrist Value.", wrist.getEncoder());
  }

  @Override
  public boolean isFinished() { 
    return pid.atSetpoint();

  } 

  @Override
  public void end(boolean interrupted){
    wrist.move(0);
  }

}
