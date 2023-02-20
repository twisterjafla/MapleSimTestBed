package frc.robot.subsystems;



import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.Constants;

public class BucketSubsystem extends SubsystemBase {


  DoubleSolenoid BucketSolenoid= new DoubleSolenoid(
    PneumaticsModuleType.REVPH,
    Constants.bucket.solenoid.fwdPort,
    Constants.bucket.solenoid.revPort
  );

  
  /** Creates a new pnumatics. */
  public BucketSubsystem(Pneumatics pneumatics) {
    bucketSolenoid = pneumatics.makeDoubleSolenoid(
      Constants.BUCKET_SOLENOID_1, 
      Constants.BUCKET_SOLENOID_2
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