package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class DriveBase extends SubsystemBase {

  final MotorControllerGroup leftMotors = new MotorControllerGroup(
      new CANSparkMax(Constants.drive.lt, MotorType.kBrushless),
      new CANSparkMax(Constants.drive.lr, MotorType.kBrushless),
      new CANSparkMax(Constants.drive.lf, MotorType.kBrushless)
      );

  final MotorControllerGroup rightMotors = new MotorControllerGroup(
      new CANSparkMax(Constants.drive.rt, MotorType.kBrushless),
      new CANSparkMax(Constants.drive.rr, MotorType.kBrushless),
      new CANSparkMax(Constants.drive.rf, MotorType.kBrushless)
      );

  final DifferentialDrive m_RobotDrive;

  public DriveBase() {
    //leftMotors.setInverted(true);
    //m_RobotDrive = new DifferentialDrive(rightMotors, leftMotors)
    m_RobotDrive = new DifferentialDrive(leftMotors, rightMotors);

    addChild("Drive", m_RobotDrive);
    
  }

  public void drive(final double ySpeed, final double rotateValue) {
    m_RobotDrive.arcadeDrive(ySpeed, rotateValue);
  }
}
