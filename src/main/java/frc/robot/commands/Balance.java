// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
//

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Constants;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Gyro;

public class Balance extends RunCommand {

	private static final PIDController pid = new PIDController(
		Constants.auto.balancePID.kP,
		Constants.auto.balancePID.kI,
		Constants.auto.balancePID.kD
	);

	/** Creates a new BalanceRobot. */
	public Balance(DriveBase drive, Gyro gyro) {
		super(
			//drive on execute of command
			()->drive.drive(
				MathUtil.clamp(
					pid.calculate(gyro.getRoll()), 
					Constants.auto.balancePID.outputMin,
					Constants.auto.balancePID.outputMax
				)/3.5,
				0
			)
		
		);

		pid.setTolerance(
			Constants.auto.balancePID.positionTolerance,
			Constants.auto.balancePID.velocityTolerance
		);
		pid.setSetpoint(0);
	}
}