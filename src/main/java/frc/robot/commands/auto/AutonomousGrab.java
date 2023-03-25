// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Bucket;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class AutonomousGrab extends SequentialCommandGroup {
  /** Creates a new AutonomousCommand. */
  DriveSubsystem drive;
  IntakeSubsystem intake;
  Bucket bucket;

  /*
   * pseudoCode:
   * 
   * humans will position robot
   * 
   * milk crate will tip, releasing cube into lowest zone
   * 
   * robot drives forward, getting more auto points
   * 
   * 
   */

  public AutonomousGrab(DriveSubsystem drive, IntakeSubsystem intake,Bucket bucket) {
    this.drive = drive;
    this.intake = intake;
    this.bucket = bucket;
    this.intake = intake;

    // Use addRequirements() here to declare subsystem dependencies.
    SmartDashboard.getNumber("Auto Selector", 0);

    addCommands(
    //   new WaitCommand(2),
    //    new WaitCommand(1),

        new InstantCommand(
         ()->{this.bucket.set(DoubleSolenoid.Value.kReverse);},
         this.bucket
       ),
       
       new WaitCommand(.5),

       new InstantCommand(
        ()->{this.bucket.set(DoubleSolenoid.Value.kForward);},
        this.bucket
      ),

      new WaitCommand(.5),
      

      new DriveStraight(drive, 2.7,Constants.auto.fwdSpeed),

      new InstantCommand(
        ()->{this.intake.set(DoubleSolenoid.Value.kReverse);},
        this.intake
      ),
      new InstantCommand(()->{
        this.intake.intakeCargo(Constants.intake.fwdSpeed);
        new DriveStraight(drive, 2.8, Constants.auto.fwdSpeed);

      }) 

      //new WaitCommand(1),

      // new InstantCommand(()->{
      //   this.intake.intakeCargo(0);
      // }),

      //new DriveStraight(drive, 2.7,Constants.auto.revSpeed)  
    );
  }
}
