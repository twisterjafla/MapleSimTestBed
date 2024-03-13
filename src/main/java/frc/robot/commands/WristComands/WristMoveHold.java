/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.WristComands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.WristIntake;

public class WristMoveHold extends Command {
  WristIntake wrist;
  double setpoint;
  double PI=Math.PI;

  private final PIDController pid = new PIDController(
        Constants.wrist.kp,
        Constants.wrist.ki,
        Constants.wrist.kd
  );
  /**
   * moves the wrist to the inputed place in degrees
   * 
   * @param wrist the wrist to be moved
   * @param setpoint the setpoint to be moved to in degrees
   */
  public WristMoveHold(WristIntake wrist, double setpoint) {
    this.wrist = wrist;
    this.setpoint=setpoint;
    
    addRequirements(wrist);
    pid.enableContinuousInput(-PI, PI);


  }

  @Override
  public void initialize() {
    pid.setSetpoint(setpoint);
    pid.setTolerance(Constants.wrist.tolerance);
    SmartDashboard.putString("wrist", "holding");

  }


  @Override 
  public void execute() {
    if (pid.atSetpoint()){
      wrist.move(0);
      pid.calculate(Math.toRadians(wrist.getEncoder()));
    }
    else{
      wrist.move(MathUtil.clamp(pid.calculate(Math.toRadians(wrist.getEncoder())), -Constants.wrist.motorSpeeds.maxSpeed, Constants.wrist.motorSpeeds.maxSpeed));
    }
  }

  @Override
  public void end(boolean interrupted){
    wrist.stop();
  }

}
