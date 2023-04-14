package frc.robot.subsystems;



import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.Constants;

public class Bucket extends SubsystemBase {


  DoubleSolenoid bucketSolenoid;
  
  /** Creates a new pnumatics. */
  public Bucket(Pneumatics pneumatics) {
    bucketSolenoid = pneumatics.makeDoubleSolenoid(
      Constants.bucket.solenoid.fwdPort, 
      Constants.bucket.solenoid.revPort
    );

    bucketSolenoid.set(DoubleSolenoid.Value.kForward);
    addChild("BucketDump", bucketSolenoid);
  }

  public void bucketToggle(){
    bucketSolenoid.toggle();
  }

  public void set(DoubleSolenoid.Value val){
    bucketSolenoid.set(val);
  }
}