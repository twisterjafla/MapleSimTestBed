package frc.robot.subsystems;



import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.Constants;

public class BucketSubsystem extends SubsystemBase {


  DoubleSolenoid BucketSolenoid= new DoubleSolenoid(
    PneumaticsModuleType.REVPH,
    Constants.bucket.solenoid.fwdPort,
    Constants.bucket.solenoid.revPort
  );

  
  /** Creates a new pnumatics. */
  public BucketSubsystem(Pneumatics pneumatics) {
    BucketSolenoid = pneumatics.makeDoubleSolenoid(
      Constants.bucket.solenoid.fwdPort, 
      Constants.bucket.solenoid.revPort
    );

    BucketSolenoid.set(DoubleSolenoid.Value.kForward);
    addChild("BucketDump", BucketSolenoid);
  }

  public void bucketToggle(){
    BucketSolenoid.toggle();
  }

  public void set(DoubleSolenoid.Value val){
    BucketSolenoid.set(val);
  }
}