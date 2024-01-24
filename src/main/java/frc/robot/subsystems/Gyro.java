package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Gyro extends SubsystemBase
{
  private final AHRS gyro = new AHRS();

  public Gyro(){

    addChild("Gyro", gyro);
  }

  public void reset() {
    gyro.reset();
  }

  public void log() {
    SmartDashboard.putNumber("Gyro", gyro.getAngle());
  }

  public double getHeading() {
    return gyro.getAngle();
  }

  public double getRoll() {
    return gyro.getRoll();
  }
}