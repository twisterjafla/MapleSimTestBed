package frc.robot.subsystems;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.Constants;

public class BucketSubsystem extends SubsystemBase {

  // no need to set this to null, bozo
  DoubleSolenoid BucketSolenoid;

  /** Creates a new pnumatics. */
  public BucketSubsystem() {
    //you don't need a super constructor
    super();
    //follow naming conventions please
    BucketSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.BUCKET_SOLENOID_1, Constants.BUCKET_SOLENOID_2);
    
    // I think I need to set an initial position idk if this is the right place
    BucketSolenoid.set(kReverse);

    addChild("BucketDump", BucketSolenoid);
  }
  public void bucketToggle(){
    BucketSolenoid.toggle();
  }

  // stop creating and overriding methods that dont exist
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}