
package frc.robot.autoRoutines;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
//import frc.robot.Constants;
import frc.robot.subsystems.Bucket;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Intake;
//simport frc.robot.subsystems.Gyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class AutonomousDumpDoNothing extends SequentialCommandGroup {
  /*
   * pseudoCode:
   * humans will position robot
   * milk crate will tip, releasing cube into lowest zone
   * robot drives forward, getting more auto points
   */

  // Subsystem to Dump Cargo then go forward over charge station
  // and then back up onto charge system to attempt balance
  public AutonomousDumpDoNothing(DriveBase drive, Intake intake, Bucket bucket) {
    super(
      new WaitCommand(1),
      //dump game piece
      new InstantCommand(
        ()->bucket.set(DoubleSolenoid.Value.kForward),
        bucket
      ),
      new WaitCommand(.7),
      //pick up milk crate
      new InstantCommand(
        ()->bucket.set(DoubleSolenoid.Value.kReverse),
        bucket
      ),
      new WaitCommand(.7),
      new InstantCommand(
        ()->bucket.set(DoubleSolenoid.Value.kForward),
        bucket
      )
    );
  }
}