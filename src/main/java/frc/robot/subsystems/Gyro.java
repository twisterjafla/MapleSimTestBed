package frc.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Gyro extends SubsystemBase{
  
  private final ADXRS450_Gyro gyro = new ADXRS450_Gyro();

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
}
