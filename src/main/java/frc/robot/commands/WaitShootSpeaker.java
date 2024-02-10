package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.SpeakerShooter;

public class WaitShootSpeaker extends SequentialCommandGroup  {

  public WaitShootSpeaker(SpeakerShooter shooter, double time) {
    super(
      new WaitCommand(2),
      //dump game piece
      new InstantCommand(
        ()->shooter.revving(),
        shooter
      ),
      new WaitCommand(Constants.speakerShooter.RevTimeCountInTicks),
      //pick up milk crate
      new InstantCommand(
        ()->shooter.SafetyFunction(Constants.speakerShooter.motorSpeeds.bottomMotorSpeed, Constants.speakerShooter.motorSpeeds.topMotorSpeed),
        shooter
      )
    );

  }

}