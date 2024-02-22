package frc.robot.subsystems;

import com.fasterxml.jackson.annotation.JacksonInject.Value;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.MathUtil;;

public class Gyro extends SubsystemBase{
  
  // private final ADXRS450_Gyro gyro = new ADXRS450_Gyro();

  private final AHRS gyro = new AHRS();

  public Gyro(){
    gyro.reset();
    //addChild("Gyro", gyro);
    //gyro.calibrate();
  }

  public void reset() {
    gyro.reset();
  }

  public void log() {
    SmartDashboard.putNumber("Gyro", gyro.getYaw());
  }

  public double getHeading() {
    return gyro.getAngle();
  }

  public Rotation2d getRoll() {
    return new Rotation2d((double)gyro.getRoll());
  }

  public Rotation2d getYaw() {
    log();
    return new Rotation2d(Math.toRadians(gyro.getYaw()));
  }
}
