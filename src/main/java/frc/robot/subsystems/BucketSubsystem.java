// Copyright (c) two falures at 1799.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.Constants;



public class BucketSubsystem extends SubsystemBase {

  DoubleSolenoid BucketSolenoid = null;

  /** Creates a new pnumatics. */
  public BucketSubsystem() {
    super();
    BucketSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.BUCKET_SOLENOID_1, Constants.BUCKET_SOLENOID_2);
    // I think I need to set an initial position idk if this is the right place
    BucketSolenoid.set(kReverse);
   

    addChild("BucketDump", BucketSolenoid);
  }
  public void bucketToggle(){
    BucketSolenoid.toggle();


  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
