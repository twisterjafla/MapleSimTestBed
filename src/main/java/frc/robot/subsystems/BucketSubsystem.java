package frc.robot.subsystems;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.Constants;

public class BucketSubsystem extends SubsystemBase {

  DoubleSolenoid BucketSolenoid =null;
  

  /** Creates a new pnumatics. */
  public BucketSubsystem() {
    BucketSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH,Constants.BUCKET_SOLENOID_1,Constants.BUCKET_SOLENOID_2);

    // I think I need to set an initial position idk if this is the right place
    BucketSolenoid.set(kReverse);

    addChild("BucketDump", BucketSolenoid);
  }
  public void bucketToggle(){
    BucketSolenoid.toggle();
  }
//May need to add something here, but I don't think that it is nessesary
}