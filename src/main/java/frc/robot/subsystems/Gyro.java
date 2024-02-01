package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Gyro extends SubsystemBase{
  
  // private final ADXRS450_Gyro gyro = new ADXRS450_Gyro();

  private final AHRS gyro = new AHRS();

  public Gyro(){
    
    addChild("Gyro", gyro);
    gyro.calibrate();
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

  public Rotation2d getRoll() {
    return new Rotation2d((double)gyro.getRoll());
  }
}
