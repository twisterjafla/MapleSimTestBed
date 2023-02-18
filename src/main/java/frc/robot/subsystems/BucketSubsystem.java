package frc.robot.subsystems;



import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.Constants;

public class BucketSubsystem extends SubsystemBase {

  DoubleSolenoid BucketSolenoid= new DoubleSolenoid(PneumaticsModuleType.REVPH,Constants.BUCKET_SOLENOID_1,Constants.BUCKET_SOLENOID_2);
  

  /** Creates a new pnumatics. */
  public BucketSubsystem() {

    // I think I need to set an initial position idk if this is the right place
    BucketSolenoid.set(DoubleSolenoid.Value.kForward);

    addChild("BucketDump", BucketSolenoid);
  }


  public void bucketToggle(){
    BucketSolenoid.toggle();
  }

  public void set(DoubleSolenoid.Value val){
    BucketSolenoid.set(val);
  }
//May need to add something here, but I don't think that it is nessesary
}