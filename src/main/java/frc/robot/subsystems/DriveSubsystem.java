
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class DriveSubsystem extends SubsystemBase {
  // right motors
  private final CANSparkMax lefttop = new CANSparkMax(Constants.LEFT_TOP, MotorType.kBrushless);
  private final CANSparkMax leftbottomright = new CANSparkMax(Constants.LEFT_BACK, MotorType.kBrushless);
  private final CANSparkMax leftbottomleft = new CANSparkMax(Constants.LEFT_FRONT, MotorType.kBrushless);

  // left motors
  private final CANSparkMax righttop = new CANSparkMax(Constants.RIGHT_TOP, MotorType.kBrushless);
  private final CANSparkMax rightbottomright = new CANSparkMax(Constants.RIGHT_BACK, MotorType.kBrushless);
  private final CANSparkMax rightbottomleft = new CANSparkMax(Constants.RIGHT_FRONT, MotorType.kBrushless);

  final MotorControllerGroup m_LeftMotorGroup = new MotorControllerGroup(lefttop, leftbottomright, leftbottomleft);
  final MotorControllerGroup m_RightMotorGroup = new MotorControllerGroup(righttop, rightbottomright, rightbottomleft);

  private final DifferentialDrive m_RobotDrive;

  private final ADXRS450_Gyro m_gyro = new ADXRS450_Gyro();

  public DriveSubsystem() {
    m_LeftMotorGroup.setInverted(true);
    m_RobotDrive = new DifferentialDrive(m_LeftMotorGroup, m_RightMotorGroup);

    addChild("Drive", m_RobotDrive);
    addChild("Gyro", m_gyro);

    m_gyro.calibrate();

    reset();
  }

  public void drive(final double ySpeed, final double rotateValue) {
    m_RobotDrive.arcadeDrive(ySpeed, rotateValue);
  }

  public void drive(final double ySpeed, final double rotateValue, final double hDriveMotorSpeed) {
    m_RobotDrive.arcadeDrive(ySpeed, rotateValue);
  }

  public void reset() {
    m_gyro.reset();
  }

  public void log() {
    SmartDashboard.putNumber("Gyro", m_gyro.getAngle());
  }

  public double getHeading() {
    return m_gyro.getAngle();
  }

}
